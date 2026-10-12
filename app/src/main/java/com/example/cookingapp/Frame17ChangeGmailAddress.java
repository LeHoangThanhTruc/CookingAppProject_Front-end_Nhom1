package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame17ChangeGmailAddress extends Activity {

    // Khai báo các thành phần tương tác trên giao diện
    private ImageButton btnBack;
    private EditText etNewEmail;
    private CheckBox cbNewsletter;
    private LinearLayout btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_gmail_address);

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
        etNewEmail = findViewById(R.id.etNewEmail);
        cbNewsletter = findViewById(R.id.cbNewsletter);
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
                String email = etNewEmail != null ? etNewEmail.getText().toString().trim() : "";
                boolean receiveUpdates = cbNewsletter != null && cbNewsletter.isChecked();

                if (email.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập địa chỉ email mới!", Toast.LENGTH_SHORT).show();
                } else if (!email.contains("@") || !email.contains(".")) {
                    Toast.makeText(this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đã gửi mã xác nhận đến: " + email + (receiveUpdates ? " (Đã đăng ký nhận bản tin)" : ""), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, Frame18EnterGmailOTP.class));
                }
            });
        }
    }
}
