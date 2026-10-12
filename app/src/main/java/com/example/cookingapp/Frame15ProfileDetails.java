package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame15ProfileDetails extends Activity {

    private ImageButton btnBack;
    private TextView btnChangeAvatar;
    private EditText etUsername, etGmail, etPhone, etPassword;
    private ImageView ivEditUsername, ivEditGmail, ivEditPhone, ivEyePassword;
    private LinearLayout btnChangePasswordRow;

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        // Ánh xạ các View từ XML
        initViews();

        // Đăng ký sự kiện Click
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);

        etUsername = findViewById(R.id.etUsername);
        etGmail = findViewById(R.id.etGmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);

        ivEditUsername = findViewById(R.id.ivEditUsername);
        ivEditGmail = findViewById(R.id.ivEditGmail);
        ivEditPhone = findViewById(R.id.ivEditPhone);
        ivEyePassword = findViewById(R.id.ivEyePassword);

        btnChangePasswordRow = findViewById(R.id.btnChangePasswordRow);
    }

    private void setupEvents() {
        // Nút Back
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Đổi ảnh đại diện
        if (btnChangeAvatar != null) {
            btnChangeAvatar.setOnClickListener(v ->
                    Toast.makeText(this, "Chọn ảnh đại diện mới", Toast.LENGTH_SHORT).show()
            );
        }

        // Chỉnh sửa tên tài khoản
        if (ivEditUsername != null) {
            ivEditUsername.setOnClickListener(v -> {
                if (etUsername != null) etUsername.requestFocus();
                Toast.makeText(this, "Chỉnh sửa tên tài khoản", Toast.LENGTH_SHORT).show();
            });
        }

        // Chỉnh sửa Gmail
        if (ivEditGmail != null) {
            ivEditGmail.setOnClickListener(v -> {
                if (etGmail != null) etGmail.requestFocus();
                Toast.makeText(this, "Chỉnh sửa Gmail", Toast.LENGTH_SHORT).show();
            });
        }

        // Chỉnh sửa Số điện thoại
        if (ivEditPhone != null) {
            ivEditPhone.setOnClickListener(v -> {
                if (etPhone != null) etPhone.requestFocus();
                Toast.makeText(this, "Cập nhật số điện thoại", Toast.LENGTH_SHORT).show();
            });
        }

        // Hiện/Ẩn mật khẩu
        if (ivEyePassword != null && etPassword != null) {
            ivEyePassword.setOnClickListener(v -> {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Toast.makeText(this, "Hiển thị mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Toast.makeText(this, "Đã ẩn mật khẩu", Toast.LENGTH_SHORT).show();
                }
                etPassword.setSelection(etPassword.getText().length());
            });
        }

        // Đổi mật khẩu row
        if (btnChangePasswordRow != null) {
            btnChangePasswordRow.setOnClickListener(v ->
                    Toast.makeText(this, "Mở màn hình Đổi mật khẩu", Toast.LENGTH_SHORT).show()
            );
        }
    }
}