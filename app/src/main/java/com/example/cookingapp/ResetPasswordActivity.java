package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        EditText newPassword = findViewById(R.id.edtNewPassword);
        EditText confirmPassword = findViewById(R.id.edtConfirmPassword);

        // Nút con mắt xem/ẩn mật khẩu mới và xác nhận mật khẩu
        ImageView iconEyeOpen1 = findViewById(R.id.iconEyeOpen1);
        ImageView iconEyeOpen2 = findViewById(R.id.iconEyeOpen2);
        PasswordToggleHelper.attach(newPassword, iconEyeOpen1);
        PasswordToggleHelper.attach(confirmPassword, iconEyeOpen2);

        if (btnSubmitReset != null) {
            btnSubmitReset.setOnClickListener(v -> {
                String password = newPassword.getText().toString();
                if (password.length() < 8) {
                    newPassword.setError("Mật khẩu cần ít nhất 8 ký tự");
                    return;
                }
                if (!password.equals(confirmPassword.getText().toString())) {
                    confirmPassword.setError("Mật khẩu không khớp");
                    return;
                }
                String resetToken = getIntent().getStringExtra("RESET_TOKEN");
                if (resetToken == null) {
                    Toast.makeText(this, "Phiên đặt lại mật khẩu không hợp lệ", Toast.LENGTH_LONG).show();
                    return;
                }
                btnSubmitReset.setEnabled(false);
                ApiClient.getClient().create(ApiService.class)
                        .resetPassword(new ApiService.ResetPasswordRequest(resetToken, password))
                        .enqueue(new Callback<ApiService.ApiResponse>() {
                            @Override
                            public void onResponse(Call<ApiService.ApiResponse> call, Response<ApiService.ApiResponse> response) {
                                btnSubmitReset.setEnabled(true);
                                if (!response.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, ApiErrorUtils.message(response, "Đặt lại mật khẩu thất bại"), Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<ApiService.ApiResponse> call, Throwable error) {
                                btnSubmitReset.setEnabled(true);
                                Toast.makeText(ResetPasswordActivity.this, "Không kết nối được máy chủ", Toast.LENGTH_LONG).show();
                            }
                        });
            });
        }
    }
}
