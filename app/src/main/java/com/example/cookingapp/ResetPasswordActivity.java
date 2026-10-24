package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSubmitReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnBack = findViewById(R.id.btnBack);
        btnSubmitReset = findViewById(R.id.btnSubmitReset);

        // Nút Back tại activity_reset_password.xml -> finish()
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Bấm nút Xác nhận đặt lại (btnSubmitReset):
        // - Nếu từ Login -> Quay về activity_login.xml (LoginActivity)
        // - Nếu từ Chi tiết hồ sơ -> Quay về activity_profile_details.xml (Frame15ProfileDetails)
        if (btnSubmitReset != null) {
            btnSubmitReset.setOnClickListener(v -> {
                Toast.makeText(this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
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
            });
        }
    }
}
