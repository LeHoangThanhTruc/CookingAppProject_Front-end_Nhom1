package com.example.cookingapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private int postId;
    private RecyclerView rvComments;
    private LinearLayout layoutEmptyState, layoutReplyingState;
    private RelativeLayout layoutPreviewMedia;
    private ImageView imgPreviewCommentMedia, btnRemovePreviewMedia, btnPickImage, btnSendComment;
    private EditText edtCommentInput;
    private TextView tvReplyingTo, btnCancelReply;

    private Uri selectedMediaUri = null;
    private String selectedMediaType = "IMAGE";
    private Integer currentReplyParentId = null;

    // 1. BỔ SUNG BIẾN CHỨA TRẠNG THÁI CHỈNH SỬA BÌNH LUẬN
    private int editingCommentId = -1;
    private boolean isEditMode = false;

    private ActivityResultLauncher<String> mediaPickerLauncher;
    private int targetId;
    private boolean isMediaComment = false;

    public CommentBottomSheetDialogFragment(int postId) {
        this.targetId = postId;
        this.isMediaComment = false;
    }

    public CommentBottomSheetDialogFragment(int targetId, boolean isMediaComment) {
        this.targetId = targetId;
        this.isMediaComment = isMediaComment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedMediaUri = uri;
                        displaySelectedMediaPreview(uri);
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_comment_bottom_sheet, container, false);

        rvComments = view.findViewById(R.id.rvComments);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        layoutReplyingState = view.findViewById(R.id.layoutReplyingState);
        layoutPreviewMedia = view.findViewById(R.id.layoutPreviewMedia);

        imgPreviewCommentMedia = view.findViewById(R.id.imgPreviewCommentMedia);
        btnRemovePreviewMedia = view.findViewById(R.id.btnRemovePreviewMedia);
        btnPickImage = view.findViewById(R.id.btnPickImage);
        btnSendComment = view.findViewById(R.id.btnSendComment);
        edtCommentInput = view.findViewById(R.id.edtCommentInput);
        tvReplyingTo = view.findViewById(R.id.tvReplyingTo);
        btnCancelReply = view.findViewById(R.id.btnCancelReply);

        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));

        btnPickImage.setOnClickListener(v -> mediaPickerLauncher.launch("*/*"));

        btnRemovePreviewMedia.setOnClickListener(v -> {
            selectedMediaUri = null;
            if (layoutPreviewMedia != null) layoutPreviewMedia.setVisibility(View.GONE);
            checkSendButtonState();
        });

        edtCommentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkSendButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSendComment.setOnClickListener(v -> handleSendComment());

        btnCancelReply.setOnClickListener(v -> resetCommentInputState());

        loadCommentsFromApi();
        return view;
    }

    private void displaySelectedMediaPreview(Uri uri) {
        String mimeType = requireContext().getContentResolver().getType(uri);
        if (mimeType != null && mimeType.startsWith("video")) {
            selectedMediaType = "VIDEO";
        } else {
            selectedMediaType = "IMAGE";
        }

        if (layoutPreviewMedia != null && imgPreviewCommentMedia != null) {
            layoutPreviewMedia.setVisibility(View.VISIBLE);
            Glide.with(this).load(uri).into(imgPreviewCommentMedia);
        }
        checkSendButtonState();
    }

    private void checkSendButtonState() {
        boolean hasText = edtCommentInput.getText().toString().trim().length() > 0;
        boolean hasMedia = selectedMediaUri != null;

        if (hasText || hasMedia) {
            btnSendComment.setImageResource(R.drawable.send_blue);
            btnSendComment.setEnabled(true);
        } else {
            btnSendComment.setImageResource(R.drawable.send_grey);
            btnSendComment.setEnabled(false);
        }
    }

    private void handleSendComment() {
        String content = edtCommentInput.getText().toString().trim();

        if (selectedMediaUri != null) {
            uploadMediaAndPostComment(content);
        } else {
            sendCommentToBackend(content, null, null);
        }
    }

    private void uploadMediaAndPostComment(String content) {
        try {
            InputStream is = requireContext().getContentResolver().openInputStream(selectedMediaUri);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();

            String mimeType = "VIDEO".equals(selectedMediaType) ? "video/mp4" : "image/jpeg";
            String fileName = "VIDEO".equals(selectedMediaType) ? "comment_video.mp4" : "comment_img.jpg";

            RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), bytes);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestFile);

            ApiService apiService = ApiClient.getAuthenticatedClient(requireContext()).create(ApiService.class);
            apiService.uploadMedia(body).enqueue(new Callback<UploadResponse>() {
                @Override
                public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String uploadedUrl = response.body().getMediaUrl();
                        sendCommentToBackend(content, uploadedUrl, selectedMediaType);
                    } else {
                        Toast.makeText(getContext(), "Không thể tải tệp lên!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UploadResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Lỗi tải tệp lên!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. CẬP NHẬT HÀM GỬI BÌNH LUẬN (HỖ TRỢ CẢ TẠO MỚI VÀ EDIT)
    private void sendCommentToBackend(String content, String mediaUrl, String mediaType) {
        btnSendComment.setEnabled(false);
        ApiService apiService = ApiClient.getAuthenticatedClient(requireContext()).create(ApiService.class);

        // NẾU ĐANG TRONG CHẾ ĐỘ CHỈNH SỬA (EDIT)
        if (isEditMode && editingCommentId != -1) {
            CommentEditRequest editReq = new CommentEditRequest(1, content);
            apiService.editComment(editingCommentId, editReq).enqueue(new Callback<CommentResponse>() {
                @Override
                public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(getContext(), "Đã cập nhật bình luận!", Toast.LENGTH_SHORT).show();

                        // Báo số lượng comment chuẩn từ DB về Activity khi Edit thành công
                        int newCommentCount = response.body().getCommentCount();
                        if (getActivity() instanceof OnCommentCountChangeListener && newCommentCount > 0) {
                            OnCommentCountChangeListener listener = (OnCommentCountChangeListener) getActivity();
                            if (isMediaComment) {
                                listener.onMediaCommentCountChanged(targetId, newCommentCount);
                            } else {
                                listener.onCommentCountChanged(targetId, newCommentCount);
                            }
                        }

                        resetCommentInputState();
                        loadCommentsFromApi();
                    } else {
                        btnSendComment.setEnabled(true);
                        Toast.makeText(getContext(), "Không thể cập nhật bình luận!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CommentResponse> call, Throwable t) {
                    btnSendComment.setEnabled(true);
                    Toast.makeText(getContext(), "Lỗi kết nối máy chủ!", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        // NẾU LÀ ĐĂNG BÌNH LUẬN MỚI (GIỮ NGUYÊN CODE CỦA BẠN)
        CommentRequest req = new CommentRequest(1, content, currentReplyParentId, mediaUrl, mediaType);
        Call<CommentResponse> call = isMediaComment ?
                apiService.addMediaComment(targetId, req) :
                apiService.addComment(targetId, req);

        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int newCommentCount = response.body().getCommentCount();

                    if (getActivity() instanceof OnCommentCountChangeListener) {
                        OnCommentCountChangeListener listener = (OnCommentCountChangeListener) getActivity();
                        if (isMediaComment) {
                            listener.onMediaCommentCountChanged(targetId, newCommentCount);
                        } else {
                            listener.onCommentCountChanged(targetId, newCommentCount);
                        }
                    }

                    resetCommentInputState();
                    loadCommentsFromApi();
                } else {
                    btnSendComment.setEnabled(true);
                    Toast.makeText(getContext(), "Không thể gửi bình luận!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                btnSendComment.setEnabled(true);
                Toast.makeText(getContext(), "Lỗi kết nối Server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 3. CẬP NHẬT DỌN DẸP TRẠNG THÁI KHI XÓA/SỬA/HỦY
    private void resetCommentInputState() {
        edtCommentInput.setText("");
        currentReplyParentId = null;
        selectedMediaUri = null;
        selectedMediaType = "IMAGE";

        // Reset trạng thái sửa
        editingCommentId = -1;
        isEditMode = false;

        if (layoutPreviewMedia != null) layoutPreviewMedia.setVisibility(View.GONE);
        if (imgPreviewCommentMedia != null) imgPreviewCommentMedia.setImageDrawable(null);
        if (layoutReplyingState != null) layoutReplyingState.setVisibility(View.GONE);

        checkSendButtonState();
    }

    // 4. CHUYỂN HAM LOAD API THÀNH PUBLIC ĐỂ COMMENTADAPTER GỌI KHI XÓA SUCCESS
    public void loadCommentsFromApi() {
        ApiService apiService = ApiClient.getAuthenticatedClient(requireContext()).create(ApiService.class);
        Call<CommentListResponse> call = isMediaComment ?
                apiService.getMediaComments(targetId, 1) :
                apiService.getComments(targetId, 1);

        call.enqueue(new Callback<CommentListResponse>() {
            @Override
            public void onResponse(Call<CommentListResponse> call, Response<CommentListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    var comments = response.body().getComments();
                    if (comments == null || comments.isEmpty()) {
                        layoutEmptyState.setVisibility(View.VISIBLE);
                        rvComments.setVisibility(View.GONE);
                    } else {
                        layoutEmptyState.setVisibility(View.GONE);
                        rvComments.setVisibility(View.VISIBLE);
                        CommentAdapter adapter = new CommentAdapter(getContext(), comments, CommentBottomSheetDialogFragment.this);
                        rvComments.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentListResponse> call, Throwable t) {}
        });
    }

    // 5. BỔ SUNG HÀM KÍCH HOẠT CHẾ ĐỘ EDIT KHI BẤM NÚT SỬA BÌNH LUẬN
    public void startEditMode(int commentId, String currentContent) {
        this.editingCommentId = commentId;
        this.isEditMode = true;

        if (layoutReplyingState != null && tvReplyingTo != null) {
            tvReplyingTo.setText("Đang chỉnh sửa bình luận");
            layoutReplyingState.setVisibility(View.VISIBLE);
        }

        if (edtCommentInput != null) {
            edtCommentInput.setText(currentContent);
            edtCommentInput.requestFocus();
            edtCommentInput.setSelection(edtCommentInput.getText().length());
        }
    }

    public void startReplyMode(int parentCommentId, String authorName) {
        this.currentReplyParentId = parentCommentId;
        this.isEditMode = false;

        tvReplyingTo.setText("Đang phản hồi " + authorName);
        layoutReplyingState.setVisibility(View.VISIBLE);

        String tag = authorName + " ";
        SpannableString spannable = new SpannableString(tag);

        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#2E7D32")),
                0, tag.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannable.setSpan(
                new StyleSpan(Typeface.BOLD),
                0, tag.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        edtCommentInput.setText(spannable);
        edtCommentInput.setSelection(edtCommentInput.getText().length());
        edtCommentInput.requestFocus();
    }
}