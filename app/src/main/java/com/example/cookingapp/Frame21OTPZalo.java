package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame21OTPZalo extends Activity {

    // Khai báo các thành phần giao diện
    private ImageButton btnBack;
    private TextView tvPhoneTarget;
    private TextView tvResendTimer;
    private LinearLayout btnNextOtp;

    // Các đường vạch đại diện cho 6 ô OTP
    private View viewOtp1, viewOtp2, viewOtp3, viewOtp4, viewOtp5, viewOtp6;

    // Các phím của bàn phím số
    private TextView key0, key1, key2, key3, key4, key5, key6, key7, key8, key9;
    private LinearLayout keyBackspace;

    // Chuỗi lưu trữ mã OTP người dùng nhập
    private final StringBuilder otpCode = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_zalo);

        // Ánh xạ các View từ XML
        initViews();

        // Đăng ký sự kiện Click
        setupEvents();
    }

    /**
     * Ánh xạ View thông qua findViewById
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvPhoneTarget = findViewById(R.id.tvPhoneTarget);
        tvResendTimer = findViewById(R.id.tvResendTimer);
        btnNextOtp = findViewById(R.id.btnNextOtp);

        viewOtp1 = findViewById(R.id.viewOtp1);
        viewOtp2 = findViewById(R.id.viewOtp2);
        viewOtp3 = findViewById(R.id.viewOtp3);
        viewOtp4 = findViewById(R.id.viewOtp4);
        viewOtp5 = findViewById(R.id.viewOtp5);
        viewOtp6 = findViewById(R.id.viewOtp6);

        key0 = findViewById(R.id.key0);
        key1 = findViewById(R.id.key1);
        key2 = findViewById(R.id.key2);
        key3 = findViewById(R.id.key3);
        key4 = findViewById(R.id.key4);
        key5 = findViewById(R.id.key5);
        key6 = findViewById(R.id.key6);
        key7 = findViewById(R.id.key7);
        key8 = findViewById(R.id.key8);
        key9 = findViewById(R.id.key9);
        keyBackspace = findViewById(R.id.keyBackspace);
    }

    /**
     * Đăng ký các sự kiện Click
     */
    private void setupEvents() {
        // 1. Nút Quay lại
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Sự kiện bấm các phím số từ 0 đến 9
        if (key0 != null) key0.setOnClickListener(v -> appendDigit("0"));
        if (key1 != null) key1.setOnClickListener(v -> appendDigit("1"));
        if (key2 != null) key2.setOnClickListener(v -> appendDigit("2"));
        if (key3 != null) key3.setOnClickListener(v -> appendDigit("3"));
        if (key4 != null) key4.setOnClickListener(v -> appendDigit("4"));
        if (key5 != null) key5.setOnClickListener(v -> appendDigit("5"));
        if (key6 != null) key6.setOnClickListener(v -> appendDigit("6"));
        if (key7 != null) key7.setOnClickListener(v -> appendDigit("7"));
        if (key8 != null) key8.setOnClickListener(v -> appendDigit("8"));
        if (key9 != null) key9.setOnClickListener(v -> appendDigit("9"));

        // 3. Phím xóa (Backspace)
        if (keyBackspace != null) {
            keyBackspace.setOnClickListener(v -> removeDigit());
        }

        // 4. Nút Tiếp theo
        if (btnNextOtp != null) {
            btnNextOtp.setOnClickListener(v -> {
                if (otpCode.length() < 6) {
                    Toast.makeText(this, "Vui lòng nhập đủ 6 chữ số mã xác thực Zalo!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Xác minh mã OTP Zalo thành công: " + otpCode.toString(), Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }

        // 5. Gửi lại mã Zalo
        if (tvResendTimer != null) {
            tvResendTimer.setOnClickListener(v ->
                    Toast.makeText(this, "Đã gửi lại mã xác thực qua Zalo!", Toast.LENGTH_SHORT).show()
            );
        }
    }

    /**
     * Thêm 1 chữ số vào chuỗi OTP
     */
    private void appendDigit(String digit) {
        if (otpCode.length() < 6) {
            otpCode.append(digit);
            updateOtpIndicators();
        }
    }

    /**
     * Xóa 1 chữ số cuối khỏi chuỗi OTP
     */
    private void removeDigit() {
        if (otpCode.length() > 0) {
            otpCode.deleteCharAt(otpCode.length() - 1);
            updateOtpIndicators();
        }
    }

    /**
     * Cập nhật màu vạch gạch chân theo số lượng chữ số đã nhập
     */
    private void updateOtpIndicators() {
        int length = otpCode.length();
        int activeColor = 0xFF2D6A2D; // Màu xanh lá chủ đạo
        int defaultColor = 0xFFCBD5E1; // Màu xám nhạt

        if (viewOtp1 != null) viewOtp1.setBackgroundColor(length >= 1 ? activeColor : defaultColor);
        if (viewOtp2 != null) viewOtp2.setBackgroundColor(length >= 2 ? activeColor : defaultColor);
        if (viewOtp3 != null) viewOtp3.setBackgroundColor(length >= 3 ? activeColor : defaultColor);
        if (viewOtp4 != null) viewOtp4.setBackgroundColor(length >= 4 ? activeColor : defaultColor);
        if (viewOtp5 != null) viewOtp5.setBackgroundColor(length >= 5 ? activeColor : defaultColor);
        if (viewOtp6 != null) viewOtp6.setBackgroundColor(length >= 6 ? activeColor : defaultColor);
    }
}