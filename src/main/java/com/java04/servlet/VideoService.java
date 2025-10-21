package com.java04.servlet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VideoService {
    // Lưu danh sách user đã like theo từng video
    // key = videoId, value = danh sách userId đã like
    private final Map<String, Set<String>> likeMap = new HashMap<>();

    /**
     * Thêm lượt thích cho video.
     * @param userId id người dùng
     * @param videoId id video
     * @return true nếu thêm mới thành công, false nếu đã like trước đó
     */
    public boolean likeVideo(String userId, String videoId) {
        if (userId == null || userId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false; // Dữ liệu không hợp lệ
        }

        // Lấy danh sách user đã like video này
        Set<String> users = likeMap.computeIfAbsent(videoId, k -> new HashSet<>());

        // Nếu user đã like rồi thì không cho like lại
        if (users.contains(userId)) {
            return false;
        }

        // Thêm user vào danh sách like
        users.add(userId);
        return true;
    }

    /**
     * Đếm tổng số lượt thích của video
     * @param videoId id video
     * @return số lượt thích
     */
    public int countLikes(String videoId) {
        Set<String> users = likeMap.get(videoId);
        return (users == null) ? 0 : users.size();
    }

    /**
     * Xóa toàn bộ dữ liệu (dùng cho test)
     */
    public void clearAll() {
        likeMap.clear();
    }
}
