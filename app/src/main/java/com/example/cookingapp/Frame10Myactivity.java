package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame10Myactivity extends Activity {

    // Khai báo các thành phần tương tác trên giao diện
    private ImageButton btnBack;
    private ImageButton btnEditHeader;

    private LinearLayout btnSavedRecipes;
    private LinearLayout btnReviews;
    private LinearLayout btnCookingCalendar;
    private LinearLayout btnFavoriteRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Myactivity);

        // Ánh xạ các View từ file XML
        initViews();

        // Đăng ký các sự kiện Click
        setupEvents();
    }

    /**
     * Ánh xạ View thông qua findViewById
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnEditHeader = findViewById(R.id.btnEditHeader);

        btnSavedRecipes = findViewById(R.id.btnSavedRecipes);
        btnReviews = findViewById(R.id.btnReviews);
        btnCookingCalendar = findViewById(R.id.btnCookingCalendar);
        btnFavoriteRecipes = findViewById(R.id.btnFavoriteRecipes);
    }

    /**
     * Đăng ký sự kiện Click cho từng mục
     */
    private void setupEvents() {
        // 1. Nút Back - quay về màn hình trước
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Chỉnh sửa (Top Right)
        if (btnEditHeader != null) {
            btnEditHeader.setOnClickListener(v ->
                    Toast.makeText(this, "Chỉnh sửa thông tin cá nhân", Toast.LENGTH_SHORT).show()
            );
        }

        // 3. Mục Công thức đã lưu
        if (btnSavedRecipes != null) {
            btnSavedRecipes.setOnClickListener(v ->
                    Toast.makeText(this, "Mở danh sách Công thức đã lưu", Toast.LENGTH_SHORT).show()
            );
        }

        // 4. Mục Đánh giá đã viết
        if (btnReviews != null) {
            btnReviews.setOnClickListener(v ->
                    Toast.makeText(this, "Mở danh sách Đánh giá đã viết", Toast.LENGTH_SHORT).show()
            );
        }

        // 5. Mục Lịch nấu ăn
        if (btnCookingCalendar != null) {
            btnCookingCalendar.setOnClickListener(v ->
                    Toast.makeText(this, "Mở Lịch nấu ăn", Toast.LENGTH_SHORT).show()
            );
        }

        // 6. Mục Công thức yêu thích
        if (btnFavoriteRecipes != null) {
            btnFavoriteRecipes.setOnClickListener(v ->
                    Toast.makeText(this, "Mở danh sách Công thức yêu thích", Toast.LENGTH_SHORT).show()
            );
        }
    }
}