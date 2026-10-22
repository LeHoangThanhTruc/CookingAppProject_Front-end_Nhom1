package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;

public class Frame25MyProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_25_my_profile);
        FrameNavigation.bindBack(this, R.id.button_back_profile);
        FrameNavigation.bindOpen(this, R.id.menu_bio, Frame26BioActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_friends, Frame27FriendsActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_following, Frame28FollowingActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_messages, Frame29MessagesActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_notifications, Frame33NotificationsActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_create_recipe, Frame31CreateRecipeActivity.class);
    }
}
