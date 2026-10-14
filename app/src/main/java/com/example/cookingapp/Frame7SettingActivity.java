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

        initViews();
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
        // Sửa nút Quay về -> Chuyển sang ManHinhChinhActivity
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(Frame7SettingActivity.this, ManHinhChinhActivity.class);
                startActivity(intent);
                finish(); // Đóng màn hình Setting lại
            });
        }

        if (btnGear != null) {
            btnGear.setOnClickListener(v ->
                    Toast.makeText(this, "Tùy chọn cài đặt nâng cao", Toast.LENGTH_SHORT).show()
            );
        }

        if (switchNotification != null) {
            switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    Toast.makeText(this, "Đã bật thông báo món mới", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đã tắt thông báo món mới", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (switchDarkMode != null) {
            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    Toast.makeText(this, "Đã bật chế độ tối", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đã tắt chế độ tối", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (btnLanguage != null) {
            btnLanguage.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame8Language.class))
            );
        }

        if (btnProfile != null) {
            btnProfile.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame25MyProfileActivity.class))
            );
        }

        if (btnChangePassword != null) {
            btnChangePassword.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame23EnterCurrentPassword.class))
            );
        }

        if (btnPrivacy != null) {
            btnPrivacy.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame32PrivacyActivity.class))
            );
        }

        if (btnActivity != null) {
            btnActivity.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame10Myactivity.class))
            );
        }

        if (btnSupport != null) {
            btnSupport.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame9Contactus.class))
            );
        }

        if (btnLogOut != null) {
            btnLogOut.setOnClickListener(v -> showLogoutConfirmationDialog());
        }
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> {
                    Toast.makeText(this, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}