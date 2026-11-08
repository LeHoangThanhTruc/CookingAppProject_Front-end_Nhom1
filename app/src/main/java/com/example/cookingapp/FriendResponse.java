package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FriendResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("friends")
    private List<Post.Author> friends;

    public String getStatus() { return status; }
    public List<Post.Author> getFriends() { return friends; }
}