package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Frame24OtherProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_24_other_profile);
        View root = findViewById(android.R.id.content);
        FrameNavigation.bindText(root, "Giới thiệu", v -> startActivity(new Intent(this, Frame26BioActivity.class)));
        FrameNavigation.bindText(root, "Bạn bè", v -> startActivity(new Intent(this, Frame27FriendsActivity.class)));
        FrameNavigation.bindText(root, "Đang theo dõi", v -> startActivity(new Intent(this, Frame28FollowingActivity.class)));
        FrameNavigation.bindText(root, "Nhắn tin với người này", v -> startActivity(new Intent(this, Frame29MessagesActivity.class)));
    }
}
