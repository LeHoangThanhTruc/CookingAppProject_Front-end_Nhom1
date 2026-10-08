package com.example.cookingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame33NotificationsActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_33_notifications);
        addHeader("", "Thông Báo", "", null);

        LinearLayout card = card();
        card.setMinimumHeight(dp(795));
        addSectionTitle(card, "Danh sách thông báo", "4 thông báo");
        card.addView(text("Chưa đọc", 12, R.color.panda_green_light_text, Typeface.BOLD));
        card.addView(notification("H", "Huy Hoàng", "Đã gửi cho bạn lời mời kết bạn", "10:18", true));
        card.addView(notification("A", "Anh An", "Đã gửi cho bạn lời mời kết bạn", "09:42", true));
        card.addView(text("Đã đọc", 12, R.color.panda_green_light_text, Typeface.BOLD));
        card.addView(notification("L", "Linh Linh", "Đã chia sẻ bài viết của bạn", "13:05", false));
        card.addView(notification("M", "Minh Minh", "Đã thích công thức của bạn", "Hôm qua", false));
        content.addView(card);
    }

    private LinearLayout notification(String letter, String name, String message, String time, boolean unread) {
        LinearLayout row = ScreenUi.row(this);
        row.addView(ScreenUi.avatar(this, letter, 44));
        LinearLayout copy = ScreenUi.vertical(this);
        LinearLayout.LayoutParams copyParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        copyParams.setMargins(dp(12), 0, dp(8), 0);
        copy.setLayoutParams(copyParams);
        copy.addView(text(name, 14, R.color.panda_green_dark, unread ? Typeface.BOLD : Typeface.NORMAL));
        copy.addView(text(message, 13, unread ? R.color.panda_green_dark : R.color.panda_gray, unread ? Typeface.BOLD : Typeface.NORMAL));
        row.addView(copy);
        row.addView(text(time + "   🗑", 12, unread ? R.color.panda_green_dark : R.color.panda_gray, Typeface.BOLD));
        return row;
    }
}
