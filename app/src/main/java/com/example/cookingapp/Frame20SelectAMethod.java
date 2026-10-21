package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame20SelectAMethod extends Activity {

    private ImageButton btnBack;
    private LinearLayout btnZaloOption;
    private LinearLayout btnSmsOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_a_method);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnZaloOption = findViewById(R.id.btnZaloOption);
        btnSmsOption = findViewById(R.id.btnSmsOption);
    }

    private void setupEvents() {
        // 1. Nút Back - quay về màn hình trước
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Phương thức Zalo
        if (btnZaloOption != null) {
            btnZaloOption.setOnClickListener(v -> {
                Toast.makeText(this, "Đã chọn nhận mã qua Zalo", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Frame21OTPZalo.class));
            });
        }

        // 3. Phương thức Tin nhắn SMS
        if (btnSmsOption != null) {
            btnSmsOption.setOnClickListener(v -> {
                Toast.makeText(this, "Đã chọn nhận mã qua Tin nhắn SMS", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Frame22OTPSDT.class));
            });
        }
    }
}
