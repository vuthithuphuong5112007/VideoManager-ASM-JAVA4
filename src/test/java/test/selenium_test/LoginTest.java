package test.selenium_test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Thu Phuong\\Downloads\\VideoManager-ASM-JAVA4\\src\\test\\resources\\drivers\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testRegister() throws InterruptedException {
        driver.get("http://localhost:8080/Ass1_VuThiThuPhuong_war/logup");

        // Đợi form login hiển thị
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("fullname")));

        // Điền form đăng ký
        driver.findElement(By.name("fullname")).sendKeys("Nguyễn Văn A");
        driver.findElement(By.name("username")).sendKeys("user01@example.com");
        driver.findElement(By.name("password")).sendKeys("123456");

        // Submit form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        System.out.println("✅ Đăng ký thành công!");
        Thread.sleep(3000); // đợi chuyển trang
    }

    @Test(dependsOnMethods = "testRegister")
    public void testLoginSuccess() throws InterruptedException{
        driver.get("http://localhost:8080/Ass1_VuThiThuPhuong_war/login");

        // Đợi form login hiển thị
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));

        // Nhập thông tin đăng nhập
        driver.findElement(By.id("username")).sendKeys("user01@example.com");
        driver.findElement(By.id("password")).sendKeys("123456");

        // Nhấn nút đăng nhập
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Kiểm tra sau khi đăng nhập
        wait.until(ExpectedConditions.urlContains("/home"));
        System.out.println("✅ Đăng nhập thành công, URL hiện tại: " + driver.getCurrentUrl());
        Thread.sleep(3000);
    }

    @Test(dependsOnMethods = "testLoginSuccess")
    public void testViewVideoDetail() throws InterruptedException {
        // Mở trang chi tiết video
        driver.get("http://localhost:8080/Ass1_VuThiThuPhuong_war/videochitiet?videoId=vid010");

        // Đợi 5 giây để xem trang
        Thread.sleep(5000);

        System.out.println("✅ Đã mở trang chi tiết video thành công!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
