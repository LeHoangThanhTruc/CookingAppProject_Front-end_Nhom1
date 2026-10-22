package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Frame27FriendsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_27_friends);
        FrameNavigation.bindOpen(this, R.id.friend_an, Frame24OtherProfileActivity.class);
        FrameNavigation.bindOpen(this, R.id.friend_huy, Frame24OtherProfileActivity.class);
        FrameNavigation.bindOpen(this, R.id.friend_linh, Frame24OtherProfileActivity.class);
        FrameNavigation.bindOpen(this, R.id.friend_minh, Frame24OtherProfileActivity.class);
        FrameNavigation.bindBack(this, R.id.button_back_list);
    }
}
