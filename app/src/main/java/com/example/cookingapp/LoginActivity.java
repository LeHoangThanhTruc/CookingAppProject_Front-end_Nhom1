package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager facebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // 1. Ánh xạ View từ XML
        TextView tvRegister = findViewById(R.id.tvRegister);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        Button btnDangNhap = findViewById(R.id.btnDangNhap);
        EditText emailOrPhone = findViewById(R.id.edtEmailPhone);
        EditText password = findViewById(R.id.edtPassword);
        android.widget.ImageView iconEye = findViewById(R.id.iconEye);
        View googleLogin = findViewById(R.id.btnLogInUsingGoogle);
        View facebookLogin = findViewById(R.id.btnLogInUsingFacebook);

        // Nút con mắt ẩn/hiện mật khẩu
        PasswordToggleHelper.attach(password, iconEye);

        // 2. Lắng nghe sự kiện click chuyển sang Đăng ký
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // 3. Lắng nghe sự kiện click chuyển sang Quên mật khẩu -> Truyền FLOW_SOURCE = "LOGIN"
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            intent.putExtra("FLOW_SOURCE", "LOGIN");
            startActivity(intent);
        });

        // 4. Đăng nhập: gọi API backend, thành công thì lưu token rồi vào màn hình chính
        btnDangNhap.setOnClickListener(v -> {
            String identifier = emailOrPhone.getText().toString().trim();
            String passwordText = password.getText().toString();

            if (identifier.isEmpty()) {
                emailOrPhone.setError("Vui lòng nhập email, tên tài khoản hoặc số điện thoại");
                emailOrPhone.requestFocus();
                return;
            }
            if (passwordText.length() < 6) {
                password.setError("Mật khẩu phải có ít nhất 6 ký tự");
                password.requestFocus();
                return;
            }

            btnDangNhap.setEnabled(false);
            ApiService api = ApiClient.getClient().create(ApiService.class);
            api.login(new AuthModels.LoginBody(identifier, passwordText))
                    .enqueue(new Callback<AuthModels.TokenResponse>() {
                        @Override
                        public void onResponse(Call<AuthModels.TokenResponse> call,
                                               Response<AuthModels.TokenResponse> response) {
                            btnDangNhap.setEnabled(true);
                            if (response.isSuccessful() && response.body() != null
                                    && response.body().access_token != null) {
                                SessionManager.saveAccessToken(
                                        LoginActivity.this, response.body().access_token);
                                Toast.makeText(LoginActivity.this,
                                        "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, ManHinhChinhActivity.class);
                                startActivity(intent);

                                // Đóng LoginActivity để bấm Back không quay lại màn hình đăng nhập
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        AuthModels.errorMessage(response), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthModels.TokenResponse> call, Throwable t) {
                            btnDangNhap.setEnabled(true);
                            Toast.makeText(LoginActivity.this,
                                    "Không kết nối được server. Kiểm tra backend đã chạy chưa.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // 5. Đăng nhập Google / Facebook: lấy token từ Google/Facebook rồi gửi cho backend xác minh
        SocialLoginHelper.Listener socialListener = new SocialLoginHelper.Listener() {
            @Override
            public void onSuccess(AuthModels.TokenResponse response) {
                SessionManager.saveAccessToken(LoginActivity.this, response.access_token);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, ManHinhChinhActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        };
        facebookCallbackManager = SocialLoginHelper.registerFacebookCallback(socialListener);

        googleLogin.setOnClickListener(v -> SocialLoginHelper.loginWithGoogle(this, socialListener));
        facebookLogin.setOnClickListener(v -> SocialLoginHelper.startFacebookLogin(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Chuyển kết quả cho Facebook SDK
        if (facebookCallbackManager != null) {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}