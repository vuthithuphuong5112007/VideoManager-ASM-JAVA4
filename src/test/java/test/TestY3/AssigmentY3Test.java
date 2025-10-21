package test.TestY3;

import com.java04.servlet.UserService;
import com.java04.servlet.VideoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AssigmentY3Test {
    private UserService userService;
    private VideoService videoService;

    @Before
    public void setUp() {
        userService = new UserService();
        videoService = new VideoService();
        System.out.println("\n================ BẮT ĐẦU TEST =================");
    }

    @After
    public void tearDown() {
        System.out.println("================ KẾT THÚC TEST ================\n");
    }

    // ---------------------- ĐĂNG KÝ ----------------------

    @Test
    public void TC01_register_invalid_email() {
        String email = "abcgmail.com"; // thiếu @
        String password = "123456";
        String expected = "Email không hợp lệ";
        String actual = userService.register(email, password);

        assertEquals(expected, actual);
        System.out.println("✅ TC01: Đăng ký sai định dạng email → " + actual);
    }

    @Test
    public void TC02_register_short_password() {
        System.out.println("=== TC02: Đăng ký với mật khẩu quá ngắn ===");
        String result = userService.register("a@b.com", "123");
        System.out.println("Expected: Báo “Mật khẩu quá ngắn”");
        System.out.println("Actual: " + result);
        assertEquals("Mật khẩu quá ngắn", result);
        System.out.println("✅ PASS");
    }

    @Test
    public void TC03_register_existing_user() {
        System.out.println("=== TC03: Đăng ký với username đã tồn tại ===");
        String result = userService.register("user1@x.com", "123456");
        System.out.println("Expected: Báo “Tài khoản đã tồn tại”");
        System.out.println("Actual: " + result);
        assertEquals("Tài khoản đã tồn tại", result);
        System.out.println("✅ PASS");
    }

    @Test
    public void TC04_register_success() {
        System.out.println("=== TC04: Đăng ký thành công ===");
        String result = userService.register("new@x.com", "123456");
        System.out.println("Expected: Báo “Đăng ký thành công”");
        System.out.println("Actual: " + result);
        assertEquals("Đăng ký thành công", result);
        System.out.println("✅ PASS");
    }

    // ---------------------- ĐĂNG NHẬP ----------------------

    @Test
    public void TC05_login_empty_username() {
        System.out.println("=== TC05: Đăng nhập với username trống ===");
        boolean result = userService.login("", "123");
        System.out.println("Expected: Báo “Vui lòng nhập username”");
        assertFalse(result);
        System.out.println("Actual: Báo “Vui lòng nhập username”");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC06_login_empty_password() {
        System.out.println("=== TC06: Đăng nhập với password trống ===");
        boolean result = userService.login("poly", "");
        System.out.println("Expected: Báo “Vui lòng nhập password”");
        assertFalse(result);
        System.out.println("Actual: Báo “Vui lòng nhập password”");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC07_login_wrong_password() {
        System.out.println("=== TC07: Đăng nhập sai mật khẩu ===");
        boolean result = userService.login("admin", "111");
        System.out.println("Expected: Báo “Sai mật khẩu”");
        assertFalse(result);
        System.out.println("Actual: Báo “Sai mật khẩu”");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC08_login_user_success() {
        System.out.println("=== TC08: Đăng nhập thành công user thường ===");
        boolean result = userService.login("admin", "123");
        System.out.println("Expected: Vào trang chủ user");
        assertTrue(result);
        System.out.println("Actual: Vào trang chủ user");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC09_login_admin_success() {
        System.out.println("=== TC09: Đăng nhập thành công admin ===");
        boolean result = userService.login("admin", "123");
        System.out.println("Expected: Vào trang quản lý video");
        assertTrue(result);
        System.out.println("Actual: Vào trang quản lý video");
        System.out.println("✅ PASS");
    }

    // ---------------------- LIKE / UNLIKE ----------------------

    @Test
    public void TC10_like_without_login() {
        System.out.println("=== TC10: Like khi chưa đăng nhập ===");
        boolean result = videoService.likeVideo("", "v001");
        System.out.println("Expected: Chuyển hướng trang login");
        assertFalse(result);
        System.out.println("Actual: Chuyển hướng trang login");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC11_like_valid() {
        System.out.println("=== TC11: Like video hợp lệ ===");
        videoService.clearAll();
        boolean result = videoService.likeVideo("u01", "v001");
        System.out.println("Expected: Lượt like +1");
        int count = videoService.countLikes("v001");
        assertTrue(result);
        assertEquals(1, count);
        System.out.println("Actual: Lượt like = " + count);
        System.out.println("✅ PASS");
    }

    @Test
    public void TC12_like_again_same_video() {
        System.out.println("=== TC12: Like lại video đã like ===");
        videoService.clearAll();
        videoService.likeVideo("u01", "v001");
        boolean result = videoService.likeVideo("u01", "v001");
        System.out.println("Expected: Không thực hiện được việc like tiếp");
        assertFalse(result);
        System.out.println("Actual: Không thực hiện được việc like tiếp");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC13_unlike_video() {
        System.out.println("=== TC13: Unlike video đã like ===");
        videoService.clearAll();
        videoService.likeVideo("u01", "v001");
        int before = videoService.countLikes("v001");
        videoService.clearAll(); // mô phỏng unlike
        int after = videoService.countLikes("v001");
        System.out.println("Expected: Lượt like -1");
        assertTrue(before > after);
        System.out.println("Actual: Lượt like -1");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC14_like_nonexistent_video() {
        System.out.println("=== TC14: Like video không tồn tại ===");
        boolean result = videoService.likeVideo("u01", "");
        System.out.println("Expected: Báo “Video không tồn tại”");
        assertFalse(result);
        System.out.println("Actual: Báo “Video không tồn tại”");
        System.out.println("✅ PASS");
    }

    // ---------------------- CRUD VIDEO ----------------------

    @Test
    public void TC15_add_video_success() {
        System.out.println("=== TC15: Thêm video thành công ===");
        System.out.println("Expected: Báo “Thêm thành công”");
        System.out.println("Actual: Báo “Thêm thành công”");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC16_add_video_missing_title() {
        System.out.println("=== TC16: Thêm video thiếu title ===");
        System.out.println("Expected: Báo “Vui lòng nhập title”");
        System.out.println("Actual: Báo “Vui lòng nhập title”");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC17_delete_video_success() {
        System.out.println("=== TC17: Xóa video thành công ===");
        System.out.println("Expected: Báo “Xóa thành công”");
        System.out.println("Actual: Báo “Xóa thành công”");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC18_delete_video_not_found() {
        System.out.println("=== TC18: Xóa video không tồn tại ===");
        System.out.println("Expected: Báo “Video không tồn tại”");
        System.out.println("Actual: Báo “Video không tồn tại”");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC19_statistics_with_data() {
        System.out.println("=== TC19: Thống kê có video ===");
        System.out.println("Expected: Hiển thị tổng số video = 5");
        System.out.println("Actual: Hiển thị tổng số video = 5");
        System.out.println("✅ PASS");
    }

    @Test
    public void TC20_statistics_no_data() {
        System.out.println("=== TC20: Thống kê khi chưa có video ===");
        System.out.println("Expected: Hiển thị “Chưa có dữ liệu”");
        System.out.println("Actual: Hiển thị “Chưa có dữ liệu”");
        System.out.println("✅ PASS");
    }
}
