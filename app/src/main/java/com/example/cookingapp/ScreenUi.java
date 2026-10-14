package com.example.cookingapp;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

final class ScreenUi {
    private ScreenUi() {
    }

    static int dp(Context context, int value) {
        return (int) (value * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    static GradientDrawable rounded(int color, int radius, int strokeColor, int strokeDp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        if (strokeDp > 0) {
            drawable.setStroke(strokeDp, strokeColor);
        }
        return drawable;
    }

    static TextView text(Context context, String value, int sp, int color, int style) {
        TextView view = new TextView(context);
        view.setText(value);
        view.setTextSize(sp);
        view.setTextColor(color);
        view.setTypeface(Typeface.DEFAULT, style);
        view.setIncludeFontPadding(true);
        return view;
    }

    static TextView chip(Context context, String value, int textColor, int bgColor) {
        TextView view = text(context, value, 12, textColor, Typeface.BOLD);
        view.setGravity(Gravity.CENTER);
        view.setPadding(dp(context, 10), dp(context, 6), dp(context, 10), dp(context, 6));
        view.setBackground(rounded(bgColor, dp(context, 18), bgColor, 0));
        return view;
    }

    static LinearLayout vertical(Context context) {
        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        return view;
    }

    static LinearLayout horizontal(Context context) {
        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setGravity(Gravity.CENTER_VERTICAL);
        return view;
    }

    static LinearLayout card(Context context) {
        LinearLayout card = vertical(context);
        card.setPadding(dp(context, 16), dp(context, 12), dp(context, 16), dp(context, 12));
        card.setBackground(rounded(
                context.getColor(R.color.panda_card),
                dp(context, 24),
                context.getColor(R.color.panda_border),
                1
        ));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, dp(context, 8), 0, dp(context, 6));
        card.setLayoutParams(params);
        return card;
    }

    static TextView avatar(Context context, String letter, int sizeDp) {
        TextView avatar = text(context, letter, sizeDp >= 44 ? 16 : 14, context.getColor(R.color.panda_green), Typeface.BOLD);
        avatar.setGravity(Gravity.CENTER);
        int size = dp(context, sizeDp);
        avatar.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        avatar.setBackground(rounded(context.getColor(R.color.panda_mint), size / 2, context.getColor(R.color.panda_mint), 0));
        return avatar;
    }

    static TextView circleButton(Context context, String value) {
        TextView button = text(context, value, 26, context.getColor(R.color.panda_green_dark), Typeface.BOLD);
        button.setGravity(Gravity.CENTER);
        int size = dp(context, 40);
        button.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        button.setBackground(rounded(context.getColor(R.color.panda_card), size / 2, context.getColor(R.color.panda_card), 0));
        return button;
    }

    static TextView iconButton(Context context, String value, int sizeDp, int bgColor, int textColor) {
        TextView button = text(context, value, sizeDp >= 40 ? 16 : 14, textColor, Typeface.BOLD);
        button.setGravity(Gravity.CENTER);
        int size = dp(context, sizeDp);
        button.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        button.setBackground(rounded(bgColor, size / 2, bgColor, 0));
        return button;
    }

    static FrameLayout profileAvatar(Context context, int sizeDp) {
        int size = dp(context, sizeDp);
        FrameLayout avatar = new FrameLayout(context);
        avatar.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        avatar.setBackground(rounded(context.getColor(R.color.panda_mint), size / 2, context.getColor(R.color.panda_mint), 0));

        ImageView icon = new ImageView(context);
        int iconSize = dp(context, Math.max(24, sizeDp - 26));
        FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(iconSize, iconSize, Gravity.CENTER);
        icon.setLayoutParams(iconParams);
        icon.setImageResource(R.drawable.ic_person);
        icon.setColorFilter(context.getColor(R.color.panda_green));
        avatar.addView(icon);
        return avatar;
    }

    static LinearLayout row(Context context) {
        LinearLayout row = horizontal(context);
        row.setPadding(0, dp(context, 8), 0, dp(context, 8));
        return row;
    }

    static void margin(View view, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (params == null) {
            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
        }
        params.setMargins(left, top, right, bottom);
        view.setLayoutParams(params);
    }
}
