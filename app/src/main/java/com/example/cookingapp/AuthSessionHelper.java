package com.example.cookingapp;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Đăng xuất: xóa token trên máy ngay lập tức và báo server cập nhật last_logout_at.
 * File mới, chỉ cần gọi 1 dòng từ màn hình Cài đặt, không đổi luồng chuyển màn hình.
 */
public final class AuthSessionHelper {

    private AuthSessionHelper() {
    }

    public static void logout(Context context) {
        Context appContext = context.getApplicationContext();
        String token = SessionManager.getAccessToken(appContext);

        // Xóa phiên trên máy trước; token được truyền thẳng vào header nên vẫn gọi được server
        SessionManager.clear(appContext);
        if (token == null || token.isEmpty()) {
            return;
        }

        ApiClient.getClient().create(ApiService.class)
                .logoutWithToken("Bearer " + token)
                .enqueue(new Callback<AuthModels.MessageResponse>() {
                    @Override
                    public void onResponse(Call<AuthModels.MessageResponse> call,
                                           Response<AuthModels.MessageResponse> response) {
                        // Không cần xử lý: người dùng đã được đăng xuất trên máy
                    }

                    @Override
                    public void onFailure(Call<AuthModels.MessageResponse> call, Throwable t) {
                        // Mất mạng thì chỉ không ghi được thời gian đăng xuất trên server
                    }
                });
    }
}