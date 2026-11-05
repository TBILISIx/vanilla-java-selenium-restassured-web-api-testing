package driver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ScreenshotUtils {

    private static final Path SCREENSHOT_DIR = Path.of("target", "screenshots");

    private ScreenshotUtils() {
    }

    public static Path takeScreenshot(WebDriver driver, String name) {
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            Path source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Path target = SCREENSHOT_DIR.resolve(name + "_" + System.nanoTime() + ".png");
            Files.copy(source, target);
            return target;
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save screenshot", e);
        }
    }

}