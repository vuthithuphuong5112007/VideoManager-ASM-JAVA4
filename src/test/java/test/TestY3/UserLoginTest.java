package test.TestY3;

import com.java04.service.MockUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserLoginTest {
    private MockUserService service;

    @Before
    public void setUp() {
        service = new MockUserService();
    }

    @After
    public void tearDown() {
        service = null;
    }

    @Test
    public void TC01_login_invalid_email_format() {
        String email = "abcgmail.com"; // sai định dạng
        String password = "123456";
        String actual = service.login(email, password);
        String expected = "Email không hợp lệ";
        assertEquals(expected, actual);
        System.out.println("TC01_login_invalid_email_format → PASS");
    }

    @Test
    public void TC02_login_wrong_password() {
        String email = "abc@gmail.com";
        String password = "sai_pass";
        String actual = service.login(email, password);
        String expected = "Sai mật khẩu";
        assertEquals(expected, actual);
        System.out.println("TC02_login_wrong_password → PASS");
    }

    @Test
    public void TC03_login_success() {
        String email = "abc@gmail.com";
        String password = "123456";
        String actual = service.login(email, password);
        String expected = "Đăng nhập thành công";
        assertEquals(expected, actual);
        System.out.println("TC03_login_success → PASS");
    }
}
