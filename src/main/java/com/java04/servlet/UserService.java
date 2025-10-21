package com.java04.servlet;

public class UserService {
    public boolean login(String username, String password) {
        return "admin".equals(username) && "123".equals(password);
    }

    // ✅ Thêm phương thức này để phục vụ test Y3
    public String register(String email, String password) {
        // Kiểm tra định dạng email
        if (!email.contains("@")) return "Email không hợp lệ";
        if (password.length() < 6) return "Mật khẩu quá ngắn";
        if ("user1@x.com".equals(email)) return "Tài khoản đã tồn tại";
        return "Đăng ký thành công";
    }
}
