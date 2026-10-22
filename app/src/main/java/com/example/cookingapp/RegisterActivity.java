package com.example.cookingapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingapp.R;

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


        // Xử lý đăng ký thành công
        Toast.makeText(this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
        // Chuyển sang LoginActivity
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

        // Đóng RegisterActivity để người dùng không bấm back quay lại màn đăng ký được nữa
        finish();
    }
}
