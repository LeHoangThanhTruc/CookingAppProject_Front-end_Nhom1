package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;

public class MediaLikeResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("media_id")
    private int mediaId;

    @SerializedName("is_liked")
    private boolean isLiked;

    @SerializedName("like_count")
    private int likeCount;

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public int getMediaId() { return mediaId; }
    public boolean isLiked() { return isLiked; }
    public int getLikeCount() { return likeCount; }
}