package com.example.cookingapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import okhttp3.MultipartBody;
import java.util.List;

public interface ApiService {

    static class RegisterRequest {
        String username;
        String full_name;
        String email;
        String phone_number;
        String password;

        public RegisterRequest(
                String username,
                String full_name,
                String email,
                String phone_number,
                String password
        ) {
            this.username = username;
            this.full_name = full_name;
            this.email = email;
            this.phone_number = phone_number;
            this.password = password;
        }
    }

    static class LoginRequest {
        String identifier;
        String email;
        String password;

        public LoginRequest(String identifier, String password) {
            this.identifier = identifier;
            this.email = identifier;
            this.password = password;
        }
    }

    static class ApiResponse {
        public String status;
        public String message;
    }

    static class AuthUser {
        public int id;
        public String username;
        public String email;
        public String full_name;
        public String avatar_url;
    }

    static class AuthResponse {
        public String access_token;
        public String token_type;
        public AuthUser user;
    }

    static class IdentifierRequest {
        String identifier;
        IdentifierRequest(String identifier) { this.identifier = identifier; }
    }

    static class VerifyOtpRequest {
        String identifier;
        String otp;
        VerifyOtpRequest(String identifier, String otp) { this.identifier = identifier; this.otp = otp; }
    }

    static class VerifyOtpResponse {
        public String reset_token;
        public String message;
    }

    static class ResetPasswordRequest {
        String reset_token;
        String new_password;
        ResetPasswordRequest(String resetToken, String newPassword) { this.reset_token = resetToken; this.new_password = newPassword; }
    }

    static class FeedAuthor {
        public int user_id;
        public String full_name;
        public String avatar_url;
    }

    static class FeedPost {
        public int post_id;
        public String post_type;
        public String caption;
        public String privacy;
        public int like_count;
        public int comment_count;
        public int share_count;
        public String created_at;
        public FeedAuthor author;
        public RecipeFeedDetails recipe_details;
        public boolean is_liked;
    }

    static class RecipeFeedDetails {
        public String title;
        public String cover_image_url;
        public int servings;
        public int cooking_time;
        public String difficulty;
    }

    static class FeedResponse {
        public String status;
        public List<FeedPost> data;
    }

    static class IngredientInput {
        public String name; public double amount; public String unit;
        IngredientInput(String name, double amount, String unit) { this.name = name; this.amount = amount; this.unit = unit; }
    }

    static class StepInput {
        public int step_number; public String instruction; public String media_url; public String media_type;
        StepInput(int stepNumber, String instruction) { this.step_number = stepNumber; this.instruction = instruction; }
    }

    static class RecipeRequest {
        String title; String caption; String cover_image_url; String difficulty; String privacy;
        int servings; int cooking_time;
        List<IngredientInput> ingredients; List<StepInput> steps;

        RecipeRequest(String title, String caption, String privacy) {
            this.title = title; this.caption = caption; this.privacy = privacy;
            this.cover_image_url = "/uploads/placeholder-recipe.png";
            this.difficulty = "Dễ"; this.servings = 1; this.cooking_time = 15;
            this.ingredients = java.util.Collections.singletonList(new IngredientInput("Xem mô tả", 1, "phần"));
            this.steps = java.util.Collections.singletonList(new StepInput(1, caption));
        }

        RecipeRequest(String title, String caption, String coverImageUrl, String difficulty,
                       String privacy, int servings, int cookingTime,
                       List<IngredientInput> ingredients, List<StepInput> steps) {
            this.title = title; this.caption = caption; this.cover_image_url = coverImageUrl;
            this.difficulty = difficulty; this.privacy = privacy; this.servings = servings;
            this.cooking_time = cookingTime; this.ingredients = ingredients; this.steps = steps;
        }
    }

    static class RecipeResponse {
        public int id; public String title; public String caption; public String privacy;
        public int servings; public int cooking_time; public String difficulty; public String cover_image_url;
        public int user_id; public FeedAuthor author;
        public List<IngredientInput> ingredients; public List<StepInput> steps;
    }

    static class MessageRequest {
        int receiver_id; String text; Integer shared_post_id;
        MessageRequest(int receiverId, String text) { this(receiverId, text, null); }
        MessageRequest(int receiverId, String text, Integer sharedPostId) { this.receiver_id = receiverId; this.text = text; this.shared_post_id = sharedPostId; }
    }

    static class MessageResponse {
        public int id; public int sender_id; public int receiver_id; public String text; public String created_at; public Integer shared_post_id;
    }

