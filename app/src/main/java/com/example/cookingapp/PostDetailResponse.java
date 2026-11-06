package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;

public class PostDetailResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("post")
    private Post post;

    public String getStatus() {
        return status;
    }

    public Post getPost() {
        return post;
    }
}