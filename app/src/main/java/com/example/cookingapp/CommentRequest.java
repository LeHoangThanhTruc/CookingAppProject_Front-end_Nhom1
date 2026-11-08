package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;

public class CommentRequest {
    @SerializedName("user_id")
    private int userId;

    @SerializedName("content")
    private String content;

    @SerializedName("parent_id")
    private Integer parentId;

    @SerializedName("media_url")
    private String mediaUrl;

    @SerializedName("media_type")
    private String mediaType;

    public CommentRequest(int userId, String content, Integer parentId, String mediaUrl, String mediaType) {
        this.userId = userId;
        this.content = content;
        this.parentId = parentId;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
    }
}