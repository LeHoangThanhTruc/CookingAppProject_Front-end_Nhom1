package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Frame32PrivacyActivity extends Activity {
    private View selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_32_privacy);

        FrameNavigation.bindBack(this, R.id.button_back_privacy);
        selectedOption = findViewById(R.id.privacy_only_me);
        FrameNavigation.bind(this, R.id.privacy_only_me, v -> selectPrivacy(v, "Chỉ Mình Tôi"));
        FrameNavigation.bind(this, R.id.privacy_friends, v -> selectPrivacy(v, "Chỉ Bạn Bè Được Xem"));
        FrameNavigation.bind(this, R.id.privacy_public, v -> selectPrivacy(v, "Public"));
        FrameNavigation.bind(this, R.id.button_publish_recipe, v -> {
            Toast.makeText(this, "Đăng công thức thành công!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Frame25MyProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void selectPrivacy(View option, String label) {
        if (selectedOption != null) {
            selectedOption.setBackgroundResource(R.drawable.bg_privacy_option_outline);
        }
        option.setBackgroundResource(R.drawable.bg_privacy_option_selected);
        selectedOption = option;
        Toast.makeText(this, "Quyền xem: " + label, Toast.LENGTH_SHORT).show();
    }
}
