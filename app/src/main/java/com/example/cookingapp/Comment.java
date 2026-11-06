package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Comment implements Serializable {
    @SerializedName("comment_id")
    private int commentId;

    @SerializedName("parent_id")
    private Integer parentId;

    @SerializedName("content")
    private String content;

    @SerializedName("media_url")
    private String mediaUrl;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("author")
    private Post.Author author;

    @SerializedName(value = "like_count", alternate = {"like_count_com_post_chung", "like_count_com_media"})
    private int likeCount;

    @SerializedName(value = "dislike_count", alternate = {"dislike_count_com_post_chung", "dislike_count_com_media"})
    private int dislikeCount;

    @SerializedName("my_reaction")
    private String myReaction;// "LIKE", "DISLIKE", "NONE"

    @SerializedName("replies")
    private List<Comment> replies;
    public int getCommentId() { return commentId; }
    public Integer getParentId() { return parentId; }
    public String getContent() { return content; }
    public String getMediaUrl() { return mediaUrl; }
    public String getMediaType() { return mediaType; }
    public String getCreatedAt() { return createdAt; }
    public Post.Author getAuthor() { return author; }
    public String getMyReaction() { return myReaction; }
    public void setMyReaction(String myReaction) { this.myReaction = myReaction; }
    public List<Comment> getReplies() { return replies; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

    public int getDislikeCount() { return dislikeCount; }
    public void setDislikeCount(int dislikeCount) { this.dislikeCount = dislikeCount; }
}