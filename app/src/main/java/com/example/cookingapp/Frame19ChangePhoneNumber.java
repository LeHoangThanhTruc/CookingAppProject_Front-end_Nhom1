package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame19ChangePhoneNumber extends Activity {

    // Khai báo các thành phần tương tác trên giao diện
    private ImageButton btnBack;
    private EditText etNewPhone;
    private CheckBox cbLogoutOtherDevices;
    private LinearLayout btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ChangePhoneNumber);

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
        etNewPhone = findViewById(R.id.etNewPhone);
        cbLogoutOtherDevices = findViewById(R.id.cbLogoutOtherDevices);
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

        // 2. Nút Tiếp theo
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                String phone = etNewPhone != null ? etNewPhone.getText().toString().trim() : "";
                boolean logoutOthers = cbLogoutOtherDevices != null && cbLogoutOtherDevices.isChecked();

                if (phone.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập số điện thoại mới!", Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 9) {
                    Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Mã xác thực đã được gửi đến: " + phone + (logoutOthers ? " (Đã chọn đăng xuất thiết bị khác)" : ""), Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }
}