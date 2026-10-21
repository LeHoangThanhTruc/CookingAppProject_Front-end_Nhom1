package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Frame31CreateRecipeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_31_create_recipe);

        // Nút Back "<" trên góc trái -> Quay về activity_frame_25_my_profile.xml (Frame25MyProfileActivity)
        TextView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(Frame31CreateRecipeActivity.this, Frame25MyProfileActivity.class);
                startActivity(intent);
                finish();
            });
        }

        View btnNext = findViewById(R.id.button_next_recipe);
        if (btnNext != null) {
            btnNext.setOnClickListener(
                    v -> startActivity(new Intent(this, Frame32PrivacyActivity.class))
            );
        }
    }
}
