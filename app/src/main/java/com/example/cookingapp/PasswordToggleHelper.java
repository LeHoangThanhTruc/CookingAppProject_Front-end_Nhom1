package com.example.cookingapp;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Gắn nút con mắt vào ô mật khẩu: bấm để hiện nội dung đã nhập, bấm lần nữa để ẩn lại.
 * Dùng cho mọi màn hình có ô mật khẩu, chỉ cần gọi một dòng:
 * PasswordToggleHelper.attach(editText, iconImageView);
 */
public final class PasswordToggleHelper {

    private PasswordToggleHelper() {
    }

    public static void attach(EditText editText, ImageView toggleIcon) {
        if (editText == null || toggleIcon == null) {
            return;
        }

        toggleIcon.setClickable(true);
        toggleIcon.setFocusable(true);
        updateIcon(editText, toggleIcon);

        toggleIcon.setOnClickListener(v -> {
            int start = editText.getSelectionStart();
            int end = editText.getSelectionEnd();

            boolean hidden = editText.getTransformationMethod() instanceof PasswordTransformationMethod;
            editText.setTransformationMethod(hidden
                    ? HideReturnsTransformationMethod.getInstance()
                    : PasswordTransformationMethod.getInstance());

            // Giữ nguyên vị trí con trỏ sau khi đổi kiểu hiển thị
            int length = editText.getText().length();
            editText.setSelection(clamp(start, length), clamp(end, length));

            updateIcon(editText, toggleIcon);
        });
    }

    private static void updateIcon(EditText editText, ImageView toggleIcon) {
        boolean hidden = editText.getTransformationMethod() instanceof PasswordTransformationMethod;
        // Đang ẩn -> hiện mắt mở (bấm để xem); đang hiện -> hiện mắt gạch chéo (bấm để ẩn)
        toggleIcon.setImageResource(hidden ? R.drawable.ic_password_show : R.drawable.ic_password_hide);
        toggleIcon.setContentDescription(hidden ? "Hiện mật khẩu" : "Ẩn mật khẩu");
    }

    private static int clamp(int value, int max) {
        return Math.max(0, Math.min(value, max));
    }
}