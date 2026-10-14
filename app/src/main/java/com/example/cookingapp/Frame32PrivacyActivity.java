package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;

public class Frame32PrivacyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_32_privacy);

        findViewById(R.id.button_back_privacy).setOnClickListener(v -> finish());
    }
}
