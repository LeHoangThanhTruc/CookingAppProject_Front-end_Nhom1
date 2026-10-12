package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame14SavedList extends Activity {

    private ImageButton btnBack;
    private ImageButton btnBookmarkHeader;
    private EditText etSearchSaved;
    private TextView chipAll, chipMain, chipQuick, chipDessert;
    private ImageButton btnBookmark1, btnBookmark2, btnBookmark3, btnBookmark4;
    private LinearLayout btnScheduleCooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);

        // Ánh xạ các View từ XML
        initViews();

        // Đăng ký sự kiện Click
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnBookmarkHeader = findViewById(R.id.btnBookmarkHeader);
        etSearchSaved = findViewById(R.id.etSearchSaved);

        chipAll = findViewById(R.id.chipAll);
        chipMain = findViewById(R.id.chipMain);
        chipQuick = findViewById(R.id.chipQuick);
        chipDessert = findViewById(R.id.chipDessert);

        btnBookmark1 = findViewById(R.id.btnBookmark1);
        btnBookmark2 = findViewById(R.id.btnBookmark2);
        btnBookmark3 = findViewById(R.id.btnBookmark3);
        btnBookmark4 = findViewById(R.id.btnBookmark4);

        btnScheduleCooking = findViewById(R.id.btnScheduleCooking);
    }

    private void setupEvents() {
        // Nút Back
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Nút Bookmark Header
        if (btnBookmarkHeader != null) {
            btnBookmarkHeader.setOnClickListener(v ->
                    Toast.makeText(this, "Danh sách món ăn đã lưu", Toast.LENGTH_SHORT).show()
            );
        }

        // Filter Chips
        if (chipAll != null) chipAll.setOnClickListener(v -> Toast.makeText(this, "Lọc: Tất cả", Toast.LENGTH_SHORT).show());
        if (chipMain != null) chipMain.setOnClickListener(v -> Toast.makeText(this, "Lọc: Món chính", Toast.LENGTH_SHORT).show());
        if (chipQuick != null) chipQuick.setOnClickListener(v -> Toast.makeText(this, "Lọc: Nhanh gọn", Toast.LENGTH_SHORT).show());
        if (chipDessert != null) chipDessert.setOnClickListener(v -> Toast.makeText(this, "Lọc: Tráng miệng", Toast.LENGTH_SHORT).show());

        // Bookmark Buttons cho từng món ăn
        if (btnBookmark1 != null) {
            btnBookmark1.setOnClickListener(v -> Toast.makeText(this, "Đã cập nhật Gỏi Cuốn Tôm Thịt", Toast.LENGTH_SHORT).show());
        }
        if (btnBookmark2 != null) {
            btnBookmark2.setOnClickListener(v -> Toast.makeText(this, "Đã cập nhật Cơm Gà Xối Mỡ", Toast.LENGTH_SHORT).show());
        }
        if (btnBookmark3 != null) {
            btnBookmark3.setOnClickListener(v -> Toast.makeText(this, "Đã cập nhật Chè Khúc Bạch", Toast.LENGTH_SHORT).show());
        }
        if (btnBookmark4 != null) {
            btnBookmark4.setOnClickListener(v -> Toast.makeText(this, "Đã cập nhật Mì Ý Sốt Bò Bằm", Toast.LENGTH_SHORT).show());
        }

        // Nút Lên lịch nấu ăn
        if (btnScheduleCooking != null) {
            btnScheduleCooking.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame13CookingSchedule.class))
            );
        }
    }
}
