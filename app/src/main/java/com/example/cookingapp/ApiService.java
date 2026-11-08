package com.example.cookingapp;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Header;

public interface ApiService {

    // 1. API tin tức & bài viết (Của bạn)
    @GET("api/posts/feed")
    Call<FeedResponse> getFeed(@Query("post_type") String postType);

    @POST("api/posts/{post_id}/share")
    Call<Void> sharePost(
            @Path("post_id") int postId,
            @Query("share_type") String shareType,
            @Query("caption") String caption
    );

    @POST("api/posts/{post_id}/like")
    Call<Void> toggleLike(
            @Path("post_id") int postId,
            @Query("user_id") int userId
    );

    // 2. Class Request & Response cho Auth (Của nhóm)
    static class RegisterRequest {
        String full_name;
        String email;
        String password;

        public RegisterRequest(String full_name, String email, String password) {
            this.full_name = full_name;
            this.email = email;
            this.password = password;
        }
    }

    static class LoginRequest {
        String email;
        String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    static class ApiResponse {
        public String status;
        public String message;
    }

    // 3. API Xác thực & Tài khoản (Của nhóm)
    @POST("api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse> loginUser(@Body LoginRequest request);

    // 4. API Thông tin cá nhân & Avatar (Của nhóm)
    @GET("api/profile/me")
    Call<Profile> getMyProfile();

    @PATCH("api/profile/me")
    Call<Profile> updateMyProfile(@Body UpdateProfileRequest request);

    @Multipart
    @POST("api/profile/me/avatar")
    Call<Profile> uploadMyAvatar(@Part MultipartBody.Part file);
    // 5. API Authentication mới (khớp backend)
    @POST("api/auth/register")
    Call<AuthModels.RegisterResponse> register(@Body AuthModels.RegisterBody body);

    @POST("api/auth/login")
    Call<AuthModels.TokenResponse> login(@Body AuthModels.LoginBody body);

    @POST("api/auth/logout")
    Call<AuthModels.MessageResponse> logoutWithToken(@Header("Authorization") String bearer);

    @POST("api/auth/forgot-password")
    Call<AuthModels.MessageResponse> forgotPassword(@Body AuthModels.ForgotPasswordBody body);

    @POST("api/auth/verify-otp")
    Call<AuthModels.VerifyOtpResponse> verifyOtp(@Body AuthModels.VerifyOtpBody body);

    @POST("api/auth/reset-password")
    Call<AuthModels.MessageResponse> resetPassword(@Body AuthModels.ResetPasswordBody body);

    @POST("api/auth/change-password")
    Call<AuthModels.MessageResponse> changePassword(@Body AuthModels.ChangePasswordBody body);

    @POST("api/auth/verify-current-password")
    Call<AuthModels.VerifyOtpResponse> verifyCurrentPassword(@Body AuthModels.VerifyCurrentPasswordBody body);
    @POST("api/auth/oauth-login")
    Call<AuthModels.TokenResponse> oauthLogin(@Body AuthModels.OAuthBody body);
}