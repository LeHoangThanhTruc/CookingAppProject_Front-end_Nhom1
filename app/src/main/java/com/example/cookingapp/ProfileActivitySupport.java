package com.example.cookingapp;

import android.app.Activity;
import android.widget.Toast;

public final class ProfileActivitySupport {
    private ProfileActivitySupport() {
    }

    public static void handleError(
            Activity activity,
            ProfileRepository.ApiError error
    ) {
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
        if (error.isUnauthorized()) {
            SessionManager.redirectToLogin(activity);
        }
    }

    public static String valueOrFallback(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}
