package com.example.cookingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class Frame7SettingActivity extends Activity {

    private ImageButton btnBack;
    private ImageButton btnGear;
    private Switch switchNotification;
    private Switch switchDarkMode;
    private LinearLayout btnLanguage;
    private LinearLayout btnProfile;
    private LinearLayout btnChangePassword;
    private LinearLayout btnPrivacy;
    private LinearLayout btnActivity;
    private LinearLayout btnSupport;
    private LinearLayout btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Ánh xạ các View từ giao diện XML (findViewById)
        initViews();

        // Đăng ký các sự kiện tương tác
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnGear = findViewById(R.id.btnGear);
        switchNotification = findViewById(R.id.switchNotification);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        btnLanguage = findViewById(R.id.btnLanguage);
        btnProfile = findViewById(R.id.btnProfile);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnPrivacy = findViewById(R.id.btnPrivacy);
        btnActivity = findViewById(R.id.btnActivity);
        btnSupport = findViewById(R.id.btnSupport);
        btnLogOut = findViewById(R.id.btnLogOut);
    }

    private void setupEvents() {
        // Nút Back - quay lại màn hình trước
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Nút Cài đặt phụ (Gear)
        if (btnGear != null) {
            btnGear.setOnClickListener(v ->
                    Toast.makeText(this, "Tùy chọn cài đặt nâng cao", Toast.LENGTH_SHORT).show()
            );
        }

        // Bật / Tắt Switch Thông báo món mới
        if (switchNotification != null) {
            switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    Toast.makeText(this, "Đã bật thông báo món mới", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đã tắt thông báo món mới", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Bật / Tắt Switch Chế độ tối
        if (switchDarkMode != null) {
            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    Toast.makeText(this, "Đã bật chế độ tối", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đã tắt chế độ tối", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Mục Ngôn ngữ
        if (btnLanguage != null) {
            btnLanguage.setOnClickListener(v ->
                    Toast.makeText(this, "Ngôn ngữ hiện tại: Tiếng Việt", Toast.LENGTH_SHORT).show()
            );
        }

        // Mục Hồ sơ của tôi
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                Intent intent = new Intent(this, Frame25MyProfileActivity.class);
                startActivity(intent);
            });
        }

        // Mục Đổi mật khẩu
        if (btnChangePassword != null) {
            btnChangePassword.setOnClickListener(v ->
                    Toast.makeText(this, "Mở màn hình Đổi mật khẩu", Toast.LENGTH_SHORT).show()
            );
        }

        // Mục Quyền riêng tư
        if (btnPrivacy != null) {
            btnPrivacy.setOnClickListener(v -> {
                Intent intent = new Intent(this, Frame32PrivacyActivity.class);
                startActivity(intent);
            });
        }

        // Mục Hoạt động của tôi
        if (btnActivity != null) {
            btnActivity.setOnClickListener(v ->
                    Toast.makeText(this, "Mở lịch sử hoạt động nấu ăn", Toast.LENGTH_SHORT).show()
            );
        }

        // Mục Trung tâm hỗ trợ
        if (btnSupport != null) {
            btnSupport.setOnClickListener(v ->
                    Toast.makeText(this, "Mở Trung tâm hỗ trợ", Toast.LENGTH_SHORT).show()
            );
        }

        // Nút Đăng xuất
        if (btnLogOut != null) {
            btnLogOut.setOnClickListener(v -> showLogoutConfirmationDialog());
        }
    }

    /**
     * Hiển thị Dialog xác nhận đăng xuất
     */
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> {
                    Toast.makeText(this, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    // Chuyển về màn hình chính
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}