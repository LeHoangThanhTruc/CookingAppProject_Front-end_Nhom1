package com.example.gamecookingtutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView btnNotification;
    private LinearLayout btnQuickSearch, btnQuickProfile, btnQuickSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Ánh xạ View từ layout activity_main.xml
        initViews();

        // 2. Lắng nghe sự kiện click cho các nút lối tắt và thông báo
        setupEvents();
    }

    private void initViews() {
        btnNotification = findViewById(R.id.btnNotification);
        btnQuickSearch = findViewById(R.id.btnQuickSearch);
        btnQuickProfile = findViewById(R.id.btnQuickProfile);
        btnQuickSettings = findViewById(R.id.btnQuickSettings);
    }

    private void setupEvents() {
        // Nút Chuông thông báo
        btnNotification.setOnClickListener(v ->
                Toast.makeText(this, "Chưa có thông báo mới!", Toast.LENGTH_SHORT).show()
        );

        // Nút Tìm kiếm
        btnQuickSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        // Nút Trang của tôi
        btnQuickProfile.setOnClickListener(v ->
                Toast.makeText(this, "Mở Trang cá nhân!", Toast.LENGTH_SHORT).show()
        );

        // Nút Cài đặt
        btnQuickSettings.setOnClickListener(v ->
                Toast.makeText(this, "Mở Màn hình Cài đặt!", Toast.LENGTH_SHORT).show()
        );
    }
}