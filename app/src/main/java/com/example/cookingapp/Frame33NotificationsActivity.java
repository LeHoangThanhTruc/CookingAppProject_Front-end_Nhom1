package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Frame33NotificationsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_33_notifications);

        findViewById(R.id.button_back_notifications).setOnClickListener(v -> finish());
        FrameNavigation.bindOpen(this, R.id.notification_friend_huy, Frame24OtherProfileActivity.class);
        FrameNavigation.bindOpen(this, R.id.notification_friend_an, Frame24OtherProfileActivity.class);
        FrameNavigation.bindOpen(this, R.id.notification_share_linh, RecipeDetailActivity.class);
        FrameNavigation.bindOpen(this, R.id.notification_like_minh, RecipeDetailActivity.class);
        bindDelete(R.id.delete_notification_huy, R.id.notification_friend_huy);
        bindDelete(R.id.delete_notification_an, R.id.notification_friend_an);
        bindDelete(R.id.delete_notification_linh, R.id.notification_share_linh);
        bindDelete(R.id.delete_notification_minh, R.id.notification_like_minh);
    }

    private void bindDelete(int deleteId, int rowId) {
        FrameNavigation.bind(this, deleteId, v -> {
            View row = findViewById(rowId);
            row.setVisibility(View.GONE);
            Toast.makeText(this, "Đã xóa thông báo", Toast.LENGTH_SHORT).show();
        });
    }
}
