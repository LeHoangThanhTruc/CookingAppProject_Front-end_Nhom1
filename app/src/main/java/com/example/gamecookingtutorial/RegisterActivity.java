package com.example.gamecookingtutorial;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText edtFullname, edtEmail, edtPassword, edtConfirmPassword;
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
        edtEmail = findViewById(R.id.edtEmail);
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

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!chkTerms.isChecked()) {
            Toast.makeText(this, "Bạn cần đồng ý với điều khoản để tiếp tục!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Xử lý đăng ký thành công
        Toast.makeText(this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
        // Chuyển sang LoginActivity
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

        // Đóng RegisterActivity để người dùng không bấm back quay lại màn đăng ký được nữa
        finish();
    }
}