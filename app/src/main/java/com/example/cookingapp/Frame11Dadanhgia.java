package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Frame11Dadanhgia extends Activity {

    private ImageButton btnBack;
    private ImageButton btnProfileHeader;

    private TextView chipAll, chip5Star, chip4Star, chip3Star, chip2Star, chip1Star;
    private TextView btnViewReview1, btnEditReview1;
    private TextView btnViewReview2, btnEditReview2;
    private TextView btnViewReview3, btnEditReview3;
    private TextView btnSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dadanhgia);

        // Ánh xạ các View từ XML
        initViews();

        // Đăng ký sự kiện Click
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnProfileHeader = findViewById(R.id.btnProfileHeader);

        chipAll = findViewById(R.id.chipAll);
        chip5Star = findViewById(R.id.chip5Star);
        chip4Star = findViewById(R.id.chip4Star);
        chip3Star = findViewById(R.id.chip3Star);
        chip2Star = findViewById(R.id.chip2Star);
        chip1Star = findViewById(R.id.chip1Star);

        btnViewReview1 = findViewById(R.id.btnViewReview1);
        btnEditReview1 = findViewById(R.id.btnEditReview1);
        btnViewReview2 = findViewById(R.id.btnViewReview2);
        btnEditReview2 = findViewById(R.id.btnEditReview2);
        btnViewReview3 = findViewById(R.id.btnViewReview3);
        btnEditReview3 = findViewById(R.id.btnEditReview3);
        btnSortOrder = findViewById(R.id.btnSortOrder);
    }

    private void setupEvents() {
        // Nút Back -> Quay về giao diện activity_myactivity.xml (Frame10Myactivity)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(Frame11Dadanhgia.this, Frame10Myactivity.class);
                startActivity(intent);
                finish();
            });
        }

        // Nút Profile
        if (btnProfileHeader != null) {
            btnProfileHeader.setOnClickListener(v -> startActivity(new Intent(this, Frame25MyProfileActivity.class)));
        }

        // Filter Chips
        if (chipAll != null) chipAll.setOnClickListener(v -> Toast.makeText(this, "Lọc: Tất cả", Toast.LENGTH_SHORT).show());
        if (chip5Star != null) chip5Star.setOnClickListener(v -> Toast.makeText(this, "Lọc: 5 sao", Toast.LENGTH_SHORT).show());
        if (chip4Star != null) chip4Star.setOnClickListener(v -> Toast.makeText(this, "Lọc: 4 sao", Toast.LENGTH_SHORT).show());
        if (chip3Star != null) chip3Star.setOnClickListener(v -> Toast.makeText(this, "Lọc: 3 sao", Toast.LENGTH_SHORT).show());
        if (chip2Star != null) chip2Star.setOnClickListener(v -> Toast.makeText(this, "Lọc: 2 sao", Toast.LENGTH_SHORT).show());
        if (chip1Star != null) chip1Star.setOnClickListener(v -> Toast.makeText(this, "Lọc: 1 sao", Toast.LENGTH_SHORT).show());
        if (btnSortOrder != null) {
            btnSortOrder.setOnClickListener(v -> {
                boolean newest = !btnSortOrder.isSelected();
                btnSortOrder.setSelected(newest);
                btnSortOrder.setText(newest ? "Mới nhất trước" : "Cũ nhất trước");
            });
        }

        // Review Item 1 (Phở Bò Hà Nội)
        if (btnViewReview1 != null) {
            btnViewReview1.setOnClickListener(v -> startActivity(new Intent(this, RecipeDetailActivity.class)));
        }
        if (btnEditReview1 != null) {
            btnEditReview1.setOnClickListener(v -> startActivity(new Intent(this, ReviewActivity.class)));
        }

        // Review Item 2 (Sườn Nướng Mật Ong)
        if (btnViewReview2 != null) {
            btnViewReview2.setOnClickListener(v -> startActivity(new Intent(this, RecipeDetailActivity.class)));
        }
        if (btnEditReview2 != null) {
            btnEditReview2.setOnClickListener(v -> startActivity(new Intent(this, ReviewActivity.class)));
        }

        // Review Item 3 (Bún Bò Huế)
        if (btnViewReview3 != null) {
            btnViewReview3.setOnClickListener(v -> startActivity(new Intent(this, RecipeDetailActivity.class)));
        }
        if (btnEditReview3 != null) {
            btnEditReview3.setOnClickListener(v -> startActivity(new Intent(this, ReviewActivity.class)));
        }
    }
}
