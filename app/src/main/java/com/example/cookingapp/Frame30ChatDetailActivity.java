package com.example.cookingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame30ChatDetailActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_30_chat_detail);
        addHeader("Trò chuyện", "Huy Hoàng", "", "H");

        LinearLayout chat = card();
        addSectionTitle(chat, "Cuộc trò chuyện", null);
        chat.addView(received("Huy Hoàng", "Bạn đã thử công thức gà rán mật ong mới chưa?", "09:42"));
        chat.addView(sent("Bạn", "Chưa, mình đang tìm công thức cho bữa tối. Bạn có thể chia sẻ lại không?"));
        chat.addView(received("Huy Hoàng", "Mình dùng 2 thìa mật ong, 1 thìa nước cốt chanh và 1 thìa dầu hào. Ướp khoảng 20 phút trước khi chiên.", "09:45"));
        chat.addView(sent("Bạn", "Cảm ơn bạn! Mình sẽ thử ngay tối nay và cho bạn biết kết quả."));
        chat.addView(received("", "Nhớ chiên lửa vừa nhé!", "09:46"));
        chat.addView(sent("", "Ok, mình nhớ rồi! 😊"));
        content.addView(chat);

        LinearLayout composer = card();
        addSectionTitle(composer, "Soạn tin nhắn", null);
        LinearLayout row = ScreenUi.horizontal(this);
        row.addView(ScreenUi.avatar(this, "📎", 40));
        TextView input = inputBox("Nhập tin nhắn...", 48);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(0, dp(48), 1f);
        inputParams.setMargins(dp(8), 0, dp(8), 0);
        row.addView(input, inputParams);
        row.addView(ScreenUi.avatar(this, "➤", 40));
        composer.addView(row);
        content.addView(composer);
    }

    private LinearLayout received(String author, String message, String time) {
        LinearLayout row = ScreenUi.horizontal(this);
        row.setGravity(Gravity.BOTTOM);
        row.addView(ScreenUi.avatar(this, "H", 32));
        LinearLayout bubble = bubble(R.color.panda_input, false);
        if (!author.isEmpty()) {
            bubble.addView(text(author, 14, R.color.panda_green_dark, Typeface.BOLD));
        }
        bubble.addView(text(message, 14, R.color.panda_green_dark, Typeface.NORMAL));
        bubble.addView(text(time, 12, R.color.panda_gray, Typeface.BOLD));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(dp(8), dp(10), dp(52), 0);
        row.addView(bubble, params);
        return row;
    }

    private LinearLayout sent(String author, String message) {
        LinearLayout row = ScreenUi.horizontal(this);
        row.setGravity(Gravity.RIGHT);
        LinearLayout bubble = bubble(R.color.panda_mint, true);
        if (!author.isEmpty()) {
            bubble.addView(text(author, 14, R.color.panda_green_dark, Typeface.BOLD));
        }
        bubble.addView(text(message, 14, R.color.panda_green_dark, Typeface.NORMAL));
        bubble.addView(text("• Đã xem", 12, R.color.panda_green, Typeface.BOLD));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(dp(96), dp(10), 0, 0);
        row.addView(bubble, params);
        return row;
    }

    private LinearLayout bubble(int color, boolean sent) {
        LinearLayout bubble = ScreenUi.vertical(this);
        bubble.setPadding(dp(12), dp(10), dp(12), dp(10));
        bubble.setBackground(ScreenUi.rounded(c(color), dp(18), c(color), 0));
        return bubble;
    }
}
