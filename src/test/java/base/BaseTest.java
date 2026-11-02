package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ChromeOptionsFactory;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(description = "This is BeforeMethod to Set up driver correctly and maximize window")
    public void setUp() {
        driver = new ChromeDriver(
                ChromeOptionsFactory.withoutAnnoyingPopups()
        );
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true, description = "This is AfterMethod to quit driver that will always run even in failed test cases to quit driver.")
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

    }

}
