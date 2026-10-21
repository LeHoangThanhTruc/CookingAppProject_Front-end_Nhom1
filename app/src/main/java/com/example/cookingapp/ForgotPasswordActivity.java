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
        if (edtEmailOrPhone == null) return;
        String input = edtEmailOrPhone.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Email hoặc Số điện thoại!", Toast.LENGTH_SHORT).show();
            return;
        }

        String flowSource = getIntent() != null ? getIntent().getStringExtra("FLOW_SOURCE") : "LOGIN";

        // Kiểm tra xem dữ liệu nhập vào là Email hay Số điện thoại
        if (input.contains("@")) {
            Toast.makeText(this, "Gửi mã xác thực qua Email!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPasswordActivity.this, Frame18EnterGmailOTP.class);
            intent.putExtra("EMAIL_TARGET", input);
            intent.putExtra("FLOW_TYPE", "FORGOT_PASSWORD");
            intent.putExtra("FLOW_SOURCE", flowSource);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Gửi mã xác thực qua SĐT!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPasswordActivity.this, Frame22OTPSDT.class);
            intent.putExtra("PHONE_TARGET", input);
            intent.putExtra("FLOW_TYPE", "FORGOT_PASSWORD");
            intent.putExtra("FLOW_SOURCE", flowSource);
            startActivity(intent);
        }
    }
}
