package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait driverWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public void click(By locator) {
        driverWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void type(By locator, String text) {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);

    }

    public String getText(By locator) {
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public abstract By marker();

    public boolean isOpened(int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(marker()));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
