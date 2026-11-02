package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhChinhActivity extends AppCompatActivity {

    private ImageView btnNotification, btnFilter;
    private LinearLayout btnQuickSearch, btnQuickProfile, btnQuickSettings;

    // Khai báo RecyclerView và Adapter
    private RecyclerView rvPostsFeed;
    private PostAdapter postAdapter;
    private List<Post> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_chinh);

        initViews();
        setupRecyclerView();
        setupEvents();

        // Nạp dữ liệu từ Backend SQLite lần đầu (Lấy tất cả bài viết)
        loadFeedFromBackend("ALL");
    }

    private void initViews() {
        btnNotification = findViewById(R.id.btnNotification);
        btnFilter = findViewById(R.id.btnFilter); // Icon bộ lọc
        btnQuickSearch = findViewById(R.id.btnQuickSearch);
        btnQuickProfile = findViewById(R.id.btnQuickProfile);
        btnQuickSettings = findViewById(R.id.btnQuickSettings);
        rvPostsFeed = findViewById(R.id.rvPostsFeed);
    }

    private void setupRecyclerView() {
        rvPostsFeed.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this, postList);
        rvPostsFeed.setAdapter(postAdapter);
    }

    private void setupEvents() {
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v -> startActivity(new Intent(this, Frame33NotificationsActivity.class)));
        }

        if (btnQuickSearch != null) {
            btnQuickSearch.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));
        }

        if (btnQuickProfile != null) {
            btnQuickProfile.setOnClickListener(v -> startActivity(new Intent(this, Frame25MyProfileActivity.class)));
        }

        if (btnQuickSettings != null) {
            btnQuickSettings.setOnClickListener(v -> startActivity(new Intent(this, Frame7SettingActivity.class)));
        }

        // Sự kiện bấm nút Filter (Bộ lọc)
        if (btnFilter != null) {
            btnFilter.setOnClickListener(v -> showFilterPopupMenu(v));
        }
    }

    // Hiển thị Menu lọc bài đăng tiếng Việt
    private void showFilterPopupMenu(android.view.View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add(0, 1, 0, "Tất cả");          // ALL
        popup.getMenu().add(0, 2, 1, "Bài viết thường"); // REGULAR
        popup.getMenu().add(0, 3, 2, "Công thức");       // RECIPE

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 1: loadFeedFromBackend("ALL"); break;
                case 2: loadFeedFromBackend("REGULAR"); break;
                case 3: loadFeedFromBackend("RECIPE"); break;
            }
            return true;
        });
        popup.show();
    }

    // Gọi API FastAPI từ Backend
    private void loadFeedFromBackend(String postType) {
        // Khởi tạo ApiService từ ApiClient đã có của bạn
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getFeed(postType).enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postList.clear();
                    postList.addAll(response.body().getData());
                    postAdapter.notifyDataSetChanged(); // Cập nhật lại giao diện RecyclerView tự động
                } else {
                    Toast.makeText(ManHinhChinhActivity.this, "Không lấy được dữ liệu bài đăng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                Toast.makeText(ManHinhChinhActivity.this, "Lỗi kết nối Server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}