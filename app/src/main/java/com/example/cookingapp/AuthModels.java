package com.example.cookingapp;

import org.json.JSONObject;

import retrofit2.Response;

/**
 * Các class dữ liệu cho API Authentication (khớp JSON của backend FastAPI).
 * Tên trường giữ nguyên dạng snake_case để Gson tự map.
 */
public final class AuthModels {

    private AuthModels() {
    }

    // ----- Request -----

    public static class RegisterBody {
        public String username; // null => Gson bỏ qua, backend tự tạo từ email
        public String email;
        public String password;
        public String full_name;
        public String phone_number; // null => Gson bỏ qua

        public RegisterBody(String fullName, String email, String password) {
            this.full_name = fullName;
            this.email = email;
            this.password = password;
        }

        public RegisterBody(String fullName, String email, String password, String phoneNumber) {
            this(fullName, email, password);
            this.phone_number = phoneNumber;
        }

        public RegisterBody(String fullName, String email, String password,
                            String phoneNumber, String username) {
            this(fullName, email, password, phoneNumber);
            this.username = username;
        }
    }

    public static class LoginBody {
        public String email; // backend chấp nhận email hoặc username
        public String password;

        public LoginBody(String emailOrUsername, String password) {
            this.email = emailOrUsername;
            this.password = password;
        }
    }

    public static class OAuthBody {
        public String provider; // "GOOGLE" hoặc "FACEBOOK"
        public String token;    // Google: ID token; Facebook: access token

        public OAuthBody(String provider, String token) {
            this.provider = provider;
            this.token = token;
        }
    }

    public static class ForgotPasswordBody {
        public String identifier; // email hoặc số điện thoại

        public ForgotPasswordBody(String identifier) {
            this.identifier = identifier;
        }
    }

    public static class VerifyOtpBody {
        public String identifier;
        public String otp;

        public VerifyOtpBody(String identifier, String otp) {
            this.identifier = identifier;
            this.otp = otp;
        }
    }

    public static class ResetPasswordBody {
        public String reset_token;
        public String new_password;

        public ResetPasswordBody(String resetToken, String newPassword) {
            this.reset_token = resetToken;
            this.new_password = newPassword;
        }
    }

    public static class ChangePasswordBody {
        public String current_password;
        public String new_password;

        public ChangePasswordBody(String currentPassword, String newPassword) {
            this.current_password = currentPassword;
            this.new_password = newPassword;
        }
    }

    public static class VerifyCurrentPasswordBody {
        public String current_password;

        public VerifyCurrentPasswordBody(String currentPassword) {
            this.current_password = currentPassword;
        }
    }

    // ----- Response -----

    public static class VerifyOtpResponse {
        public String reset_token;
        public int expires_in_minutes;
    }

    public static class UserDto {
        public int id;
        public String username;
        public String email;
        public String full_name;
        public String avatar_url;
        public String auth_provider;
    }

    public static class RegisterResponse {
        public String message;
        public UserDto user;
    }

    public static class TokenResponse {
        public String access_token;
        public String token_type;
        public UserDto user;
    }

    public static class MessageResponse {
        public String message;
    }

    // ----- Đọc thông báo lỗi từ backend (dạng {"detail": "..."}) -----

    public static String errorMessage(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                JSONObject json = new JSONObject(response.errorBody().string());
                Object detail = json.opt("detail");
                if (detail instanceof String) {
                    return (String) detail;
                }
            }
        } catch (Exception ignored) {
            // Rơi xuống thông báo mặc định bên dưới
        }
        return "Có lỗi xảy ra (mã " + response.code() + ")";
    }
}