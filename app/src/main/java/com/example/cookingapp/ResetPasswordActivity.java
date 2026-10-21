package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSubmitReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnBack = findViewById(R.id.btnBack);
        btnSubmitReset = findViewById(R.id.btnSubmitReset);

        // Bấm mũi tên quay về -> Quay về giao diện activity_enter_current_password.xml (Frame23EnterCurrentPassword)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navigateToEnterCurrentPassword());
        }

        // Bấm nút Xác nhận đặt lại -> Quay về giao diện activity_profile_details.xml (Frame15ProfileDetails)
        if (btnSubmitReset != null) {
            btnSubmitReset.setOnClickListener(v -> {
                Toast.makeText(this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ResetPasswordActivity.this, Frame15ProfileDetails.class);
                startActivity(intent);
                finish(); // Đóng ResetPasswordActivity
            });
        }
    }

    private void navigateToEnterCurrentPassword() {
        Intent intent = new Intent(ResetPasswordActivity.this, Frame23EnterCurrentPassword.class);
        startActivity(intent);
        finish(); // Đóng ResetPasswordActivity
    }
}
