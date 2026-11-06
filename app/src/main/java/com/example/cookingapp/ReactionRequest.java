package com.example.cookingapp;
import com.google.gson.annotations.SerializedName;

public class ReactionRequest {
    @SerializedName("user_id") int userId;
    @SerializedName("reaction_type") String reactionType; // "LIKE" hoặc "DISLIKE"

    public ReactionRequest(int userId, String reactionType) {
        this.userId = userId;
        this.reactionType = reactionType;
    }
}
