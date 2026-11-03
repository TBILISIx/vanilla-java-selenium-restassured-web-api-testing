package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


// Driver Manager that provides a separate WebDriver instance per thread,
// allowing tests to execute in parallel using independent browser sessions.

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void createDriver() {
        WebDriver driver = new ChromeDriver(ChromeOptionsFactory.withoutAnnoyingPopups());
        DRIVER.set(driver);
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "No active WebDriver for thread " + Thread.currentThread().getName()
                            + ". Was createDriver() called?");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DRIVER.remove();
        }
    }
}