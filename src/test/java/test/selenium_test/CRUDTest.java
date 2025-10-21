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
public class CRUDTest {
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
        driver.findElement(By.name("fullname")).sendKeys("Trần Văn C");
        driver.findElement(By.name("username")).sendKeys("admin@example.com");
        driver.findElement(By.name("password")).sendKeys("admin123");

        // Submit form
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Chờ URL đổi (nếu có redirect sau khi đăng ký)
        Thread.sleep(2000); // nghỉ 2s để form kịp submit
        System.out.println("✅ Đăng ký thành công!");
    }

    @Test(dependsOnMethods = "testRegister")
    public void testLoginSuccess() throws InterruptedException{
        driver.get("http://localhost:8080/Ass1_VuThiThuPhuong_war/login");

        // Đợi form login hiển thị
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));

        // Nhập thông tin đăng nhập
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("username")).sendKeys("admin@example.com");
        driver.findElement(By.id("password")).sendKeys("admin123");

        // Nhấn nút đăng nhập
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Kiểm tra sau khi đăng nhập
        wait.until(ExpectedConditions.urlContains("/user-management"));
        System.out.println("✅ Đăng nhập thành công, URL hiện tại: " + driver.getCurrentUrl());
        Thread.sleep(3000);
    }

    @Test(dependsOnMethods = "testLoginSuccess")
    public void testResetVideoForm() throws InterruptedException {
        // Mở trang edit có dữ liệu sẵn
        driver.get("http://localhost:8080/Ass1_VuThiThuPhuong_war/admin/video/edit/vid001");

        // Đợi form hiển thị
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("form")));

        // Tìm nút Reset
        WebElement resetButton = driver.findElement(
                By.xpath("//a[contains(@href, '/admin/video/reset')]")
        );

        // Cuộn xuống đến nút
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", resetButton);
        // Nghỉ 2 giây cho cuộn mượt
        Thread.sleep(2000);

        // Click bằng JavaScript để tránh bị intercept
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", resetButton);

        // Chờ URL đổi sang /reset
        wait.until(ExpectedConditions.urlContains("/admin/video/reset"));
        // Đợi thêm 3 giây để nhìn thấy trang sau reset
        Thread.sleep(3000);
        System.out.println("✅ Đã nhấn Reset thành công, URL hiện tại: " + driver.getCurrentUrl());

    }



    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
