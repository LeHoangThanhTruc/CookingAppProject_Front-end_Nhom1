package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.R;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView btnBack, btnShare, btnSaveRecipe, imgInstructionChevron;
    private RelativeLayout layoutInstructionHeader, btnWriteReview;
    private LinearLayout layoutInstructionContent;

    private boolean isInstructionExpanded = false; // Mặc định thu ẩn theo thiết kế

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        btnSaveRecipe = findViewById(R.id.btnSaveRecipe);

        imgInstructionChevron = findViewById(R.id.imgInstructionChevron);
        layoutInstructionHeader = findViewById(R.id.layoutInstructionHeader);
        layoutInstructionContent = findViewById(R.id.layoutInstructionContent);
        btnWriteReview = findViewById(R.id.btnWriteReview);
    }

    private void setupEvents() {
        // 1. Bấm mũi tên quay về -> Mở lại SearchActivity và đóng màn hình hiện tại
        btnBack.setOnClickListener(v -> finish());

        // 2. Nút Chia sẻ công thức
        btnShare.setOnClickListener(v ->
                Toast.makeText(this, "Chia sẻ công thức!", Toast.LENGTH_SHORT).show()
        );

        // 3. Nút Lưu công thức
        btnSaveRecipe.setOnClickListener(v -> {
            boolean selected = !btnSaveRecipe.isSelected();
            btnSaveRecipe.setSelected(selected);
            btnSaveRecipe.setAlpha(selected ? 0.55f : 1f);
            Toast.makeText(this, selected ? "Đã lưu công thức!" : "Đã bỏ lưu công thức!", Toast.LENGTH_SHORT).show();
        });

        // 4. Bật / Tắt hiển thị Hướng dẫn chi tiết bằng Chevron (Thay đổi chevron_down <-> chevron_up)
        layoutInstructionHeader.setOnClickListener(v -> toggleInstructionContent());

        // Bấm nút Tự Đánh Giá -> Chuyển sang Màn hình Đánh giá (ReviewActivity)
        btnWriteReview.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeDetailActivity.this, ReviewActivity.class);
            startActivity(intent);
        });
    }

    private void toggleInstructionContent() {
        if (isInstructionExpanded) {
            // Ẩn nội dung hướng dẫn -> Đổi icon về chevron_down
            layoutInstructionContent.setVisibility(View.GONE);
            imgInstructionChevron.setImageResource(R.drawable.chevron_down);
            isInstructionExpanded = false;
        } else {
            // Hiện nội dung hướng dẫn -> Đổi icon sang chevron_up
            layoutInstructionContent.setVisibility(View.VISIBLE);
            imgInstructionChevron.setImageResource(R.drawable.chevron_up);
            isInstructionExpanded = true;
        }
    }
}
