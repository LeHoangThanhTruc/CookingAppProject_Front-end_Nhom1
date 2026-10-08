package com.example.cookingapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlaceholderActivity extends Activity {
    static final String EXTRA_FRAME_NAME = "frame_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        LinearLayout content = findViewById(R.id.placeholder_content);
        String name = getIntent().getStringExtra(EXTRA_FRAME_NAME);
        if (name == null) {
            name = "Frame chưa có tên";
        }

        TextView back = ScreenUi.text(this, "‹", 30, getColor(R.color.panda_green_dark), Typeface.BOLD);
        back.setOnClickListener(v -> finish());
        content.addView(back);

        content.addView(ScreenUi.text(this, name, 24, getColor(R.color.panda_green_dark), Typeface.BOLD));
        TextView note = ScreenUi.text(this, "Nhánh nền đã tạo placeholder. Nhánh riêng của frame này sẽ thay màn hình này bằng giao diện thật từ Figma.", 15, getColor(R.color.panda_green_mid), Typeface.NORMAL);
        note.setPadding(0, ScreenUi.dp(this, 12), 0, 0);
        content.addView(note);
    }
}
