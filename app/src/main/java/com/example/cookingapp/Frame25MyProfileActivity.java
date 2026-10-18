package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Frame25MyProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_25_my_profile);
        View root = findViewById(android.R.id.content);
        FrameNavigation.bindText(root, "Giới thiệu", v -> startActivity(new Intent(this, Frame26BioActivity.class)));
        FrameNavigation.bindText(root, "Bạn bè", v -> startActivity(new Intent(this, Frame27FriendsActivity.class)));
        FrameNavigation.bindText(root, "Người bạn đang theo dõi", v -> startActivity(new Intent(this, Frame28FollowingActivity.class)));
        FrameNavigation.bindText(root, "Tin nhắn", v -> startActivity(new Intent(this, Frame29MessagesActivity.class)));
        FrameNavigation.bindText(root, "Thông Báo", v -> startActivity(new Intent(this, Frame33NotificationsActivity.class)));
        FrameNavigation.bindText(root, "Tạo Công Thức", v -> startActivity(new Intent(this, Frame31CreateRecipeActivity.class)));
    }
}
