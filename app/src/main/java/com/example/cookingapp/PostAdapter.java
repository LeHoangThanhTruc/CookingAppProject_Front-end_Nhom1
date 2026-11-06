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

import androidx.activity.result.ActivityResultLauncher;
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
import android.net.Uri;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_REGULAR = 1;
    private static final int VIEW_TYPE_RECIPE = 2;

    private final Context context;
    private final List<Post> postList;
    //  1. Khai báo biến launcher để nhận từ Activity truyền vào
    private ActivityResultLauncher<Intent> postDetailLauncher;

    // Constructor gốc (Giữ nguyên để tương thích code cũ)
    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    // 2. BỔ SUNG Constructor mới có nhận ActivityResultLauncher
    public PostAdapter(Context context, List<Post> postList, ActivityResultLauncher<Intent> postDetailLauncher) {
        this.context = context;
        this.postList = postList;
        this.postDetailLauncher = postDetailLauncher;
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
            ((RecipeViewHolder) holder).bind(post, context, postDetailLauncher);
        } else if (holder instanceof RegularViewHolder) {
            ((RegularViewHolder) holder).bind(post, context, postDetailLauncher);
        }
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    // Cập nhật riêng số lượng comment
    public void updateCommentCount(int postId, int newCommentCount) {
        if (postList != null) {
            for (int i = 0; i < postList.size(); i++) {
                Post post = postList.get(i);
                if (post.getPostId() == postId) {
                    post.setCommentCount(newCommentCount);
                    notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    //  3. BỔ SUNG: Cập nhật tổng thể cả Like và Comment từ PostDetailActivity trả về
    public void updatePostStats(int postId, int newCommentCount, int newLikeCount, boolean isLiked) {
        if (postList != null) {
            for (int i = 0; i < postList.size(); i++) {
                Post p = postList.get(i);
                if (p.getPostId() == postId) {
                    if (newCommentCount != -1) p.setCommentCount(newCommentCount);
                    if (newLikeCount != -1) p.setLikeCount(newLikeCount);
                    p.setLiked(isLiked);
                    notifyItemChanged(i); // Refresh riêng dòng này
                    break;
                }
            }
        }
    }

    public static String getTimeAgo(String createdAtStr) {
        if (createdAtStr == null || createdAtStr.isEmpty()) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date postDate = sdf.parse(createdAtStr);
            if (postDate == null) return createdAtStr;
            long timeInMillis = postDate.getTime();
            long now = System.currentTimeMillis();
            long diff = (now - timeInMillis) / 1000;

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

        FrameLayout containerMedia;
        LinearLayout layoutOriginalPost;
        ImageView imgOrigAvatar, imgOrigPostMedia;
        TextView tvOrigAuthorName, tvOrigCaption, tvOrigTimeAgo;
        ImageView imgOrigPrivacy;
        GridLayout gridMedia;
        ImageView imgMedia1, imgMedia2, imgMedia3, imgMedia4;
        RelativeLayout layoutMediaOverlay;
        TextView tvMoreMediaCount;
        LinearLayout layoutAttachmentFile;
        TextView tvAttachmentUrl;

        public RegularViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvAuthor = itemView.findViewById(R.id.tvAuthorName);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            btnToggleCaption = itemView.findViewById(R.id.btnToggleCaption);
            tvLikes = itemView.findViewById(R.id.tvLikeCount);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            imgPrivacy = itemView.findViewById(R.id.imgPrivacy);

            containerMedia = itemView.findViewById(R.id.containerMedia);
            layoutOriginalPost = itemView.findViewById(R.id.layoutOriginalPost);
            imgOrigAvatar = itemView.findViewById(R.id.imgOrigAvatar);
            tvOrigAuthorName = itemView.findViewById(R.id.tvOrigAuthorName);
            tvOrigTimeAgo = itemView.findViewById(R.id.tvOrigTimeAgo);
            tvOrigCaption = itemView.findViewById(R.id.tvOrigCaption);
            imgOrigPostMedia = itemView.findViewById(R.id.imgOrigPostMedia);
            imgOrigPrivacy = itemView.findViewById(R.id.imgOrigPrivacy);

            gridMedia = itemView.findViewById(R.id.gridMedia);
            imgMedia1 = itemView.findViewById(R.id.imgMedia1);
            imgMedia2 = itemView.findViewById(R.id.imgMedia2);
            imgMedia3 = itemView.findViewById(R.id.imgMedia3);
            imgMedia4 = itemView.findViewById(R.id.imgMedia4);
            layoutMediaOverlay = itemView.findViewById(R.id.layoutMediaOverlay);
            tvMoreMediaCount = itemView.findViewById(R.id.tvMoreMediaCount);

            btnLike = itemView.findViewById(R.id.btnLike);
            imgLike = itemView.findViewById(R.id.imgLike);

            btnComment = itemView.findViewById(R.id.btnComment);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);

            btnShare = itemView.findViewById(R.id.btnShare);
            tvShareCount = itemView.findViewById(R.id.tvShareCount);

            btnSave = itemView.findViewById(R.id.btnSave);
            imgSave = itemView.findViewById(R.id.imgSave);
            layoutAttachmentFile = itemView.findViewById(R.id.layoutAttachmentFile);
            tvAttachmentUrl = itemView.findViewById(R.id.tvAttachmentUrl);
        }

        private void setupMediaAndShare(Post post, Context context, ActivityResultLauncher<Intent> launcher) {
            boolean hasCaption = post.getCaption() != null && !post.getCaption().trim().isEmpty();
            boolean isShared = "SHARED".equals(post.getPostType()) && post.getOriginalPost() != null;
            boolean hasMedia = post.getMediaList() != null && !post.getMediaList().isEmpty();

            if (hasCaption) {
                if (tvCaption != null) {
                    tvCaption.setVisibility(View.VISIBLE);
                    tvCaption.setText(post.getCaption());
                }
            } else {
                if (tvCaption != null) tvCaption.setVisibility(View.GONE);
            }

            if (containerMedia != null) {
                containerMedia.setVisibility((isShared || hasMedia) ? View.VISIBLE : View.GONE);
            }

            if (isShared) {
                if (layoutOriginalPost != null) layoutOriginalPost.setVisibility(View.VISIBLE);
                if (gridMedia != null) gridMedia.setVisibility(View.GONE);

                Post orig = post.getOriginalPost();
                if (orig != null) {
                    if (orig.getAuthor() != null && tvOrigAuthorName != null) {
                        tvOrigAuthorName.setText(orig.getAuthor().getFullName());
                        if (orig.getAuthor().getAvatarUrl() != null && imgOrigAvatar != null) {
                            Glide.with(context).load(orig.getAuthor().getAvatarUrl()).into(imgOrigAvatar);
                        }
                    }

                    if (tvOrigCaption != null) tvOrigCaption.setText(orig.getCaption());

                    if (tvOrigTimeAgo != null && orig.getCreatedAt() != null) {
                        tvOrigTimeAgo.setText(getTimeAgo(orig.getCreatedAt()));
                    }

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

                    if (orig.getMediaList() != null && !orig.getMediaList().isEmpty() && imgOrigPostMedia != null) {
                        imgOrigPostMedia.setVisibility(View.VISIBLE);
                        Post.Media origMedia = orig.getMediaList().get(0);
                        Glide.with(context).load(origMedia.getUrl()).into(imgOrigPostMedia);
                        imgOrigPostMedia.setOnClickListener(v -> handleMediaClick(context, origMedia, orig, launcher));
                    } else if (imgOrigPostMedia != null) {
                        imgOrigPostMedia.setVisibility(View.GONE);
                    }
                }
            } else if (hasMedia) {
                if (layoutOriginalPost != null) layoutOriginalPost.setVisibility(View.GONE);
                List<Post.Media> mediaList = post.getMediaList();
                Post.Media firstMedia = mediaList.get(0);
                String mediaType = firstMedia.getMediaType() != null ? firstMedia.getMediaType().toUpperCase() : "IMAGE";

                if (!mediaType.contains("IMAGE") && !mediaType.contains("VIDEO")) {
                    if (gridMedia != null) gridMedia.setVisibility(View.GONE);
                    if (layoutAttachmentFile != null && tvAttachmentUrl != null) {
                        layoutAttachmentFile.setVisibility(View.VISIBLE);
                        tvAttachmentUrl.setText(firstMedia.getUrl());
                        layoutAttachmentFile.setOnClickListener(v -> handleMediaClick(context, firstMedia, post, launcher));
                    }
                    return;
                }
                if (gridMedia != null) gridMedia.setVisibility(View.VISIBLE);
                if (layoutAttachmentFile != null) layoutAttachmentFile.setVisibility(View.GONE);

                resetMediaViews();
                int total = mediaList.size();

                if (total == 1) {
                    bindMediaItem(context, mediaList.get(0), imgMedia1, post, launcher);
                } else if (total == 2) {
                    bindMediaItem(context, mediaList.get(0), imgMedia1, post, launcher);
                    bindMediaItem(context, mediaList.get(1), imgMedia2, post, launcher);
                } else if (total == 3) {
                    bindMediaItem(context, mediaList.get(0), imgMedia1, post, launcher);
                    bindMediaItem(context, mediaList.get(1), imgMedia2, post, launcher);
                    bindMediaItem(context, mediaList.get(2), imgMedia3, post, launcher);
                } else if (total >= 4) {
                    bindMediaItem(context, mediaList.get(0), imgMedia1, post, launcher);
                    bindMediaItem(context, mediaList.get(1), imgMedia2, post, launcher);
                    bindMediaItem(context, mediaList.get(2), imgMedia3, post, launcher);
                    bindMediaItem(context, mediaList.get(3), imgMedia4, post, launcher);

                    if (layoutMediaOverlay != null) layoutMediaOverlay.setVisibility(View.VISIBLE);
                    if (total > 4 && tvMoreMediaCount != null) {
                        tvMoreMediaCount.setVisibility(View.VISIBLE);
                        tvMoreMediaCount.setText("+" + (total - 3));
                    }
                    if (layoutMediaOverlay != null) {
                        layoutMediaOverlay.setOnClickListener(v -> handleMediaClick(context, mediaList.get(3), post, launcher));
                    }
                }
            } else {
                if (layoutOriginalPost != null) layoutOriginalPost.setVisibility(View.GONE);
                if (gridMedia != null) gridMedia.setVisibility(View.GONE);
            }
        }

        private void bindMediaItem(Context context, Post.Media media, ImageView imageView, Post post, ActivityResultLauncher<Intent> launcher) {
            if (imageView == null) return;
            imageView.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(media.getUrl())
                    .placeholder(R.drawable.public_globe)
                    .into(imageView);

            String type = media.getMediaType() != null ? media.getMediaType().toUpperCase() : "";
            if (type.contains("VIDEO") || media.getUrl().toLowerCase().endsWith(".mp4")) {
                android.graphics.drawable.Drawable playIcon = androidx.core.content.ContextCompat.getDrawable(context, android.R.drawable.ic_media_play);
                if (playIcon != null) {
                    android.graphics.drawable.LayerDrawable layerDrawable = new android.graphics.drawable.LayerDrawable(new android.graphics.drawable.Drawable[]{playIcon});
                    int sizeInPx = (int) (48 * context.getResources().getDisplayMetrics().density);
                    layerDrawable.setLayerSize(0, sizeInPx, sizeInPx);
                    layerDrawable.setLayerGravity(0, android.view.Gravity.CENTER);
                    imageView.setForeground(layerDrawable);
                }
            } else {
                imageView.setForeground(null);
            }

            imageView.setOnClickListener(v -> handleMediaClick(context, media, post, launcher));
        }

        //4. BỔ SUNG: Mở PostDetailActivity bằng Launcher để hứng kết quả khi quay lại
        private void handleMediaClick(Context context, Post.Media media, Post post, ActivityResultLauncher<Intent> launcher) {
            if (context == null || media == null || media.getUrl() == null) return;

            String url = media.getUrl();
            String type = media.getMediaType() != null ? media.getMediaType().toUpperCase() : "";

            if (type.contains("VIDEO") || url.toLowerCase().endsWith(".mp4") || url.contains("xlsx")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), url.toLowerCase().endsWith(".mp4") ? "video/*" : "*/*");
                    context.startActivity(intent);
                } catch (Exception e) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                }
            } else {
                try {
                    Intent intent = new Intent(context, PostDetailActivity.class);
                    intent.putExtra("POST_DATA", post);
                    if (launcher != null) {
                        launcher.launch(intent); //  Gọi mở qua Launcher để hứng kết quả
                    } else {
                        context.startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Không thể mở chi tiết bài viết", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void resetMediaViews() {
            if (imgMedia1 != null) imgMedia1.setVisibility(View.GONE);
            if (imgMedia2 != null) imgMedia2.setVisibility(View.GONE);
            if (imgMedia3 != null) imgMedia3.setVisibility(View.GONE);
            if (imgMedia4 != null) imgMedia4.setVisibility(View.GONE);

            if (layoutMediaOverlay != null) layoutMediaOverlay.setVisibility(View.GONE);
            if (tvMoreMediaCount != null) tvMoreMediaCount.setVisibility(View.GONE);
        }

        public void bind(Post post, Context context, ActivityResultLauncher<Intent> launcher) {
            if (post.getAuthor() != null) {
                if (tvAuthor != null) tvAuthor.setText(post.getAuthor().getFullName());
                if (imgAvatar != null && post.getAuthor().getAvatarUrl() != null) {
                    Glide.with(context)
                            .load(post.getAuthor().getAvatarUrl())
                            .placeholder(R.drawable.bxs_user)
                            .into(imgAvatar);
                }
            }

            setupMediaAndShare(post, context, launcher);

            if (tvLikes != null) tvLikes.setText(String.valueOf(post.getLikeCount()));
            if (tvShareCount != null) tvShareCount.setText(String.valueOf(post.getShareCount()));
            if (tvCommentCount != null) tvCommentCount.setText(String.valueOf(post.getCommentCount()));
            if (tvTimeAgo != null) tvTimeAgo.setText(getTimeAgo(post.getCreatedAt()));

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

            if (btnComment != null) {
                btnComment.setOnClickListener(v -> {
                    if (context instanceof androidx.fragment.app.FragmentActivity) {
                        CommentBottomSheetDialogFragment commentSheet = new CommentBottomSheetDialogFragment(post.getPostId());
                        commentSheet.show(((androidx.fragment.app.FragmentActivity) context).getSupportFragmentManager(), "CommentSheet");
                    }
                });
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

        public void bind(Post post, Context context, ActivityResultLauncher<Intent> launcher) {
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

            if (btnComment != null) {
                btnComment.setOnClickListener(v -> {
                    if (context instanceof androidx.fragment.app.FragmentActivity) {
                        CommentBottomSheetDialogFragment commentSheet = new CommentBottomSheetDialogFragment(post.getPostId());
                        commentSheet.show(((androidx.fragment.app.FragmentActivity) context).getSupportFragmentManager(), "CommentSheet");
                    }
                });
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