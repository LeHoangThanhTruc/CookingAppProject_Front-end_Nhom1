package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Frame31CreateRecipeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_31_create_recipe);

        findViewById(R.id.button_next_recipe).setOnClickListener(
                v -> startActivity(new Intent(this, Frame32PrivacyActivity.class))
        );
    }
}
