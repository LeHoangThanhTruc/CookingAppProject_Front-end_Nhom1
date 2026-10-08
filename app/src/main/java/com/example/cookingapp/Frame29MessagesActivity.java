package com.example.cookingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame29MessagesActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_29_messages);
        addHeader("Tin nhắn", "Danh sách các tài khoản đang nhắn tin", "", "⚙");

        LinearLayout search = card();
        addSectionTitle(search, "Tìm kiếm cuộc trò chuyện", null);
        TextView box = inputBox("Tìm theo tên hoặc tài khoản", 58);
        ScreenUi.margin(box, 0, dp(8), 0, 0);
        search.addView(box);
        content.addView(search);

        LinearLayout list = card();
        addSectionTitle(list, "Danh sách trò chuyện", "4 cuộc trò chuyện");
        list.addView(text("Chưa đọc", 12, R.color.panda_green_light_text, Typeface.BOLD));
        list.addView(chat("H", "Huy Hoàng", "Bạn đã gửi file đúng chưa?", "1", "10:18", true));
        list.addView(chat("A", "Anh An", "Cần gửi ảnh cho bạn trước 18h nhé", "3", "09:42", true));
        list.addView(text("Đã đọc", 12, R.color.panda_green_light_text, Typeface.BOLD));
        list.addView(chat("L", "Linh Linh", "Tối nay ăn gì vậy?", "", "13:05", false));
        list.addView(chat("M", "Minh Minh", "Đã gửi cho bạn rồi", "", "Hôm qua", false));
        content.addView(list);
    }

    private LinearLayout chat(String letter, String name, String message, String badge, String time, boolean unread) {
        LinearLayout row = ScreenUi.row(this);
        row.addView(ScreenUi.avatar(this, letter, 44));
        LinearLayout copy = ScreenUi.vertical(this);
        LinearLayout.LayoutParams copyParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        copyParams.setMargins(dp(12), 0, dp(8), 0);
        copy.setLayoutParams(copyParams);
        copy.addView(text(name, 14, R.color.panda_green_dark, unread ? Typeface.BOLD : Typeface.NORMAL));
        copy.addView(text(message, 13, unread ? R.color.panda_green_dark : R.color.panda_gray, unread ? Typeface.BOLD : Typeface.NORMAL));
        row.addView(copy);
        LinearLayout meta = ScreenUi.vertical(this);
        if (!badge.isEmpty()) {
            meta.addView(ScreenUi.chip(this, badge, android.R.color.white, c(R.color.panda_green)));
        }
        meta.addView(text(time, 12, unread ? R.color.panda_green_dark : R.color.panda_gray, Typeface.BOLD));
        row.addView(meta);
        return row;
    }
}
