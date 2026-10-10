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
    private ImageButton btnBack, btnGear;
    private Switch switchNotification, switchDarkMode;
    private LinearLayout btnLanguage, btnProfile, btnChangePassword, btnPrivacy, btnActivity, btnSupport, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnGear != null) btnGear.setOnClickListener(v -> Toast.makeText(this, "Cài đặt nâng cao", Toast.LENGTH_SHORT).show());
        if (switchNotification != null) switchNotification.setOnCheckedChangeListener((b, isChecked) -> Toast.makeText(this, isChecked ? "Đã bật thông báo" : "Đã tắt thông báo", Toast.LENGTH_SHORT).show());
        if (switchDarkMode != null) switchDarkMode.setOnCheckedChangeListener((b, isChecked) -> Toast.makeText(this, isChecked ? "Đã bật chế độ tối" : "Đã tắt chế độ tối", Toast.LENGTH_SHORT).show());
        if (btnLanguage != null) btnLanguage.setOnClickListener(v -> startActivity(new Intent(this, Frame8Language.class)));
        if (btnProfile != null) btnProfile.setOnClickListener(v -> startActivity(new Intent(this, Frame25MyProfileActivity.class)));
        if (btnChangePassword != null) btnChangePassword.setOnClickListener(v -> Toast.makeText(this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show());
        if (btnPrivacy != null) btnPrivacy.setOnClickListener(v -> startActivity(new Intent(this, Frame32PrivacyActivity.class)));
        if (btnActivity != null) btnActivity.setOnClickListener(v -> startActivity(new Intent(this, Frame10Myactivity.class)));
        if (btnSupport != null) btnSupport.setOnClickListener(v -> startActivity(new Intent(this, Frame9Contactus.class)));
        if (btnLogOut != null) btnLogOut.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc muốn đăng xuất?")
                .setPositiveButton("Đăng xuất", (d, w) -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
        });
    }
}