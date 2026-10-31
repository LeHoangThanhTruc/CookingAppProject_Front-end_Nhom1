package com.example.cookingapp;

import android.content.Context;

import java.net.URI;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    public static final String BASE_URL = BuildConfig.API_BASE_URL;

    private static volatile Retrofit publicRetrofit;
    private static volatile Retrofit authenticatedRetrofit;

    private ApiClient() {
    }

    /**
     * Client không gắn access token, giữ tương thích cho module đăng nhập/đăng ký.
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

    public static Retrofit getAuthenticatedClient(Context context) {
        if (authenticatedRetrofit == null) {
            synchronized (ApiClient.class) {
                if (authenticatedRetrofit == null) {
                    Context appContext = context.getApplicationContext();
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(chain -> {
                                Request request = chain.request();
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
                    authenticatedRetrofit = createRetrofit(client);
                }
            }
        }
        return authenticatedRetrofit;
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
