package com.example.cookingapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Frame34OtherBioActivity extends AppCompatActivity {
    public static final String EXTRA_FULL_NAME = "other_profile_full_name";
    public static final String EXTRA_PHONE = "other_profile_phone";
    public static final String EXTRA_EMAIL = "other_profile_email";
    public static final String EXTRA_BIO = "other_profile_bio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_34_other_bio);

        findViewById(R.id.btnBack).setOnClickListener(view -> finish());
        bindValue(R.id.tvFullname, EXTRA_FULL_NAME, "Thảo Vy");
        bindValue(R.id.tvPhone, EXTRA_PHONE, "Chưa cập nhật");
        bindValue(R.id.tvEmail, EXTRA_EMAIL, "Chưa cập nhật");
        bindValue(
                R.id.tvBio,
                EXTRA_BIO,
                "Người dùng chưa cập nhật phần giới thiệu."
        );
    }

    private void bindValue(int viewId, String extraKey, String fallback) {
        String value = getIntent().getStringExtra(extraKey);
        if (value == null || value.trim().isEmpty()) {
            value = fallback;
        }
        ((TextView) findViewById(viewId)).setText(value);
    }
}
