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
        // 1. Nút Back tại activity_otpsdt.xml -> Quay về giao diện activity_forgot_password.xml
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Tiếp theo (btnNextOtp) -> Nhảy qua giao diện activity_reset_password.xml (ResetPasswordActivity)
        if (btnNextOtp != null) {
            btnNextOtp.setOnClickListener(v -> {
                String flowSource = getIntent() != null ? getIntent().getStringExtra("FLOW_SOURCE") : "LOGIN";
                Toast.makeText(this, "Xác thực OTP SĐT thành công, chuyển sang đặt lại mật khẩu!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Frame22OTPSDT.this, ResetPasswordActivity.class);
                intent.putExtra("FLOW_SOURCE", flowSource);
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
