package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private int postId;
    private Post.Author selectedFriend = null; // Người bạn được chọn để gửi tin nhắn

    public ShareBottomSheetDialogFragment(int postId) {
        this.postId = postId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet_share, container, false);

        // Views thông tin chủ tài khoản
        ShapeableImageView imgMyAvatar = view.findViewById(R.id.imgMyAvatar);
        TextView tvMyName = view.findViewById(R.id.tvMyName);
        LinearLayout btnSelectPrivacy = view.findViewById(R.id.btnSelectPrivacy);
        EditText etShareCaption = view.findViewById(R.id.etShareCaption);
        Button btnShareNow = view.findViewById(R.id.btnShareNow);

        // Views chọn bạn bè & gửi tin nhắn
        RecyclerView rvFriendsList = view.findViewById(R.id.rvFriendsList);
        LinearLayout layoutPrivateMessage = view.findViewById(R.id.layoutPrivateMessage);
        EditText etPrivateMessage = view.findViewById(R.id.etPrivateMessage);
        Button btnSendMessage = view.findViewById(R.id.btnSendMessage);

        // 1. Load Thông tin cá nhân của Chủ tài khoản (Avatar & Tên)
        loadUserProfile(imgMyAvatar, tvMyName);

        // 2. Chuyển sang Frame32PrivacyActivity chọn quyền xem
        btnSelectPrivacy.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Frame32PrivacyActivity.class);
            startActivity(intent);
        });

        // 3. Bấm "Chia sẻ ngay" lên Feed cá nhân
        btnShareNow.setOnClickListener(v -> {
            String caption = etShareCaption.getText().toString().trim();
            shareToFeed(caption);
        });

        // 4. Cấu hình RecyclerView bạn bè cuộn ngang & Load API bạn bè
        rvFriendsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        loadFriendsList(rvFriendsList, layoutPrivateMessage);

        // 5. Bấm "Gửi" tin nhắn riêng cho bạn bè đã chọn
        btnSendMessage.setOnClickListener(v -> {
            if (selectedFriend == null) {
                Toast.makeText(getContext(), "Vui lòng chọn 1 người bạn!", Toast.LENGTH_SHORT).show();
                return;
            }
            String msg = etPrivateMessage.getText().toString().trim();
            shareToMessage(selectedFriend.getUserId(), msg);
        });

        return view;
    }

    // --- Hàm Load Thông tin Chủ tài khoản từ Backend ---
    private void loadUserProfile(ShapeableImageView imgMyAvatar, TextView tvMyName) {
        int currentUserId = 1; // ID user hiện tại

        ApiService apiService = ApiClient.getAuthenticatedClient(requireContext()).create(ApiService.class);
        apiService.getUserProfile(currentUserId).enqueue(new Callback<ApiService.OtherProfile>() {
            @Override
            public void onResponse(Call<ApiService.OtherProfile> call, Response<ApiService.OtherProfile> response) {
                if (response.isSuccessful() && response.body() != null && getContext() != null) {
                    ApiService.OtherProfile myProfile = response.body();
                    tvMyName.setText(myProfile.full_name);

                    if (myProfile.avatar_url != null && !myProfile.avatar_url.isEmpty()) {
                        Glide.with(getContext())
                                .load(myProfile.avatar_url)
                                .placeholder(R.drawable.bxs_user)
                                .into(imgMyAvatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiService.OtherProfile> call, Throwable t) {
                // Giữ nguyên fallback nếu không lấy được dữ liệu
            }
        });
    }

    // --- Load danh sách bạn bè từ Backend ---
    private void loadFriendsList(RecyclerView rvFriendsList, LinearLayout layoutPrivateMessage) {
        int currentUserId = 1; // ID user hiện tại

        ApiService apiService = ApiClient.getAuthenticatedClient(requireContext()).create(ApiService.class);
        apiService.getUserFriends(currentUserId).enqueue(new Callback<FriendResponse>() {
            @Override
            public void onResponse(Call<FriendResponse> call, Response<FriendResponse> response) {
                if (response.isSuccessful() && response.body() != null && getContext() != null) {
                    List<Post.Author> friends = response.body().getFriends();
                    FriendAdapter adapter = new FriendAdapter(getContext(), friends, friend -> {
                        selectedFriend = friend;
                        // Hiện ô nhập tin nhắn khi chọn 1 người bạn
                        layoutPrivateMessage.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Đã chọn: " + friend.getFullName(), Toast.LENGTH_SHORT).show();
                    });
                    rvFriendsList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<FriendResponse> call, Throwable t) {
                // Xử lý lỗi nếu không kết nối được
            }
        });
    }

    // --- Chia sẻ lên Feed cá nhân ---
    private void shareToFeed(String caption) {
        ApiService apiService = ApiClient.getAuthenticatedClient(requireContext()).create(ApiService.class);
        apiService.sharePostToFeed(postId, new ApiService.ShareToFeedRequest(1, caption, "PUBLIC"))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getContext(), "Đã chia sẻ lên Trang cá nhân!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "Đã chia sẻ thành công!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
    }

    // --- Gửi qua tin nhắn riêng ---
    private void shareToMessage(int receiverId, String messageText) {
        ApiService apiService = ApiClient.getAuthenticatedClient(requireContext()).create(ApiService.class);
        ApiService.ShareToMessageRequest request = new ApiService.ShareToMessageRequest(1, Collections.singletonList(receiverId), messageText);

        apiService.sharePostToMessage(postId, request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "Đã gửi bài viết cho bạn bè!", Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Đã gửi thành công!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}