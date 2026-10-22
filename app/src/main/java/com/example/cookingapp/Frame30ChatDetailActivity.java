package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Frame30ChatDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_30_chat_detail);
        FrameNavigation.bindBack(this, R.id.button_back_chat);
        FrameNavigation.bindToast(this, R.id.button_attach_message, "Chọn tệp đính kèm (bản mẫu)");
        FrameNavigation.bind(this, R.id.button_send_message, v -> {
            TextView input = findViewById(R.id.input_message);
            String message = input.getText().toString().trim();
            if (message.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nội dung tin nhắn", Toast.LENGTH_SHORT).show();
                return;
            }
            input.setText("");
            Toast.makeText(this, "Đã gửi tin nhắn", Toast.LENGTH_SHORT).show();
        });
    }
}