    static class ToggleResponse { public boolean active; public boolean following; public String status; public String message; }
    static class FriendItem { public int id; public String username; public String full_name; public String avatar_url; }
    static class ScheduleItem { public int id; public int recipe_id; public String title; public String scheduled_for; public String note; }
    static class ScheduleRequest {
        int recipe_id; String scheduled_for; String note;
        ScheduleRequest(int recipeId, String scheduledFor, String note) { this.recipe_id=recipeId; this.scheduled_for=scheduledFor; this.note=note; }
    }
    static class ReviewRequest { int rating; String content; String media_url; ReviewRequest(int rating, String content, String mediaUrl){this.rating=rating;this.content=content;this.media_url=mediaUrl;} }
    static class ReviewItem { public int id; public int user_id; public int recipe_id; public String username; public String title; public int rating; public String content; public String media_url; public String created_at; }
    static class CommentRequest { String content; Integer parent_id; CommentRequest(String content){this(content,null);} CommentRequest(String content,Integer parentId){this.content=content;this.parent_id=parentId;} }
    static class CommentItem { public int comment_id; public String content; public int like_count; public int dislike_count; public FeedAuthor author; public List<CommentItem> replies; }
    static class CommentsResponse { public String status; public List<CommentItem> comments; }
    static class ReactionRequest { String reaction_type; ReactionRequest(String type){reaction_type=type;} }
    static class ReactionResponse { public int comment_id; public String my_reaction; public int like_count; public int dislike_count; }
    static class FriendRequestBody { int friend_id; FriendRequestBody(int friendId){friend_id=friendId;} }
    static class FriendRequestItem { public int id; public int user_id; public int friend_id; public String username; public String full_name; public String avatar_url; public String status; }
    static class ConversationItem { public int user_id; public String username; public String full_name; public String avatar_url; public String last_message; public String created_at; public Integer shared_post_id; }
    static class NotificationItem { public int id; public int actor_id; public String type; public String message; public Integer reference_id; public int is_read; public String created_at; }
    static class ChangePasswordRequest { String current_password; String new_password; ChangePasswordRequest(String currentPassword,String newPassword){current_password=currentPassword;new_password=newPassword;} }
    static class ChangeEmailRequest { String current_password; String new_email; ChangeEmailRequest(String password,String email){current_password=password;new_email=email;} }
    static class PostDetailResponse { public String status; public FeedPost post; }
    static class UploadResponse { public String media_url; }
    static class OtherProfile { public int id; public String username; public String full_name; public String bio; public String avatar_url; public String friend_status; public int followers_count; public int following_count; public int posts_count; public boolean is_following; }
    static class UserPost { public int id; public String post_type; public String caption; public String privacy; public String created_at; public String recipe_title; public String cover_image_url; }

    static class ShareToFeedRequest {
        int user_id; String caption; String privacy;
        public ShareToFeedRequest(int userId, String caption, String privacy) {
            this.user_id = userId; this.caption = caption; this.privacy = privacy;
        }
    }

    static class ShareToMessageRequest {
        int sender_id; List<Integer> receiver_ids; String message_text;
        public ShareToMessageRequest(int senderId, List<Integer> receiverIds, String messageText) {
            this.sender_id = senderId; this.receiver_ids = receiverIds; this.message_text = messageText;
        }
    }

