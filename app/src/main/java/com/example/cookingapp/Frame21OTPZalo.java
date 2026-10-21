package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame21OTPZalo extends Activity {

    private ImageButton btnBack;
    private TextView tvPhoneTarget;
    private TextView tvResendTimer;
    private LinearLayout btnNextOtp;

    private View viewOtp1, viewOtp2, viewOtp3, viewOtp4, viewOtp5, viewOtp6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_zalo);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvPhoneTarget = findViewById(R.id.tvPhoneTarget);
        tvResendTimer = findViewById(R.id.tvResendTimer);
        btnNextOtp = findViewById(R.id.btnNextOtp);

        viewOtp1 = findViewById(R.id.viewOtp1);
        viewOtp2 = findViewById(R.id.viewOtp2);
        viewOtp3 = findViewById(R.id.viewOtp3);
        viewOtp4 = findViewById(R.id.viewOtp4);
        viewOtp5 = findViewById(R.id.viewOtp5);
        viewOtp6 = findViewById(R.id.viewOtp6);
    }

    private void setupEvents() {
        // 1. Nút Back -> Quay lại giao diện trước đó
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Tiếp theo -> Quay về activity_profile_details.xml (Frame15ProfileDetails)
        if (btnNextOtp != null) {
            btnNextOtp.setOnClickListener(v -> {
                Toast.makeText(this, "Xác minh mã OTP Zalo thành công!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Frame21OTPZalo.this, Frame15ProfileDetails.class);
                startActivity(intent);
                finish();
            });
        }

        if (tvResendTimer != null) {
            tvResendTimer.setOnClickListener(v ->
                    Toast.makeText(this, "Đã gửi lại mã xác thực qua Zalo!", Toast.LENGTH_SHORT).show()
            );
        }
    }
}
