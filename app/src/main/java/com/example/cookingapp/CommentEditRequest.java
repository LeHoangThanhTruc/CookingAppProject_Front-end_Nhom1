package com.example.cookingapp;

public class CommentEditRequest {
    private int user_id;
    private String content;

    public CommentEditRequest(int user_id, String content) {
        this.user_id = user_id;
        this.content = content;
    }

    public int getUserId() { return user_id; }
    public void setUserId(int user_id) { this.user_id = user_id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}