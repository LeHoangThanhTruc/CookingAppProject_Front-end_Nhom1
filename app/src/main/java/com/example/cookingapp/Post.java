//Post.java dùng để hứng cấu trúc JSON mà API /api/posts/feed trả về
package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Post implements java.io.Serializable{
    @SerializedName("post_id")
    private int postId;

    @SerializedName("post_type")
    private String postType; // "REGULAR" hoặc "RECIPE"

    @SerializedName("caption")
    private String caption;

    @SerializedName("privacy")
    private String privacy; // "PUBLIC", "FRIENDS", "ONLY_ME"

    @SerializedName("like_count")
    private int likeCount;

    @SerializedName("comment_count")
    private int commentCount;

    @SerializedName("share_count")
    private int shareCount;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("author")
    private Author author;

    @SerializedName("media_list")
    private List<Media> mediaList;

    @SerializedName("recipe_details")
    private RecipeDetails recipeDetails;
    @SerializedName("is_liked")
    private boolean isLiked;
    @SerializedName("original_post")
    private Post originalPost;
    // Getters & Setters
    public int getPostId() { return postId; }
    public String getPostType() { return postType; }
    public String getCaption() { return caption; }
    public String getPrivacy() { return privacy; }
    public int getCommentCount() { return commentCount; }
    public int getShareCount() { return shareCount; }
    public String getCreatedAt() { return createdAt; }
    public Author getAuthor() { return author; }
    public List<Media> getMediaList() { return mediaList; }
    public RecipeDetails getRecipeDetails() { return recipeDetails; }
    // Getter & Setter cho likeCount
    public int getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    // Getter & Setter cho isLiked (trạng thái thả tim)
    public boolean isLiked() {
        return isLiked;
    }
    public void setLiked(boolean liked) {
        isLiked = liked;
    }
    public Post getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(Post originalPost) {
        this.originalPost = originalPost;
    }
    public static class Author {
        @SerializedName("user_id")
        private int userId;
        @SerializedName("full_name")
        private String fullName;
        @SerializedName("avatar_url")
        private String avatarUrl;

        public int getUserId() { return userId; }
        public String getFullName() { return fullName; }
        public String getAvatarUrl() { return avatarUrl; }
    }

    public static class Media {
        @SerializedName("url")
        private String url;
        @SerializedName("type")
        private String type;

        public String getUrl() { return url; }
        public String getType() { return type; }
    }

    public static class RecipeDetails {
        @SerializedName("title")
        private String title;
        @SerializedName("ingredients")
        private List<Ingredient> ingredients;
        @SerializedName("steps")
        private List<Step> steps;

        public String getTitle() { return title; }
        public List<Ingredient> getIngredients() { return ingredients; }
        public List<Step> getSteps() { return steps; }
    }

    public static class Ingredient {
        @SerializedName("name")
        private String name;
        @SerializedName("amount")
        private String amount;

        public String getName() { return name; }
        public String getAmount() { return amount; }
    }

    public static class Step {
        @SerializedName("step")
        private int step;
        @SerializedName("instruction")
        private String instruction;

        public int getStep() { return step; }
        public String getInstruction() { return instruction; }
    }
}