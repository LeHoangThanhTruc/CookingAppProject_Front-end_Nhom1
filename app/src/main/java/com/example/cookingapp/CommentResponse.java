package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;

public class CommentResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("comment_count")
    private int commentCount;

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public int getCommentCount() { return commentCount; }
}