    // API Declarations
    @POST("api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<AuthResponse> loginUser(@Body LoginRequest request);

    @POST("api/auth/forgot-password")
    Call<ApiResponse> forgotPassword(@Body IdentifierRequest request);

    @POST("api/auth/verify-otp")
    Call<VerifyOtpResponse> verifyOtp(@Body VerifyOtpRequest request);

    @POST("api/auth/reset-password")
    Call<ApiResponse> resetPassword(@Body ResetPasswordRequest request);

    @POST("api/auth/logout")
    Call<ApiResponse> logout();

    @GET("api/posts/feed")
    Call<com.example.cookingapp.FeedResponse> getFeed();

    @GET("api/posts/feed")
    Call<com.example.cookingapp.FeedResponse> getFeed(@Query("post_type") String postType);

    @GET("api/posts/{postId}")
    Call<PostDetailResponse> getPost(@Path("postId") int postId);

    @POST("api/posts/{postId}/like")
    Call<ToggleResponse> togglePostLike(@Path("postId") int postId);

    @GET("api/posts/{postId}/comments")
    Call<CommentsResponse> getComments(@Path("postId") int postId);

    @POST("api/posts/{postId}/comments")
    Call<ToggleResponse> addComment(@Path("postId") int postId, @Body CommentRequest request);

    @POST("api/posts/comments/{commentId}/react")
    Call<ReactionResponse> reactToComment(@Path("commentId") int commentId, @Body ReactionRequest request);

    @POST("api/recipes")
    Call<RecipeResponse> createRecipe(@Body RecipeRequest request);

    @Multipart
    @POST("api/recipes/upload")
    Call<UploadResponse> uploadRecipeImage(@Part MultipartBody.Part file);

    @GET("api/recipes/{recipeId}")
    Call<RecipeResponse> getRecipe(@Path("recipeId") int recipeId);

    @PUT("api/recipes/{recipeId}")
    Call<RecipeResponse> updateRecipe(@Path("recipeId") int recipeId, @Body RecipeRequest request);

    @DELETE("api/recipes/{recipeId}")
    Call<Void> deleteRecipe(@Path("recipeId") int recipeId);

    @GET("api/recipes")
    Call<List<RecipeResponse>> searchRecipes(@Query("q") String query, @Query("difficulty") String difficulty,
                                             @Query("exclude_ingredient") List<String> excludedIngredients,
                                             @Query("min_rating") Integer minRating, @Query("max_time") Integer maxTime);

    @GET("api/recipes/favorites/me")
    Call<List<RecipeResponse>> getFavoriteRecipes();

    @GET("api/recipes/saved/me")
    Call<List<RecipeResponse>> getSavedRecipes();

    @POST("api/recipes/{recipeId}/favorite")
    Call<ToggleResponse> toggleFavorite(@Path("recipeId") int recipeId);

    @POST("api/recipes/{recipeId}/save")
    Call<ToggleResponse> toggleSaved(@Path("recipeId") int recipeId);

    @POST("api/recipes/{recipeId}/reviews")
    Call<ReviewItem> submitReview(@Path("recipeId") int recipeId, @Body ReviewRequest request);

    @GET("api/recipes/{recipeId}/reviews")
    Call<List<ReviewItem>> getReviews(@Path("recipeId") int recipeId);

    @GET("api/recipes/reviews/me")
    Call<List<ReviewItem>> getMyReviews();

    @GET("api/schedules")
    Call<List<ScheduleItem>> getSchedules();

    @POST("api/schedules")
    Call<ScheduleItem> createSchedule(@Body ScheduleRequest request);

    @DELETE("api/schedules/{scheduleId}")
    Call<Void> deleteSchedule(@Path("scheduleId") int scheduleId);

    @GET("api/social/friends")
    Call<List<FriendItem>> getFriends();

    @GET("api/social/users")
    Call<List<FriendItem>> searchUsers(@Query("q") String query);

    @GET("api/social/friend-requests")
    Call<List<FriendRequestItem>> getFriendRequests();

    @GET("api/social/friend-requests/sent")
    Call<List<FriendRequestItem>> getSentFriendRequests();

    @POST("api/social/friend-requests")
    Call<FriendRequestItem> sendFriendRequest(@Body FriendRequestBody body);

    @POST("api/social/friend-requests/{requestId}/accept")
    Call<FriendRequestItem> acceptFriendRequest(@Path("requestId") int requestId);

    @POST("api/social/friend-requests/{requestId}/reject")
    Call<FriendRequestItem> rejectFriendRequest(@Path("requestId") int requestId);

    @DELETE("api/social/friend-requests/{requestId}")
    Call<Void> cancelFriendRequest(@Path("requestId") int requestId);

    @DELETE("api/social/friends/{friendId}")
    Call<Void> removeFriend(@Path("friendId") int friendId);

    @GET("api/social/following")
    Call<List<FriendItem>> getFollowing();

    @POST("api/social/follow/{userId}")
    Call<ToggleResponse> toggleFollow(@Path("userId") int userId);

    @POST("api/messages")
    Call<MessageResponse> sendMessage(@Body MessageRequest request);

    @GET("api/messages/{otherUserId}")
    Call<List<MessageResponse>> getConversation(@Path("otherUserId") int otherUserId);

    @GET("api/messages")
    Call<List<ConversationItem>> getConversations();

    @GET("api/notifications")
    Call<List<NotificationItem>> getNotifications();

    @POST("api/notifications/{notificationId}/read")
    Call<NotificationItem> markNotificationRead(@Path("notificationId") int notificationId);

    @DELETE("api/notifications/{notificationId}")
    Call<Void> deleteNotification(@Path("notificationId") int notificationId);

    @POST("api/auth/change-password")
    Call<ApiResponse> changePassword(@Body ChangePasswordRequest request);

    @POST("api/auth/change-email")
    Call<ApiResponse> changeEmail(@Body ChangeEmailRequest request);

    @GET("api/profile/me")
    Call<Profile> getMyProfile();

    @GET("api/profile/{userId}")
    Call<OtherProfile> getUserProfile(@Path("userId") int userId);

    @GET("api/profile/{userId}/posts")
    Call<List<UserPost>> getUserPosts(@Path("userId") int userId);

    @PATCH("api/profile/me")
    Call<Profile> updateMyProfile(@Body UpdateProfileRequest request);

    @Multipart
    @POST("api/profile/me/avatar")
    Call<Profile> uploadMyAvatar(@Part MultipartBody.Part file);

    // Compatibility endpoints
    @POST("api/auth/logout")
    Call<AuthModels.MessageResponse> logoutWithToken(@Header("Authorization") String bearer);

    @POST("api/auth/forgot-password")
    Call<AuthModels.MessageResponse> forgotPassword(@Body AuthModels.ForgotPasswordBody body);

    @POST("api/auth/verify-otp")
    Call<AuthModels.VerifyOtpResponse> verifyOtp(@Body AuthModels.VerifyOtpBody body);

    @POST("api/auth/reset-password")
    Call<AuthModels.MessageResponse> resetPassword(@Body AuthModels.ResetPasswordBody body);

    @POST("api/auth/change-password")
    Call<AuthModels.MessageResponse> changePassword(@Body AuthModels.ChangePasswordBody body);

    @POST("api/auth/verify-current-password")
    Call<AuthModels.VerifyOtpResponse> verifyCurrentPassword(@Body AuthModels.VerifyCurrentPasswordBody body);

    @POST("api/auth/oauth-login")
    Call<AuthModels.TokenResponse> oauthLogin(@Body AuthModels.OAuthBody body);

    @POST("api/posts/{post_id}/like")
    Call<Void> toggleLike(@Path("post_id") int postId, @Query("user_id") int userId);

    @POST("api/posts/media/{media_id}/like")
    Call<MediaLikeResponse> toggleMediaLike(@Path("media_id") int mediaId, @Query("user_id") int userId);

    @Multipart
    @POST("api/posts/upload")
    Call<com.example.cookingapp.UploadResponse> uploadMedia(@Part MultipartBody.Part file);

    @GET("api/posts/{post_id}/comments")
    Call<CommentListResponse> getComments(@Path("post_id") int postId, @Query("current_user_id") int currentUserId);

    @POST("api/posts/{post_id}/comments")
    Call<CommentResponse> addComment(@Path("post_id") int postId, @Body com.example.cookingapp.CommentRequest request);

    @POST("api/posts/comments/{comment_id}/react")
    Call<CommentReactionResponse> reactComment(@Path("comment_id") int commentId, @Body com.example.cookingapp.ReactionRequest request);

    @GET("api/posts/media/{media_id}/comments")
    Call<CommentListResponse> getMediaComments(@Path("media_id") int mediaId, @Query("current_user_id") int currentUserId);

    @POST("api/posts/media/{media_id}/comments")
    Call<CommentResponse> addMediaComment(@Path("media_id") int mediaId, @Body com.example.cookingapp.CommentRequest request);

    @DELETE("api/posts/comments/{comment_id}")
    Call<CommentResponse> deleteComment(@Path("comment_id") int commentId, @Query("user_id") int userId);

    @DELETE("api/posts/comments/{comment_id}")
    Call<Void> deleteOwnComment(@Path("comment_id") int commentId);

    @PUT("api/posts/comments/{comment_id}")
    Call<CommentResponse> editComment(@Path("comment_id") int commentId, @Body CommentEditRequest request);

    @GET("api/posts/{post_id}")
    Call<com.example.cookingapp.PostDetailResponse> getPostDetail(@Path("post_id") int postId, @Query("current_user_id") int currentUserId);

    @GET("api/posts/users/{user_id}/friends")
    Call<FriendResponse> getUserFriends(@Path("user_id") int userId);

    @POST("api/posts/{post_id}/share-to-feed")
    Call<Void> sharePostToFeed(@Path("post_id") int postId, @Body ShareToFeedRequest body);

    @POST("api/posts/{post_id}/share-to-message")
    Call<Void> sharePostToMessage(@Path("post_id") int postId, @Body ShareToMessageRequest body);
}
