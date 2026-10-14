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
    private LinearLayout btnQuickSearch, btnQuickProfile, btnQuickSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_chinh);

        initViews();

        //Lắng nghe sự kiện click cho các nút lối tắt và thông báo
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
            Intent intent = new Intent(ManHinhChinhActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        // Nút Trang của tôi -> Chuyển sang Frame10Myactivity
        btnQuickProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ManHinhChinhActivity.this, Frame10Myactivity.class);
            startActivity(intent);
        });

        // Nút Cài đặt -> Chuyển sang Frame7SettingActivity
        btnQuickSettings.setOnClickListener(v -> {
            Intent intent = new Intent(ManHinhChinhActivity.this, Frame7SettingActivity.class);
            startActivity(intent);
        });
    }
}
