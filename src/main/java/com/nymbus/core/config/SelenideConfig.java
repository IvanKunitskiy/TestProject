package com.nymbus.core.config;

import com.codeborne.selenide.Configuration;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SelenideConfig {
    private static final String VIDEO_NAME_PATTERN = "HH:mm:ss:SSS";

    /*For Selenoid*/
    private static DesiredCapabilities getBrowserCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (Constants.REMOTE_URL != null) {
            capabilities.setCapability("enableVNC", Boolean.parseBoolean(System.getProperty("enableVnc", "true")));
            capabilities.setCapability("enableVideo", Boolean.parseBoolean(System.getProperty("enableVideo", "false")));
            capabilities.setCapability("videoName", String.format("video_%s.mp4", DateTime.getLocalDateTimeByPattern(VIDEO_NAME_PATTERN)));
            capabilities.setCapability("sessionTimeout", "5m");
        }
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return capabilities;
    }

    public static void createBrowserConfig(String browser) {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.ALL);

        Configuration.browser = browser;

        System.out.println("REMOTEUTL: " + Constants.REMOTE_URL);
        if (Constants.REMOTE_URL != null) {
            Configuration.driverManagerEnabled = false;
            Configuration.remote = Constants.REMOTE_URL;
        }
        Configuration.browserCapabilities = getBrowserCapabilities();


        Configuration.startMaximized = true;
        Configuration.browserCapabilities = getBrowserCapabilities();
        Configuration.fastSetValue = false;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        Configuration.pollingInterval = 5000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 60000;
    }
}