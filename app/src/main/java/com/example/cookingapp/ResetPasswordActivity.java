package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSubmitReset;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;
    private String sourceScreen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Lấy nguồn màn hình chuyển tới (mặc định là "FORGOT_PASSWORD")
        if (getIntent() != null && getIntent().hasExtra("SOURCE_SCREEN")) {
            sourceScreen = getIntent().getStringExtra("SOURCE_SCREEN");
        }

        btnBack = findViewById(R.id.btnBack);
        btnSubmitReset = findViewById(R.id.btnSubmitReset);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        // Nút Mũi tên quay về
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> handleBackNavigation());
        }

        // Nút Xác nhận đặt lại mật khẩu
        if (btnSubmitReset != null) {
            btnSubmitReset.setOnClickListener(v -> handleSubmitSuccess());
        }
    }

    private void handleBackNavigation() {
        finish();
    }

    private void handleSubmitSuccess() {
        String password = edtNewPassword.getText().toString().trim();
        String confirmation = edtConfirmPassword.getText().toString().trim();
        if (password.isEmpty() || confirmation.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải chứa ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmation)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();

        if ("ENTER_CURRENT_PASSWORD".equals(sourceScreen)) {
            // Chuyển sang Frame15ProfileDetails
            Intent intent = new Intent(ResetPasswordActivity.this, Frame15ProfileDetails.class);
            startActivity(intent);
        } else {
            // Chuyển sang LoginActivity
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
