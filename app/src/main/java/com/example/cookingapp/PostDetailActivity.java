package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity implements OnCommentCountChangeListener {
    private Post post;
    private ImageView imgHeaderLike;
    private TextView tvHeaderLikeCount, tvHeaderCommentCount;
    private FullMediaAdapter fullMediaAdapter;
    private RecyclerView rvFullMediaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvTitle = findViewById(R.id.tvTitle);
        rvFullMediaList = findViewById(R.id.rvFullMediaList);

        // Header Views
        ImageView imgHeaderAvatar = findViewById(R.id.imgHeaderAvatar);
        TextView tvHeaderAuthor = findViewById(R.id.tvHeaderAuthor);
        TextView tvHeaderTimeAgo = findViewById(R.id.tvHeaderTimeAgo);
        ImageView imgHeaderPrivacy = findViewById(R.id.imgHeaderPrivacy);
        TextView tvHeaderCaption = findViewById(R.id.tvHeaderCaption);

        LinearLayout btnHeaderLike = findViewById(R.id.btnHeaderLike);
        imgHeaderLike = findViewById(R.id.imgHeaderLike);
        tvHeaderLikeCount = findViewById(R.id.tvHeaderLikeCount);

        LinearLayout btnHeaderComment = findViewById(R.id.btnHeaderComment);
        tvHeaderCommentCount = findViewById(R.id.tvHeaderCommentCount);

        LinearLayout btnHeaderShare = findViewById(R.id.btnHeaderShare);
        TextView tvHeaderShareCount = findViewById(R.id.tvHeaderShareCount);

        btnBack.setOnClickListener(v -> finishWithResult());

        post = (Post) getIntent().getSerializableExtra("POST_DATA");
        if (post != null) {
            // 1. Đổ dữ liệu tạm thời từ Intent lên UI cho mượt
            bindPostDataToUI(tvTitle, tvHeaderAuthor, imgHeaderAvatar, tvHeaderTimeAgo,
                    imgHeaderPrivacy, tvHeaderCaption, tvHeaderShareCount);

            // 🟢 2. GỌI API LẤY DỮ LIỆU THỜI GIAN THỰC TỪ DATABASE KHI MỞ TRANG
            fetchPostDetailFromDatabase();

            // Sự kiện Bình luận Header
            btnHeaderComment.setOnClickListener(v -> {
                CommentBottomSheetDialogFragment cmtSheet = new CommentBottomSheetDialogFragment(post.getPostId());
                cmtSheet.show(getSupportFragmentManager(), "PostHeaderCommentSheet");
            });

            // Logic Thả tim Header
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
        }
    }

    // HÀM GỌI API LẤY TRỰC TIẾP DỮ LIỆU TỪ DATABASE VỀ MỖI KHI MỞ TRANG
    public void fetchPostDetailFromDatabase() {
        if (post == null) return;

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getPostDetail(post.getPostId(), 1).enqueue(new Callback<PostDetailResponse>() {
            @Override
            public void onResponse(Call<PostDetailResponse> call, Response<PostDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Post updatedPost = response.body().getPost();
                    if (updatedPost != null) {
                        // Cập nhật các chỉ số Like/Comment mới nhất từ DB vào đối tượng post
                        post.setLikeCount(updatedPost.getLikeCount());
                        post.setCommentCount(updatedPost.getCommentCount());
                        post.setLiked(updatedPost.isLiked());

                        // Cập nhật lại UI Header
                        updateHeaderLikeUI();
                        if (tvHeaderCommentCount != null) {
                            tvHeaderCommentCount.setText(String.valueOf(post.getCommentCount()));
                        }

                        // Cập nhật danh sách Media với lượt tim và comment chuẩn từ DB
                        if (updatedPost.getMediaList() != null) {
                            post.setMediaList(updatedPost.getMediaList());
                            rvFullMediaList.setLayoutManager(new LinearLayoutManager(PostDetailActivity.this));
                            fullMediaAdapter = new FullMediaAdapter(PostDetailActivity.this, post.getMediaList(), post);
                            rvFullMediaList.setAdapter(fullMediaAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PostDetailResponse> call, Throwable t) {
                // Nếu lỗi kết nối thì giữ nguyên dữ liệu tĩnh từ Intent
            }
        });
    }

    private void bindPostDataToUI(TextView tvTitle, TextView tvHeaderAuthor, ImageView imgHeaderAvatar,
                                  TextView tvHeaderTimeAgo, ImageView imgHeaderPrivacy,
                                  TextView tvHeaderCaption, TextView tvHeaderShareCount) {
        if (post.getAuthor() != null) {
            tvTitle.setText("Bài viết của " + post.getAuthor().getFullName());
            tvHeaderAuthor.setText(post.getAuthor().getFullName());
            if (post.getAuthor().getAvatarUrl() != null) {
                Glide.with(this).load(post.getAuthor().getAvatarUrl()).into(imgHeaderAvatar);
            }
        }

        if (post.getCreatedAt() != null) {
            tvHeaderTimeAgo.setText(PostAdapter.getTimeAgo(post.getCreatedAt()));
        }

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

        if (post.getCaption() != null && !post.getCaption().trim().isEmpty()) {
            tvHeaderCaption.setVisibility(View.VISIBLE);
            tvHeaderCaption.setText(post.getCaption());
        }

        updateHeaderLikeUI();
        if (tvHeaderCommentCount != null) tvHeaderCommentCount.setText(String.valueOf(post.getCommentCount()));
        if (tvHeaderShareCount != null) tvHeaderShareCount.setText(String.valueOf(post.getShareCount()));

        if (post.getMediaList() != null && !post.getMediaList().isEmpty()) {
            rvFullMediaList.setLayoutManager(new LinearLayoutManager(this));
            fullMediaAdapter = new FullMediaAdapter(this, post.getMediaList(), post);
            rvFullMediaList.setAdapter(fullMediaAdapter);
        }
    }

    private void finishWithResult() {
        if (post != null) {
            Intent intent = new Intent();
            intent.putExtra("POST_ID", post.getPostId());
            intent.putExtra("NEW_COMMENT_COUNT", post.getCommentCount());
            intent.putExtra("NEW_LIKE_COUNT", post.getLikeCount());
            intent.putExtra("IS_LIKED", post.isLiked());
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        finishWithResult();
        super.onBackPressed();
    }

    private void updateHeaderLikeUI() {
        if (imgHeaderLike != null) {
            imgHeaderLike.setImageResource(post.isLiked() ? R.drawable.heart_red : R.drawable.heart);
        }
        if (tvHeaderLikeCount != null) {
            tvHeaderLikeCount.setText(post.getLikeCount() > 0 ? String.valueOf(post.getLikeCount()) : "Thích");
        }
    }

    @Override
    public void onCommentCountChanged(int postId, int newCount) {
        if (post != null && post.getPostId() == postId) {
            post.setCommentCount(newCount);
            if (tvHeaderCommentCount != null) {
                tvHeaderCommentCount.setText(String.valueOf(newCount));
            }
        }
    }

    @Override
    public void onMediaCommentCountChanged(int mediaId, int newCount) {
        if (fullMediaAdapter != null) {
            fullMediaAdapter.updateMediaCommentCount(mediaId, newCount);
        }
    }
}