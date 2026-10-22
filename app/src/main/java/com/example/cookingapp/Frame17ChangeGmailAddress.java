package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame17ChangeGmailAddress extends Activity {

    private ImageButton btnBack;
    private EditText etNewEmail;
    private CheckBox cbNewsletter;
    private LinearLayout btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_gmail_address);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etNewEmail = findViewById(R.id.etNewEmail);
        cbNewsletter = findViewById(R.id.cbNewsletter);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setupEvents() {
        // 1. Nút Back -> Quay lại activity_profile_details.xml (Frame15ProfileDetails)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(Frame17ChangeGmailAddress.this, Frame15ProfileDetails.class);
                startActivity(intent);
                finish();
            });
        }

        // 2. Nút Tiếp theo -> Chuyển sang activity_enter_gmail_otp.xml (Frame18EnterGmailOTP)
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                String email = etNewEmail != null ? etNewEmail.getText().toString().trim() : "";

                if (email.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập địa chỉ email mới!", Toast.LENGTH_SHORT).show();
                } else if (!email.contains("@") || !email.contains(".")) {
                    Toast.makeText(this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đã gửi mã xác nhận đến: " + email, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Frame17ChangeGmailAddress.this, Frame18EnterGmailOTP.class);
                    intent.putExtra("EMAIL_TARGET", email);
                    intent.putExtra("FLOW_TYPE", "CHANGE_GMAIL");
                    startActivity(intent);
                }
            });
        }
        findViewById(R.id.layoutNewsletter).setOnClickListener(v -> cbNewsletter.setChecked(!cbNewsletter.isChecked()));
    }
}
