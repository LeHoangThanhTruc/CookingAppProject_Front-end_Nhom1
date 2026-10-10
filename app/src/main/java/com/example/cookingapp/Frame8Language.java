package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame8Language extends Activity {
    private ImageButton btnBack;
    private LinearLayout btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        btnBack = findViewById(R.id.btnBack);
        btnApply = findViewById(R.id.btnApply);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnApply != null) btnApply.setOnClickListener(v -> {
            Toast.makeText(this, "Đã áp dụng ngôn ngữ", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}