package com.example.cookingapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.CallbackManager;
import java.security.MessageDigest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmailPhone;
    private EditText edtPassword;
    private Button btnDangNhap;
    private View googleLogin;
    private View facebookLogin;
    private CallbackManager facebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // In KeyHash ra Logcat (tag: KeyHash)
        printKeyHash();

        // Ánh xạ View
        TextView tvRegister = findViewById(R.id.tvRegister);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtEmailPhone = findViewById(R.id.edtEmailPhone);
        edtPassword = findViewById(R.id.edtPassword);
        ImageView iconEye = findViewById(R.id.iconEye);
        googleLogin = findViewById(R.id.btnLogInUsingGoogle);
        facebookLogin = findViewById(R.id.btnLogInUsingFacebook);

        PasswordToggleHelper.attach(edtPassword, iconEye);

        // Đăng ký Facebook callback một lần trong Activity
        facebookCallbackManager = SocialLoginHelper.registerFacebookCallback(
                new SocialLoginHelper.Listener() {
                    @Override
                    public void onSuccess(AuthModels.TokenResponse response) {
                        handleSocialLoginSuccess(response);
                    }

                    @Override
                    public void onError(String message) {
                        showMessage(message);
                    }
                }
        );

        // Chuyển sang đăng ký
        if (tvRegister != null) {
            tvRegister.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }

        // Quên mật khẩu
        if (tvForgotPassword != null) {
            tvForgotPassword.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                intent.putExtra("FLOW_SOURCE", "LOGIN");
                startActivity(intent);
            });
        }

        // Đăng nhập bằng tài khoản và mật khẩu
        if (btnDangNhap != null) {
            btnDangNhap.setOnClickListener(v -> login());
        }

        // Đăng nhập Google
        if (googleLogin != null) {
            googleLogin.setOnClickListener(v -> {
                setSocialButtonsEnabled(false);
                SocialLoginHelper.loginWithGoogle(
                        LoginActivity.this,
                        new SocialLoginHelper.Listener() {
                            @Override
                            public void onSuccess(AuthModels.TokenResponse response) {
                                setSocialButtonsEnabled(true);
                                handleSocialLoginSuccess(response);
                            }

                            @Override
                            public void onError(String message) {
                                setSocialButtonsEnabled(true);
                                showMessage(message);
                            }
                        }
                );
            });
        }

        // Đăng nhập Facebook
        if (facebookLogin != null) {
            facebookLogin.setOnClickListener(v -> {
                setSocialButtonsEnabled(false);
                SocialLoginHelper.startFacebookLogin(this);
            });
        }
    }

    private void printKeyHash() {
        try {
            PackageInfo info;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info = getPackageManager().getPackageInfo(
                        getPackageName(),
                        PackageManager.GET_SIGNING_CERTIFICATES
                );
                if (info.signingInfo != null) {
                    Signature[] signatures = info.signingInfo.getApkContentsSigners();
                    for (Signature signature : signatures) {
                        MessageDigest md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                    }
                }
            } else {
                info = getPackageManager().getPackageInfo(
                        getPackageName(),
                        PackageManager.GET_SIGNATURES
                );
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                }
            }
        } catch (Exception e) {
            Log.e("KeyHash", "Lỗi lấy key hash", e);
        }
    }

    // Facebook SDK trả kết quả về đây
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (facebookCallbackManager != null) {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    // ================= LOGIN THƯỜNG =================
    private void login() {
        String identifier = edtEmailPhone.getText().toString().trim();
        String password = edtPassword.getText().toString();

        if (identifier.isEmpty()) {
            edtEmailPhone.setError("Vui lòng nhập username, email hoặc số điện thoại");
            edtEmailPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            edtPassword.requestFocus();
            return;
        }

        loginUser(identifier, password);
    }

    private void loginUser(String identifier, String password) {
        btnDangNhap.setEnabled(false);
        ApiService service = ApiClient.getAuthenticatedClient(this).create(ApiService.class);
        service.loginUser(new ApiService.LoginRequest(identifier, password))
                .enqueue(new Callback<ApiService.AuthResponse>() {
                    @Override
                    public void onResponse(
                            Call<ApiService.AuthResponse> call,
                            Response<ApiService.AuthResponse> response
                    ) {
                        btnDangNhap.setEnabled(true);
                        ApiService.AuthResponse body = response.body();
                        if (!response.isSuccessful()
                                || body == null
                                || body.access_token == null
                                || body.access_token.isEmpty()) {
                            showMessage(ApiErrorUtils.message(response, "Đăng nhập thất bại"));
                            return;
                        }
                        SessionManager.saveAccessToken(LoginActivity.this, body.access_token);
                        goToMainScreen();
                    }

                    @Override
                    public void onFailure(Call<ApiService.AuthResponse> call, Throwable error) {
                        btnDangNhap.setEnabled(true);
                        showMessage("Không kết nối được máy chủ");
                    }
                });
    }

    // ================= SOCIAL LOGIN =================
    private void handleSocialLoginSuccess(AuthModels.TokenResponse response) {
        if (response == null || response.access_token == null || response.access_token.isEmpty()) {
            showMessage("Máy chủ không trả về access token");
            return;
        }
        SessionManager.saveAccessToken(LoginActivity.this, response.access_token);
        showMessage("Đăng nhập thành công!");
        goToMainScreen();
    }

    private void setSocialButtonsEnabled(boolean enabled) {
        if (googleLogin != null) googleLogin.setEnabled(enabled);
        if (facebookLogin != null) facebookLogin.setEnabled(enabled);
    }

    private void goToMainScreen() {
        Intent intent = new Intent(LoginActivity.this, ManHinhChinhActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
