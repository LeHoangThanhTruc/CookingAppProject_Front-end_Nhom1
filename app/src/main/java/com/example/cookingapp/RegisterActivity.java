package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ImageView btnBack;
    private EditText edtFullname, edtUsername, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
    private CheckBox chkTerms;
    private RelativeLayout btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ View
        initViews();
        // Nút quay lại
        btnBack.setOnClickListener(v -> finish());
        // Nút Đăng ký
        btnRegister.setOnClickListener(v -> handleRegister());
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        edtFullname = findViewById(R.id.edtFullname);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        chkTerms = findViewById(R.id.chkTerms);
        btnRegister = findViewById(R.id.btnRegister);

        // Nút con mắt xem/ẩn mật khẩu và xác nhận mật khẩu
        ImageView iconTogglePassword = findViewById(R.id.iconTogglePassword);
        ImageView iconToggleConfirmPassword = findViewById(R.id.iconToggleConfirmPassword);
        PasswordToggleHelper.attach(edtPassword, iconTogglePassword);
        PasswordToggleHelper.attach(edtConfirmPassword, iconToggleConfirmPassword);
    }

    private void handleRegister() {
        String username = edtUsername.getText().toString().trim();
        String fullname = edtFullname.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        if (username.isEmpty()) {
            edtUsername.setError("Vui lòng nhập tên tài khoản");
            edtUsername.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Vui lòng nhập email");
            edtEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            edtPhone.requestFocus();
            return;
        }
        if (fullname.isEmpty()) {
            edtFullname.setError("Vui lòng nhập họ và tên");
            edtFullname.requestFocus();
            return;
        }
        if (password.length() < 8 || password.length() > 128) {
            edtPassword.setError("Mật khẩu phải từ 8 đến 128 ký tự");
            edtPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            edtConfirmPassword.requestFocus();
            return;
        }
        if (chkTerms != null && !chkTerms.isChecked()) {
            Toast.makeText(this, "Bạn cần đồng ý với điều khoản để tiếp tục!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gửi đầy đủ thông tin lên backend
        ApiService.RegisterRequest request =
                new ApiService.RegisterRequest(
                        username,
                        fullname,
                        email,
                        phone,
                        password
                );

        btnRegister.setEnabled(false);
        ApiService service = ApiClient.getClient().create(ApiService.class);
        service.registerUser(request)
                .enqueue(new Callback<ApiService.ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiService.ApiResponse> call, Response<ApiService.ApiResponse> response) {
                        btnRegister.setEnabled(true);
                        if (!response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, ApiErrorUtils.message(response, "Đăng ký thất bại"), Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công, hãy đăng nhập", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ApiService.ApiResponse> call, Throwable error) {
                        btnRegister.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, "Không kết nối được máy chủ", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
