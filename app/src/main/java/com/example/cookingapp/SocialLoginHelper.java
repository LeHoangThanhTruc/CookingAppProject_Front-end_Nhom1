package com.example.cookingapp;

import android.app.Activity;
import android.os.CancellationSignal;

import androidx.core.content.ContextCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.NoCredentialException;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Đăng nhập bằng Google và Facebook.
 * App chỉ lấy token từ Google/Facebook rồi gửi cho backend; backend tự xác minh token.
 */
public final class SocialLoginHelper {

    public interface Listener {
        void onSuccess(AuthModels.TokenResponse response);

        void onError(String message);
    }

    private SocialLoginHelper() {
    }

    // ---------------- Google (Credential Manager) ----------------

    public static void loginWithGoogle(Activity activity, Listener listener) {
        try {
            String webClientId = activity.getString(R.string.google_web_client_id);
            GetSignInWithGoogleOption option =
                    new GetSignInWithGoogleOption.Builder(webClientId).build();
            GetCredentialRequest request = new GetCredentialRequest.Builder()
                    .addCredentialOption(option)
                    .build();

            CredentialManager.create(activity).getCredentialAsync(
                    activity,
                    request,
                    new CancellationSignal(),
                    ContextCompat.getMainExecutor(activity),
                    new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                        @Override
                        public void onResult(GetCredentialResponse result) {
                            Credential credential = result.getCredential();
                            if (credential instanceof CustomCredential
                                    && GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                                    .equals(credential.getType())) {
                                try {
                                    GoogleIdTokenCredential googleCredential =
                                            GoogleIdTokenCredential.createFrom(
                                                    ((CustomCredential) credential).getData());
                                    exchangeWithBackend("GOOGLE", googleCredential.getIdToken(), listener);
                                } catch (Exception error) {
                                    listener.onError("Không đọc được thông tin tài khoản Google");
                                }
                            } else {
                                listener.onError("Loại tài khoản Google không được hỗ trợ");
                            }
                        }

                        @Override
                        public void onError(GetCredentialException error) {
                            if (error instanceof GetCredentialCancellationException) {
                                listener.onError("Bạn đã hủy đăng nhập Google");
                            } else if (error instanceof NoCredentialException) {
                                listener.onError("Không tìm thấy tài khoản Google trên máy. "
                                        + "Hãy thêm tài khoản Google trong Cài đặt của máy rồi thử lại");
                            } else {
                                listener.onError("Đăng nhập Google thất bại: " + error.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            listener.onError("Lỗi khởi tạo đăng nhập Google: " + e.getMessage());
        }
    }

    // ---------------- Facebook ----------------

    /** Gọi một lần trong onCreate; giữ CallbackManager trả về để chuyển tiếp onActivityResult. */
    public static CallbackManager registerFacebookCallback(Listener listener) {
        try {
            CallbackManager callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            if (loginResult != null && loginResult.getAccessToken() != null) {
                                String accessToken = loginResult.getAccessToken().getToken();
                                // Đăng xuất khỏi SDK để lần sau cho chọn lại tài khoản
                                LoginManager.getInstance().logOut();
                                exchangeWithBackend("FACEBOOK", accessToken, listener);
                            } else {
                                listener.onError("Không lấy được token Facebook");
                            }
                        }

                        @Override
                        public void onCancel() {
                            listener.onError("Bạn đã hủy đăng nhập Facebook");
                        }

                        @Override
                        public void onError(FacebookException error) {
                            listener.onError("Đăng nhập Facebook thất bại: " + (error != null ? error.getMessage() : "Lỗi không xác định"));
                        }
                    });
            return callbackManager;
        } catch (Exception e) {
            return null;
        }
    }

    public static void startFacebookLogin(Activity activity) {
        try {
            LoginManager.getInstance().logInWithReadPermissions(
                    activity, Arrays.asList("public_profile", "email"));
        } catch (Exception e) {
            // Xử lý an toàn nếu SDK chưa mở được
        }
    }

    // ---------------- Gửi token cho backend ----------------

    private static void exchangeWithBackend(String provider, String token, Listener listener) {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.oauthLogin(new AuthModels.OAuthBody(provider, token))
                .enqueue(new Callback<AuthModels.TokenResponse>() {
                    @Override
                    public void onResponse(Call<AuthModels.TokenResponse> call,
                                           Response<AuthModels.TokenResponse> response) {
                        if (response.isSuccessful() && response.body() != null
                                && response.body().access_token != null) {
                            listener.onSuccess(response.body());
                        } else {
                            listener.onError(AuthModels.errorMessage(response));
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthModels.TokenResponse> call, Throwable t) {
                        listener.onError("Không kết nối được server. Kiểm tra backend đã chạy chưa.");
                    }
                });
    }
}
