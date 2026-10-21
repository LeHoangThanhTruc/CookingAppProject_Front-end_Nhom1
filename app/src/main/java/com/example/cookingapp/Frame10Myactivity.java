package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame10Myactivity extends Activity {

    private ImageButton btnBack;
    private ImageButton btnEditHeader;
    private LinearLayout btnSavedRecipes;
    private LinearLayout btnReviews;
    private LinearLayout btnCookingCalendar;
    private LinearLayout btnFavoriteRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnEditHeader = findViewById(R.id.btnEditHeader);
        btnSavedRecipes = findViewById(R.id.btnSavedRecipes);
        btnReviews = findViewById(R.id.btnReviews);
        btnCookingCalendar = findViewById(R.id.btnCookingCalendar);
        btnFavoriteRecipes = findViewById(R.id.btnFavoriteRecipes);
    }

    private void setupEvents() {
        // Nút Quay về -> Chuyển sang Frame7SettingActivity (activity_setting.xml)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(Frame10Myactivity.this, Frame7SettingActivity.class);
                startActivity(intent);
                finish();
            });
        }

        if (btnEditHeader != null) {
            btnEditHeader.setOnClickListener(v ->
                    Toast.makeText(this, "Chỉnh sửa thông tin cá nhân", Toast.LENGTH_SHORT).show()
            );
        }

        // 1. Chuyển sang activity_saved_list.xml (Frame14SavedList)
        if (btnSavedRecipes != null) {
            btnSavedRecipes.setOnClickListener(v -> {
                Intent intent = new Intent(Frame10Myactivity.this, Frame14SavedList.class);
                startActivity(intent);
            });
        }

        // 2. Chuyển sang activity_dadanhgia.xml (Frame11Dadanhgia)
        if (btnReviews != null) {
            btnReviews.setOnClickListener(v -> {
                Intent intent = new Intent(Frame10Myactivity.this, Frame11Dadanhgia.class);
                startActivity(intent);
            });
        }

        // 3. Chuyển sang activity_cooking_schedule.xml (Frame13CookingSchedule)
        if (btnCookingCalendar != null) {
            btnCookingCalendar.setOnClickListener(v -> {
                Intent intent = new Intent(Frame10Myactivity.this, Frame13CookingSchedule.class);
                startActivity(intent);
            });
        }

        // 4. Chuyển sang activity_favorites_list.xml (Frame12FavoritesList)
        if (btnFavoriteRecipes != null) {
            btnFavoriteRecipes.setOnClickListener(v -> {
                Intent intent = new Intent(Frame10Myactivity.this, Frame12FavoritesList.class);
                startActivity(intent);
            });
        }
    }
}
