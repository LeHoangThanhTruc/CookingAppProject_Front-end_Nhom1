package com.example.cookingapp;

import android.app.Activity;
import android.os.CancellationSignal;
import androidx.core.content.ContextCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.NoCredentialException;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import java.util.Arrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class SocialLoginHelper {
    public interface Listener {
        void onSuccess(AuthModels.TokenResponse response);
        void onError(String message);
    }

    private SocialLoginHelper() {
    }

    // ================= GOOGLE =================
    public static void loginWithGoogle(
            Activity activity,
            Listener listener
    ) {
        try {
            String webClientId =
                    activity.getString(R.string.google_web_client_id).trim();
            if (webClientId.isEmpty()
                    || webClientId.equals("YOUR_WEB_CLIENT_ID")) {
                listener.onError("Chưa cấu hình Google Web Client ID");
                return;
            }

            GetSignInWithGoogleOption option =
                    new GetSignInWithGoogleOption.Builder(webClientId)
                            .build();

            GetCredentialRequest request =
                    new GetCredentialRequest.Builder()
                            .addCredentialOption(option)
                            .build();

            CredentialManager credentialManager =
                    CredentialManager.create(activity);

            credentialManager.getCredentialAsync(
                    activity,
                    request,
                    new CancellationSignal(),
                    ContextCompat.getMainExecutor(activity),
                    new CredentialManagerCallback<
                            GetCredentialResponse,
                            GetCredentialException>() {
                        @Override
                        public void onResult(GetCredentialResponse result) {
                            Credential credential = result.getCredential();
                            if (credential instanceof CustomCredential
                                    && GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                                    .equals(credential.getType())) {
                                try {
                                    GoogleIdTokenCredential googleCredential =
                                            GoogleIdTokenCredential.createFrom(
                                                    ((CustomCredential) credential).getData()
                                            );
                                    String idToken = googleCredential.getIdToken();
                                    if (idToken == null || idToken.isEmpty()) {
                                        listener.onError("Không lấy được Google ID Token");
                                        return;
                                    }
                                    exchangeWithBackend("GOOGLE", idToken, listener);
                                } catch (Exception e) {
                                    listener.onError("Không đọc được thông tin Google: " + e.getMessage());
                                }
                            } else {
                                listener.onError("Google trả về loại thông tin không được hỗ trợ");
                            }
                        }

                        @Override
                        public void onError(GetCredentialException error) {
                            if (error instanceof GetCredentialCancellationException) {
                                listener.onError("Bạn đã hủy đăng nhập Google");
                            } else if (error instanceof NoCredentialException) {
                                listener.onError(
                                        "Không tìm thấy tài khoản Google phù hợp. "
                                                + "Hãy kiểm tra tài khoản Google trên thiết bị."
                                );
                            } else {
                                listener.onError("Đăng nhập Google thất bại: " + error.getMessage());
                            }
                        }
                    }
            );
        } catch (Exception e) {
            listener.onError("Lỗi khởi tạo Google: " + e.getMessage());
        }
    }

    // ================= FACEBOOK =================
    public static CallbackManager registerFacebookCallback(Listener listener) {
        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult result) {
                        if (result == null || result.getAccessToken() == null) {
                            listener.onError("Không lấy được Facebook Access Token");
                            return;
                        }
                        String accessToken = result.getAccessToken().getToken();
                        if (accessToken == null || accessToken.isEmpty()) {
                            listener.onError("Facebook Access Token không hợp lệ");
                            return;
                        }
                        exchangeWithBackend("FACEBOOK", accessToken, listener);
                    }

                    @Override
                    public void onCancel() {
                        listener.onError("Bạn đã hủy đăng nhập Facebook");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        listener.onError(
                                "Đăng nhập Facebook thất bại: "
                                        + (error != null ? error.getMessage() : "Lỗi không xác định")
                        );
                    }
                }
        );
        return callbackManager;
    }

    public static void startFacebookLogin(Activity activity) {
        try {
            LoginManager.getInstance().logInWithReadPermissions(
                    activity,
                    Arrays.asList("public_profile", "email")
            );
        } catch (Exception e) {
            // CallbackManager sẽ nhận lỗi nếu SDK phát sinh lỗi đăng nhập.
        }
    }

    // ================= BACKEND =================
    private static void exchangeWithBackend(
            String provider,
            String token,
            Listener listener
    ) {
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.oauthLogin(new AuthModels.OAuthBody(provider, token))
                .enqueue(new Callback<AuthModels.TokenResponse>() {
                    @Override
                    public void onResponse(
                            Call<AuthModels.TokenResponse> call,
                            Response<AuthModels.TokenResponse> response
                    ) {
                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().access_token != null
                                && !response.body().access_token.isEmpty()) {
                            listener.onSuccess(response.body());
                        } else {
                            listener.onError(
                                    ApiErrorUtils.message(
                                            response,
                                            "Đăng nhập " + provider + " thất bại"
                                    )
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<AuthModels.TokenResponse> call,
                            Throwable error
                    ) {
                        listener.onError(
                                "Không kết nối được máy chủ. "
                                        + "Kiểm tra backend và địa chỉ API."
                        );
                    }
                });
    }
}
