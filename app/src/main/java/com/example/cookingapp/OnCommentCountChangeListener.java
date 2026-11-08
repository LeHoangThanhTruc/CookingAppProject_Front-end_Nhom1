package com.example.cookingapp;

public interface OnCommentCountChangeListener {
    // Callback cho Bài post chung
    void onCommentCountChanged(int targetId, int newCount);

    // Callback riêng cho từng Media
    default void onMediaCommentCountChanged(int mediaId, int newCount) {}
}