package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText edtEmailOrPhone;
    private RelativeLayout btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Ánh xạ View
        btnBack = findViewById(R.id.btnBack);
        edtEmailOrPhone = findViewById(R.id.edtEmailOrPhone);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Nút Mũi tên quay về: Quay lại màn hình Đăng Nhập
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Nút Tiếp tục
        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(v -> handleSubmit());
        }
    }

    private void handleSubmit() {
        String input = edtEmailOrPhone != null ? edtEmailOrPhone.getText().toString().trim() : "";

        if (input.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Email hoặc Số điện thoại!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Yêu cầu hợp lệ, chuyển sang đặt lại mật khẩu!", Toast.LENGTH_SHORT).show();

        // Chuyển sang form Đặt lại mật khẩu mới kèm theo cờ SOURCE_SCREEN
        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
        intent.putExtra("SOURCE_SCREEN", "FORGOT_PASSWORD");
        startActivity(intent);
    }
}