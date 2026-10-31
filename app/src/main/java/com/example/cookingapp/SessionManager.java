package com.example.cookingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public final class SessionManager {
    public static final String PREFERENCES_NAME = "auth_session";
    public static final String KEY_ACCESS_TOKEN = "access_token";

    private SessionManager() {
    }

    public static void saveAccessToken(Context context, String token) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_ACCESS_TOKEN, token)
                .apply();
    }

    public static String getAccessToken(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(KEY_ACCESS_TOKEN, null);
    }

    public static boolean hasAccessToken(Context context) {
        String token = getAccessToken(context);
        return token != null && !token.trim().isEmpty();
    }

    public static void clear(Context context) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }

    public static boolean requireAuthenticated(Activity activity) {
        if (hasAccessToken(activity)) {
            return true;
        }
        Toast.makeText(activity, "Vui lòng đăng nhập để xem hồ sơ", Toast.LENGTH_SHORT).show();
        redirectToLogin(activity);
        return false;
    }

    public static void redirectToLogin(Activity activity) {
        clear(activity);
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
}
