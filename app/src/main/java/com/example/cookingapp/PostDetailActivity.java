package com.example.cookingapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvTitle = findViewById(R.id.tvTitle);
        RecyclerView rvFullMediaList = findViewById(R.id.rvFullMediaList);

        btnBack.setOnClickListener(v -> finish());

        Post post = (Post) getIntent().getSerializableExtra("POST_DATA");
        if (post != null) {
            if (post.getAuthor() != null) {
                tvTitle.setText("Bài viết của " + post.getAuthor().getFullName());
            }

            // Hiển thị toàn bộ danh sách Ảnh/Video dạng cuộn dọc từ trên xuống dưới
            if (post.getMediaList() != null && !post.getMediaList().isEmpty()) {
                rvFullMediaList.setLayoutManager(new LinearLayoutManager(this));
                FullMediaAdapter adapter = new FullMediaAdapter(this, post.getMediaList());
                rvFullMediaList.setAdapter(adapter);
            }
        }
    }
}
