package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Frame10Myactivity extends Activity {
    private ImageButton btnBack;
    private LinearLayout btnReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Myactivity);

        btnBack = findViewById(R.id.btnBack);
        btnReviews = findViewById(R.id.btnReviews);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnReviews != null) btnReviews.setOnClickListener(v -> startActivity(new Intent(this, Frame11Dadanhgia.class)));
    }
}