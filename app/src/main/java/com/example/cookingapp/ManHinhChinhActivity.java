package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.R;

public class ManHinhChinhActivity extends AppCompatActivity {

    private ImageView btnNotification;
    private ImageView imgAvatar1; // Thêm khai báo imgAvatar1
    private LinearLayout btnQuickSearch, btnQuickProfile, btnQuickSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_chinh);

        initViews();

        // Lắng nghe sự kiện click cho các nút lối tắt và thông báo
        setupEvents();
    }

    private void initViews() {
        btnNotification = findViewById(R.id.btnNotification);
        imgAvatar1 = findViewById(R.id.imgAvatar1); // Ánh xạ ID imgAvatar1
        btnQuickSearch = findViewById(R.id.btnQuickSearch);
        btnQuickProfile = findViewById(R.id.btnQuickProfile);
        btnQuickSettings = findViewById(R.id.btnQuickSettings);
    }

    private void setupEvents() {
        // Nút Chuông thông báo
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v ->
                    Toast.makeText(this, "Chưa có thông báo mới!", Toast.LENGTH_SHORT).show()
            );
        }

        // Bấm imgAvatar1 -> Chuyển sang Frame24OtherProfileActivity
        if (imgAvatar1 != null) {
            imgAvatar1.setOnClickListener(v -> {
                Intent intent = new Intent(ManHinhChinhActivity.this, Frame24OtherProfileActivity.class);
                startActivity(intent);
            });
        }

        // Nút Tìm kiếm
        if (btnQuickSearch != null) {
            btnQuickSearch.setOnClickListener(v -> {
                Intent intent = new Intent(ManHinhChinhActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        // Nút Trang của tôi -> Chuyển sang Frame10Myactivity
        if (btnQuickProfile != null) {
            btnQuickProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ManHinhChinhActivity.this, Frame10Myactivity.class);
                startActivity(intent);
            });
        }

        // Nút Cài đặt -> Chuyển sang Frame7SettingActivity
        if (btnQuickSettings != null) {
            btnQuickSettings.setOnClickListener(v -> {
                Intent intent = new Intent(ManHinhChinhActivity.this, Frame7SettingActivity.class);
                startActivity(intent);
            });
        }
    }
}