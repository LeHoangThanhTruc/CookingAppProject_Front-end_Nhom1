package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Frame10Myactivity extends Activity {

    private ImageButton btnBack;
    private ImageButton btnEditHeader;
    private LinearLayout btnSavedRecipes;
    private LinearLayout btnReviews;
    private LinearLayout btnCookingCalendar;
    private LinearLayout btnFavoriteRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);

        initViews();
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnEditHeader = findViewById(R.id.btnEditHeader);
        btnSavedRecipes = findViewById(R.id.btnSavedRecipes);
        btnReviews = findViewById(R.id.btnReviews);
        btnCookingCalendar = findViewById(R.id.btnCookingCalendar);
        btnFavoriteRecipes = findViewById(R.id.btnFavoriteRecipes);
    }

    private void setupEvents() {
        // Nút Quay về -> Chuyển sang ManHinhChinhActivity
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnEditHeader != null) {
            btnEditHeader.setOnClickListener(v -> startActivity(new Intent(this, Frame15ProfileDetails.class)));
        }

        if (btnSavedRecipes != null) {
            btnSavedRecipes.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame14SavedList.class))
            );
        }

        if (btnReviews != null) {
            btnReviews.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame11Dadanhgia.class))
            );
        }

        if (btnCookingCalendar != null) {
            btnCookingCalendar.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame13CookingSchedule.class))
            );
        }

        if (btnFavoriteRecipes != null) {
            btnFavoriteRecipes.setOnClickListener(v ->
                    startActivity(new Intent(this, Frame12FavoritesList.class))
            );
        }
    }
}
