package com.example.cookingapp;

import android.app.Activity;
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
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnEditHeader != null) {
            btnEditHeader.setOnClickListener(v ->
                    Toast.makeText(this, "Chỉnh sửa thông tin cá nhân", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnSavedRecipes != null) {
            btnSavedRecipes.setOnClickListener(v ->
                    Toast.makeText(this, "Mở danh sách Công thức đã lưu", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnReviews != null) {
            btnReviews.setOnClickListener(v ->
                    Toast.makeText(this, "Mở danh sách Đánh giá đã viết", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnCookingCalendar != null) {
            btnCookingCalendar.setOnClickListener(v ->
                    Toast.makeText(this, "Mở Lịch nấu ăn", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnFavoriteRecipes != null) {
            btnFavoriteRecipes.setOnClickListener(v ->
                    Toast.makeText(this, "Mở danh sách Công thức yêu thích", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
