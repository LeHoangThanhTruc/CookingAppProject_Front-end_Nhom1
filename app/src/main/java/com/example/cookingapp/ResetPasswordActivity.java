package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSubmitReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnBack = findViewById(R.id.btnBack);
        btnSubmitReset = findViewById(R.id.btnSubmitReset);

        // Bấm mũi tên quay về -> Quay về LoginActivity
        btnBack.setOnClickListener(v -> navigateToLogin());

        // Bấm nút Xác nhận đặt lại -> Quay về LoginActivity
        btnSubmitReset.setOnClickListener(v -> {
            Toast.makeText(this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            navigateToLogin();
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đóng ResetPasswordActivity
    }
}
