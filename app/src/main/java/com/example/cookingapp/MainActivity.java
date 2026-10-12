package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout list = findViewById(R.id.frame_list);
        list.addView(title("Cooking Panda", 26, R.color.panda_green_dark, Typeface.BOLD));
        list.addView(title("Menu kiểm thử giao diện frame 24-33", 14, R.color.panda_green_mid, Typeface.NORMAL));

        for (FrameDefinition frame : FrameRoutes.FRAMES) {
            TextView button = item(frame);
            button.setOnClickListener(v -> openFrame(frame));
            list.addView(button);
        }
    }

    private TextView title(String value, int sp, int color, int style) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(sp);
        view.setTextColor(getColor(color));
        view.setTypeface(Typeface.DEFAULT, style);
        view.setPadding(0, 0, 0, dp(8));
        return view;
    }

    private TextView item(FrameDefinition frame) {
        TextView view = new TextView(this);
        view.setText(frame.number + ". " + frame.title + "  ›");
        view.setTextSize(16);
        view.setTextColor(getColor(R.color.panda_green_dark));
        view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        view.setPadding(dp(18), dp(16), dp(18), dp(16));
        view.setBackground(ScreenUi.rounded(getColor(R.color.panda_card), dp(22), getColor(R.color.panda_border), 1));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(8), 0, dp(4));
        view.setLayoutParams(params);
        return view;
    }

    private void openFrame(FrameDefinition frame) {
        try {
            Class<?> clazz = Class.forName(getPackageName() + "." + frame.activityClass);
            Intent intent = new Intent(this, clazz);
            intent.putExtra(PlaceholderActivity.EXTRA_FRAME_NAME, frame.number + ". " + frame.title);
            startActivity(intent);
        } catch (ClassNotFoundException ignored) {
            Intent intent = new Intent(this, PlaceholderActivity.class);
            intent.putExtra(PlaceholderActivity.EXTRA_FRAME_NAME, frame.number + ". " + frame.title);
            startActivity(intent);
        }
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
