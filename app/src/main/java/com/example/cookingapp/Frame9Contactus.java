package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame9Contactus extends Activity {
    private ImageButton btnBack;
    private LinearLayout btnSendContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        btnBack = findViewById(R.id.btnBack);
        btnSendContact = findViewById(R.id.btnSendContact);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnSendContact != null) btnSendContact.setOnClickListener(v -> {
            Toast.makeText(this, "Gửi liên hệ thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}