//Post.java dùng để hứng cấu trúc JSON mà API /api/posts/feed trả về
package com.example.cookingapp;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {
    @SerializedName("post_id")
    private int postId;

    @SerializedName("post_type")
    private String postType; // "REGULAR", "RECIPE", "SHARED"

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

    // Getters & Setters cho Post
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getPrivacy() { return privacy; }
    public void setPrivacy(String privacy) { this.privacy = privacy; }

    public int getCommentCount() { return commentCount; }
    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

    public int getShareCount() { return shareCount; }
    public void setShareCount(int shareCount) { this.shareCount = shareCount; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public List<Media> getMediaList() { return mediaList; }
    public void setMediaList(List<Media> mediaList) { this.mediaList = mediaList; }

    public RecipeDetails getRecipeDetails() { return recipeDetails; }
    public void setRecipeDetails(RecipeDetails recipeDetails) { this.recipeDetails = recipeDetails; }

    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    public Post getOriginalPost() { return originalPost; }
    public void setOriginalPost(Post originalPost) { this.originalPost = originalPost; }

    // ================= Inner Class Author =================
    public static class Author implements Serializable {
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

    // ================= Inner Class Media =================
    public static class Media implements Serializable {
        @SerializedName("url")
        private String url;

        // Khớp đúng key "media_type" từ Backend JSON
        @SerializedName("media_type")
        private String mediaType;
        @SerializedName("media_id")
        private int mediaId;
        @SerializedName("like_count")
        private int likeCount;

        @SerializedName("comment_count")
        private int commentCount;

        @SerializedName("is_liked")
        private boolean isLiked;
        public int getMediaId() { return mediaId; }
        public String getUrl() { return url; }

        // Trả về media_type ("IMAGE", "VIDEO", "EXCEL", "DOCUMENT")
        public String getMediaType() {
            return mediaType != null ? mediaType : "IMAGE";
        }

        // Giữ lại getType() để tương thích ngược với code cũ
        public String getType() {
            return getMediaType();
        }
        public int getLikeCount() { return likeCount; }
        public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

        public int getCommentCount() { return commentCount; }
        public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

        public boolean isLiked() { return isLiked; }
        public void setLiked(boolean liked) { isLiked = liked; }
    }

    // ================= Inner Class RecipeDetails =================
    public static class RecipeDetails implements Serializable {
        @SerializedName("title")
        private String title;

        @SerializedName("cover_image_url")
        private String coverImageUrl;

        @SerializedName("servings")
        private int servings;

        @SerializedName("servings_to")
        private int servingsTo;

        @SerializedName("cooking_time")
        private int cookingTime;

        @SerializedName("cooking_time_unit")
        private String cookingTimeUnit;

        @SerializedName("difficulty")
        private String difficulty;

        @SerializedName("ingredients")
        private List<Ingredient> ingredients;

        @SerializedName("steps")
        private List<Step> steps;

        // Getters & Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getCoverImageUrl() { return coverImageUrl; }
        public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

        public int getServings() { return servings; }
        public void setServings(int servings) { this.servings = servings; }

        public int getServingsTo() { return servingsTo; }
        public void setServingsTo(int servingsTo) { this.servingsTo = servingsTo; }

        public int getCookingTime() { return cookingTime; }
        public void setCookingTime(int cookingTime) { this.cookingTime = cookingTime; }

        public String getCookingTimeUnit() { return cookingTimeUnit; }
        public void setCookingTimeUnit(String cookingTimeUnit) { this.cookingTimeUnit = cookingTimeUnit; }

        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

        public List<Ingredient> getIngredients() { return ingredients; }
        public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

        public List<Step> getSteps() { return steps; }
        public void setSteps(List<Step> steps) { this.steps = steps; }
    }

    // ================= Inner Class Ingredient =================
    public static class Ingredient implements Serializable {
        @SerializedName("name")
        private String name;

        @SerializedName("amount")
        private double amount;

        @SerializedName("unit")
        private String unit;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }

        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
    }

    // ================= Inner Class Step =================
    public static class Step implements Serializable {
        @SerializedName("step_number")
        private int stepNumber;

        @SerializedName("instruction")
        private String instruction;

        @SerializedName("media_url")
        private String mediaUrl;

        @SerializedName("media_type")
        private String mediaType;

        public int getStepNumber() { return stepNumber; }
        public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }

        public String getInstruction() { return instruction; }
        public void setInstruction(String instruction) { this.instruction = instruction; }

        public String getMediaUrl() { return mediaUrl; }
        public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

        public String getMediaType() { return mediaType; }
        public void setMediaType(String mediaType) { this.mediaType = mediaType; }
    }
}