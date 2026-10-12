package com.example.cookingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame28FollowingActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_28_following);
        addHeader("Theo dõi", "Danh sách người bạn đang theo dõi", "Xem và theo dõi các tài khoản bạn đã theo dõi", "⚙");
        LinearLayout search = card();
        addSectionTitle(search, "Tìm kiếm tài khoản", null);
        TextView box = inputBox("Tìm theo tên hoặc tài khoản", 58);
        ScreenUi.margin(box, 0, dp(8), 0, 0);
        search.addView(box);
        content.addView(search);

        LinearLayout list = card();
        addSectionTitle(list, "Người bạn đang theo dõi", "4 tài khoản");
        list.addView(person("A", "Anh An", "@anh.an"));
        list.addView(person("H", "Huy Hoàng", "@huy.hoang"));
        list.addView(person("L", "Linh Linh", "@linh.linh"));
        list.addView(person("M", "Minh Minh", "@minh.minh"));
        content.addView(list);
    }

    private LinearLayout person(String letter, String name, String handle) {
        LinearLayout row = ScreenUi.row(this);
        row.addView(ScreenUi.avatar(this, letter, 44));
        LinearLayout copy = ScreenUi.vertical(this);
        LinearLayout.LayoutParams copyParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        copyParams.setMargins(dp(12), 0, dp(8), 0);
        copy.setLayoutParams(copyParams);
        copy.addView(text(name, 14, R.color.panda_green_dark, Typeface.BOLD));
        copy.addView(text(handle, 12, R.color.panda_gray, Typeface.NORMAL));
        row.addView(copy);
        row.addView(ScreenUi.chip(this, "Đang theo dõi", c(R.color.panda_green), c(R.color.panda_mint)));
        return row;
    }
}
