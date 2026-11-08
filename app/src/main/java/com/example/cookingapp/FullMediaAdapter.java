package com.example.cookingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullMediaAdapter extends RecyclerView.Adapter<FullMediaAdapter.MediaViewHolder> {

    private final Context context;
    private final List<Post.Media> mediaList;
    private final Post parentPost;

    public FullMediaAdapter(Context context, List<Post.Media> mediaList, Post parentPost) {
        this.context = context;
        this.mediaList = mediaList;
        this.parentPost = parentPost;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_full_media, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        Post.Media media = mediaList.get(position);

        // Load media
        Glide.with(context)
                .load(media.getUrl())
                .placeholder(R.drawable.bg_figma_card)
                .into(holder.imgMedia);

        String type = media.getMediaType() != null ? media.getMediaType().toUpperCase() : "";
        boolean isVideo = type.contains("VIDEO") || media.getUrl().toLowerCase().endsWith(".mp4");

        if (isVideo) {
            holder.imgMedia.setForeground(ContextCompat.getDrawable(context, android.R.drawable.ic_media_play));
        } else {
            holder.imgMedia.setForeground(null);
        }

        holder.imgMedia.setOnClickListener(v -> {
            if (isVideo) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(media.getUrl()), "video/*");
                    context.startActivity(intent);
                } catch (Exception e) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(media.getUrl()));
                    context.startActivity(browserIntent);
                }
            }
        });

        // 🟢 1. CẬP NHẬT TOÀN BỘ GIAO DIỆN LIKE & COMMENT BẰNG HÀM CHUẨN
        updateMediaUI(holder, media);

        // 🟢 2. Sự kiện bấm nút Thả tim trên từng Media
        holder.btnMediaLike.setOnClickListener(v -> {
            int currentLikes = media.getLikeCount();
            boolean isCurrentlyLiked = media.isLiked();

            // Cập nhật logic trên RAM trước cho mượt
            boolean newLikedState = !isCurrentlyLiked;
            int newLikeCount = newLikedState ? currentLikes + 1 : Math.max(0, currentLikes - 1);

            media.setLiked(newLikedState);
            media.setLikeCount(newLikeCount);
            updateMediaUI(holder, media);

            // Gửi API xuống Server Database
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            apiService.toggleMediaLike(media.getMediaId(), 1).enqueue(new Callback<MediaLikeResponse>() {
                @Override
                public void onResponse(Call<MediaLikeResponse> call, Response<MediaLikeResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Cập nhật lại chuẩn xác dữ liệu trả về từ DB
                        media.setLiked(response.body().isLiked());
                        media.setLikeCount(response.body().getLikeCount());
                        updateMediaUI(holder, media);
                    }
                }

                @Override
                public void onFailure(Call<MediaLikeResponse> call, Throwable t) {
                    // Lỗi mạng -> Revert về trạng thái ban đầu
                    media.setLiked(isCurrentlyLiked);
                    media.setLikeCount(currentLikes);
                    updateMediaUI(holder, media);
                }
            });
        });

        // 🟢 3. Bình luận riêng từng media
        holder.btnMediaComment.setOnClickListener(v -> {
            if (context instanceof FragmentActivity) {
                CommentBottomSheetDialogFragment cmtSheet = new CommentBottomSheetDialogFragment(media.getMediaId(), true);
                cmtSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "MediaCommentSheet");
            }
        });

        // 🟢 4. Chia sẻ chung cho cả bài viết
        holder.btnMediaShare.setOnClickListener(v -> {
            if (context instanceof FragmentActivity && parentPost != null) {
                ShareBottomSheetDialogFragment shareSheet = new ShareBottomSheetDialogFragment(parentPost.getPostId());
                shareSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "ShareSheet");
            }
        });
    }

    // 🟢 HÀM QUẢN LÝ QUY TẮC HIỂN THỊ LIKE & COMMENT DUY NHẤT
    private void updateMediaUI(MediaViewHolder holder, Post.Media media) {
        // Xử lý UI Like
        if (holder.imgMediaLikeIcon != null && holder.tvMediaLikeCount != null) {
            if (media.isLiked()) {
                holder.imgMediaLikeIcon.setImageResource(R.drawable.heart_red);
                holder.tvMediaLikeCount.setText(media.getLikeCount() > 0 ? String.valueOf(media.getLikeCount()) : "Thích");
            } else {
                holder.imgMediaLikeIcon.setImageResource(R.drawable.heart);
                holder.tvMediaLikeCount.setText(media.getLikeCount() > 0 ? String.valueOf(media.getLikeCount()) : "Thích");
            }
        }

        // Xử lý UI Comment
        if (holder.tvMediaCommentCount != null) {
            if (media.getCommentCount() > 0) {
                holder.tvMediaCommentCount.setText(String.valueOf(media.getCommentCount()));
            } else {
                holder.tvMediaCommentCount.setText("Bình luận");
            }
        }
    }

    // Giúp PostDetailActivity cập nhật lại số lượng comment của Media khi có comment mới / xóa comment
    public void updateMediaCommentCount(int mediaId, int newCommentCount) {
        if (mediaList != null) {
            for (int i = 0; i < mediaList.size(); i++) {
                Post.Media media = mediaList.get(i);
                if (media.getMediaId() == mediaId) {
                    media.setCommentCount(newCommentCount);
                    notifyItemChanged(i);
                    break;
                }
            }
        }
    }

// (XÓA BỎ HÀM updateMediaLikeUI THỪA NẰM Ở DƯỚI)

    @Override
    public int getItemCount() {
        return mediaList != null ? mediaList.size() : 0;
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMedia, imgMediaLikeIcon;
        TextView tvMediaLikeCount, tvMediaCommentCount;
        LinearLayout btnMediaLike, btnMediaComment, btnMediaShare;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMedia = itemView.findViewById(R.id.imgMediaItem);
            imgMediaLikeIcon = itemView.findViewById(R.id.imgMediaLikeIcon);
            tvMediaLikeCount = itemView.findViewById(R.id.tvMediaLikeCount);

            tvMediaCommentCount = itemView.findViewById(R.id.tvMediaCommentCount);

            btnMediaLike = itemView.findViewById(R.id.btnMediaLike);
            btnMediaComment = itemView.findViewById(R.id.btnMediaComment);
            btnMediaShare = itemView.findViewById(R.id.btnMediaShare);
        }
    }
}