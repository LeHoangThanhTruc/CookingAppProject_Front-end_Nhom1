package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("media_url")
    private String mediaUrl;

    public String getStatus() { return status; }
    public String getMediaUrl() { return mediaUrl; }
}