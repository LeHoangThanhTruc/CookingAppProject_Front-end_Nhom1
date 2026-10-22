package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Frame31CreateRecipeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_31_create_recipe);

        FrameNavigation.bindBack(this, R.id.button_back_recipe);
        FrameNavigation.bind(this, R.id.button_next_recipe, v -> {
            EditText name = findViewById(R.id.input_recipe_name);
            EditText description = findViewById(R.id.input_recipe_description);
            if (name.getText().toString().trim().isEmpty()) {
                name.setError("Vui lòng nhập tên món ăn");
                name.requestFocus();
                return;
            }
            if (description.getText().toString().trim().isEmpty()) {
                description.setError("Vui lòng nhập mô tả ngắn");
                description.requestFocus();
                return;
            }
            Toast.makeText(this, "Đã lưu nội dung công thức", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Frame32PrivacyActivity.class));
        });
    }
}
