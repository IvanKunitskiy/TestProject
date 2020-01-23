package com.nymbus.tools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URI;

import static org.openqa.selenium.remote.BrowserType.CHROME;
import static org.openqa.selenium.remote.BrowserType.FIREFOX;

public class WebDriverFactory {

    public static WebDriver createBrowser(String browser) throws IOException{
        boolean isHeadlessMode = Boolean.valueOf(System.getProperty("headless"));

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(isHeadlessMode);
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--ignore-certificate-errors");

                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(isHeadlessMode);

                return new FirefoxDriver(firefoxOptions);
            case "Selenoid":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                capabilities.setVersion("79");
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", false);

                return new RemoteWebDriver(
                        URI.create("http://selenoid:4444/wd/hub").toURL(),
                        capabilities
                );
            default:
                throw new WebDriverException(String.format
                    ("Browser: %s is invalid or not supported by project", browser));
        }
    }
}
