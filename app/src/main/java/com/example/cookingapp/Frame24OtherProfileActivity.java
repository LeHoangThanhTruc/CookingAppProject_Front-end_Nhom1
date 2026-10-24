package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Frame24OtherProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_24_other_profile);
        FrameNavigation.bindBack(this, R.id.button_back_profile);
        FrameNavigation.bind(this, R.id.menu_bio, view -> openOtherUserBio());
        FrameNavigation.bindOpen(this, R.id.menu_friends, Frame27FriendsActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_following, Frame28FollowingActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_messages, Frame30ChatDetailActivity.class);
        bindToggle(R.id.button_add_friend, "Kết Bạn", "Đã Gửi Lời Mời");
        bindToggle(R.id.button_follow_profile, "Theo Dõi Người Này", "Đang Theo Dõi");
    }

    private void openOtherUserBio() {
        Intent intent = new Intent(this, Frame34OtherBioActivity.class);
        intent.putExtra(Frame34OtherBioActivity.EXTRA_FULL_NAME, "Thảo Vy");
        intent.putExtra(Frame34OtherBioActivity.EXTRA_PHONE, "0901 234 567");
        intent.putExtra(Frame34OtherBioActivity.EXTRA_EMAIL, "thaovy@example.com");
        intent.putExtra(
                Frame34OtherBioActivity.EXTRA_BIO,
                "Yêu thích nấu ăn gia đình, khám phá công thức mới và chia sẻ những bữa ăn ấm áp."
        );
        startActivity(intent);
    }

    private void bindToggle(int id, String idleText, String selectedText) {
        FrameNavigation.bind(this, id, view -> {
            TextView button = (TextView) view;
            boolean selected = !button.isSelected();
            button.setSelected(selected);
            button.setText(selected ? selectedText : idleText);
            button.setAlpha(selected ? 0.75f : 1f);
            Toast.makeText(this, selected ? selectedText : "Đã hủy", Toast.LENGTH_SHORT).show();
        });
    }
}
