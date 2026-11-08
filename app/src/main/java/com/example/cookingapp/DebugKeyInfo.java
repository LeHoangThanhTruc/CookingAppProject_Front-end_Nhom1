package com.example.cookingapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;

/**
 * TẠM THỜI để gỡ lỗi đăng nhập Google/Facebook: in tên gói, SHA-1 và Key Hash
 * của chính app đang chạy ra Logcat (lọc theo KEYINFO). Xóa file này sau khi xong.
 */
public final class DebugKeyInfo {

    private DebugKeyInfo() {
    }

    @SuppressWarnings("deprecation")
    public static void log(Context context) {
        try {
            Signature[] signatures;
            if (Build.VERSION.SDK_INT >= 28) {
                PackageInfo info = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
                signatures = info.signingInfo.getApkContentsSigners();
            } else {
                PackageInfo info = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), PackageManager.GET_SIGNATURES);
                signatures = info.signatures;
            }

            for (Signature signature : signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                byte[] digest = md.digest();

                StringBuilder hex = new StringBuilder();
                for (int i = 0; i < digest.length; i++) {
                    if (i > 0) {
                        hex.append(':');
                    }
                    hex.append(String.format("%02X", digest[i]));
                }

                Log.d("KEYINFO", "package = " + context.getPackageName());
                Log.d("KEYINFO", "SHA1 (dán vào Google) = " + hex);
                Log.d("KEYINFO", "Key Hash (dán vào Facebook) = "
                        + Base64.encodeToString(digest, Base64.NO_WRAP));
            }
        } catch (Exception error) {
            Log.e("KEYINFO", "Không đọc được chữ ký của app", error);
        }
    }
}