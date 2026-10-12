package com.example.cookingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame32PrivacyActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_32_privacy);
        addHeader("", "Chọn quyền xem", "", "🐼");

        content.addView(text("Ai có thể xem bài đăng này?", 15, R.color.panda_green_dark, Typeface.BOLD));
        content.addView(text("Chọn chế độ đăng bài phù hợp để kiểm soát quyền xem công thức của bạn.", 13, R.color.panda_gray, Typeface.NORMAL));

        LinearLayout card = card();
        card.addView(option("Chỉ Mình Tôi", "Chỉ bạn mới có thể xem bài đăng này.", true));
        card.addView(option("Chỉ Bạn Bè Được Xem", "Bạn bè của bạn có thể xem bài đăng này.", false));
        card.addView(option("Public", "Mọi người đều có thể xem bài đăng này.", false));
        content.addView(card);

        TextView hint = text("ⓘ  Mặc định là “Chỉ Mình Tôi” để bảo vệ riêng tư cho bạn.", 12, R.color.panda_green_dark, Typeface.NORMAL);
        hint.setPadding(dp(12), dp(10), dp(12), dp(10));
        hint.setBackground(ScreenUi.rounded(c(R.color.panda_mint_light), dp(12), c(R.color.panda_mint_light), 0));
        ScreenUi.margin(hint, 0, dp(10), 0, dp(320));
        content.addView(hint);

        TextView post = primaryButton("🐼 Đăng bài");
        content.addView(post);
    }

    private LinearLayout option(String title, String desc, boolean selected) {
        LinearLayout row = ScreenUi.horizontal(this);
        row.setPadding(dp(12), dp(12), dp(12), dp(12));
        row.setBackground(ScreenUi.rounded(selected ? c(R.color.panda_mint_light) : c(R.color.panda_card), dp(12), c(R.color.panda_mint_light), selected ? 0 : 2));
        ScreenUi.margin(row, 0, dp(4), 0, dp(6));
        LinearLayout copy = ScreenUi.vertical(this);
        copy.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        copy.addView(text(title, 14, R.color.panda_green_dark, Typeface.BOLD));
        copy.addView(text(desc, 12, R.color.panda_gray, Typeface.NORMAL));
        row.addView(copy);
        TextView check = text(selected ? "✓" : "", 14, selected ? android.R.color.white : R.color.panda_green, Typeface.BOLD);
        check.setGravity(Gravity.CENTER);
        check.setBackground(ScreenUi.rounded(selected ? c(R.color.panda_green) : c(R.color.panda_card), dp(10), c(R.color.panda_mint_light), 2));
        row.addView(check, new LinearLayout.LayoutParams(dp(20), dp(20)));
        return row;
    }
}
