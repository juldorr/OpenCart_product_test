package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;

public class OpenCartProductTest {
    private static final String BASE_URL = "https://demo.opencart.com/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    public static void tear() {
        if (driver != null) {
            driver.quit();
        }
        System.out.println("@AfterAll executed");
    }

    @BeforeEach
    public void setUpThis() {
        driver.get(BASE_URL);
        System.out.println("\t@BeforeEach executed");
    }

    @AfterEach
    public void tearThis() throws InterruptedException {
        Thread.sleep(3000); // for presentation
        System.out.println("\t@AfterEach executed");
    }

    @Test
    public void testUI() throws InterruptedException {
        WebElement currency = driver.findElement(By.xpath("//span[text() = 'Currency']"));
        currency.click();

        WebElement euro = driver.findElement(By.xpath("//a[text() = '€ Euro']"));
        euro.click();

        WebElement desktopsMenu = driver.findElement(By.xpath("//a[text()='Desktops']"));
        desktopsMenu.click();

        WebElement macInMenu = driver.findElement(By.xpath("//a[text()='Mac (1)']"));
        macInMenu.click();

        WebElement price = driver.findElement(By.xpath("//a[text() = 'iMac']/../..//span[@class='price-new']"));

        Thread.sleep(2000); // for presentation
        Actions action = new Actions(driver);
        action.moveToElement(price).perform();

        Assertions.assertTrue(price.getText().contains("111.55€"), "the price of " + price.getText() + " euros is present on the page");

    }
}
