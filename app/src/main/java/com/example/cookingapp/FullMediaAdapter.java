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

        // ĐỒNG BỘ HIỂN THỊ VÀ THẢ TIM CHO TỪNG MEDIA ITEM
        updateMediaLikeUI(holder, media);

        // Sự kiện bấm nút Thả tim trên từng Media
        holder.btnMediaLike.setOnClickListener(v -> {
            // 1. Thay đổi tạm thời trên UI trước cho mượt
            int currentLikes = media.getLikeCount();
            boolean newLikedState = !media.isLiked();
            media.setLiked(newLikedState);
            media.setLikeCount(newLikedState ? currentLikes + 1 : Math.max(0, currentLikes - 1));
            updateMediaLikeUI(holder, media);

            // 2. Gửi API xuống Backend (VS Code)
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            apiService.toggleMediaLike(media.getMediaId(), 1).enqueue(new Callback<MediaLikeResponse>() {
                @Override
                public void onResponse(Call<MediaLikeResponse> call, Response<MediaLikeResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        MediaLikeResponse res = response.body();
                        // Cập nhật lại UI chính xác theo dữ liệu SQLite trả về từ Server
                        media.setLiked(res.isLiked());
                        media.setLikeCount(res.getLikeCount());
                        updateMediaLikeUI(holder, media);
                    }
                }

                @Override
                public void onFailure(Call<MediaLikeResponse> call, Throwable t) {
                    // Nếu lỗi mạng -> Revert quay về trạng thái cũ
                    media.setLiked(!media.isLiked());
                    media.setLikeCount(currentLikes);
                    updateMediaLikeUI(holder, media);
                    Toast.makeText(context, "Lỗi kết nối máy chủ!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Bình luận riêng từng media
        holder.btnMediaComment.setOnClickListener(v ->
                Toast.makeText(context, "Mở bình luận cho ảnh này...", Toast.LENGTH_SHORT).show());

        // Chia sẻ chung cho cả bài viết
        holder.btnMediaShare.setOnClickListener(v -> {
            if (context instanceof FragmentActivity && parentPost != null) {
                ShareBottomSheetDialogFragment shareSheet = new ShareBottomSheetDialogFragment(parentPost.getPostId());
                shareSheet.show(((FragmentActivity) context).getSupportFragmentManager(), "ShareSheet");
            }
        });
    }

    private void updateMediaLikeUI(MediaViewHolder holder, Post.Media media) {
        if (holder.imgMediaLikeIcon != null) {
            // Đổi icon tim đỏ / tim thường
            holder.imgMediaLikeIcon.setImageResource(media.isLiked() ? R.drawable.heart_red : R.drawable.heart);
        }
        if (holder.tvMediaLikeCount != null) {
            // Cập nhật số lượt tim trên UI
            holder.tvMediaLikeCount.setText(media.getLikeCount() > 0 ? String.valueOf(media.getLikeCount()) : "Thích");
        }
    }

    @Override
    public int getItemCount() {
        return mediaList != null ? mediaList.size() : 0;
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMedia, imgMediaLikeIcon;
        TextView tvMediaLikeCount;
        LinearLayout btnMediaLike, btnMediaComment, btnMediaShare;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMedia = itemView.findViewById(R.id.imgMediaItem);
            imgMediaLikeIcon = itemView.findViewById(R.id.imgMediaLikeIcon);
            tvMediaLikeCount = itemView.findViewById(R.id.tvMediaLikeCount);
            btnMediaLike = itemView.findViewById(R.id.btnMediaLike);
            btnMediaComment = itemView.findViewById(R.id.btnMediaComment);
            btnMediaShare = itemView.findViewById(R.id.btnMediaShare);
        }
    }
}