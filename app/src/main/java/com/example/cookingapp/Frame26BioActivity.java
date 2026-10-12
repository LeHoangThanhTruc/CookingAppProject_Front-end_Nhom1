package com.example.cookingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame26BioActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_26_bio);
        addHeader("Hồ sơ", "Giới thiệu bản thân", "Cập nhật thông tin cá nhân để mọi người hiểu rõ hơn về bạn", "⚙");

        LinearLayout card = card();
        addSectionTitle(card, "Thông tin giới thiệu", null);
        field(card, "Họ Và Tên", "Nhập họ và tên", 58);
        field(card, "SĐT", "Nhập số điện thoại", 58);
        field(card, "Gmail", "Nhập địa chỉ email", 58);
        field(card, "Giới thiệu chung", "Viết một vài dòng giới thiệu về bản thân, sở thích hoặc phong cách nấu ăn của bạn", 333);
        TextView update = primaryButton("Cập nhật");
        ScreenUi.margin(update, 0, dp(12), 0, 0);
        card.addView(update);
        content.addView(card);
    }

    private void field(LinearLayout parent, String label, String hint, int height) {
        TextView labelView = text(label, 13, R.color.panda_green_dark, Typeface.BOLD);
        ScreenUi.margin(labelView, 0, dp(12), 0, dp(8));
        parent.addView(labelView);
        parent.addView(inputBox(hint, height));
    }
}
