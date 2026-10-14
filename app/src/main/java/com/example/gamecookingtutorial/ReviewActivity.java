package com.example.gamecookingtutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {

    private ImageView btnDeleteImage, btnDeleteVideo;
    private LinearLayout btnAddPicture, btnAddVideo;
    private LinearLayout layoutMediaPreview;
    private RelativeLayout itemImagePreview, itemVideoPreview;
    private Button btnCancelReview, btnSubmitReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnAddPicture = findViewById(R.id.btnAddPicture);
        btnAddVideo = findViewById(R.id.btnAddVideo);

        layoutMediaPreview = findViewById(R.id.layoutMediaPreview);
        itemImagePreview = findViewById(R.id.itemImagePreview);
        itemVideoPreview = findViewById(R.id.itemVideoPreview);

        btnDeleteImage = findViewById(R.id.btnDeleteImage);
        btnDeleteVideo = findViewById(R.id.btnDeleteVideo);

        btnCancelReview = findViewById(R.id.btnCancelReview);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
    }

    private void setupEvents() {
        // Thêm ảnh đính kèm
        btnAddPicture.setOnClickListener(v -> {
            layoutMediaPreview.setVisibility(View.VISIBLE);
            itemImagePreview.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Đã đính kèm 1 ảnh!", Toast.LENGTH_SHORT).show();
        });

        // Thêm video đính kèm
        btnAddVideo.setOnClickListener(v -> {
            layoutMediaPreview.setVisibility(View.VISIBLE);
            itemVideoPreview.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Đã đính kèm 1 video!", Toast.LENGTH_SHORT).show();
        });

        // Xóa ảnh khi nhấn nút Thùng rác
        btnDeleteImage.setOnClickListener(v -> {
            itemImagePreview.setVisibility(View.GONE);
            checkHideMediaContainer();
            Toast.makeText(this, "Đã xóa ảnh!", Toast.LENGTH_SHORT).show();
        });

        // Xóa video khi nhấn nút Thùng rác
        btnDeleteVideo.setOnClickListener(v -> {
            itemVideoPreview.setVisibility(View.GONE);
            checkHideMediaContainer();
            Toast.makeText(this, "Đã xóa video!", Toast.LENGTH_SHORT).show();
        });

        // Nút Hủy -> Quay về Màn hình Chi tiết công thức
        btnCancelReview.setOnClickListener(v -> navigateBackToDetail());

        // Nút Gửi -> Thông báo & Quay về Màn hình Chi tiết công thức
        btnSubmitReview.setOnClickListener(v -> {
            Toast.makeText(this, "Gửi đánh giá thành công!", Toast.LENGTH_SHORT).show();
            navigateBackToDetail();
        });
    }

    private void checkHideMediaContainer() {
        if (itemImagePreview.getVisibility() == View.GONE && itemVideoPreview.getVisibility() == View.GONE) {
            layoutMediaPreview.setVisibility(View.GONE);
        }
    }

    private void navigateBackToDetail() {
        Intent intent = new Intent(ReviewActivity.this, RecipeDetailActivity.class);
        startActivity(intent);
        finish();
    }
}