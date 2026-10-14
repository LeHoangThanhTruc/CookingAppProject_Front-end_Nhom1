package com.example.cookingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Frame24OtherProfileActivity extends BaseFrameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContent(R.layout.activity_frame_24_other_profile);

        addHeader("", "Trang cá nhân", "", null);
        profileCard("Thảo Vy", "");
        actionButtons();
        menuSection();
        postsSection("Thảo Vy");
    }

    private void profileCard(String name, String sub) {
        LinearLayout card = card();
        TextView cover = text("Ảnh bìa mặc định", 18, R.color.panda_green, Typeface.BOLD);
        cover.setGravity(Gravity.CENTER);
        cover.setBackground(ScreenUi.rounded(c(R.color.panda_mint), dp(20), c(R.color.panda_mint), 0));
        card.addView(cover, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(96)));

        LinearLayout top = ScreenUi.horizontal(this);
        top.setPadding(0, dp(12), 0, 0);
        top.addView(ScreenUi.profileAvatar(this, 56));
        LinearLayout copy = ScreenUi.vertical(this);
        LinearLayout.LayoutParams copyParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        copyParams.setMargins(dp(12), 0, 0, 0);
        copy.setLayoutParams(copyParams);
        copy.addView(text(name, 20, R.color.panda_green_dark, Typeface.BOLD));
        if (!sub.isEmpty()) {
            copy.addView(text(sub, 13, R.color.panda_green_mid, Typeface.NORMAL));
        }
        top.addView(copy);
        card.addView(top);
        content.addView(card);
    }

    private void actionButtons() {
        LinearLayout row = ScreenUi.horizontal(this);
        TextView add = primaryButton("Kết Bạn");
        add.setBackground(ScreenUi.rounded(c(R.color.panda_blue), dp(18), c(R.color.panda_blue), 0));
        TextView follow = primaryButton("Theo Dõi Người Này");
        LinearLayout.LayoutParams half = new LinearLayout.LayoutParams(0, dp(48), 1f);
        half.setMargins(0, dp(6), dp(6), dp(6));
        row.addView(add, half);
        LinearLayout.LayoutParams half2 = new LinearLayout.LayoutParams(0, dp(48), 1f);
        half2.setMargins(dp(6), dp(6), 0, dp(6));
        row.addView(follow, half2);
        content.addView(row);
    }

    private void menuSection() {
        LinearLayout card = card();
        addSectionTitle(card, "Mục nhanh", null);
        card.addView(menuItem("📄", "Giới thiệu", Frame26BioActivity.class));
        card.addView(menuItem("👥", "Bạn bè", Frame27FriendsActivity.class));
        card.addView(menuItem("👀", "Đang theo dõi", Frame28FollowingActivity.class));
        card.addView(menuItem("📨", "Nhắn tin với người này", Frame30ChatDetailActivity.class));
        content.addView(card);
    }

    private LinearLayout menuItem(String icon, String title, Class<?> target) {
        LinearLayout row = menuRow(icon, title);
        row.setOnClickListener(v -> startActivity(new Intent(this, target)));
        return row;
    }

    private void postsSection(String owner) {
        LinearLayout card = card();
        addSectionTitle(card, "Bài viết đã đăng", null);
        card.addView(post(owner, "2 giờ trước", "Bài viết mẫu: Bữa tối tự nấu hôm nay — một chút yêu thương từ căn bếp nhỏ. 🥗", "12", "4"));
        card.addView(post(owner, "Hôm qua", "Bài viết mẫu: Cuối tuần thử một công thức mới. Bạn thích nấu món gì nhất?", "8", "2"));
        content.addView(card);
    }

    private LinearLayout post(String owner, String time, String body, String likes, String comments) {
        LinearLayout post = ScreenUi.vertical(this);
        post.setPadding(dp(12), dp(10), dp(12), dp(10));
        post.setBackground(ScreenUi.rounded(c(R.color.panda_card_soft), dp(18), c(R.color.panda_border), 1));
        ScreenUi.margin(post, 0, dp(10), 0, 0);

        LinearLayout header = ScreenUi.horizontal(this);
        header.addView(ScreenUi.avatar(this, "A", 32));
        LinearLayout meta = ScreenUi.vertical(this);
        LinearLayout.LayoutParams metaParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        metaParams.setMargins(dp(10), 0, 0, 0);
        meta.setLayoutParams(metaParams);
        meta.addView(text(owner, 14, R.color.panda_green_dark, Typeface.BOLD));
        meta.addView(text(time, 12, R.color.panda_gray, Typeface.NORMAL));
        header.addView(meta);
        post.addView(header);
        post.addView(text(body, 14, R.color.panda_green_dark, Typeface.NORMAL));
        post.addView(postActions(likes, comments));
        return post;
    }

    private LinearLayout postActions(String likes, String comments) {
        LinearLayout row = ScreenUi.horizontal(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.addView(text(likes, 13, R.color.panda_green_mid, Typeface.BOLD));
        TextView likeLabel = text("thích", 13, R.color.panda_gray, Typeface.NORMAL);
        LinearLayout.LayoutParams likeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        likeParams.setMargins(dp(6), 0, dp(16), 0);
        likeLabel.setLayoutParams(likeParams);
        row.addView(likeLabel);
        row.addView(text(comments, 13, R.color.panda_green_mid, Typeface.BOLD));
        TextView commentLabel = text("bình luận", 13, R.color.panda_gray, Typeface.NORMAL);
        LinearLayout.LayoutParams commentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        commentParams.setMargins(dp(6), 0, 0, 0);
        commentLabel.setLayoutParams(commentParams);
        row.addView(commentLabel);
        return row;
    }
}
