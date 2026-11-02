package com.example.cookingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_REGULAR = 1;
    private static final int VIEW_TYPE_RECIPE = 2;

    private final Context context;
    private final List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public int getItemViewType(int position) {
        if ("RECIPE".equals(postList.get(position).getPostType())) {
            return VIEW_TYPE_RECIPE;
        }
        return VIEW_TYPE_REGULAR;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_RECIPE) {
            View view = inflater.inflate(R.layout.item_post_recipe, parent, false);
            return new RecipeViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_post, parent, false);
            return new RegularViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = postList.get(position);
        if (holder instanceof RecipeViewHolder) {
            ((RecipeViewHolder) holder).bind(post, context);
        } else if (holder instanceof RegularViewHolder) {
            ((RegularViewHolder) holder).bind(post, context);
        }
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    // Hàm tính khoảng thời gian đăng bài (Time Ago)
    public static String getTimeAgo(String createdAtStr) {
        if (createdAtStr == null || createdAtStr.isEmpty()) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date postDate = sdf.parse(createdAtStr);
            if (postDate == null) return createdAtStr;
            long timeInMillis = postDate.getTime();
            long now = System.currentTimeMillis();
            long diff = (now - timeInMillis) / 1000; // Đơn vị giây

            if (diff < 60) return "Vừa xong";
            if (diff < 3600) return (diff / 60) + " phút trước";
            if (diff < 86400) return (diff / 3600) + " giờ trước";
            if (diff < 2592000) return (diff / 86400) + " ngày trước";
            return (diff / 2592000) + " tháng trước";
        } catch (Exception e) {
            return createdAtStr;
        }
    }

    // ================= 1. ViewHolder Bài đăng thường =================
    static class RegularViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvCaption, tvLikes, tvTimeAgo, tvShareCount, tvCommentCount, btnToggleCaption;
        ImageView imgAvatar, imgPrivacy, imgLike, imgSave;
        LinearLayout btnLike, btnComment, btnShare, btnSave;

        // Khai báo thêm đầy đủ các biến View cho Media Grid và Bài Share
        FrameLayout containerMedia;
        LinearLayout layoutOriginalPost;
        ImageView imgOrigAvatar, imgOrigPostMedia;
        TextView tvOrigAuthorName, tvOrigCaption, tvOrigTimeAgo;
        ImageView imgOrigPrivacy;
        GridLayout gridMedia;
        ImageView imgMedia1, imgMedia2, imgMedia3, imgMedia4;
        RelativeLayout layoutMediaOverlay;
        TextView tvMoreMediaCount;

        public RegularViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvAuthor = itemView.findViewById(R.id.tvAuthorName);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            btnToggleCaption = itemView.findViewById(R.id.btnToggleCaption);
            tvLikes = itemView.findViewById(R.id.tvLikeCount);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            imgPrivacy = itemView.findViewById(R.id.imgPrivacy);

            // Ánh xạ Khung Media & Bài Share
            containerMedia = itemView.findViewById(R.id.containerMedia);
            layoutOriginalPost = itemView.findViewById(R.id.layoutOriginalPost);
            imgOrigAvatar = itemView.findViewById(R.id.imgOrigAvatar);
            tvOrigAuthorName = itemView.findViewById(R.id.tvOrigAuthorName);
            tvOrigTimeAgo = itemView.findViewById(R.id.tvOrigTimeAgo);
            tvOrigCaption = itemView.findViewById(R.id.tvOrigCaption);
            imgOrigPostMedia = itemView.findViewById(R.id.imgOrigPostMedia);
            imgOrigPrivacy = itemView.findViewById(R.id.imgOrigPrivacy);
            // Ánh xạ Grid Media 4 Ảnh & Lớp phủ +N
            gridMedia = itemView.findViewById(R.id.gridMedia);
            imgMedia1 = itemView.findViewById(R.id.imgMedia1);
            imgMedia2 = itemView.findViewById(R.id.imgMedia2);
            imgMedia3 = itemView.findViewById(R.id.imgMedia3);
            imgMedia4 = itemView.findViewById(R.id.imgMedia4);
            layoutMediaOverlay = itemView.findViewById(R.id.layoutMediaOverlay);
            tvMoreMediaCount = itemView.findViewById(R.id.tvMoreMediaCount);

            // Ánh xạ 4 Nút Tương tác
            btnLike = itemView.findViewById(R.id.btnLike);
            imgLike = itemView.findViewById(R.id.imgLike);

            btnComment = itemView.findViewById(R.id.btnComment);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);

            btnShare = itemView.findViewById(R.id.btnShare);
            tvShareCount = itemView.findViewById(R.id.tvShareCount);

            btnSave = itemView.findViewById(R.id.btnSave);
            imgSave = itemView.findViewById(R.id.imgSave);
        }

        private void setupMediaAndShare(Post post, Context context) {
            boolean hasCaption = post.getCaption() != null && !post.getCaption().trim().isEmpty();
            boolean isShared = "SHARED".equals(post.getPostType()) && post.getOriginalPost() != null;
            boolean hasMedia = post.getMediaList() != null && !post.getMediaList().isEmpty();

            // 1. Kiểm tra Ràng buộc Caption
            if (hasCaption) {
                if (tvCaption != null) {
                    tvCaption.setVisibility(View.VISIBLE);
                    tvCaption.setText(post.getCaption());
                }
            } else {
                if (tvCaption != null) tvCaption.setVisibility(View.GONE);
            }

            // 2. Khung Container Media
            if (containerMedia != null) {
                if (isShared || hasMedia) {
                    containerMedia.setVisibility(View.VISIBLE);
                } else {
                    containerMedia.setVisibility(View.GONE);
                }
            }

            // 3. Nếu là Bài SHARED -> Nằm gọn hoàn toàn bên trong Khung Media
            if (isShared) {
                if (layoutOriginalPost != null) layoutOriginalPost.setVisibility(View.VISIBLE);
                if (gridMedia != null) gridMedia.setVisibility(View.GONE);

                Post orig = post.getOriginalPost();
                if (orig != null) {
                    // 1. Tên & Avatar tác giả gốc
                    if (orig.getAuthor() != null && tvOrigAuthorName != null) {
                        tvOrigAuthorName.setText(orig.getAuthor().getFullName());
                        if (orig.getAuthor().getAvatarUrl() != null && imgOrigAvatar != null) {
                            Glide.with(context).load(orig.getAuthor().getAvatarUrl()).into(imgOrigAvatar);
                        }
                    }

                    // 2. Caption bài gốc
                    if (tvOrigCaption != null) tvOrigCaption.setText(orig.getCaption());

                    // 3. Thời gian bài gốc (Time Ago)
                    if (tvOrigTimeAgo != null && orig.getCreatedAt() != null) {
                        tvOrigTimeAgo.setText(getTimeAgo(orig.getCreatedAt()));
                    }

                    // 4. NẠP QUYỀN XEM ĐỘNG TỪ DATABASE CHO BÀI GỐC
                    if (imgOrigPrivacy != null && orig.getPrivacy() != null) {
                        switch (orig.getPrivacy()) {
                            case "PUBLIC":
                                imgOrigPrivacy.setImageResource(R.drawable.public_globe);
                                break;
                            case "FRIENDS":
                                imgOrigPrivacy.setImageResource(R.drawable.friends);
                                break;
                            case "ONLY_ME":
                                imgOrigPrivacy.setImageResource(R.drawable.ic_lock);
                                break;
                        }
                    }

                    // 5. Media/Ảnh bài gốc
                    if (orig.getMediaList() != null && !orig.getMediaList().isEmpty() && imgOrigPostMedia != null) {
                        imgOrigPostMedia.setVisibility(View.VISIBLE);
                        Glide.with(context).load(orig.getMediaList().get(0).getUrl()).into(imgOrigPostMedia);
                    } else if (imgOrigPostMedia != null) {
                        imgOrigPostMedia.setVisibility(View.GONE);
                    }
                }
            }
            // 4. Bài thường có Media -> Phân bổ Grid và hiển thị lớp phủ +N
            else if (hasMedia) {
                if (layoutOriginalPost != null) layoutOriginalPost.setVisibility(View.GONE);
                if (gridMedia != null) gridMedia.setVisibility(View.VISIBLE);

                List<Post.Media> mediaList = post.getMediaList();
                int total = mediaList.size();

                // Reset trạng thái hiển thị
                if (imgMedia1 != null) imgMedia1.setVisibility(View.GONE);
                if (imgMedia2 != null) imgMedia2.setVisibility(View.GONE);
                if (imgMedia3 != null) imgMedia3.setVisibility(View.GONE);
                if (layoutMediaOverlay != null) layoutMediaOverlay.setVisibility(View.GONE);
                if (tvMoreMediaCount != null) tvMoreMediaCount.setVisibility(View.GONE);

                // Click mở Chi tiết Bài viết
                View.OnClickListener openDetailListener = v -> {
                    Intent intent = new Intent(context, PostDetailActivity.class);
                    intent.putExtra("POST_DATA", post);
                    context.startActivity(intent);
                };

                if (total == 1) {
                    if (imgMedia1 != null) {
                        imgMedia1.setVisibility(View.VISIBLE);
                        Glide.with(context).load(mediaList.get(0).getUrl()).into(imgMedia1);
                        imgMedia1.setOnClickListener(openDetailListener);
                    }
                } else if (total == 2) {
                    if (imgMedia1 != null && imgMedia2 != null) {
                        imgMedia1.setVisibility(View.VISIBLE);
                        imgMedia2.setVisibility(View.VISIBLE);
                        Glide.with(context).load(mediaList.get(0).getUrl()).into(imgMedia1);
                        Glide.with(context).load(mediaList.get(1).getUrl()).into(imgMedia2);
                        imgMedia1.setOnClickListener(openDetailListener);
                        imgMedia2.setOnClickListener(openDetailListener);
                    }
                } else if (total == 3) {
                    if (imgMedia1 != null && imgMedia2 != null && imgMedia3 != null) {
                        imgMedia1.setVisibility(View.VISIBLE);
                        imgMedia2.setVisibility(View.VISIBLE);
                        imgMedia3.setVisibility(View.VISIBLE);
                        Glide.with(context).load(mediaList.get(0).getUrl()).into(imgMedia1);
                        Glide.with(context).load(mediaList.get(1).getUrl()).into(imgMedia2);
                        Glide.with(context).load(mediaList.get(2).getUrl()).into(imgMedia3);
                        imgMedia1.setOnClickListener(openDetailListener);
                        imgMedia2.setOnClickListener(openDetailListener);
                        imgMedia3.setOnClickListener(openDetailListener);
                    }
                } else if (total >= 4) {
                    if (imgMedia1 != null && imgMedia2 != null && imgMedia3 != null && layoutMediaOverlay != null && imgMedia4 != null) {
                        imgMedia1.setVisibility(View.VISIBLE);
                        imgMedia2.setVisibility(View.VISIBLE);
                        imgMedia3.setVisibility(View.VISIBLE);
                        layoutMediaOverlay.setVisibility(View.VISIBLE);

                        Glide.with(context).load(mediaList.get(0).getUrl()).into(imgMedia1);
                        Glide.with(context).load(mediaList.get(1).getUrl()).into(imgMedia2);
                        Glide.with(context).load(mediaList.get(2).getUrl()).into(imgMedia3);
                        Glide.with(context).load(mediaList.get(3).getUrl()).into(imgMedia4);

                        if (total > 4 && tvMoreMediaCount != null) {
                            tvMoreMediaCount.setVisibility(View.VISIBLE);
                            tvMoreMediaCount.setText("+" + (total - 3));
                        }

                        imgMedia1.setOnClickListener(openDetailListener);
                        imgMedia2.setOnClickListener(openDetailListener);
                        imgMedia3.setOnClickListener(openDetailListener);
                        layoutMediaOverlay.setOnClickListener(openDetailListener);
                    }
                }
            } else {
                if (layoutOriginalPost != null) layoutOriginalPost.setVisibility(View.GONE);
                if (gridMedia != null) gridMedia.setVisibility(View.GONE);
            }
        }

        public void bind(Post post, Context context) {
            // 1. Nạp thông tin Tác giả & Avatar
            if (post.getAuthor() != null) {
                if (tvAuthor != null) tvAuthor.setText(post.getAuthor().getFullName());
                if (imgAvatar != null && post.getAuthor().getAvatarUrl() != null) {
                    Glide.with(context)
                            .load(post.getAuthor().getAvatarUrl())
                            .placeholder(R.drawable.bxs_user)
                            .into(imgAvatar);
                }
            }

            // 2. Gọi hàm xử lý Media, Caption & Bài Share
            setupMediaAndShare(post, context);

            // 3. Nạp "Xem thêm / Thu gọn" Caption
            if (tvCaption != null && post.getCaption() != null) {
                tvCaption.post(() -> {
                    if (tvCaption.getLineCount() > 3) {
                        if (btnToggleCaption != null) btnToggleCaption.setVisibility(View.VISIBLE);
                    } else {
                        if (btnToggleCaption != null) btnToggleCaption.setVisibility(View.GONE);
                    }
                });

                if (btnToggleCaption != null) {
                    btnToggleCaption.setOnClickListener(v -> {
                        if (tvCaption.getMaxLines() == 3) {
                            tvCaption.setMaxLines(Integer.MAX_VALUE);
                            btnToggleCaption.setText("Thu gọn");
                        } else {
                            tvCaption.setMaxLines(3);
                            btnToggleCaption.setText("Xem thêm");
                        }
                    });
                }
            }

            // 4. Nạp số liệu Thả tim, Share, Comment
            if (tvLikes != null) tvLikes.setText(String.valueOf(post.getLikeCount()));
            if (tvShareCount != null) tvShareCount.setText(String.valueOf(post.getShareCount()));
            if (tvCommentCount != null) tvCommentCount.setText(String.valueOf(post.getCommentCount()));

            // 5. Nạp thời gian
            if (tvTimeAgo != null) tvTimeAgo.setText(getTimeAgo(post.getCreatedAt()));

            // 6. Đổi biểu tượng Quyền xem
            if (imgPrivacy != null && post.getPrivacy() != null) {
                switch (post.getPrivacy()) {
                    case "PUBLIC":
                        imgPrivacy.setImageResource(R.drawable.public_globe);
                        break;
                    case "FRIENDS":
                        imgPrivacy.setImageResource(R.drawable.friends);
                        break;
                    case "ONLY_ME":
                        imgPrivacy.setImageResource(R.drawable.ic_lock);
                        break;
                }
            }

            // --- Logic Thả tim / Bỏ tim ---
            if (imgLike != null) {
                imgLike.setImageResource(post.isLiked() ? R.drawable.heart_red : R.drawable.heart);
                View.OnClickListener likeClickListener = v -> {
                    int currentLikes = post.getLikeCount();
                    if (post.isLiked()) {
                        post.setLiked(false);
                        post.setLikeCount(Math.max(0, currentLikes - 1));
                        imgLike.setImageResource(R.drawable.heart);
                    } else {
                        post.setLiked(true);
                        post.setLikeCount(currentLikes + 1);
                        imgLike.setImageResource(R.drawable.heart_red);
                    }
                    if (tvLikes != null) tvLikes.setText(String.valueOf(post.getLikeCount()));

                    int currentUserId = 1;
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    apiService.toggleLike(post.getPostId(), currentUserId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {}

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            post.setLiked(!post.isLiked());
                            post.setLikeCount(currentLikes);
                            if (tvLikes != null) tvLikes.setText(String.valueOf(currentLikes));
                            imgLike.setImageResource(post.isLiked() ? R.drawable.heart_red : R.drawable.heart);
                        }
                    });
                };
                imgLike.setOnClickListener(likeClickListener);
                if (btnLike != null) btnLike.setOnClickListener(likeClickListener);
            }

            // --- Logic Nút Comment, Share & Save ---
            if (btnComment != null) {
                btnComment.setOnClickListener(v ->
                        Toast.makeText(context, "Mở khung bình luận...", Toast.LENGTH_SHORT).show());
            }

            if (btnShare != null) {
                btnShare.setOnClickListener(v -> {
                    if (context instanceof androidx.fragment.app.FragmentActivity) {
                        ShareBottomSheetDialogFragment shareSheet = new ShareBottomSheetDialogFragment(post.getPostId());
                        shareSheet.show(((androidx.fragment.app.FragmentActivity) context).getSupportFragmentManager(), "ShareSheet");
                    }
                });
            }

            if (btnSave != null) {
                btnSave.setOnClickListener(v ->
                        Toast.makeText(context, "Đã lưu bài viết!", Toast.LENGTH_SHORT).show());
            }
        }
    }

    // ================= 2. ViewHolder Bài đăng Công thức =================
    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvRecipeTitle, tvRecipeDescription, btnToggleRecipe, tvTimeAgo, tvShareCount, tvLikes, tvCommentCount, btnToggleCaption;
        ImageView imgAvatar, imgPrivacy, imgLike, imgSave;
        LinearLayout layoutRecipeDetails, btnLike, btnComment, btnShare, btnSave;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvAuthor = itemView.findViewById(R.id.tvAuthorName);
            tvRecipeTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvRecipeDescription = itemView.findViewById(R.id.tvRecipeDescription);
            btnToggleCaption = itemView.findViewById(R.id.btnToggleCaption);
            btnToggleRecipe = itemView.findViewById(R.id.btnToggleRecipe);
            layoutRecipeDetails = itemView.findViewById(R.id.layoutRecipeDetails);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            imgPrivacy = itemView.findViewById(R.id.imgPrivacy);

            btnLike = itemView.findViewById(R.id.btnLike);
            imgLike = itemView.findViewById(R.id.imgLike);
            tvLikes = itemView.findViewById(R.id.tvLikeCount);

            btnComment = itemView.findViewById(R.id.btnComment);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);

            btnShare = itemView.findViewById(R.id.btnShare);
            tvShareCount = itemView.findViewById(R.id.tvShareCount);

            btnSave = itemView.findViewById(R.id.btnSave);
            imgSave = itemView.findViewById(R.id.imgSave);
        }

        public void bind(Post post, Context context) {
            if (post.getAuthor() != null) {
                if (tvAuthor != null) tvAuthor.setText(post.getAuthor().getFullName());
                if (imgAvatar != null && post.getAuthor().getAvatarUrl() != null) {
                    Glide.with(context)
                            .load(post.getAuthor().getAvatarUrl())
                            .placeholder(R.drawable.bxs_user)
                            .into(imgAvatar);
                }
            }

            if (post.getRecipeDetails() != null && tvRecipeTitle != null) {
                tvRecipeTitle.setText(post.getRecipeDetails().getTitle());
            }
            if (tvRecipeDescription != null) tvRecipeDescription.setText(post.getCaption());

            if (tvLikes != null) tvLikes.setText(String.valueOf(post.getLikeCount()));
            if (tvShareCount != null) tvShareCount.setText(String.valueOf(post.getShareCount()));
            if (tvCommentCount != null) tvCommentCount.setText(String.valueOf(post.getCommentCount()));
            if (tvTimeAgo != null) tvTimeAgo.setText(getTimeAgo(post.getCreatedAt()));

            if (imgPrivacy != null && post.getPrivacy() != null) {
                switch (post.getPrivacy()) {
                    case "PUBLIC":
                        imgPrivacy.setImageResource(R.drawable.public_globe);
                        break;
                    case "FRIENDS":
                        imgPrivacy.setImageResource(R.drawable.friends);
                        break;
                    case "ONLY_ME":
                        imgPrivacy.setImageResource(R.drawable.ic_lock);
                        break;
                }
            }

            // Logic Ẩn/Hiện Chi tiết Công thức
            if (btnToggleRecipe != null && layoutRecipeDetails != null) {
                btnToggleRecipe.setOnClickListener(v -> {
                    if (layoutRecipeDetails.getVisibility() == View.GONE) {
                        layoutRecipeDetails.setVisibility(View.VISIBLE);
                        btnToggleRecipe.setText("Thu gọn công thức 🔼");
                    } else {
                        layoutRecipeDetails.setVisibility(View.GONE);
                        btnToggleRecipe.setText("Xem chi tiết công thức 🔽");
                    }
                });
            }

            // Logic Thả tim
            if (imgLike != null) {
                imgLike.setImageResource(post.isLiked() ? R.drawable.heart_red : R.drawable.heart);
                View.OnClickListener likeClickListener = v -> {
                    int currentLikes = post.getLikeCount();
                    if (post.isLiked()) {
                        post.setLiked(false);
                        post.setLikeCount(Math.max(0, currentLikes - 1));
                        imgLike.setImageResource(R.drawable.heart);
                    } else {
                        post.setLiked(true);
                        post.setLikeCount(currentLikes + 1);
                        imgLike.setImageResource(R.drawable.heart_red);
                    }
                    if (tvLikes != null) tvLikes.setText(String.valueOf(post.getLikeCount()));

                    int currentUserId = 1;
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    apiService.toggleLike(post.getPostId(), currentUserId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {}

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            post.setLiked(!post.isLiked());
                            post.setLikeCount(currentLikes);
                            if (tvLikes != null) tvLikes.setText(String.valueOf(currentLikes));
                            imgLike.setImageResource(post.isLiked() ? R.drawable.heart_red : R.drawable.heart);
                        }
                    });
                };
                imgLike.setOnClickListener(likeClickListener);
                if (btnLike != null) btnLike.setOnClickListener(likeClickListener);
            }

            // Logic Nút Comment, Share & Save
            if (btnComment != null) {
                btnComment.setOnClickListener(v ->
                        Toast.makeText(context, "Mở khung bình luận...", Toast.LENGTH_SHORT).show());
            }

            if (btnShare != null) {
                btnShare.setOnClickListener(v -> {
                    if (context instanceof androidx.fragment.app.FragmentActivity) {
                        ShareBottomSheetDialogFragment shareSheet = new ShareBottomSheetDialogFragment(post.getPostId());
                        shareSheet.show(((androidx.fragment.app.FragmentActivity) context).getSupportFragmentManager(), "ShareSheet");
                    }
                });
            }

            if (btnSave != null) {
                btnSave.setOnClickListener(v ->
                        Toast.makeText(context, "Đã lưu bài viết!", Toast.LENGTH_SHORT).show());
            }
        }
    }
}