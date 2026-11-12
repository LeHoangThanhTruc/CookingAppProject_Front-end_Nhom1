package com.example.cookingapp;

import android.content.Context;
import java.net.URI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    //public static final String BASE_URL = BuildConfig.API_BASE_URL;
    public static final String BASE_URL = "http://192.168.71.162:8000/";
    private static volatile Retrofit publicRetrofit;

    private ApiClient() {
    }

    /**
     * Client không gắn access token, dùng cho đăng nhập/đăng ký.
     */
    public static Retrofit getClient() {
        if (publicRetrofit == null) {
            synchronized (ApiClient.class) {
                if (publicRetrofit == null) {
                    publicRetrofit = createRetrofit(new OkHttpClient());
                }
            }
        }
        return publicRetrofit;
    }

    /**
     * Client tự động đính kèm access token mới nhất từ SessionManager cho mỗi request.
     */
    public static Retrofit getAuthenticatedClient(Context context) {
        Context appContext = context.getApplicationContext();

        // Tạo OkHttpClient mới với Interceptor luôn đọc token mới nhất từ SharedPreferences
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    // Đọc token mới nhất mỗi khi có request gọi đi
                    String token = SessionManager.getAccessToken(appContext);
                    if (token != null && !token.isEmpty()) {
                        request = request.newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build();
                    }

                    Response response = chain.proceed(request);
                    if (response.code() == 401) {
                        SessionManager.clear(appContext);
                    }
                    return response;
                })
                .build();

        // Trả về Retrofit instance với client cập nhật token động
        return createRetrofit(client);
    }

    private static Retrofit createRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static String resolveUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }
        URI value = URI.create(url.trim());
        if (value.isAbsolute()) {
            return value.toString();
        }
        return URI.create(BASE_URL).resolve(value).toString();
    }
}