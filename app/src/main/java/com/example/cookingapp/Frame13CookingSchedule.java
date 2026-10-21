package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame13CookingSchedule extends Activity {

    private ImageButton btnBack;
    private ImageButton btnNotificationHeader;
    private ImageButton btnAvatarHeader;

    private TextView chipLastWeek, chipThisWeek, chipNextWeek;
    private LinearLayout headerDay1;
    private LinearLayout cardDay2, cardDay3, cardDay4, cardDay5, cardDay6, cardDay7;
    private TextView btnChooseRecipe2, btnChooseRecipe3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_schedule);

        // Ánh xạ các View từ XML
        initViews();

        // Đăng ký sự kiện Click
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnNotificationHeader = findViewById(R.id.btnNotificationHeader);
        btnAvatarHeader = findViewById(R.id.btnAvatarHeader);

        chipLastWeek = findViewById(R.id.chipLastWeek);
        chipThisWeek = findViewById(R.id.chipThisWeek);
        chipNextWeek = findViewById(R.id.chipNextWeek);

        headerDay1 = findViewById(R.id.headerDay1);
        cardDay2 = findViewById(R.id.cardDay2);
        cardDay3 = findViewById(R.id.cardDay3);
        cardDay4 = findViewById(R.id.cardDay4);
        cardDay5 = findViewById(R.id.cardDay5);
        cardDay6 = findViewById(R.id.cardDay6);
        cardDay7 = findViewById(R.id.cardDay7);

        btnChooseRecipe2 = findViewById(R.id.btnChooseRecipe2);
        btnChooseRecipe3 = findViewById(R.id.btnChooseRecipe3);
    }

    private void setupEvents() {
        // Nút Back -> Quay về giao diện activity_myactivity.xml (Frame10Myactivity)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(Frame13CookingSchedule.this, Frame10Myactivity.class);
                startActivity(intent);
                finish();
            });
        }

        // Nút Thông báo
        if (btnNotificationHeader != null) {
            btnNotificationHeader.setOnClickListener(v ->
                    Toast.makeText(this, "Thông báo lịch nấu ăn", Toast.LENGTH_SHORT).show()
            );
        }

        // Nút Avatar
        if (btnAvatarHeader != null) {
            btnAvatarHeader.setOnClickListener(v ->
                    Toast.makeText(this, "Hồ sơ cá nhân", Toast.LENGTH_SHORT).show()
            );
        }

        // Filter Chips tuần
        if (chipLastWeek != null) chipLastWeek.setOnClickListener(v -> Toast.makeText(this, "Xem lịch Tuần trước", Toast.LENGTH_SHORT).show());
        if (chipThisWeek != null) chipThisWeek.setOnClickListener(v -> Toast.makeText(this, "Xem lịch Tuần này", Toast.LENGTH_SHORT).show());
        if (chipNextWeek != null) chipNextWeek.setOnClickListener(v -> Toast.makeText(this, "Xem lịch Tuần sau", Toast.LENGTH_SHORT).show());

        // Các ngày trong tuần
        if (headerDay1 != null) {
            headerDay1.setOnClickListener(v -> Toast.makeText(this, "Thu gọn/Mở rộng lịch Thứ 2", Toast.LENGTH_SHORT).show());
        }
        if (cardDay2 != null) cardDay2.setOnClickListener(v -> Toast.makeText(this, "Mở rộng lịch Thứ 3", Toast.LENGTH_SHORT).show());
        if (cardDay3 != null) cardDay3.setOnClickListener(v -> Toast.makeText(this, "Mở rộng lịch Thứ 4", Toast.LENGTH_SHORT).show());
        if (cardDay4 != null) cardDay4.setOnClickListener(v -> Toast.makeText(this, "Mở rộng lịch Thứ 5", Toast.LENGTH_SHORT).show());
        if (cardDay5 != null) cardDay5.setOnClickListener(v -> Toast.makeText(this, "Mở rộng lịch Thứ 6", Toast.LENGTH_SHORT).show());
        if (cardDay6 != null) cardDay6.setOnClickListener(v -> Toast.makeText(this, "Mở rộng lịch Thứ 7", Toast.LENGTH_SHORT).show());
        if (cardDay7 != null) cardDay7.setOnClickListener(v -> Toast.makeText(this, "Mở rộng lịch Chủ nhật", Toast.LENGTH_SHORT).show());

        // Chọn món đã lưu cho bữa trưa / chiều
        if (btnChooseRecipe2 != null) {
            btnChooseRecipe2.setOnClickListener(v -> Toast.makeText(this, "Chọn món đã lưu cho bữa trưa", Toast.LENGTH_SHORT).show());
        }
        if (btnChooseRecipe3 != null) {
            btnChooseRecipe3.setOnClickListener(v -> Toast.makeText(this, "Chọn món đã lưu cho bữa chiều", Toast.LENGTH_SHORT).show());
        }
    }
}
