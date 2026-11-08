package com.example.cookingapp;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Header;

import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import java.util.List;
public interface ApiService {

    // API tin tức & bài viết
    @GET("api/posts/feed")
    Call<FeedResponse> getFeed(@Query("post_type") String postType);

    @POST("api/posts/{post_id}/share")
    Call<Void> sharePost(
            @Path("post_id") int postId,
            @Query("share_type") String shareType,
            @Query("caption") String caption
    );

    @POST("api/posts/{post_id}/like")
    Call<Void> toggleLike(
            @Path("post_id") int postId,
            @Query("user_id") int userId
    );
    //hàm gọi API thả tim riêng cho từng Media
    @POST("api/posts/media/{media_id}/like")
    Call<MediaLikeResponse> toggleMediaLike(@Path("media_id") int mediaId, @Query("user_id") int userId);
    // API Upload file/ảnh
    @Multipart
    @POST("api/posts/upload")
    Call<UploadResponse> uploadMedia(@Part MultipartBody.Part file);

    // 2. API Lấy danh sách bình luận của bài viết
    @GET("api/posts/{post_id}/comments")
    Call<CommentListResponse> getComments(@Path("post_id") int postId, @Query("current_user_id") int currentUserId);

    // 3. API Thêm bình luận mới (Có đính kèm media nếu có)
    @POST("api/posts/{post_id}/comments")
    Call<CommentResponse> addComment(@Path("post_id") int postId, @Body CommentRequest request);

    // 4. API Like/Dislike bình luận
    @POST("api/posts/comments/{comment_id}/react")
    Call<CommentReactionResponse> reactComment(@Path("comment_id") int commentId, @Body ReactionRequest request);
    // Lấy comment của riêng từng media
    @GET("api/posts/media/{media_id}/comments")
    Call<CommentListResponse> getMediaComments(
            @Path("media_id") int mediaId,
            @Query("current_user_id") int currentUserId
    );

    // Thêm comment cho riêng từng media
    @POST("api/posts/media/{media_id}/comments")
    Call<CommentResponse> addMediaComment(
            @Path("media_id") int mediaId,
            @Body CommentRequest request
    );
    // Xóa bình luận
    @DELETE("api/posts/comments/{comment_id}")
    Call<CommentResponse> deleteComment(
            @Path("comment_id") int commentId,
            @Query("user_id") int userId
    );

    // Chỉnh sửa bình luận
    @PUT("api/posts/comments/{comment_id}")
    Call<CommentResponse> editComment(
            @Path("comment_id") int commentId,
            @Body CommentEditRequest request
    );
    // Lấy chi tiết bài viết và danh sách Media mới nhất từ Server Database
    @GET("api/posts/{post_id}")
    Call<PostDetailResponse> getPostDetail(
            @Path("post_id") int postId,
            @Query("current_user_id") int currentUserId
    );
    // 1. Lấy danh sách bạn bè
    @GET("api/posts/users/{user_id}/friends")
    Call<FriendResponse> getUserFriends(@Path("user_id") int userId);
    // Trong ApiService.java
    @GET("api/posts/users/{user_id}")
    Call<Post.Author> getUserProfile(@Path("user_id") int userId);
    // 2. Request body cho Share Feed
    class ShareToFeedRequest {
        int user_id;
        String caption;
        String privacy;

        public ShareToFeedRequest(int user_id, String caption, String privacy) {
            this.user_id = user_id;
            this.caption = caption;
            this.privacy = privacy;
        }
    }

    @POST("api/posts/{post_id}/share-to-feed")
    Call<Void> sharePostToFeed(@Path("post_id") int postId, @Body ShareToFeedRequest body);

    // 3. Request body cho Share Message
    class ShareToMessageRequest {
        int sender_id;
        List<Integer> receiver_ids;
        String message_text;

        public ShareToMessageRequest(int sender_id, List<Integer> receiver_ids, String message_text) {
            this.sender_id = sender_id;
            this.receiver_ids = receiver_ids;
            this.message_text = message_text;
        }
    }

    @POST("api/posts/{post_id}/share-to-message")
    Call<Void> sharePostToMessage(@Path("post_id") int postId, @Body ShareToMessageRequest body);
    // Class Request & Response cho Auth
    static class RegisterRequest {
        String full_name;
        String email;
        String password;

        public RegisterRequest(String full_name, String email, String password) {
            this.full_name = full_name;
            this.email = email;
            this.password = password;
        }
    }

    static class LoginRequest {
        String email;
        String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    static class ApiResponse {
        public String status;
        public String message;
    }

    // 3. API Xác thực & Tài khoản (Của nhóm)
    @POST("api/auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse> loginUser(@Body LoginRequest request);

    // 4. API Thông tin cá nhân & Avatar (Của nhóm)
    @GET("api/profile/me")
    Call<Profile> getMyProfile();

    @PATCH("api/profile/me")
    Call<Profile> updateMyProfile(@Body UpdateProfileRequest request);

    @Multipart
    @POST("api/profile/me/avatar")
    Call<Profile> uploadMyAvatar(@Part MultipartBody.Part file);
    // 5. API Authentication mới (khớp backend)
    @POST("api/auth/register")
    Call<AuthModels.RegisterResponse> register(@Body AuthModels.RegisterBody body);

    @POST("api/auth/login")
    Call<AuthModels.TokenResponse> login(@Body AuthModels.LoginBody body);

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
}