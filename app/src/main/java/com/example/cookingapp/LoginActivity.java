package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, ManHinhChinhActivity.class);
            startActivity(intent);

            // Đóng LoginActivity để người dùng bấm nút Back không quay lại màn hình đăng nhập
            finish();
        });
    }
}
