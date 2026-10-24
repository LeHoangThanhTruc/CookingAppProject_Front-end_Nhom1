package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Frame26BioActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_26_bio); // Đổi tên file XML tương ứng của bạn

        // Ánh xạ View
        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);

        // 1. Nút Back -> Quay về đúng màn hình đã mở Frame 26
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Cập nhật (btnSubmit) -> Thông báo thành công & Quay về Frame25MyProfileActivity
        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(v -> {
                Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                navigateToMyProfile();
            });
        }
    }

    /**
     * Hàm dùng chung để điều hướng về Frame25MyProfileActivity
     */
    private void navigateToMyProfile() {
        Intent intent = new Intent(Frame26BioActivity.this, Frame25MyProfileActivity.class);
        // Xóa các Activity nằm phía trên MyProfile trong BackStack nếu có để tránh tạo trùng lặp màn hình
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // Đóng màn hình hiện tại
    }
}
