package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Frame30ChatDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_30_chat_detail);
        FrameNavigation.bindText(findViewById(android.R.id.content), "‹", v -> {
            startActivity(new Intent(this, Frame29MessagesActivity.class));
            finish();
        });
    }
}
