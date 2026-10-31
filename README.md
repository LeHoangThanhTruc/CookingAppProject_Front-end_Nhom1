# ĐỀ TÀI: ỨNG DỤNG NẤU ĂN VỚI BỘ LỌC VÀ LIÊN KẾT MẠNG XÃ HỘI
## Danh sách thành viên nhóm 1:
- **Lê Hoàng Thanh Trúc** - 24521868
- **Huỳnh Thị Như Hiếu** - 24520489
- **Nguyễn Phan Thanh Thủy** - 23521558

## Cấu hình User Profile API

- Debug trên Android Emulator mặc định gọi `http://10.0.2.2:8000/`.
- Có thể đổi backend khi build bằng `-PAPI_BASE_URL=https://api.example.com/`.
- Release build bắt buộc truyền một URL HTTPS.
- Sau khi đăng nhập thành công, module auth cần gọi
  `SessionManager.saveAccessToken(context, accessToken)`. Token phải là JWT HS256 có
  claim `sub` chứa `user_id` và có thời hạn `exp`.
