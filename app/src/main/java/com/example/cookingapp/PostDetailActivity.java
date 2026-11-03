package com.example.cookingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {

    private Post post;
    private ImageView imgHeaderLike;
    private TextView tvHeaderLikeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvTitle = findViewById(R.id.tvTitle);
        RecyclerView rvFullMediaList = findViewById(R.id.rvFullMediaList);

        // Header Views
        ImageView imgHeaderAvatar = findViewById(R.id.imgHeaderAvatar);
        TextView tvHeaderAuthor = findViewById(R.id.tvHeaderAuthor);
        TextView tvHeaderTimeAgo = findViewById(R.id.tvHeaderTimeAgo);
        ImageView imgHeaderPrivacy = findViewById(R.id.imgHeaderPrivacy);
        TextView tvHeaderCaption = findViewById(R.id.tvHeaderCaption);
        TextView btnHeaderToggleCaption = findViewById(R.id.btnHeaderToggleCaption);

        LinearLayout btnHeaderLike = findViewById(R.id.btnHeaderLike);
        imgHeaderLike = findViewById(R.id.imgHeaderLike);
        tvHeaderLikeCount = findViewById(R.id.tvHeaderLikeCount);

        LinearLayout btnHeaderComment = findViewById(R.id.btnHeaderComment);
        TextView tvHeaderCommentCount = findViewById(R.id.tvHeaderCommentCount);

        LinearLayout btnHeaderShare = findViewById(R.id.btnHeaderShare);
        TextView tvHeaderShareCount = findViewById(R.id.tvHeaderShareCount);

        btnBack.setOnClickListener(v -> finish());

        post = (Post) getIntent().getSerializableExtra("POST_DATA");
        if (post != null) {
            // 1. Tên trên Toolbar
            if (post.getAuthor() != null) {
                tvTitle.setText("Bài viết của " + post.getAuthor().getFullName());
                tvHeaderAuthor.setText(post.getAuthor().getFullName());
                if (post.getAuthor().getAvatarUrl() != null) {
                    Glide.with(this).load(post.getAuthor().getAvatarUrl()).into(imgHeaderAvatar);
                }
            }

            // 2. Thời gian
            if (post.getCreatedAt() != null) {
                tvHeaderTimeAgo.setText(PostAdapter.getTimeAgo(post.getCreatedAt()));
            }

            // 3. Biểu tượng Quyền xem chính xác
            if (post.getPrivacy() != null) {
                switch (post.getPrivacy().toUpperCase()) {
                    case "PUBLIC":
                        imgHeaderPrivacy.setImageResource(R.drawable.public_globe);
                        break;
                    case "FRIENDS":
                        imgHeaderPrivacy.setImageResource(R.drawable.friends);
                        break;
                    case "ONLY_ME":
                        imgHeaderPrivacy.setImageResource(R.drawable.ic_lock);
                        break;
                }
            }

            // 4. Caption
            if (post.getCaption() != null && !post.getCaption().trim().isEmpty()) {
                tvHeaderCaption.setVisibility(View.VISIBLE);
                tvHeaderCaption.setText(post.getCaption());
            }

            // 5. Đồng bộ Số liệu Thích, Bình luận, Chia sẻ Header chính
            updateHeaderLikeUI();
            if (tvHeaderCommentCount != null) tvHeaderCommentCount.setText(String.valueOf(post.getCommentCount()));
            if (tvHeaderShareCount != null) tvHeaderShareCount.setText(String.valueOf(post.getShareCount()));

            // Logic Thả tim Header (Gom chung API như ở Newsfeed)
            View.OnClickListener likeClickListener = v -> {
                int currentLikes = post.getLikeCount();
                if (post.isLiked()) {
                    post.setLiked(false);
                    post.setLikeCount(Math.max(0, currentLikes - 1));
                } else {
                    post.setLiked(true);
                    post.setLikeCount(currentLikes + 1);
                }
                updateHeaderLikeUI();

                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                apiService.toggleLike(post.getPostId(), 1).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {}

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        post.setLiked(!post.isLiked());
                        post.setLikeCount(currentLikes);
                        updateHeaderLikeUI();
                    }
                });
            };
            btnHeaderLike.setOnClickListener(likeClickListener);

            btnHeaderShare.setOnClickListener(v -> {
                ShareBottomSheetDialogFragment shareSheet = new ShareBottomSheetDialogFragment(post.getPostId());
                shareSheet.show(getSupportFragmentManager(), "ShareSheet");
            });

            // 6. Danh sách Media
            if (post.getMediaList() != null && !post.getMediaList().isEmpty()) {
                rvFullMediaList.setLayoutManager(new LinearLayoutManager(this));
                FullMediaAdapter adapter = new FullMediaAdapter(this, post.getMediaList(), post);
                rvFullMediaList.setAdapter(adapter);
            }
        }
    }

    private void updateHeaderLikeUI() {
        if (imgHeaderLike != null) {
            imgHeaderLike.setImageResource(post.isLiked() ? R.drawable.heart_red : R.drawable.heart);
        }
        if (tvHeaderLikeCount != null) {
            tvHeaderLikeCount.setText(post.getLikeCount() > 0 ? String.valueOf(post.getLikeCount()) : "Thích");
        }
    }
}