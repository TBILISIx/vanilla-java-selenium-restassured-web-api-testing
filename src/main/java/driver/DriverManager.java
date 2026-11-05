package driver;

import config.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

// Driver Manager that provides a separate WebDriver instance per thread,
// allowing tests to execute in parallel using independent browser sessions.

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void createDriver() {
        String browser = Configuration.getRequired("browser").toLowerCase();
        boolean headless = Configuration.getRequiredBoolean("headless");

        WebDriver driver = switch (browser) {
            case "chrome" -> createChrome(headless);
            case "firefox" -> createFirefox(headless);
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser + ". Use chrome or firefox.");
        };

        DRIVER.set(driver);
        driver.manage().window().maximize();
    }

    private static WebDriver createChrome(boolean headless) {
        ChromeOptions options = ChromeOptionsFactory.withoutAnnoyingPopups();
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
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