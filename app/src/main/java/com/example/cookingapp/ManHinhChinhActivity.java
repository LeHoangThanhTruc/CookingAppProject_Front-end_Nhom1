package com.example.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhChinhActivity extends AppCompatActivity implements OnCommentCountChangeListener {

    private ImageView btnNotification, btnFilter;
    private LinearLayout btnQuickSearch, btnQuickProfile, btnQuickSettings;

    private RecyclerView rvPostsFeed;
    private PostAdapter postAdapter;
    private List<Post> postList = new ArrayList<>();

    // 1. KHAI BÁO LAUNCHER HỨNG DỮ LIỆU TỪ POSTDETAILACTIVITY TRỞ VỀ
    private final ActivityResultLauncher<Intent> postDetailLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    int postId = result.getData().getIntExtra("POST_ID", -1);
                    int newCommentCount = result.getData().getIntExtra("NEW_COMMENT_COUNT", -1);
                    int newLikeCount = result.getData().getIntExtra("NEW_LIKE_COUNT", -1);
                    boolean isLiked = result.getData().getBooleanExtra("IS_LIKED", false);

                    if (postId != -1 && postAdapter != null) {
                        // Cập nhật trực tiếp số liệu dòng bài viết đó trong Adapter Newsfeed
                        postAdapter.updatePostStats(postId, newCommentCount, newLikeCount, isLiked);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_chinh);

        initViews();
        setupRecyclerView();
        setupEvents();

        loadFeedFromBackend("ALL");
    }

    private void initViews() {
        btnNotification = findViewById(R.id.btnNotification);
        btnFilter = findViewById(R.id.btnFilter);
        btnQuickSearch = findViewById(R.id.btnQuickSearch);
        btnQuickProfile = findViewById(R.id.btnQuickProfile);
        btnQuickSettings = findViewById(R.id.btnQuickSettings);
        rvPostsFeed = findViewById(R.id.rvPostsFeed);
    }

    private void setupRecyclerView() {
        rvPostsFeed.setLayoutManager(new LinearLayoutManager(this));
        // Đảm bảo truyền postDetailLauncher vào PostAdapter nếu cần mở chi tiết bài viết
        postAdapter = new PostAdapter(this, postList, postDetailLauncher);
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

        if (btnFilter != null) {
            btnFilter.setOnClickListener(this::showFilterPopupMenu);
        }
    }

    private void showFilterPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add(0, 1, 0, "Tất cả");
        popup.getMenu().add(0, 2, 1, "Bài viết thường");
        popup.getMenu().add(0, 3, 2, "Công thức");

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

    private void loadFeedFromBackend(String postType) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getFeed(postType).enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postList.clear();
                    postList.addAll(response.body().getData());
                    postAdapter.notifyDataSetChanged();
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

    // 2. CẬP NHẬT TRỰC TIẾP KHI ĐĂNG COMMENT TRONG BOTTOM SHEET Ở MÀN HÌNH CHÍNH
    @Override
    public void onCommentCountChanged(int postId, int newCount) {
        if (postAdapter != null) {
            postAdapter.updateCommentCount(postId, newCount);
        }
    }

    // Getter cho Launcher để PostAdapter có thể gọi mở PostDetailActivity
    public ActivityResultLauncher<Intent> getPostDetailLauncher() {
        return postDetailLauncher;
    }
}