package com.example.cookingapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseFrameActivity extends Activity {
    protected LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void bindContent(int layoutId) {
        setContentView(layoutId);
        content = findViewById(R.id.screen_content);
    }

    protected int c(int colorId) {
        return getColor(colorId);
    }

    protected int dp(int value) {
        return ScreenUi.dp(this, value);
    }

    protected TextView text(String value, int sp, int colorId, int style) {
        return ScreenUi.text(this, value, sp, c(colorId), style);
    }

    protected LinearLayout card() {
        return ScreenUi.card(this);
    }

    protected void addHeader(String eyebrow, String title, String subtitle, String action) {
        LinearLayout header = ScreenUi.horizontal(this);
        header.setPadding(0, 0, 0, dp(10));

        TextView back = ScreenUi.circleButton(this, "‹");
        back.setOnClickListener(v -> finish());
        header.addView(back);

        LinearLayout copy = ScreenUi.vertical(this);
        LinearLayout.LayoutParams copyParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        copyParams.setMargins(dp(12), 0, dp(12), 0);
        copy.setLayoutParams(copyParams);
        if (eyebrow != null && !eyebrow.isEmpty()) {
            copy.addView(text(eyebrow, 14, R.color.panda_green_light_text, Typeface.BOLD));
        }
        copy.addView(text(title, 24, R.color.panda_green_dark, Typeface.BOLD));
        if (subtitle != null && !subtitle.isEmpty()) {
            copy.addView(text(subtitle, 13, R.color.panda_green_mid, Typeface.NORMAL));
        }
        header.addView(copy);

        if (action != null && !action.isEmpty()) {
            TextView actionButton = ScreenUi.circleButton(this, action);
            actionButton.setTextSize(17);
            header.addView(actionButton);
        }
        content.addView(header);
    }

    protected TextView primaryButton(String label) {
        TextView button = text(label, 15, android.R.color.white, Typeface.BOLD);
        button.setGravity(Gravity.CENTER);
        button.setPadding(dp(16), dp(14), dp(16), dp(14));
        button.setBackground(ScreenUi.rounded(c(R.color.panda_green), dp(18), c(R.color.panda_green), 0));
        return button;
    }

    protected TextView inputBox(String value, int heightDp) {
        TextView view = text(value, 14, R.color.panda_gray, Typeface.NORMAL);
        view.setGravity(Gravity.CENTER_VERTICAL);
        view.setPadding(dp(16), 0, dp(16), 0);
        view.setBackground(ScreenUi.rounded(c(R.color.panda_input), dp(18), c(R.color.panda_input), 0));
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(heightDp)));
        return view;
    }

    protected void addSectionTitle(LinearLayout parent, String left, String right) {
        LinearLayout row = ScreenUi.horizontal(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        TextView title = text(left, 16, R.color.panda_green_dark, Typeface.BOLD);
        title.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(title);
        if (right != null) {
            row.addView(text(right, 13, R.color.panda_green_mid, Typeface.BOLD));
        }
        parent.addView(row);
    }

    protected LinearLayout menuRow(String icon, String title) {
        LinearLayout row = ScreenUi.row(this);
        TextView badge = ScreenUi.avatar(this, icon, 38);
        badge.setTextSize(16);
        row.addView(badge);
        TextView label = text(title, 14, R.color.panda_green_dark, Typeface.BOLD);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        labelParams.setMargins(dp(12), 0, dp(8), 0);
        label.setLayoutParams(labelParams);
        row.addView(label);
        row.addView(text("›", 20, R.color.panda_gray, Typeface.BOLD));
        return row;
    }
}
