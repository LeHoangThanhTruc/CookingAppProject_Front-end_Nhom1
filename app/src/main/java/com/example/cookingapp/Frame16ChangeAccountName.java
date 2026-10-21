package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame16ChangeAccountName extends Activity {

    private ImageButton btnBack;
    private EditText etNewAccountName;
    private LinearLayout btnSaveName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account_name);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etNewAccountName = findViewById(R.id.etNewAccountName);
        btnSaveName = findViewById(R.id.btnSaveName);
    }

    private void setupEvents() {
        // 1. Nút Back -> Quay lại activity_profile_details.xml (Frame15ProfileDetails)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(Frame16ChangeAccountName.this, Frame15ProfileDetails.class);
                startActivity(intent);
                finish();
            });
        }

        // 2. Nút Lưu thay đổi -> Quay lại activity_profile_details.xml (Frame15ProfileDetails)
        if (btnSaveName != null) {
            btnSaveName.setOnClickListener(v -> {
                String newName = etNewAccountName != null ? etNewAccountName.getText().toString().trim() : "";

                if (newName.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập tên tài khoản mới!", Toast.LENGTH_SHORT).show();
                } else if (newName.length() < 3) {
                    Toast.makeText(this, "Tên tài khoản phải có ít nhất 3 ký tự!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Đổi tên tài khoản thành công: " + newName, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Frame16ChangeAccountName.this, Frame15ProfileDetails.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}
