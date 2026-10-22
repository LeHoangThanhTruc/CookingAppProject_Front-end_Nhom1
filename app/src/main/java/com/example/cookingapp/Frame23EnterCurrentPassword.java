package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame23EnterCurrentPassword extends Activity {

    // Khai báo các thành phần tương tác trên giao diện
    private ImageButton btnBack;
    private ImageButton btnGearHeader;
    private EditText etCurrentPassword;
    private TextView tvForgotPassword;
    private LinearLayout btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_password);

        // Ánh xạ các View từ XML
        initViews();

        // Đăng ký sự kiện Click
        setupEvents();
    }

    /**
     * Ánh xạ View thông qua findViewById
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnGearHeader = findViewById(R.id.btnGearHeader);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnNext = findViewById(R.id.btnNext);
    }

    /**
     * Đăng ký sự kiện Click cho từng mục
     */
    private void setupEvents() {
        // 1. Nút Back - quay về màn hình trước
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Cài đặt trên Header
        if (btnGearHeader != null) {
            btnGearHeader.setOnClickListener(v -> startActivity(new Intent(this, Frame7SettingActivity.class)));
        }

        // 3. Link Quên mật khẩu
        if (tvForgotPassword != null) {
            tvForgotPassword.setOnClickListener(v -> startActivity(new Intent(this, ForgotPasswordActivity.class)));
        }

<<<<<<< HEAD
        // 4. Nút Tiếp theo (btnNext) -> Chuyển sang ResetPasswordActivity
=======
        // 4. Nút Tiếp theo
>>>>>>> 400e4cd020184747325bacac4bd82a274422985c
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                String password = etCurrentPassword != null ? etCurrentPassword.getText().toString().trim() : "";

                if (password.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập mật khẩu hiện tại!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(this, "Mật khẩu phải chứa ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                } else {
<<<<<<< HEAD
                    Toast.makeText(this, "Xác nhận mật khẩu hiện tại thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Frame23EnterCurrentPassword.this, ResetPasswordActivity.class);
                    //gửi cờ nguồn sang ResetPasswordActivity
                    intent.putExtra("SOURCE_SCREEN", "ENTER_CURRENT_PASSWORD");
                    startActivity(intent);
=======
                    Toast.makeText(this, "Xác nhận mật khẩu hiện tại thành công!", Toast.LENGTH_LONG).show();
                    finish();
>>>>>>> 400e4cd020184747325bacac4bd82a274422985c
                }
            });
        }
    }
}
