package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Frame28FollowingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_28_following);
        FrameNavigation.bindOpen(this, R.id.following_an, Frame24OtherProfileActivity.class);
        FrameNavigation.bindOpen(this, R.id.following_huy, Frame24OtherProfileActivity.class);
        FrameNavigation.bindOpen(this, R.id.following_linh, Frame24OtherProfileActivity.class);
        FrameNavigation.bindOpen(this, R.id.following_minh, Frame24OtherProfileActivity.class);
        FrameNavigation.bindBack(this, R.id.button_back_list);
    }
}
