package com.example.cookingapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import okhttp3.MultipartBody;
//ApiService đang được hoàn thiện và sửa chửa do chưa hoàn thành code api ở backend
//nên chưa có đầy đủ thông tin để hoàn thiện phần này
public interface ApiService {

    // Class mô tả dữ liệu gửi lên khi Đăng ký
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

    // Class mô tả dữ liệu gửi lên khi Đăng nhập
    static class LoginRequest {
        String email;
        String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    // Class nhận phản hồi từ Server Backend
    static class ApiResponse {
        public String status;
        public String message;
    }

    // Định nghĩa API Đăng ký
    @POST("api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    // Định nghĩa API Đăng nhập
    @POST("api/auth/login")
    Call<ApiResponse> loginUser(@Body LoginRequest request);

    @GET("api/profile/me")
    Call<Profile> getMyProfile();

    @PATCH("api/profile/me")
    Call<Profile> updateMyProfile(@Body UpdateProfileRequest request);

    @Multipart
    @POST("api/profile/me/avatar")
    Call<Profile> uploadMyAvatar(@Part MultipartBody.Part file);
}
