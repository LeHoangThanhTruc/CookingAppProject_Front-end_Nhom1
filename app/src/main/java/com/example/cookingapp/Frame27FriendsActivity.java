package com.example.cookingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame27FriendsActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_27_friends);
        addHeader("Bạn bè", "Danh sách bạn bè đã kết bạn", "Xem và nhắn tin với bạn bè đã kết bạn", "⚙");
        searchCard();
        listCard();
    }

    private void searchCard() {
        LinearLayout card = card();
        addSectionTitle(card, "Tìm kiếm bạn bè", null);
        TextView search = inputBox("Tìm theo tên hoặc tài khoản", 58);
        ScreenUi.margin(search, 0, dp(8), 0, 0);
        card.addView(search);
        content.addView(card);
    }

    private void listCard() {
        LinearLayout card = card();
        addSectionTitle(card, "Danh sách bạn bè", "4 bạn bè");
        card.addView(friend("A", "Anh An", "@anh.an"));
        card.addView(friend("H", "Huy Hoàng", "@huy.hoang"));
        card.addView(friend("L", "Linh Linh", "@linh.linh"));
        card.addView(friend("M", "Minh Minh", "@minh.minh"));
        content.addView(card);
    }

    private LinearLayout friend(String letter, String name, String handle) {
        LinearLayout row = ScreenUi.row(this);
        row.addView(ScreenUi.avatar(this, letter, 44));
        LinearLayout copy = ScreenUi.vertical(this);
        LinearLayout.LayoutParams copyParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        copyParams.setMargins(dp(12), 0, dp(8), 0);
        copy.setLayoutParams(copyParams);
        copy.addView(text(name, 14, R.color.panda_green_dark, Typeface.BOLD));
        copy.addView(text(handle, 12, R.color.panda_gray, Typeface.NORMAL));
        row.addView(copy);
        TextView chat = ScreenUi.avatar(this, "📨", 40);
        chat.setTextSize(15);
        row.addView(chat);
        return row;
    }
}
