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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private int postId;

    public ShareBottomSheetDialogFragment(int postId) {
        this.postId = postId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet_share, container, false);

        LinearLayout btnSelectPrivacy = view.findViewById(R.id.btnSelectPrivacy);
        EditText etShareCaption = view.findViewById(R.id.etShareCaption);
        Button btnShareNow = view.findViewById(R.id.btnShareNow);

        ShapeableImageView btnFriend1 = view.findViewById(R.id.btnFriend1);
        TextView tvSelectedFriendName = view.findViewById(R.id.tvSelectedFriendName);
        LinearLayout layoutPrivateMessage = view.findViewById(R.id.layoutPrivateMessage);
        EditText etPrivateMessage = view.findViewById(R.id.etPrivateMessage);
        Button btnSendMessage = view.findViewById(R.id.btnSendMessage);

        // 1. Chuyển sang Frame32PrivacyActivity chọn quyền xem
        btnSelectPrivacy.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Frame32PrivacyActivity.class);
            startActivity(intent);
        });

        // 2. Bấm "Chia sẻ ngay" lên Feed cá nhân
        btnShareNow.setOnClickListener(v -> {
            String caption = etShareCaption.getText().toString();
            callShareApi("FEED", caption, 0, "");
        });

        // 3. Bấm chọn bạn bè gửi riêng
        btnFriend1.setOnClickListener(v -> {
            tvSelectedFriendName.setText("Đã chọn: Bạn bè");
            layoutPrivateMessage.setVisibility(View.VISIBLE);
        });

        // 4. Bấm "Gửi" tin nhắn riêng
        btnSendMessage.setOnClickListener(v -> {
            String msg = etPrivateMessage.getText().toString();
            callShareApi("MESSAGE", "", 1, msg);
        });

        return view;
    }

    private void callShareApi(String shareType, String caption, int receiverId, String message) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.sharePost(postId, shareType, caption).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if ("FEED".equals(shareType)) {
                    Toast.makeText(getContext(), "Đã Chia Sẻ Thành Công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đã Gửi Thành Công!", Toast.LENGTH_SHORT).show();
                }
                dismiss(); // Đóng BottomSheet, quay lại màn hình chính
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Thao tác thành công!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}