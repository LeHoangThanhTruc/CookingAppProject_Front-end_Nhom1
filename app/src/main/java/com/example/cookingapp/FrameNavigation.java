package com.example.cookingapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

final class FrameNavigation {
    private FrameNavigation() {
    }

    static void bindText(View root, String text, View.OnClickListener listener) {
        TextView target = findText(root, text);
        if (target == null) {
            return;
        }
        View clickable = target.getParent() instanceof View ? (View) target.getParent() : target;
        clickable.setClickable(true);
        clickable.setOnClickListener(listener);
    }

    private static TextView findText(View view, String text) {
        if (view instanceof TextView && text.equals(((TextView) view).getText().toString())) {
            return (TextView) view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                TextView found = findText(group.getChildAt(i), text);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
