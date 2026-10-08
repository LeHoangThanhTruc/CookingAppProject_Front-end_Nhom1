package com.example.cookingapp;

final class FrameRoutes {
    static final FrameDefinition[] FRAMES = {
            new FrameDefinition(24, "Trang của người khác", "Frame24OtherProfileActivity"),
            new FrameDefinition(25, "Trang của tôi", "Frame25MyProfileActivity"),
            new FrameDefinition(26, "Giới thiệu bản thân", "Frame26BioActivity"),
            new FrameDefinition(27, "Danh Sách Bạn Bè", "Frame27FriendsActivity"),
            new FrameDefinition(28, "Danh Sách Người Theo Dõi", "Frame28FollowingActivity"),
            new FrameDefinition(29, "Tin Nhắn", "Frame29MessagesActivity"),
            new FrameDefinition(30, "Giao Diện Nhắn Tin", "Frame30ChatDetailActivity"),
            new FrameDefinition(31, "Tạo công thức", "Frame31CreateRecipeActivity"),
            new FrameDefinition(32, "Quyền Xem", "Frame32PrivacyActivity"),
            new FrameDefinition(33, "Thông Báo", "Frame33NotificationsActivity")
    };

    private FrameRoutes() {
    }
}
