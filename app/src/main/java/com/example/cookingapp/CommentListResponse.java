package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CommentListResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("comments")
    private List<Comment> comments;

    public String getStatus() { return status; }
    public List<Comment> getComments() { return comments; }
}