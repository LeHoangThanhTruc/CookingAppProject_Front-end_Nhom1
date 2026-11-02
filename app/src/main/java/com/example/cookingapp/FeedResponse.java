package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FeedResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Post> data;

    public String getStatus() { return status; }
    public List<Post> getData() { return data; }
}