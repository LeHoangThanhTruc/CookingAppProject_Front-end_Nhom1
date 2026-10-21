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
        // 1. Nút Back -> Quay lại giao diện trước đó
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Tiếp theo (btnNextOtp)
        if (btnNextOtp != null) {
            btnNextOtp.setOnClickListener(v -> {
                String flowSource = getIntent() != null ? getIntent().getStringExtra("FLOW_SOURCE") : "LOGIN";
                Intent intent;
                if ("LOGIN".equals(flowSource)) {
                    Toast.makeText(this, "Xác minh thành công, quay về màn hình Đăng nhập!", Toast.LENGTH_LONG).show();
                    intent = new Intent(Frame18EnterGmailOTP.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                } else if ("ENTER_CURRENT_PASSWORD".equals(flowSource)) {
                    Toast.makeText(this, "Xác minh thành công, quay về chi tiết hồ sơ!", Toast.LENGTH_LONG).show();
                    intent = new Intent(Frame18EnterGmailOTP.this, Frame15ProfileDetails.class);
                } else {
                    Toast.makeText(this, "Xác minh mã OTP Email thành công!", Toast.LENGTH_LONG).show();
                    intent = new Intent(Frame18EnterGmailOTP.this, Frame15ProfileDetails.class);
                }
                startActivity(intent);
                finish();
            });
        }

        if (tvResendTimer != null) {
            tvResendTimer.setOnClickListener(v ->
                    Toast.makeText(this, "Đã gửi lại mã xác thực đến Email của bạn!", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
