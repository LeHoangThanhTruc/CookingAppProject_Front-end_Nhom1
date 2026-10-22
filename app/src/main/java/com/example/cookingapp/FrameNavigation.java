package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

final class FrameNavigation {
    private FrameNavigation() {
    }

    static void bind(Activity activity, int viewId, View.OnClickListener listener) {
        View target = activity.findViewById(viewId);
        if (target != null) {
            bind(target, listener);
        }
    }

    static void bindOpen(Activity activity, int viewId, Class<? extends Activity> destination) {
        bind(activity, viewId, view -> activity.startActivity(new Intent(activity, destination)));
    }

    static void bindBack(Activity activity, int viewId) {
        bind(activity, viewId, view -> activity.finish());
    }

    static void bindToast(Activity activity, int viewId, String message) {
        bind(activity, viewId, view -> Toast.makeText(activity, message, Toast.LENGTH_SHORT).show());
    }

    private static void bind(View target, View.OnClickListener listener) {
        target.setClickable(true);
        target.setOnClickListener(view -> {
            if (!view.isEnabled()) {
                return;
            }
            view.setEnabled(false);
            view.postDelayed(() -> view.setEnabled(true), 450L);
            listener.onClick(view);
        });
    }

}
