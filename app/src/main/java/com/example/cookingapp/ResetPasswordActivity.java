package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSubmitReset;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;

    // Có giá trị khi đến từ màn hình nhập OTP đã xác thực thành công
    private String resetToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnBack = findViewById(R.id.btnBack);
        btnSubmitReset = findViewById(R.id.btnSubmitReset);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        resetToken = getIntent() != null ? getIntent().getStringExtra("RESET_TOKEN") : null;

        // Nút con mắt (đã có sẵn trong layout) để xem/ẩn mật khẩu
        ImageView iconEyeNew = findViewById(R.id.iconEyeOpen1);
        ImageView iconEyeConfirm = findViewById(R.id.iconEyeOpen2);
        PasswordToggleHelper.attach(edtNewPassword, iconEyeNew);
        PasswordToggleHelper.attach(edtConfirmPassword, iconEyeConfirm);

        // Nút Back tại activity_reset_password.xml -> finish()
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Bấm nút Xác nhận đặt lại (btnSubmitReset):
        // - Có reset token -> gọi API đặt mật khẩu mới rồi mới chuyển màn hình
        // - Không có token (luồng khác chưa nối backend) -> giữ hành vi cũ
        if (btnSubmitReset != null) {
            btnSubmitReset.setOnClickListener(v -> {
                if (resetToken != null) {
                    submitNewPassword();
                } else {
                    Toast.makeText(this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    navigateAfterReset();
                }
            });
        }
    }

    private void submitNewPassword() {
        String newPassword = edtNewPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        if (newPassword.length() < 8) {
            edtNewPassword.setError("Mật khẩu phải có ít nhất 8 ký tự");
            edtNewPassword.requestFocus();
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            edtConfirmPassword.requestFocus();
            return;
        }

        btnSubmitReset.setEnabled(false);
        PasswordResetHelper.resetPassword(resetToken, newPassword,
                new PasswordResetHelper.Listener<AuthModels.MessageResponse>() {
                    @Override
                    public void onSuccess(AuthModels.MessageResponse value) {
                        btnSubmitReset.setEnabled(true);
                        Toast.makeText(ResetPasswordActivity.this,
                                "Đặt lại mật khẩu thành công! Hãy đăng nhập bằng mật khẩu mới.",
                                Toast.LENGTH_LONG).show();
                        navigateAfterReset();
                    }

                    @Override
                    public void onError(String message) {
                        btnSubmitReset.setEnabled(true);
                        Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
    }

    // - Nếu từ Login -> Quay về activity_login.xml (LoginActivity)
    // - Nếu từ Chi tiết hồ sơ -> Quay về activity_profile_details.xml (Frame15ProfileDetails)
    private void navigateAfterReset() {
        String flowSource = getIntent() != null ? getIntent().getStringExtra("FLOW_SOURCE") : "LOGIN";
        Intent intent;
        if ("LOGIN".equals(flowSource)) {
            intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            intent = new Intent(ResetPasswordActivity.this, Frame15ProfileDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        startActivity(intent);
        finish();
    }
}