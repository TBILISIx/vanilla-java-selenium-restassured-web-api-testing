package basetest;

import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    @BeforeMethod(description = "This is BeforeMethod to Set up driver correctly and maximize window")
    public void setUp() {
        DriverManager.createDriver();
    }

    @AfterMethod(alwaysRun = true, description = "This is AfterMethod to quit driver that will always run even in failed test cases to quit driver.")
    public void tearDown() {

        DriverManager.quitDriver();

    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

}
