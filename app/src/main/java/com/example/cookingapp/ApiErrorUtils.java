package com.example.cookingapp;

import retrofit2.Response;

public final class ApiErrorUtils {
    private ApiErrorUtils() {
    }

    public static String message(Response<?> response, String fallback) {
        if (response == null) {
            return fallback;
        }
        String msg = AuthModels.errorMessage(response);
        if (msg == null || msg.startsWith("Có lỗi xảy ra")) {
            return fallback;
        }
        return msg;
    }
}
