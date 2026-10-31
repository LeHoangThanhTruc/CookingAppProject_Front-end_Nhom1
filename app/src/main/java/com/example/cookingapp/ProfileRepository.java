package com.example.cookingapp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class ProfileRepository {
    private static final int MAX_AVATAR_BYTES = 5 * 1024 * 1024;

    private static volatile ProfileRepository instance;

    private final Context appContext;
    private final ApiService apiService;
    private final ExecutorService fileExecutor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ProfileRepository(Context context) {
        appContext = context.getApplicationContext();
        apiService = ApiClient.getAuthenticatedClient(appContext).create(ApiService.class);
    }

    public static ProfileRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (ProfileRepository.class) {
                if (instance == null) {
                    instance = new ProfileRepository(context);
                }
            }
        }
        return instance;
    }

    public void getProfile(ApiCallback<Profile> callback) {
        enqueue(apiService.getMyProfile(), callback);
    }

    public void updateProfile(
            String fullName,
            String phoneNumber,
            String bio,
            ApiCallback<Profile> callback
    ) {
        enqueue(
                apiService.updateMyProfile(new UpdateProfileRequest(fullName, phoneNumber, bio)),
                callback
        );
    }

    public void uploadAvatar(Uri uri, ApiCallback<Profile> callback) {
        fileExecutor.execute(() -> {
            try {
                MultipartBody.Part part = buildAvatarPart(uri);
                mainHandler.post(() -> enqueue(apiService.uploadMyAvatar(part), callback));
            } catch (IOException | IllegalArgumentException error) {
                mainHandler.post(() -> callback.onError(new ApiError(0, error.getMessage())));
            }
        });
    }

    private MultipartBody.Part buildAvatarPart(Uri uri) throws IOException {
        ContentResolver resolver = appContext.getContentResolver();
        String contentType = resolver.getType(uri);
        if (contentType == null || !isSupportedImageType(contentType)) {
            throw new IllegalArgumentException("Vui lòng chọn ảnh JPEG, PNG hoặc WebP");
        }

        byte[] content;
        try (InputStream input = resolver.openInputStream(uri);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            if (input == null) {
                throw new IOException("Không thể đọc ảnh đã chọn");
            }
            byte[] buffer = new byte[8192];
            int total = 0;
            int read;
            while ((read = input.read(buffer)) != -1) {
                total += read;
                if (total > MAX_AVATAR_BYTES) {
                    throw new IllegalArgumentException("Ảnh đại diện không được vượt quá 5 MiB");
                }
                output.write(buffer, 0, read);
            }
            content = output.toByteArray();
        }

        String filename = queryFilename(resolver, uri);
        RequestBody body = RequestBody.create(MediaType.parse(contentType), content);
        return MultipartBody.Part.createFormData("file", filename, body);
    }

    private static boolean isSupportedImageType(String contentType) {
        String normalized = contentType.toLowerCase(Locale.ROOT);
        return normalized.equals("image/jpeg")
                || normalized.equals("image/jpg")
                || normalized.equals("image/png")
                || normalized.equals("image/webp");
    }

    private static String queryFilename(ContentResolver resolver, Uri uri) {
        try (Cursor cursor = resolver.query(
                uri,
                new String[]{OpenableColumns.DISPLAY_NAME},
                null,
                null,
                null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (index >= 0) {
                    String value = cursor.getString(index);
                    if (value != null && !value.trim().isEmpty()) {
                        return value;
                    }
                }
            }
        } catch (RuntimeException ignored) {
            // Một số ContentProvider không hỗ trợ truy vấn tên file.
        }
        return "avatar";
    }

    private static <T> void enqueue(Call<T> call, ApiCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> ignored, Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                    return;
                }
                callback.onError(ApiError.fromResponse(response));
            }

            @Override
            public void onFailure(Call<T> ignored, Throwable error) {
                callback.onError(new ApiError(
                        0,
                        "Không thể kết nối máy chủ. Vui lòng kiểm tra mạng và thử lại."
                ));
            }
        });
    }

    public interface ApiCallback<T> {
        void onSuccess(T value);

        void onError(ApiError error);
    }

    public static final class ApiError {
        private final int statusCode;
        private final String message;

        ApiError(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message == null || message.trim().isEmpty()
                    ? "Đã xảy ra lỗi. Vui lòng thử lại."
                    : message;
        }

        static ApiError fromResponse(Response<?> response) {
            String fallback = response.code() == 401
                    ? "Phiên đăng nhập đã hết hạn"
                    : "Máy chủ không thể xử lý yêu cầu";
            if (response.errorBody() == null) {
                return new ApiError(response.code(), fallback);
            }
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                Object detail = json.opt("detail");
                if (detail instanceof String) {
                    fallback = (String) detail;
                }
            } catch (Exception ignored) {
                // Dùng thông báo dự phòng nếu phản hồi lỗi không phải JSON chuẩn.
            }
            return new ApiError(response.code(), fallback);
        }

        public boolean isUnauthorized() {
            return statusCode == 401;
        }

        public String getMessage() {
            return message;
        }
    }
}
