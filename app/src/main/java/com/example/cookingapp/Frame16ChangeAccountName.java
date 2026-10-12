package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame16ChangeAccountName extends Activity {

    // Khai báo các thành phần tương tác trên giao diện
    private ImageButton btnBack;
    private EditText etNewAccountName;
    private LinearLayout btnSaveName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ChangeAccountName);

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
        etNewAccountName = findViewById(R.id.etNewAccountName);
        btnSaveName = findViewById(R.id.btnSaveName);
    }

    /**
     * Đăng ký sự kiện Click cho từng mục
     */
    private void setupEvents() {
        // 1. Nút Back - quay về màn hình trước
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Lưu thay đổi
        if (btnSaveName != null) {
            btnSaveName.setOnClickListener(v -> {
                String newName = etNewAccountName != null ? etNewAccountName.getText().toString().trim() : "";

                if (newName.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập tên tài khoản mới!", Toast.LENGTH_SHORT).show();
                } else if (newName.length() < 3) {
                    Toast.makeText(this, "Tên tài khoản phải có ít nhất 3 ký tự!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đổi tên tài khoản thành công: " + newName, Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }
}