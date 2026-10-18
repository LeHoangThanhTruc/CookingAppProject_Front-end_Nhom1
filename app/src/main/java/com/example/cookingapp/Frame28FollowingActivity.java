package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Frame28FollowingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_28_following);
        View root = findViewById(android.R.id.content);
        for (String name : new String[]{"Anh An", "Huy Hoàng", "Linh Linh", "Minh Minh"}) {
            FrameNavigation.bindText(root, name, v -> startActivity(new Intent(this, Frame24OtherProfileActivity.class)));
        }
    }
}
