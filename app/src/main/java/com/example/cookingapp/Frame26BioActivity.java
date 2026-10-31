package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Frame26BioActivity extends AppCompatActivity {
    private ImageView btnBack;
    private RelativeLayout btnSubmit;
    private EditText edtFullName;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtBio;
    private ProfileRepository profileRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_26_bio);

        if (!SessionManager.requireAuthenticated(this)) {
            return;
        }

        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtFullName = findViewById(R.id.edtFullname);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtBio = findViewById(R.id.edtBio);
        profileRepository = ProfileRepository.getInstance(this);

        edtEmail.setEnabled(false);
        edtEmail.setFocusable(false);
        edtEmail.setAlpha(0.75f);

        btnBack.setOnClickListener(v -> finish());
        btnSubmit.setOnClickListener(v -> submitProfile());

        loadProfile();
    }

    private void loadProfile() {
        setSubmitting(true);
        profileRepository.getProfile(new ProfileRepository.ApiCallback<Profile>() {
            @Override
            public void onSuccess(Profile profile) {
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                edtFullName.setText(profile.getFullName());
                edtPhone.setText(profile.getPhoneNumber());
                edtEmail.setText(profile.getEmail());
                edtBio.setText(profile.getBio());
                setSubmitting(false);
            }

            @Override
            public void onError(ProfileRepository.ApiError error) {
                setSubmitting(false);
                ProfileActivitySupport.handleError(Frame26BioActivity.this, error);
            }
        });
    }

    private void submitProfile() {
        String fullName = edtFullName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String bio = edtBio.getText().toString().trim();

        if (fullName.isEmpty()) {
            edtFullName.setError("Vui lòng nhập họ và tên");
            edtFullName.requestFocus();
            return;
        }
        if (fullName.length() > 100) {
            edtFullName.setError("Họ và tên không được vượt quá 100 ký tự");
            edtFullName.requestFocus();
            return;
        }
        if (!phone.isEmpty() && !phone.matches("^\\+?\\d{8,15}$")) {
            edtPhone.setError("Số điện thoại phải gồm 8-15 chữ số");
            edtPhone.requestFocus();
            return;
        }
        if (bio.length() > 500) {
            edtBio.setError("Giới thiệu không được vượt quá 500 ký tự");
            edtBio.requestFocus();
            return;
        }

        setSubmitting(true);
        profileRepository.updateProfile(fullName, phone, bio,
                new ProfileRepository.ApiCallback<Profile>() {
                    @Override
                    public void onSuccess(Profile profile) {
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        setSubmitting(false);
                        Toast.makeText(
                                Frame26BioActivity.this,
                                "Cập nhật thông tin thành công!",
                                Toast.LENGTH_SHORT
                        ).show();
                        navigateToMyProfile();
                    }

                    @Override
                    public void onError(ProfileRepository.ApiError error) {
                        setSubmitting(false);
                        ProfileActivitySupport.handleError(Frame26BioActivity.this, error);
                    }
                });
    }

    private void setSubmitting(boolean submitting) {
        if (btnSubmit != null) {
            btnSubmit.setEnabled(!submitting);
            btnSubmit.setAlpha(submitting ? 0.55f : 1f);
        }
    }

    private void navigateToMyProfile() {
        Intent intent = new Intent(this, Frame25MyProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
