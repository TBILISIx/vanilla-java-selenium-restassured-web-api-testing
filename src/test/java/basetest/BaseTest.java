package basetest;

import basepage.BasePage;
import basepage.PageObjectFactory;
import driver.DriverManager;
import driver.ScreenshotUtils;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

// $env:Path += ";C:\Program Files\allure-2.46.1\bin"
//allure --version
//allure serve target/allure-results

// allure generate target/allure-results --clean -o target/allure-report
//allure open target/allure-report
//The first line saves a permanent report folder, and the second opens it. This is useful if you want to zip the report and send it to someone.

public abstract class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod(description = "This is BeforeMethod to Set up driver correctly and maximize window")
    public void setUp() {
        DriverManager.createDriver();
    }

    // Modified teardown method with helper and logger to take screenshots and log them
    @AfterMethod(alwaysRun = true, description = "Save failure evidence, Then @AfterMethod always close this test's browser even in failed test cases / quits driver")
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                captureFailureEvidence(result);
            }
        } finally {
            DriverManager.quitDriver();
        }
    }

    private void captureFailureEvidence(ITestResult result) {
        try {
            WebDriver driver = getDriver();
            Path screenshot = ScreenshotUtils.takeScreenshot(driver, result.getName());
            logger.error("Test '{}' failed. URL: {}. Screenshot: {}",
                    result.getName(), driver.getCurrentUrl(), screenshot);

            Allure.addAttachment("Failure screenshot", "image/png",
                    new ByteArrayInputStream(Files.readAllBytes(screenshot)), "png");
        } catch (Exception e) {
            logger.warn("Could not capture failure evidence: {}", e.getMessage());
        }
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    // This helper  connects the factory to the current test’s driver.
    // Add imports for BasePage and PageObjectFactory.
    protected <T extends BasePage> T initializePage(Class<T> pageClass) {
        return PageObjectFactory.create(pageClass, getDriver());
    }

}