package com.example.cookingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame31CreateRecipeActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_31_create_recipe);
        addHeader("", "Tạo công thức mới", "", "🐼");
        basicInfo();
        ingredients();
        steps();
        categories();
        TextView next = primaryButton("🐼 Tiếp Theo");
        ScreenUi.margin(next, 0, dp(12), 0, 0);
        content.addView(next);
    }

    private void basicInfo() {
        LinearLayout card = card();
        field(card, "Tên món ăn *", "VD: Phở Bò Hà Nội", 44);
        field(card, "Mô tả ngắn *", "Giới thiệu về món ăn của bạn...", 44);
        content.addView(card);
    }

    private void ingredients() {
        LinearLayout card = card();
        addSectionTitle(card, "Nguyên liệu", "Đầy đủ");
        card.addView(pair("Bánh phở", "500g"));
        card.addView(pair("Thịt bò", "300g"));
        card.addView(pair("Hành tây", "2 củ"));
        TextView add = text("+  Thêm nguyên liệu", 14, R.color.panda_green, Typeface.BOLD);
        add.setGravity(Gravity.CENTER);
        add.setPadding(0, dp(10), 0, dp(4));
        card.addView(add);
        content.addView(card);
    }

    private void steps() {
        LinearLayout row = ScreenUi.horizontal(this);
        row.addView(text("Số bước *", 14, R.color.panda_green_dark, Typeface.BOLD));
        TextView count = inputBox("2", 34);
        LinearLayout.LayoutParams countParams = new LinearLayout.LayoutParams(dp(80), dp(34));
        countParams.setMargins(dp(16), dp(12), 0, dp(12));
        row.addView(count, countParams);
        content.addView(row);
        content.addView(text("Các bước thực hiện", 15, R.color.panda_green_dark, Typeface.BOLD));
        content.addView(step("Bước 1", "Sơ chế nguyên liệu và chuẩn bị nước dùng..."));
        content.addView(step("Bước 2", "Tiến hành nấu nước dùng với xương bò..."));
        TextView add = text("⊕  Thêm bước thực hiện", 14, R.color.panda_green, Typeface.BOLD);
        add.setGravity(Gravity.CENTER);
        add.setPadding(dp(12), dp(8), dp(12), dp(8));
        add.setBackground(ScreenUi.rounded(c(R.color.panda_card), dp(12), c(R.color.panda_green), 2));
        content.addView(add);
    }

    private void categories() {
        content.addView(text("Danh mục *", 14, R.color.panda_green_dark, Typeface.BOLD));
        LinearLayout row = ScreenUi.horizontal(this);
        row.setPadding(0, dp(6), 0, 0);
        row.addView(ScreenUi.chip(this, "Món chính", android.R.color.white, c(R.color.panda_green)));
        row.addView(ScreenUi.chip(this, "Món nhanh", c(R.color.panda_green_dark), c(R.color.panda_card)));
        row.addView(ScreenUi.chip(this, "Món Việt", android.R.color.white, c(R.color.panda_green)));
        row.addView(ScreenUi.chip(this, "Món chay", c(R.color.panda_green_dark), c(R.color.panda_card)));
        content.addView(row);
    }

    private void field(LinearLayout parent, String label, String hint, int h) {
        TextView labelView = text(label, 14, R.color.panda_green_dark, Typeface.BOLD);
        ScreenUi.margin(labelView, 0, dp(4), 0, dp(4));
        parent.addView(labelView);
        TextView box = inputBox(hint, h);
        box.setBackground(ScreenUi.rounded(c(R.color.panda_card), dp(12), c(R.color.panda_mint_light), 2));
        parent.addView(box);
    }

    private LinearLayout pair(String left, String right) {
        LinearLayout row = ScreenUi.horizontal(this);
        TextView l = text(left, 14, R.color.panda_green_dark, Typeface.BOLD);
        l.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(l);
        row.addView(text(right + "   ×", 14, R.color.panda_green, Typeface.BOLD));
        return row;
    }

    private LinearLayout step(String title, String body) {
        LinearLayout card = card();
        card.addView(text(title + "                                      🗑", 14, R.color.panda_green, Typeface.BOLD));
        TextView box = inputBox(body, 44);
        box.setBackground(ScreenUi.rounded(c(R.color.panda_card), dp(8), c(R.color.panda_mint_light), 2));
        card.addView(box);
        LinearLayout media = ScreenUi.horizontal(this);
        TextView img = text("▧  + Ảnh", 12, R.color.panda_green_dark, Typeface.BOLD);
        TextView video = text("◷  + Video", 12, R.color.panda_green_dark, Typeface.BOLD);
        img.setGravity(Gravity.CENTER);
        video.setGravity(Gravity.CENTER);
        img.setBackground(ScreenUi.rounded(c(R.color.panda_mint_light), dp(8), c(R.color.panda_green), 1));
        video.setBackground(ScreenUi.rounded(c(R.color.panda_mint_light), dp(8), c(R.color.panda_green), 1));
        media.addView(img, new LinearLayout.LayoutParams(0, dp(30), 1f));
        media.addView(video, new LinearLayout.LayoutParams(0, dp(30), 1f));
        card.addView(media);
        return card;
    }
}
