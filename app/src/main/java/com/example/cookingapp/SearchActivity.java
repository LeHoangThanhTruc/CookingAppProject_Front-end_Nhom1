package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.example.cookingapp.R;

public class SearchActivity extends AppCompatActivity {

    private ImageView btnHome, imgChevron;
    private LinearLayout layoutAdvancedHeader;
    private NestedScrollView cardAdvancedFilter;
    private TextView btnTypeAll, btnTypeDishes, btnTypeUsers;
    private TextView btnViewRecipe; // Nút chuyển sang màn hình Chi tiết công thức
    private TextView btnResetFilter, btnApplyFilter;
    private EditText edtSearchQuery;
    private boolean isFilterExpanded = true; // Mặc định đang mở

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnHome = findViewById(R.id.btnHome);
        imgChevron = findViewById(R.id.imgChevron);
        layoutAdvancedHeader = findViewById(R.id.layoutAdvancedHeader);
        cardAdvancedFilter = findViewById(R.id.cardAdvancedFilter);

        btnTypeAll = findViewById(R.id.btnTypeAll);
        btnTypeDishes = findViewById(R.id.btnTypeDishes);
        btnTypeUsers = findViewById(R.id.btnTypeUsers);
        // Ánh xạ nút Xem công thức món ăn
        btnViewRecipe = findViewById(R.id.btnViewRecipe);
        btnResetFilter = findViewById(R.id.btnResetFilter);
        btnApplyFilter = findViewById(R.id.btnApplyFilter);
        edtSearchQuery = findViewById(R.id.edtSearchQuery);
    }

    private void setupEvents() {
        // 1. Nút mũi tên quay về: Mở lại MainActivity và đóng SearchActivity
        btnHome.setOnClickListener(v -> finish());

        // 2. Bật / Tắt hiển thị bộ lọc nâng cao bằng Chevron
        layoutAdvancedHeader.setOnClickListener(v -> toggleAdvancedFilter());
        findViewById(R.id.btnToggleFilter).setOnClickListener(v -> toggleAdvancedFilter());

        // 3. Xử lý chọn loại tìm kiếm (Tất cả / Món ăn / Người dùng)
        btnTypeAll.setOnClickListener(v -> selectTypeFilter("ALL"));
        btnTypeDishes.setOnClickListener(v -> selectTypeFilter("DISHES"));
        btnTypeUsers.setOnClickListener(v -> selectTypeFilter("USERS"));
        btnResetFilter.setOnClickListener(v -> {
            edtSearchQuery.setText("");
            selectTypeFilter("ALL");
            Toast.makeText(this, "Đã đặt lại bộ lọc", Toast.LENGTH_SHORT).show();
        });
        btnApplyFilter.setOnClickListener(v -> Toast.makeText(this, "Đã áp dụng bộ lọc", Toast.LENGTH_SHORT).show());

        // 4. Lắng nghe sự kiện click -> Chuyển sang màn hình Chi tiết công thức (RecipeDetailActivity)
        if (btnViewRecipe != null) {
            btnViewRecipe.setOnClickListener(v -> {
                Intent intent = new Intent(SearchActivity.this, RecipeDetailActivity.class);
                startActivity(intent);
            });
        }
    }

    private void toggleAdvancedFilter() {
        if (isFilterExpanded) {
            // Ẩn bộ lọc nâng cao
            cardAdvancedFilter.setVisibility(View.GONE);
            imgChevron.setImageResource(R.drawable.chevron_up);
            isFilterExpanded = false;
        } else {
            // Mở bộ lọc nâng cao
            cardAdvancedFilter.setVisibility(View.VISIBLE);
            imgChevron.setImageResource(R.drawable.chevron_down);
            isFilterExpanded = true;
        }
    }

    private void selectTypeFilter(String type) {
        // Reset giao diện các nút
        btnTypeAll.setBackgroundResource(R.drawable.bg_pill_white);
        btnTypeAll.setTextColor(getColor(R.color.black));
        btnTypeDishes.setBackgroundResource(R.drawable.bg_pill_white);
        btnTypeDishes.setTextColor(getColor(R.color.black));
        btnTypeUsers.setBackgroundResource(R.drawable.bg_pill_white);
        btnTypeUsers.setTextColor(getColor(R.color.black));

        // Highlight nút được chọn
        switch (type) {
            case "ALL":
                btnTypeAll.setBackgroundResource(R.drawable.bg_button_green);
                btnTypeAll.setTextColor(getColor(R.color.white));
                Toast.makeText(this, "Đang lọc: Tất cả", Toast.LENGTH_SHORT).show();
                break;
            case "DISHES":
                btnTypeDishes.setBackgroundResource(R.drawable.bg_button_green);
                btnTypeDishes.setTextColor(getColor(R.color.white));
                Toast.makeText(this, "Đang lọc: Món ăn", Toast.LENGTH_SHORT).show();
                break;
            case "USERS":
                btnTypeUsers.setBackgroundResource(R.drawable.bg_button_green);
                btnTypeUsers.setTextColor(getColor(R.color.white));
                Toast.makeText(this, "Đang lọc: Người dùng", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
