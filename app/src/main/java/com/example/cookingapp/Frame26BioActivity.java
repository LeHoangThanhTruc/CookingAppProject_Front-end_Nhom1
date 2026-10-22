package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;

public class Frame26BioActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_26_bio);
        FrameNavigation.bindBack(this, R.id.button_back_profile);
        FrameNavigation.bindToast(this, R.id.menu_bio, "Bạn đang xem phần giới thiệu");
        FrameNavigation.bindOpen(this, R.id.menu_friends, Frame27FriendsActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_following, Frame28FollowingActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_messages, Frame29MessagesActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_notifications, Frame33NotificationsActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_create_recipe, Frame31CreateRecipeActivity.class);
    }
}
