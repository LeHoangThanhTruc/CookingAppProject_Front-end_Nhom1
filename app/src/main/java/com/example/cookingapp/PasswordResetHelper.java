package com.example.cookingapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Hàm dùng chung cho luồng Quên mật khẩu / Đặt lại mật khẩu / Đổi mật khẩu.
 * File mới, không thay thế code của thành viên khác.
 */
public final class PasswordResetHelper {

    private static final long RESEND_SECONDS = 60;
    private static final String NETWORK_ERROR =
            "Không kết nối được server. Kiểm tra backend đã chạy chưa.";

    private PasswordResetHelper() {
    }

    public interface Listener<T> {
        void onSuccess(T value);

        void onError(String message);
    }

    // ---------------- Gọi API ----------------

    public static void requestOtp(String identifier,
                                  Listener<AuthModels.MessageResponse> listener) {
        enqueue(publicApi().forgotPassword(new AuthModels.ForgotPasswordBody(identifier)), listener);
    }

    public static void verifyOtp(String identifier, String otp,
                                 Listener<AuthModels.VerifyOtpResponse> listener) {
        enqueue(publicApi().verifyOtp(new AuthModels.VerifyOtpBody(identifier, otp)), listener);
    }

    public static void resetPassword(String resetToken, String newPassword,
                                     Listener<AuthModels.MessageResponse> listener) {
        enqueue(publicApi().resetPassword(
                new AuthModels.ResetPasswordBody(resetToken, newPassword)), listener);
    }

    /** Dành cho người đã đăng nhập (tự gắn access token vào header). */
    public static void changePassword(Context context, String currentPassword, String newPassword,
                                      Listener<AuthModels.MessageResponse> listener) {
        ApiService api = ApiClient.getAuthenticatedClient(context.getApplicationContext())
                .create(ApiService.class);
        enqueue(api.changePassword(
                new AuthModels.ChangePasswordBody(currentPassword, newPassword)), listener);
    }

    /**
     * Người đã đăng nhập xác nhận mật khẩu hiện tại; thành công trả về reset_token
     * để sang bước đặt mật khẩu mới (ResetPasswordActivity).
     */
    public static void verifyCurrentPassword(Context context, String currentPassword,
                                             Listener<AuthModels.VerifyOtpResponse> listener) {
        ApiService api = ApiClient.getAuthenticatedClient(context.getApplicationContext())
                .create(ApiService.class);
        enqueue(api.verifyCurrentPassword(
                new AuthModels.VerifyCurrentPasswordBody(currentPassword)), listener);
    }

    private static ApiService publicApi() {
        return ApiClient.getClient().create(ApiService.class);
    }

    private static <T> void enqueue(Call<T> call, Listener<T> listener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> ignored, Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onError(AuthModels.errorMessage(response));
                }
            }

            @Override
            public void onFailure(Call<T> ignored, Throwable error) {
                listener.onError(NETWORK_ERROR);
            }
        });
    }

    // ---------------- Giao diện OTP ----------------

    /** Tô màu 6 gạch chân theo số chữ số đã nhập trong ô edtOtp. */
    public static void bindOtpInput(Activity activity, EditText edtOtp) {
        if (edtOtp == null) {
            return;
        }
        final int[] slotIds = {
                R.id.viewOtp1, R.id.viewOtp2, R.id.viewOtp3,
                R.id.viewOtp4, R.id.viewOtp5, R.id.viewOtp6
        };
        final int active = ContextCompat.getColor(activity, R.color.panda_green);
        final int inactive = Color.parseColor("#CBD5E1");

        // Bấm vào 6 gạch chân (hoặc khi vừa mở màn hình) thì chuyển con trỏ vào ô nhập và bật bàn phím
        final View.OnClickListener focusInput = v -> {
            edtOtp.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(edtOtp, InputMethodManager.SHOW_IMPLICIT);
            }
        };
        for (int slotId : slotIds) {
            View slot = activity.findViewById(slotId);
            if (slot != null) {
                slot.setOnClickListener(focusInput);
            }
        }
        edtOtp.postDelayed(() -> focusInput.onClick(edtOtp), 300);

        edtOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int highlighted = Math.max(s.length(), 1);
                for (int i = 0; i < slotIds.length; i++) {
                    View slot = activity.findViewById(slotIds[i]);
                    if (slot != null) {
                        slot.setBackgroundColor(i < highlighted ? active : inactive);
                    }
                }
            }
        });
    }

    /**
     * Đếm ngược 60 giây trên nhãn gửi lại mã; hết giờ thì nhãn bấm được để gửi lại.
     * Trả về timer để Activity hủy trong onDestroy.
     */
    public static CountDownTimer startResendCountdown(TextView label, Runnable onResend) {
        if (label == null) {
            return null;
        }
        label.setOnClickListener(null);
        label.setClickable(false);

        CountDownTimer timer = new CountDownTimer(RESEND_SECONDS * 1000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = (millisUntilFinished + 999L) / 1000L;
                label.setText("Vui lòng chờ " + seconds + " giây để nhận lại mã xác thực");
            }

            @Override
            public void onFinish() {
                label.setText("Gửi lại mã xác thực");
                label.setClickable(true);
                label.setOnClickListener(v -> onResend.run());
            }
        };
        timer.start();
        return timer;
    }
}