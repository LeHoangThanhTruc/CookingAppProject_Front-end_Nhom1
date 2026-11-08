package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;

public class CommentReactionResponse {
    @SerializedName("status") private String status;
    @SerializedName("my_reaction") private String myReaction;
    @SerializedName("like_count") private int likeCount;
    @SerializedName("dislike_count") private int dislikeCount;

    public String getMyReaction() { return myReaction; }
    public int getLikeCount() { return likeCount; }
    public int getDislikeCount() { return dislikeCount; }
}