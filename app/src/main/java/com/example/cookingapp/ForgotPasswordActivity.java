package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText edtEmailOrPhone;
    private RelativeLayout btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Ánh xạ View
        btnBack = findViewById(R.id.btnBack);
        edtEmailOrPhone = findViewById(R.id.edtEmailOrPhone);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Nút Back tại activity_forgot_password.xml -> Nếu từ Profile Details thì quay về activity_profile_details.xml, ngược lại finish()
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                String flowSource = getIntent() != null ? getIntent().getStringExtra("FLOW_SOURCE") : "LOGIN";
                if ("PROFILE_DETAILS".equals(flowSource)) {
                    Intent intent = new Intent(ForgotPasswordActivity.this, Frame15ProfileDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            });
        }

        // Nút Tiếp tục (btnSubmit)
        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(v -> handleSubmit());
        }
    }

    private void handleSubmit() {
        if (edtEmailOrPhone == null) return;
        String input = edtEmailOrPhone.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Email đã đăng ký!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Mã xác thực chỉ gửi về email đã đăng ký nên bắt buộc nhập đúng định dạng email
        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            Toast.makeText(this, "Email không hợp lệ! Vui lòng nhập email đã đăng ký.", Toast.LENGTH_SHORT).show();
            return;
        }

        String flowSource = getIntent() != null ? getIntent().getStringExtra("FLOW_SOURCE") : "LOGIN";

        // Yêu cầu backend gửi mã OTP về email, thành công thì mới sang màn hình nhập mã
        btnSubmit.setEnabled(false);
        PasswordResetHelper.requestOtp(input, new PasswordResetHelper.Listener<AuthModels.MessageResponse>() {
            @Override
            public void onSuccess(AuthModels.MessageResponse value) {
                btnSubmit.setEnabled(true);
                Toast.makeText(ForgotPasswordActivity.this,
                        "Nếu email đã đăng ký, mã xác thực đã được gửi tới email đó!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPasswordActivity.this, Frame18EnterGmailOTP.class);
                intent.putExtra("EMAIL_TARGET", input);
                intent.putExtra("RESET_IDENTIFIER", input);
                intent.putExtra("FLOW_SOURCE", flowSource);
                startActivity(intent);
            }

            @Override
            public void onError(String message) {
                btnSubmit.setEnabled(true);
                Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}