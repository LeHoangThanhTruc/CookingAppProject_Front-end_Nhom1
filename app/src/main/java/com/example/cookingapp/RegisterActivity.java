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

        // Nút con mắt để xem/ẩn mật khẩu
        ImageView iconTogglePassword = findViewById(R.id.iconTogglePassword);
        ImageView iconToggleConfirmPassword = findViewById(R.id.iconToggleConfirmPassword);
        PasswordToggleHelper.attach(edtPassword, iconTogglePassword);
        PasswordToggleHelper.attach(edtConfirmPassword, iconToggleConfirmPassword);

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
    }

    private void handleRegister() {
        String fullname = edtFullname.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tên tài khoản (dùng để đăng nhập): 3-50 ký tự chữ không dấu, số, "_" hoặc "."
        String username = null;
        if (edtUsername != null) {
            username = edtUsername.getText().toString().trim();
            if (username.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên tài khoản!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!username.matches("^[A-Za-z0-9_.]{3,50}$")) {
                Toast.makeText(this,
                        "Tên tài khoản dài 3-50 ký tự, chỉ gồm chữ không dấu, số, dấu _ và dấu .",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (username.matches("^\\+?\\d{9,15}$")) {
                Toast.makeText(this, "Tên tài khoản không được chỉ gồm số!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Số điện thoại: bỏ khoảng trắng, dấu chấm, gạch ngang, ngoặc rồi kiểm tra 9-15 chữ số
        String phone = null;
        if (edtPhone != null) {
            phone = edtPhone.getText().toString().replaceAll("[\\s.\\-()]", "");
            if (phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!phone.matches("^\\+?\\d{9,15}$")) {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Backend yêu cầu mật khẩu từ 8 ký tự trở lên
        if (password.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!chkTerms.isChecked()) {
            Toast.makeText(this, "Bạn cần đồng ý với điều khoản để tiếp tục!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi API đăng ký (nếu không có ô tên tài khoản, backend tự tạo username từ email)
        btnRegister.setEnabled(false);
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.register(new AuthModels.RegisterBody(fullname, email, password, phone, username))
                .enqueue(new Callback<AuthModels.RegisterResponse>() {
                    @Override
                    public void onResponse(Call<AuthModels.RegisterResponse> call,
                                           Response<AuthModels.RegisterResponse> response) {
                        btnRegister.setEnabled(true);
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            // Chuyển sang LoginActivity
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                            // Đóng RegisterActivity để người dùng không bấm back quay lại màn đăng ký được nữa
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this,
                                    AuthModels.errorMessage(response), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthModels.RegisterResponse> call, Throwable t) {
                        btnRegister.setEnabled(true);
                        Toast.makeText(RegisterActivity.this,
                                "Không kết nối được server. Kiểm tra backend đã chạy chưa.",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}