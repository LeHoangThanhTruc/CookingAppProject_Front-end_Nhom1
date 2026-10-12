package com.example.cookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Frame12FavoritesList extends Activity {

    private ImageButton btnBack;
    private ImageButton btnHeartHeader;
    private EditText etSearchFavorite;
    private TextView chipAll, chipMain, chipQuick, chipDessert;
    private ImageButton btnFavorite1, btnFavorite2, btnFavorite3, btnFavorite4;
    private LinearLayout btnExploreMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_FavoritesList);

        // Ánh xạ các View từ XML
        initViews();

        // Đăng ký sự kiện Click
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnHeartHeader = findViewById(R.id.btnHeartHeader);
        etSearchFavorite = findViewById(R.id.etSearchFavorite);

        chipAll = findViewById(R.id.chipAll);
        chipMain = findViewById(R.id.chipMain);
        chipQuick = findViewById(R.id.chipQuick);
        chipDessert = findViewById(R.id.chipDessert);

        btnFavorite1 = findViewById(R.id.btnFavorite1);
        btnFavorite2 = findViewById(R.id.btnFavorite2);
        btnFavorite3 = findViewById(R.id.btnFavorite3);
        btnFavorite4 = findViewById(R.id.btnFavorite4);

        btnExploreMore = findViewById(R.id.btnExploreMore);
    }

    private void setupEvents() {
        // Nút Back
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Nút Heart Header
        if (btnHeartHeader != null) {
            btnHeartHeader.setOnClickListener(v ->
                    Toast.makeText(this, "Danh sách công thức yêu thích của bạn", Toast.LENGTH_SHORT).show()
            );
        }

        // Filter Chips
        if (chipAll != null) chipAll.setOnClickListener(v -> Toast.makeText(this, "Lọc: Tất cả", Toast.LENGTH_SHORT).show());
        if (chipMain != null) chipMain.setOnClickListener(v -> Toast.makeText(this, "Lọc: Món chính", Toast.LENGTH_SHORT).show());
        if (chipQuick != null) chipQuick.setOnClickListener(v -> Toast.makeText(this, "Lọc: Nhanh gọn", Toast.LENGTH_SHORT).show());
        if (chipDessert != null) chipDessert.setOnClickListener(v -> Toast.makeText(this, "Lọc: Tráng miệng", Toast.LENGTH_SHORT).show());

        // Favorite Buttons cho từng món ăn
        if (btnFavorite1 != null) {
            btnFavorite1.setOnClickListener(v -> Toast.makeText(this, "Đã bỏ Gỏi Cuốn Tôm Thịt khỏi yêu thích", Toast.LENGTH_SHORT).show());
        }
        if (btnFavorite2 != null) {
            btnFavorite2.setOnClickListener(v -> Toast.makeText(this, "Đã bỏ Cơm Gà Xối Mỡ khỏi yêu thích", Toast.LENGTH_SHORT).show());
        }
        if (btnFavorite3 != null) {
            btnFavorite3.setOnClickListener(v -> Toast.makeText(this, "Đã bỏ Chè Khúc Bạch khỏi yêu thích", Toast.LENGTH_SHORT).show());
        }
        if (btnFavorite4 != null) {
            btnFavorite4.setOnClickListener(v -> Toast.makeText(this, "Đã bỏ Mì Ý Sốt Bò Bằm khỏi yêu thích", Toast.LENGTH_SHORT).show());
        }

        // Nút Khám phá thêm công thức
        if (btnExploreMore != null) {
            btnExploreMore.setOnClickListener(v ->
                    Toast.makeText(this, "Khám phá thêm công thức mới", Toast.LENGTH_SHORT).show()
            );
        }
    }
}