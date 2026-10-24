package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame18EnterGmailOTP extends Activity {

    private ImageButton btnBack;
    private TextView tvEmailTarget;
    private TextView tvResendTimer;
    private LinearLayout btnNextOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_gmail_otp);

        initViews();

        if (getIntent() != null && getIntent().hasExtra("EMAIL_TARGET")) {
            String email = getIntent().getStringExtra("EMAIL_TARGET");
            if (tvEmailTarget != null && email != null && !email.isEmpty()) {
                tvEmailTarget.setText(email);
            }
        }

        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvEmailTarget = findViewById(R.id.tvEmailTarget);
        tvResendTimer = findViewById(R.id.tvResendTimer);
        btnNextOtp = findViewById(R.id.btnNextOtp);
    }

    private void setupEvents() {
        // 1. Nút Back tại activity_enter_gmail_otp.xml -> Về giao diện trước đó (activity_change_gmail_address.xml hoặc activity_forgot_password.xml)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Tiếp theo (btnNextOtp):
        // - Nếu từ Đổi Gmail (CHANGE_GMAIL) -> Quay về activity_profile_details.xml (Frame15ProfileDetails)
        // - Nếu từ Quên mật khẩu -> Nhảy qua activity_reset_password.xml (ResetPasswordActivity)
        if (btnNextOtp != null) {
            btnNextOtp.setOnClickListener(v -> {
                String flowType = getIntent() != null ? getIntent().getStringExtra("FLOW_TYPE") : null;
                String flowSource = getIntent() != null ? getIntent().getStringExtra("FLOW_SOURCE") : null;

                if ("CHANGE_GMAIL".equals(flowType)) {
                    Toast.makeText(this, "Đổi Gmail thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Frame18EnterGmailOTP.this, Frame15ProfileDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else if ("ENTER_CURRENT_PASSWORD".equals(flowSource) || "FORGOT_PASSWORD".equals(flowType) || "PROFILE_DETAILS".equals(flowSource)) {
                    Toast.makeText(this, "Xác thực OTP Email thành công, chuyển sang đặt lại mật khẩu!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Frame18EnterGmailOTP.this, ResetPasswordActivity.class);
                    intent.putExtra("FLOW_SOURCE", flowSource);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Xác minh mã OTP Email thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Frame18EnterGmailOTP.this, Frame15ProfileDetails.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (tvResendTimer != null) {
            tvResendTimer.setOnClickListener(v ->
                    Toast.makeText(this, "Đã gửi lại mã xác thực đến Email của bạn!", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
