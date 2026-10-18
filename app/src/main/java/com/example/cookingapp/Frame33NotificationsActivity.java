package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Frame33NotificationsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_33_notifications);

        findViewById(R.id.button_back_notifications).setOnClickListener(v -> finish());
        View root = findViewById(android.R.id.content);
        FrameNavigation.bindText(root, "Đã gửi cho bạn lời mời kết bạn", v -> startActivity(new Intent(this, Frame24OtherProfileActivity.class)));
        FrameNavigation.bindText(root, "Đã chia sẻ bài viết của bạn", v -> startActivity(new Intent(this, Frame25MyProfileActivity.class)));
        FrameNavigation.bindText(root, "Đã thích công thức của bạn", v -> startActivity(new Intent(this, Frame25MyProfileActivity.class)));
    }
}
