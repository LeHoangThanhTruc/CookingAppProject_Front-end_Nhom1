package com.example.cookingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Frame15ProfileDetails extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView btnChangeAvatar;
    private EditText etUsername;
    private EditText etGmail;
    private EditText etPhone;
    private EditText etPassword;
    private ImageView ivAvatar;
    private ImageView ivEditUsername;
    private ImageView ivEditGmail;
    private ImageView ivEditPhone;
    private ImageView ivEyePassword;
    private LinearLayout btnChangePasswordRow;
    private ProfileRepository profileRepository;

    private final ActivityResultLauncher<String> avatarPicker = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            this::uploadSelectedAvatar
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        if (!SessionManager.requireAuthenticated(this)) {
            return;
        }

        initViews();
        profileRepository = ProfileRepository.getInstance(this);
        setupEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (profileRepository != null && SessionManager.hasAccessToken(this)) {
            loadProfile();
        }
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);
        ivAvatar = findViewById(R.id.ivAvatar);
        etUsername = findViewById(R.id.etUsername);
        etGmail = findViewById(R.id.etGmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        ivEditUsername = findViewById(R.id.ivEditUsername);
        ivEditGmail = findViewById(R.id.ivEditGmail);
        ivEditPhone = findViewById(R.id.ivEditPhone);
        ivEyePassword = findViewById(R.id.ivEyePassword);
        btnChangePasswordRow = findViewById(R.id.btnChangePasswordRow);

        etUsername.setFocusable(false);
        etGmail.setFocusable(false);
        etPhone.setFocusable(false);
        etPassword.setFocusable(false);
        etPassword.setText("••••••••");
        ivEditUsername.setVisibility(View.GONE);
        ivEditGmail.setVisibility(View.GONE);
        ivEyePassword.setVisibility(View.GONE);
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, Frame7SettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnChangeAvatar.setOnClickListener(v -> avatarPicker.launch("image/*"));
        ivEditPhone.setOnClickListener(v ->
                startActivity(new Intent(this, Frame26BioActivity.class))
        );

        btnChangePasswordRow.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            intent.putExtra("FLOW_SOURCE", "PROFILE_DETAILS");
            startActivity(intent);
        });
    }

    private void loadProfile() {
        profileRepository.getProfile(new ProfileRepository.ApiCallback<Profile>() {
            @Override
            public void onSuccess(Profile profile) {
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                etUsername.setText(profile.getUsername());
                etGmail.setText(profile.getEmail());
                etPhone.setText(ProfileActivitySupport.valueOrFallback(
                        profile.getPhoneNumber(),
                        "Chưa cập nhật"
                ));
                ProfileImageLoader.load(ivAvatar, profile.getAvatarUrl());
            }

            @Override
            public void onError(ProfileRepository.ApiError error) {
                ProfileActivitySupport.handleError(Frame15ProfileDetails.this, error);
            }
        });
    }

    private void uploadSelectedAvatar(Uri uri) {
        if (uri == null || profileRepository == null) {
            return;
        }

        setAvatarUploading(true);
        profileRepository.uploadAvatar(uri, new ProfileRepository.ApiCallback<Profile>() {
            @Override
            public void onSuccess(Profile profile) {
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                setAvatarUploading(false);
                ProfileImageLoader.load(ivAvatar, profile.getAvatarUrl());
                Toast.makeText(
                        Frame15ProfileDetails.this,
                        "Đã cập nhật ảnh đại diện",
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onError(ProfileRepository.ApiError error) {
                setAvatarUploading(false);
                ProfileActivitySupport.handleError(Frame15ProfileDetails.this, error);
            }
        });
    }

    private void setAvatarUploading(boolean uploading) {
        if (btnChangeAvatar != null) {
            btnChangeAvatar.setEnabled(!uploading);
            btnChangeAvatar.setAlpha(uploading ? 0.55f : 1f);
            btnChangeAvatar.setText(uploading ? "Đang tải ảnh..." : "Đổi ảnh đại diện");
        }
    }
}
