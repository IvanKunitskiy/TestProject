package com.nymbus.core.config;

import com.codeborne.selenide.Configuration;
import com.nymbus.core.utils.DateTime;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;

public class SelenideConfig {
    private static final String VIDEO_NAME_PATTERN = "HH:mm:ss:SSS";

    /*For Selenoid*/
    private static DesiredCapabilities getBrowserCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("enableVNC", Boolean.parseBoolean(System.getProperty("enableVnc", "true")));
//        capabilities.setCapability("enableVideo", Boolean.parseBoolean(System.getProperty("enableVideo", "false")));
//        capabilities.setCapability("videoName", String.format("video_%s.mp4", DateTime.getLocalDateTimeByPattern(VIDEO_NAME_PATTERN)));
//        capabilities.setCapability("sessionTimeout", "5m");

        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return capabilities;
    }

    public static void createBrowserConfig(String browser) {
        Configuration.browser = browser;

        /*Configuration.driverManagerEnabled = false;
        Configuration.remote = PropertyLoader.get("remote_url");
        Configuration.browserCapabilities = getBrowserCapabilities();*/

        Configuration.holdBrowserOpen = true;

        Configuration.startMaximized = true;
        Configuration.browserCapabilities = getBrowserCapabilities();
        Configuration.fastSetValue = false;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
        Configuration.pollingInterval = 5000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 60000;
    }
}
