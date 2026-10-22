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

public class LoginActivity extends AppCompatActivity {

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
        View googleLogin = findViewById(R.id.btnLogInUsingGoogle);
        View facebookLogin = findViewById(R.id.btnLogInUsingFacebook);

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

        // 4. Lắng nghe sự kiện click Đăng nhập -> Chuyển sang Màn hình chính (MainActivity)
        btnDangNhap.setOnClickListener(v -> {
            if (emailOrPhone.getText().toString().trim().isEmpty()) {
                emailOrPhone.setError("Vui lòng nhập email hoặc số điện thoại");
                emailOrPhone.requestFocus();
                return;
            }
            if (password.getText().toString().length() < 6) {
                password.setError("Mật khẩu phải có ít nhất 6 ký tự");
                password.requestFocus();
                return;
            }
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, ManHinhChinhActivity.class);
            startActivity(intent);

            // Đóng LoginActivity để người dùng bấm nút Back không quay lại màn hình đăng nhập
            finish();
        });

        googleLogin.setOnClickListener(v -> openPrototypeHome("Google"));
        facebookLogin.setOnClickListener(v -> openPrototypeHome("Facebook"));
    }

    private void openPrototypeHome(String provider) {
        Toast.makeText(this, "Đăng nhập " + provider + " ở chế độ bản mẫu", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ManHinhChinhActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
