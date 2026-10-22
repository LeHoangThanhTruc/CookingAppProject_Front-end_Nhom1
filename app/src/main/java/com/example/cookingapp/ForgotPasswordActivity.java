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

        // Nút Mũi tên quay về: Quay về giao diện activity_enter_current_password.xml (hoặc Login tùy flow)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Nút Tiếp tục (btnSubmit): Chuyển sang OTP Gmail hoặc OTP SĐT dựa vào dữ liệu nhập
        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(v -> handleSubmit());
        }
    }

    private void handleSubmit() {
        Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
        // Thêm dòng này để đánh dấu nguồn chuyển tới là FORGOT_PASSWORD
        intent.putExtra("SOURCE_SCREEN", "FORGOT_PASSWORD");
        startActivity(intent);
    }
}
