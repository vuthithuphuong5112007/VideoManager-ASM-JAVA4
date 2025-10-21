package com.java04.service;

public class MockUserService {
    public String login(String email, String password) {
        // Giả lập kiểm tra định dạng email
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            return "Email không hợp lệ";
        }

        // Giả lập tài khoản hợp lệ
        if (email.equals("abc@gmail.com") && password.equals("123456")) {
            return "Đăng nhập thành công";
        }

        // Nếu email đúng nhưng pass sai
        if (email.equals("abc@gmail.com") && !password.equals("123456")) {
            return "Sai mật khẩu";
        }

        // Mặc định
        return "Tài khoản không tồn tại";
    }
}
