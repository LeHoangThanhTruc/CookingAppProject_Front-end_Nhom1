package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Frame25MyProfileActivity extends Activity {
    private ImageView profileAvatar;
    private TextView profileFullName;
    private TextView profileUsername;
    private ProfileRepository profileRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_25_my_profile);

        if (!SessionManager.requireAuthenticated(this)) {
            return;
        }

        profileAvatar = findViewById(R.id.ivProfileAvatar);
        profileFullName = findViewById(R.id.tvProfileFullName);
        profileUsername = findViewById(R.id.tvProfileUsername);
        profileRepository = ProfileRepository.getInstance(this);

        FrameNavigation.bindBack(this, R.id.button_back_profile);
        FrameNavigation.bindOpen(this, R.id.menu_bio, Frame26BioActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_friends, Frame27FriendsActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_following, Frame28FollowingActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_messages, Frame29MessagesActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_notifications, Frame33NotificationsActivity.class);
        FrameNavigation.bindOpen(this, R.id.menu_create_recipe, Frame31CreateRecipeActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (profileRepository != null && SessionManager.hasAccessToken(this)) {
            loadProfile();
        }
    }

    private void loadProfile() {
        profileRepository.getProfile(new ProfileRepository.ApiCallback<Profile>() {
            @Override
            public void onSuccess(Profile profile) {
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                String displayName = ProfileActivitySupport.valueOrFallback(
                        profile.getFullName(),
                        profile.getUsername()
                );
                profileFullName.setText(displayName);
                profileUsername.setText("@" + profile.getUsername());
                ProfileImageLoader.load(profileAvatar, profile.getAvatarUrl());
            }

            @Override
            public void onError(ProfileRepository.ApiError error) {
                ProfileActivitySupport.handleError(Frame25MyProfileActivity.this, error);
            }
        });
    }
}
