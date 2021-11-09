package com.nymbus.core.config;

import com.codeborne.selenide.Configuration;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SelenideConfig {
    private static final String VIDEO_NAME_PATTERN = "HH:mm:ss:SSS";

    /*For Selenoid*/
    private static DesiredCapabilities getBrowserCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (Constants.REMOTE_URL != null) {
            capabilities.setBrowserName(System.getProperty("browserName", "chrome"));
            capabilities.setVersion(System.getProperty("browserVersion", "79.0"));
            capabilities.setCapability("enableVNC", Boolean.parseBoolean(System.getProperty("enableVnc", "true")));
            capabilities.setCapability("enableVideo", Boolean.parseBoolean(System.getProperty("enableVideo", "false")));
            capabilities.setCapability("videoName", String.format("video_%s.mp4", DateTime.getLocalDateTimeByPattern(VIDEO_NAME_PATTERN)));
            capabilities.setCapability("sessionTimeout", "5m");
        }
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return capabilities;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        Map<String, Object> profile = new HashMap<String, Object>();
        Map<String, Object> contentSettings = new HashMap<String, Object>();

        // Enable notifications
        // 0 - Default, 1 - Allow, 2 - Block
        contentSettings.put("notifications", 1);
        profile.put("managed_default_content_settings", contentSettings);
        prefs.put("profile", profile);
        options.setExperimentalOption("prefs", prefs);

        return options;
    }

    public static void createBrowserConfig(String browser) {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.ALL);

        Configuration.browser = browser;

        if (Constants.REMOTE_URL != null) {
            Configuration.driverManagerEnabled = false;
            Configuration.remote = Constants.REMOTE_URL;
        }

//        Configuration.holdBrowserOpen = true;

        DesiredCapabilities caps = getBrowserCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());

//        Configuration.startMaximized = true;
        Configuration.browserSize = "1920x1080";
        Configuration.browserCapabilities = caps;
        Configuration.fastSetValue = false;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        Configuration.pollingInterval = 5000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 18000;
        Configuration.reportsFolder = "screenshots/";
    }
}