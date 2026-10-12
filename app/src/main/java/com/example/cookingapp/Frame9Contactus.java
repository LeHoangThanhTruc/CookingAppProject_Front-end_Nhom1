package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame9Contactus extends Activity {

    // Khai báo các thành phần tương tác trên giao diện
    private ImageButton btnBack;
    private ImageButton btnSupportHeader;

    private LinearLayout btnEmailChannel;
    private LinearLayout btnHotlineChannel;
    private LinearLayout btnAddressChannel;

    private EditText etSubject;
    private EditText etUserEmail;
    private EditText etMessage;

    private LinearLayout btnSendContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        // Ánh xạ các View từ file XML
        initViews();

        // Đăng ký các sự kiện Click
        setupEvents();
    }

    /**
     * Ánh xạ View thông qua findViewById
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnSupportHeader = findViewById(R.id.btnSupportHeader);

        btnEmailChannel = findViewById(R.id.btnEmailChannel);
        btnHotlineChannel = findViewById(R.id.btnHotlineChannel);
        btnAddressChannel = findViewById(R.id.btnAddressChannel);

        etSubject = findViewById(R.id.etSubject);
        etUserEmail = findViewById(R.id.etUserEmail);
        etMessage = findViewById(R.id.etMessage);

        btnSendContact = findViewById(R.id.btnSendContact);
    }

    /**
     * Đăng ký sự kiện Click cho từng mục
     */
    private void setupEvents() {
        // 1. Nút Back - chuyển về màn hình Cài đặt (Frame7SettingActivity)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(this, Frame7SettingActivity.class);
                startActivity(intent);
                finish();
            });
        }

        // 2. Nút Hỗ trợ (Top Right)
        if (btnSupportHeader != null) {
            btnSupportHeader.setOnClickListener(v ->
                    Toast.makeText(this, "Trung tâm trợ giúp Panda", Toast.LENGTH_SHORT).show()
            );
        }

        // 3. Kênh Email
        if (btnEmailChannel != null) {
            btnEmailChannel.setOnClickListener(v ->
                    Toast.makeText(this, "Email: support@panda-cooking.app", Toast.LENGTH_SHORT).show()
            );
        }

        // 4. Kênh Hotline
        if (btnHotlineChannel != null) {
            btnHotlineChannel.setOnClickListener(v ->
                    Toast.makeText(this, "Hotline: 1900 1234", Toast.LENGTH_SHORT).show()
            );
        }

        // 5. Kênh Địa chỉ
        if (btnAddressChannel != null) {
            btnAddressChannel.setOnClickListener(v ->
                    Toast.makeText(this, "Địa chỉ: UIT, TP. Hồ Chí Minh", Toast.LENGTH_SHORT).show()
            );
        }

        // 6. Nút Gửi liên hệ
        if (btnSendContact != null) {
            btnSendContact.setOnClickListener(v -> {
                String subject = etSubject != null ? etSubject.getText().toString().trim() : "";
                String email = etUserEmail != null ? etUserEmail.getText().toString().trim() : "";
                String message = etMessage != null ? etMessage.getText().toString().trim() : "";

                if (subject.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin gửi liên hệ!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Gửi liên hệ thành công! Panda sẽ phản hồi sớm nhất.", Toast.LENGTH_LONG).show();
                    
                    // Chuyển về màn hình Cài đặt sau khi gửi thành công
                    Intent intent = new Intent(this, Frame7SettingActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}