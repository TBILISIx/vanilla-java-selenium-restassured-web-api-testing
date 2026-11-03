package driver;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public final class ChromeOptionsFactory {

    private ChromeOptionsFactory() {
    }

    public static ChromeOptions withoutAnnoyingPopups() {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();

        // suppresses "Warn you if a password was compromised in a data breach"
        prefs.put("profile.password_manager_leak_detection", false);

        // suppresses "Save password?" offer
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        // put prefs into options
        options.setExperimentalOption("prefs", prefs);

        return options;
    }

}
