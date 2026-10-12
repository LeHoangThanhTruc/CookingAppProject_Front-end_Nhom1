package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame8Language extends Activity {

    private ImageButton btnBack;
    private ImageButton btnGear;

    private LinearLayout btnVietnamese;
    private ImageView ivVietnameseCheck;

    private LinearLayout btnEnglish;
    private ImageView ivEnglishCheck;

    private LinearLayout btnSpanish;
    private ImageView ivSpanishCheck;

    private LinearLayout btnFrench;
    private ImageView ivFrenchCheck;

    private LinearLayout btnApply;

    // Biến lưu mã và tên ngôn ngữ đang được chọn (Mặc định: Tiếng Việt)
    private String selectedLanguageCode = "vi";
    private String selectedLanguageName = "Tiếng Việt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        // Ánh xạ các View từ file giao diện XML
        initViews();

        // Cấu hình sự kiện Click cho từng mục
        setupEvents();

        // Đặt trạng thái hiển thị mặc định
        selectLanguage("vi", "Tiếng Việt");
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnGear = findViewById(R.id.btnGear);

        btnVietnamese = findViewById(R.id.btnVietnamese);
        ivVietnameseCheck = findViewById(R.id.ivVietnameseCheck);

        btnEnglish = findViewById(R.id.btnEnglish);
        ivEnglishCheck = findViewById(R.id.ivEnglishCheck);

        btnSpanish = findViewById(R.id.btnSpanish);
        ivSpanishCheck = findViewById(R.id.ivSpanishCheck);

        btnFrench = findViewById(R.id.btnFrench);
        ivFrenchCheck = findViewById(R.id.ivFrenchCheck);

        btnApply = findViewById(R.id.btnApply);
    }

    private void setupEvents() {
        // Nút Back - quay về màn hình Cài đặt (Setting)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> navigateToSettingScreen());
        }

        // Nút Cài đặt phụ (Gear)
        if (btnGear != null) {
            btnGear.setOnClickListener(v ->
                    Toast.makeText(this, "Tùy chọn cài đặt nâng cao", Toast.LENGTH_SHORT).show()
            );
        }

        // Chọn Tiếng Việt
        if (btnVietnamese != null) {
            btnVietnamese.setOnClickListener(v -> selectLanguage("vi", "Tiếng Việt"));
        }

        // Chọn English
        if (btnEnglish != null) {
            btnEnglish.setOnClickListener(v -> selectLanguage("en", "English"));
        }

        // Chọn Español
        if (btnSpanish != null) {
            btnSpanish.setOnClickListener(v -> selectLanguage("es", "Español"));
        }

        // Chọn Français
        if (btnFrench != null) {
            btnFrench.setOnClickListener(v -> selectLanguage("fr", "Français"));
        }

        // Nút Áp dụng - lưu ngôn ngữ và trở về màn hình Setting
        if (btnApply != null) {
            btnApply.setOnClickListener(v -> {
                Toast.makeText(this, "Đã áp dụng ngôn ngữ: " + selectedLanguageName, Toast.LENGTH_SHORT).show();
                navigateToSettingScreen();
            });
        }
    }

    /**
     * Cập nhật giao diện khi chọn 1 ngôn ngữ
     */
    private void selectLanguage(String code, String name) {
        selectedLanguageCode = code;
        selectedLanguageName = name;

        // Đưa tất cả các mục về trạng thái chưa chọn
        resetItemStyle(btnVietnamese, ivVietnameseCheck);
        resetItemStyle(btnEnglish, ivEnglishCheck);
        resetItemStyle(btnSpanish, ivSpanishCheck);
        resetItemStyle(btnFrench, ivFrenchCheck);

        // Đánh dấu mục được chọn
        switch (code) {
            case "vi":
                setItemSelectedStyle(btnVietnamese, ivVietnameseCheck);
                break;
            case "en":
                setItemSelectedStyle(btnEnglish, ivEnglishCheck);
                break;
            case "es":
                setItemSelectedStyle(btnSpanish, ivSpanishCheck);
                break;
            case "fr":
                setItemSelectedStyle(btnFrench, ivFrenchCheck);
                break;
        }

        Toast.makeText(this, "Đã chọn: " + name, Toast.LENGTH_SHORT).show();
    }

    private void resetItemStyle(LinearLayout container, ImageView checkIcon) {
        if (container != null) {
            container.setBackgroundResource(R.drawable.bg_language_item_normal);
        }
        if (checkIcon != null) {
            checkIcon.setVisibility(View.GONE);
        }
    }

    private void setItemSelectedStyle(LinearLayout container, ImageView checkIcon) {
        if (container != null) {
            container.setBackgroundResource(R.drawable.bg_language_item_selected);
        }
        if (checkIcon != null) {
            checkIcon.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Chuyển về màn hình Cài đặt (Setting)
     */
    private void navigateToSettingScreen() {
        Intent intent = new Intent(this, Frame7SettingActivity.class);
        startActivity(intent);
        finish();
    }
}