package com.example.cookingapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final Context context;
    private final List<Comment> commentList;
    private final CommentBottomSheetDialogFragment fragment;

    public CommentAdapter(Context context, List<Comment> commentList, CommentBottomSheetDialogFragment fragment) {
        this.context = context;
        this.commentList = commentList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment cmt = commentList.get(position);

        if (cmt.getAuthor() != null) {
            holder.tvCmtAuthor.setText(cmt.getAuthor().getFullName());
            if (cmt.getAuthor().getAvatarUrl() != null) {
                Glide.with(context).load(cmt.getAuthor().getAvatarUrl()).into(holder.imgCmtAvatar);
            }
        }

        // Nội dung text
        if (cmt.getContent() != null && !cmt.getContent().isEmpty()) {
            holder.tvCmtContent.setVisibility(View.VISIBLE);
            holder.tvCmtContent.setText(cmt.getContent());
        } else {
            holder.tvCmtContent.setVisibility(View.GONE);
        }

        // Hiển thị Thời gian
        if (cmt.getCreatedAt() != null) {
            holder.tvCmtTimeAgo.setText(getTimeAgo(cmt.getCreatedAt()));
        }

        // Hiển thị Media đính kèm (ảnh/GIF)
        if (cmt.getMediaUrl() != null && !cmt.getMediaUrl().isEmpty()) {
            holder.imgCmtMedia.setVisibility(View.VISIBLE);
            Glide.with(context).load(cmt.getMediaUrl()).into(holder.imgCmtMedia);
        } else {
            holder.imgCmtMedia.setVisibility(View.GONE);
        }

        // SỰ KIỆN NHẤN GIỮ BÌNH LUẬN CHA ĐỂ MỞ MENU OPTIONS
        holder.itemView.setOnLongClickListener(v -> {
            showCommentOptionsMenu(cmt);
            return true;
        });

        // CẬP NHẬT TRẠNG THÁI LIKE/DISLIKE BAN ĐẦU
        updateReactionUI(holder.btnCmtLike, holder.btnCmtDislike, holder.tvLikeCount, holder.tvDislikeCount,
                cmt.getMyReaction(), cmt.getLikeCount(), cmt.getDislikeCount());

        // SỰ KIỆN BẤM LIKE
        holder.btnCmtLike.setOnClickListener(v -> {
            String targetReaction = "LIKE".equals(cmt.getMyReaction()) ? "NONE" : "LIKE";
            sendReactionApi(cmt, targetReaction, holder.btnCmtLike, holder.btnCmtDislike, holder.tvLikeCount, holder.tvDislikeCount);
        });

        // SỰ KIỆN BẤM DISLIKE
        holder.btnCmtDislike.setOnClickListener(v -> {
            String targetReaction = "DISLIKE".equals(cmt.getMyReaction()) ? "NONE" : "DISLIKE";
            sendReactionApi(cmt, targetReaction, holder.btnCmtLike, holder.btnCmtDislike, holder.tvLikeCount, holder.tvDislikeCount);
        });

        // Bấm nút "Trả lời"
        holder.btnReply.setOnClickListener(v -> {
            if (fragment != null && cmt.getAuthor() != null) {
                fragment.startReplyMode(cmt.getCommentId(), cmt.getAuthor().getFullName());
            }
        });

        // Xử lý các Bình luận con (Replies)
        List<Comment> replies = cmt.getReplies();
        if (replies != null && !replies.isEmpty()) {
            holder.btnToggleReplies.setVisibility(View.VISIBLE);
            holder.btnToggleReplies.setText("Xem " + replies.size() + " câu trả lời");
            holder.btnToggleReplies.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_chevron_down, 0, 0, 0);

            holder.btnToggleReplies.setOnClickListener(v -> {
                if (holder.layoutSubReplies.getVisibility() == View.GONE) {
                    holder.layoutSubReplies.setVisibility(View.VISIBLE);
                    holder.layoutSubReplies.removeAllViews();

                    for (Comment reply : replies) {
                        View replyView = LayoutInflater.from(context).inflate(R.layout.item_comment, holder.layoutSubReplies, false);

                        // 🟢 1B. SỰ KIỆN NHẤN GIỮ BÌNH LUẬN CON ĐỂ MỞ MENU OPTIONS
                        replyView.setOnLongClickListener(subV -> {
                            showCommentOptionsMenu(reply);
                            return true;
                        });

                        // Binding dữ liệu cho reply con
                        ImageView imgAvatar = replyView.findViewById(R.id.imgCmtAvatar);
                        ImageView imgMedia = replyView.findViewById(R.id.imgCmtMedia);
                        ImageView btnSubLike = replyView.findViewById(R.id.btnCmtLike);
                        ImageView btnSubDislike = replyView.findViewById(R.id.btnCmtDislike);

                        TextView tvAuthor = replyView.findViewById(R.id.tvCmtAuthor);
                        TextView tvContent = replyView.findViewById(R.id.tvCmtContent);
                        TextView tvTimeAgo = replyView.findViewById(R.id.tvCmtTimeAgo);
                        TextView btnSubReply = replyView.findViewById(R.id.btnReply);
                        TextView tvSubLikeCount = replyView.findViewById(R.id.tvLikeCount);
                        TextView tvSubDislikeCount = replyView.findViewById(R.id.tvDislikeCount);

                        if (reply.getAuthor() != null) {
                            tvAuthor.setText(reply.getAuthor().getFullName());
                            Glide.with(context).load(reply.getAuthor().getAvatarUrl()).into(imgAvatar);
                        }
                        tvContent.setText(reply.getContent());

                        if (reply.getCreatedAt() != null && tvTimeAgo != null) {
                            tvTimeAgo.setText(getTimeAgo(reply.getCreatedAt()));
                        }

                        if (reply.getMediaUrl() != null && !reply.getMediaUrl().isEmpty()) {
                            imgMedia.setVisibility(View.VISIBLE);
                            Glide.with(context).load(reply.getMediaUrl()).into(imgMedia);
                        }

                        // LIKE/DISLIKE CHO COMMENT CON
                        updateReactionUI(btnSubLike, btnSubDislike, tvSubLikeCount, tvSubDislikeCount,
                                reply.getMyReaction(), reply.getLikeCount(), reply.getDislikeCount());

                        btnSubLike.setOnClickListener(subV -> {
                            String target = "LIKE".equals(reply.getMyReaction()) ? "NONE" : "LIKE";
                            sendReactionApi(reply, target, btnSubLike, btnSubDislike, tvSubLikeCount, tvSubDislikeCount);
                        });

                        btnSubDislike.setOnClickListener(subV -> {
                            String target = "DISLIKE".equals(reply.getMyReaction()) ? "NONE" : "DISLIKE";
                            sendReactionApi(reply, target, btnSubLike, btnSubDislike, tvSubLikeCount, tvSubDislikeCount);
                        });

                        btnSubReply.setOnClickListener(subV -> {
                            if (fragment != null && reply.getAuthor() != null) {
                                fragment.startReplyMode(cmt.getCommentId(), reply.getAuthor().getFullName());
                            }
                        });

                        holder.layoutSubReplies.addView(replyView);
                    }

                    holder.btnToggleReplies.setText("Ẩn câu trả lời");
                    holder.btnToggleReplies.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_chevron_up, 0, 0, 0);
                } else {
                    holder.layoutSubReplies.setVisibility(View.GONE);
                    holder.btnToggleReplies.setText("Xem " + replies.size() + " câu trả lời");
                    holder.btnToggleReplies.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_chevron_down, 0, 0, 0);
                }
            });
        } else {
            holder.btnToggleReplies.setVisibility(View.GONE);
            holder.layoutSubReplies.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    // 2. BỔ SUNG HÀM HIỂN THỊ DIALOG MENU TƯƠNG TÁC BÌNH LUẬN
    private void showCommentOptionsMenu(Comment comment) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_comment_options, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true); // Cho phép chạm ra ngoài vùng trắng hoặc trượt xuống để tắt tab

        TextView btnCopy = view.findViewById(R.id.btnCopyComment);
        TextView btnEdit = view.findViewById(R.id.btnEditComment);
        TextView btnDelete = view.findViewById(R.id.btnDeleteComment);

        // 1. CHỨC NĂNG SAO CHÉP
        btnCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("CommentText", comment.getContent());
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Đã sao chép bình luận!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        // 2. CHỨC NĂNG CHỈNH SỬA
        btnEdit.setOnClickListener(v -> {
            dialog.dismiss();
            if (fragment != null) {
                fragment.startEditMode(comment.getCommentId(), comment.getContent());
            }
        });

        // 3. CHỨC NĂNG XÓA
        btnDelete.setOnClickListener(v -> {
            dialog.dismiss();
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            apiService.deleteComment(comment.getCommentId(), 1).enqueue(new Callback<CommentResponse>() {
                @Override
                public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(context, "Đã xóa bình luận", Toast.LENGTH_SHORT).show();

                        // 1. Tải lại danh sách bình luận bên dưới
                        if (fragment != null) {
                            fragment.loadCommentsFromApi();
                        }

                        // 2. Cập nhật lại số đếm trên Header bài viết ngay lập tức!
                        if (context instanceof PostDetailActivity) {
                            ((PostDetailActivity) context).fetchPostDetailFromDatabase();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CommentResponse> call, Throwable t) {
                    Toast.makeText(context, "Không thể xóa bình luận!", Toast.LENGTH_SHORT).show();
                }
            });
        });
        dialog.show();
    }

    // HÀM TÍNH THỜI GIAN
    public static String getTimeAgo(String dateStr) {
        if (dateStr == null) return "Vừa xong";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = sdf.parse(dateStr);
            if (date == null) return "Vừa xong";
            long now = System.currentTimeMillis();
            long diff = now - date.getTime();

            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            long weeks = days / 7;
            long months = days / 30;
            long years = days / 365;

            if (seconds < 60) return "Vừa xong";
            if (minutes < 60) return minutes + " phút";
            if (hours < 24) return hours + " giờ";
            if (days < 7) return days + " ngày";
            if (weeks < 4) return weeks + " tuần";
            if (months < 12) return months + " tháng";
            return years + " năm";
        } catch (ParseException e) {
            return "Vừa xong";
        }
    }

    private void sendReactionApi(Comment cmt, String targetReaction, ImageView btnLike, ImageView btnDislike, TextView tvLikes, TextView tvDislikes) {
        String oldReaction = cmt.getMyReaction();
        int oldLikes = cmt.getLikeCount();
        int oldDislikes = cmt.getDislikeCount();

        int newLikes = oldLikes;
        int newDislikes = oldDislikes;

        if ("LIKE".equals(oldReaction)) newLikes--;
        if ("DISLIKE".equals(oldReaction)) newDislikes--;

        if ("LIKE".equals(targetReaction)) newLikes++;
        if ("DISLIKE".equals(targetReaction)) newDislikes++;

        cmt.setMyReaction(targetReaction);
        cmt.setLikeCount(Math.max(0, newLikes));
        cmt.setDislikeCount(Math.max(0, newDislikes));
        updateReactionUI(btnLike, btnDislike, tvLikes, tvDislikes, targetReaction, cmt.getLikeCount(), cmt.getDislikeCount());

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        int currentUserId = 1;

        apiService.reactComment(cmt.getCommentId(), new ReactionRequest(currentUserId, targetReaction))
                .enqueue(new Callback<CommentReactionResponse>() {
                    @Override
                    public void onResponse(Call<CommentReactionResponse> call, Response<CommentReactionResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            CommentReactionResponse res = response.body();
                            cmt.setMyReaction(res.getMyReaction());
                            cmt.setLikeCount(res.getLikeCount());
                            cmt.setDislikeCount(res.getDislikeCount());
                            updateReactionUI(btnLike, btnDislike, tvLikes, tvDislikes, res.getMyReaction(), res.getLikeCount(), res.getDislikeCount());
                        } else {
                            rollbackUI(cmt, oldReaction, oldLikes, oldDislikes, btnLike, btnDislike, tvLikes, tvDislikes);
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentReactionResponse> call, Throwable t) {
                        rollbackUI(cmt, oldReaction, oldLikes, oldDislikes, btnLike, btnDislike, tvLikes, tvDislikes);
                    }
                });
    }

    private void rollbackUI(Comment cmt, String oldReaction, int oldLikes, int oldDislikes, ImageView btnLike, ImageView btnDislike, TextView tvLikes, TextView tvDislikes) {
        cmt.setMyReaction(oldReaction);
        cmt.setLikeCount(oldLikes);
        cmt.setDislikeCount(oldDislikes);
        updateReactionUI(btnLike, btnDislike, tvLikes, tvDislikes, oldReaction, oldLikes, oldDislikes);
    }

    private void updateReactionUI(ImageView btnLike, ImageView btnDislike, TextView tvLikes, TextView tvDislikes,
                                  String reaction, int likes, int dislikes) {
        if ("LIKE".equals(reaction)) {
            btnLike.setImageResource(R.drawable.like_filled);
            btnDislike.setImageResource(R.drawable.dislike_outlined);
        } else if ("DISLIKE".equals(reaction)) {
            btnLike.setImageResource(R.drawable.like_outlined);
            btnDislike.setImageResource(R.drawable.dislike_filled);
        } else {
            btnLike.setImageResource(R.drawable.like_outlined);
            btnDislike.setImageResource(R.drawable.dislike_outlined);
        }

        if (tvLikes != null) {
            tvLikes.setText(likes > 0 ? String.valueOf(likes) : "");
        }
        if (tvDislikes != null) {
            tvDislikes.setText(dislikes > 0 ? String.valueOf(dislikes) : "");
        }
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCmtAvatar, imgCmtMedia, btnCmtLike, btnCmtDislike;
        TextView tvCmtAuthor, tvCmtContent, tvCmtTimeAgo, btnReply, btnToggleReplies, tvLikeCount, tvDislikeCount;
        LinearLayout layoutSubReplies;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCmtAvatar = itemView.findViewById(R.id.imgCmtAvatar);
            imgCmtMedia = itemView.findViewById(R.id.imgCmtMedia);
            btnCmtLike = itemView.findViewById(R.id.btnCmtLike);
            btnCmtDislike = itemView.findViewById(R.id.btnCmtDislike);
            tvCmtAuthor = itemView.findViewById(R.id.tvCmtAuthor);
            tvCmtContent = itemView.findViewById(R.id.tvCmtContent);
            tvCmtTimeAgo = itemView.findViewById(R.id.tvCmtTimeAgo);
            btnReply = itemView.findViewById(R.id.btnReply);
            btnToggleReplies = itemView.findViewById(R.id.btnToggleReplies);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvDislikeCount = itemView.findViewById(R.id.tvDislikeCount);
            layoutSubReplies = itemView.findViewById(R.id.layoutSubReplies);
        }
    }
}