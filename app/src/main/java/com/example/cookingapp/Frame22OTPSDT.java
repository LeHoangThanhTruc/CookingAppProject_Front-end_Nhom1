package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame22OTPSDT extends Activity {

    private ImageButton btnBack;
    private TextView tvPhoneTarget;
    private TextView tvResendTimer;
    private LinearLayout btnNextOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpsdt);

        initViews();

        if (getIntent() != null && getIntent().hasExtra("PHONE_TARGET")) {
            String phone = getIntent().getStringExtra("PHONE_TARGET");
            if (tvPhoneTarget != null && phone != null && !phone.isEmpty()) {
                tvPhoneTarget.setText(phone);
            }
        }

        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvPhoneTarget = findViewById(R.id.tvPhoneTarget);
        tvResendTimer = findViewById(R.id.tvResendTimer);
        btnNextOtp = findViewById(R.id.btnNextOtp);
    }

    private void setupEvents() {
        // 1. Nút Back -> Quay lại giao diện trước đó
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Tiếp theo
        if (btnNextOtp != null) {
            btnNextOtp.setOnClickListener(v -> {
                String flowSource = getIntent() != null ? getIntent().getStringExtra("FLOW_SOURCE") : "LOGIN";
                Intent intent;
                if ("LOGIN".equals(flowSource)) {
                    Toast.makeText(this, "Xác minh thành công, quay về màn hình Đăng nhập!", Toast.LENGTH_LONG).show();
                    intent = new Intent(Frame22OTPSDT.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                } else if ("ENTER_CURRENT_PASSWORD".equals(flowSource)) {
                    Toast.makeText(this, "Xác minh thành công, quay về chi tiết hồ sơ!", Toast.LENGTH_LONG).show();
                    intent = new Intent(Frame22OTPSDT.this, Frame15ProfileDetails.class);
                } else {
                    Toast.makeText(this, "Xác minh mã OTP SĐT thành công!", Toast.LENGTH_LONG).show();
                    intent = new Intent(Frame22OTPSDT.this, Frame15ProfileDetails.class);
                }
                startActivity(intent);
                finish();
            });
        }

        if (tvResendTimer != null) {
            tvResendTimer.setOnClickListener(v ->
                    Toast.makeText(this, "Đã gửi lại mã xác thực qua Tin nhắn SMS!", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
