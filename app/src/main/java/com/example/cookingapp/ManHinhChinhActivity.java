package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.R;

public class ManHinhChinhActivity extends AppCompatActivity {

    private ImageView btnNotification;
    private ImageView imgAvatar1;
    private LinearLayout btnQuickSearch, btnQuickProfile, btnQuickSettings;
    private TextView tvSeeAll;
    private ImageView imgAvatar2;

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
        imgAvatar1 = findViewById(R.id.imgAvatar1);
        btnQuickSearch = findViewById(R.id.btnQuickSearch);
        btnQuickProfile = findViewById(R.id.btnQuickProfile);
        btnQuickSettings = findViewById(R.id.btnQuickSettings);
        tvSeeAll = findViewById(R.id.tvSeeAll);
        imgAvatar2 = findViewById(R.id.imgAvatar2);
    }

    private void setupEvents() {
        // Nút Chuông thông báo
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v -> startActivity(new Intent(this, Frame33NotificationsActivity.class)));
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

        // Nút Trang của tôi -> Chuyển sang activity_frame_25_my_profile.xml (Frame25MyProfileActivity)
        if (btnQuickProfile != null) {
            btnQuickProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ManHinhChinhActivity.this, Frame25MyProfileActivity.class);
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
        if (tvSeeAll != null) {
            tvSeeAll.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));
        }
        if (imgAvatar2 != null) {
            imgAvatar2.setOnClickListener(v -> startActivity(new Intent(this, Frame24OtherProfileActivity.class)));
        }
    }
}
