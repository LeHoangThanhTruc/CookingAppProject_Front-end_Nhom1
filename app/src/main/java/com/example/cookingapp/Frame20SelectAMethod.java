package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame20SelectAMethod extends Activity {

    // Khai báo các thành phần tương tác trên giao diện
    private ImageButton btnBack;
    private LinearLayout btnZaloOption;
    private LinearLayout btnSmsOption;
    private LinearLayout btnCallOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_a_method);

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
        btnZaloOption = findViewById(R.id.btnZaloOption);
        btnSmsOption = findViewById(R.id.btnSmsOption);
        btnCallOption = findViewById(R.id.btnCallOption);
    }

    /**
     * Đăng ký sự kiện Click cho từng phương thức
     */
    private void setupEvents() {
        // 1. Nút Back - quay về màn hình trước
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Phương thức Zalo
        if (btnZaloOption != null) {
            btnZaloOption.setOnClickListener(v -> {
                Toast.makeText(this, "Đã chọn nhận mã qua Zalo", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        // 3. Phương thức Tin nhắn SMS
        if (btnSmsOption != null) {
            btnSmsOption.setOnClickListener(v -> {
                Toast.makeText(this, "Đã chọn nhận mã qua Tin nhắn SMS", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        // 4. Phương thức Cuộc gọi thoại
        if (btnCallOption != null) {
            btnCallOption.setOnClickListener(v -> {
                Toast.makeText(this, "Đã chọn nhận mã qua Cuộc gọi thoại", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}