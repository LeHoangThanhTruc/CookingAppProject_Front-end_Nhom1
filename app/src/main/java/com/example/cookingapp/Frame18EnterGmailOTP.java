package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame18EnterGmailOTP extends Activity {

    private ImageButton btnBack;
    private TextView tvEmailTarget;
    private TextView tvResendTimer;
    private LinearLayout btnNextOtp;
    private EditText edtOtp;

    // Khác null khi đi từ luồng Quên mật khẩu (ForgotPasswordActivity)
    private String resetIdentifier;
    private CountDownTimer resendTimer;

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

        resetIdentifier = getIntent() != null ? getIntent().getStringExtra("RESET_IDENTIFIER") : null;
        if (resetIdentifier != null) {
            PasswordResetHelper.bindOtpInput(this, edtOtp);
            resendTimer = PasswordResetHelper.startResendCountdown(tvResendTimer, this::resendOtp);
        }

        setupEvents();
    }

    @Override
    protected void onDestroy() {
        if (resendTimer != null) {
            resendTimer.cancel();
        }
        super.onDestroy();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvEmailTarget = findViewById(R.id.tvEmailTarget);
        tvResendTimer = findViewById(R.id.tvResendTimer);
        btnNextOtp = findViewById(R.id.btnNextOtp);
        edtOtp = findViewById(R.id.edtOtp);
    }

    private void setupEvents() {
        // 1. Nút Back tại activity_enter_gmail_otp.xml -> Về giao diện trước đó (activity_change_gmail_address.xml hoặc activity_forgot_password.xml)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Nút Tiếp theo (btnNextOtp):
        // - Luồng Quên mật khẩu (có RESET_IDENTIFIER) -> xác thực OTP với backend rồi sang ResetPasswordActivity
        // - Nếu từ Đổi Gmail (CHANGE_GMAIL) -> Quay về activity_profile_details.xml (Frame15ProfileDetails)
        if (btnNextOtp != null) {
            btnNextOtp.setOnClickListener(v -> {
                if (resetIdentifier != null) {
                    verifyOtp();
                    return;
                }

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

        // Luồng khác (đổi Gmail...) vẫn giữ hành vi cũ; luồng quên mật khẩu dùng bộ đếm ngược ở onCreate
        if (tvResendTimer != null && resetIdentifier == null) {
            tvResendTimer.setOnClickListener(v ->
                    Toast.makeText(this, "Đã gửi lại mã xác thực đến Email của bạn!", Toast.LENGTH_SHORT).show()
            );
        }
    }

    private void verifyOtp() {
        if (edtOtp == null) {
            Toast.makeText(this, "Layout chưa có ô nhập mã OTP (id edtOtp)", Toast.LENGTH_LONG).show();
            return;
        }
        String otp = edtOtp.getText().toString().trim();
        if (otp.length() != 6) {
            edtOtp.setError("Vui lòng nhập mã gồm 6 số");
            edtOtp.requestFocus();
            return;
        }

        btnNextOtp.setEnabled(false);
        PasswordResetHelper.verifyOtp(resetIdentifier, otp,
                new PasswordResetHelper.Listener<AuthModels.VerifyOtpResponse>() {
                    @Override
                    public void onSuccess(AuthModels.VerifyOtpResponse value) {
                        btnNextOtp.setEnabled(true);
                        Intent intent = new Intent(Frame18EnterGmailOTP.this, ResetPasswordActivity.class);
                        intent.putExtra("FLOW_SOURCE", getIntent().getStringExtra("FLOW_SOURCE"));
                        intent.putExtra("RESET_TOKEN", value.reset_token);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        btnNextOtp.setEnabled(true);
                        Toast.makeText(Frame18EnterGmailOTP.this, message, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void resendOtp() {
        PasswordResetHelper.requestOtp(resetIdentifier,
                new PasswordResetHelper.Listener<AuthModels.MessageResponse>() {
                    @Override
                    public void onSuccess(AuthModels.MessageResponse value) {
                        Toast.makeText(Frame18EnterGmailOTP.this,
                                "Đã gửi lại mã xác thực đến Email của bạn!", Toast.LENGTH_SHORT).show();
                        if (resendTimer != null) {
                            resendTimer.cancel();
                        }
                        resendTimer = PasswordResetHelper.startResendCountdown(
                                tvResendTimer, Frame18EnterGmailOTP.this::resendOtp);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(Frame18EnterGmailOTP.this, message, Toast.LENGTH_LONG).show();
                    }
                });
    }
}