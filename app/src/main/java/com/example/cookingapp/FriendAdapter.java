package com.example.cookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    public interface OnFriendClickListener {
        void onFriendClick(Post.Author friend);
    }

    private final Context context;
    private final List<Post.Author> friendList;
    private final OnFriendClickListener listener;
    private int selectedPosition = -1; // Vị trí friend được chọn (-1 là chưa chọn)

    public FriendAdapter(Context context, List<Post.Author> friendList, OnFriendClickListener listener) {
        this.context = context;
        this.friendList = friendList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend_share, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Post.Author friend = friendList.get(position);
        if (friend == null) return;

        holder.tvName.setText(friend.getFullName());

        if (friend.getAvatarUrl() != null && !friend.getAvatarUrl().isEmpty()) {
            Glide.with(context)
                    .load(friend.getAvatarUrl())
                    .placeholder(R.drawable.bxs_user)
                    .into(holder.imgAvatar);
        } else {
            holder.imgAvatar.setImageResource(R.drawable.bxs_user);
        }

        // Hiển thị/Ẩn dấu tích xanh tùy vào vị trí được chọn
        if (selectedPosition == position) {
            holder.imgCheck.setVisibility(View.VISIBLE);
        } else {
            holder.imgCheck.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            // Cập nhật lại UI cho item cũ và item mới chọn
            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);

            if (listener != null) listener.onFriendClick(friend);
        });
    }

    @Override
    public int getItemCount() {
        return friendList != null ? friendList.size() : 0;
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imgAvatar;
        ImageView imgCheck;
        TextView tvName;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgFriendAvatar);
            imgCheck = itemView.findViewById(R.id.imgSelectedCheck);
            tvName = itemView.findViewById(R.id.tvFriendName);
        }
    }
}