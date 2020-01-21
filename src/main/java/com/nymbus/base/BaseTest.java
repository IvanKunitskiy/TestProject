package com.nymbus.base;

import com.nymbus.tools.WebDriverFactory;
import com.nymbus.util.Constants;
import com.nymbus.util.testlistener.TestListener;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class})
public class BaseTest {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private static ThreadLocal<WebDriver> concurrentDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return concurrentDriver.get();
    }

    protected void navigateToUrl(String url) {
        LOG.info("Navigate to url {}", url);
        getDriver().get(url);
    }

    @BeforeClass(alwaysRun = true, description = "Open BROWSER")
    public void startBrowser() {
        WebDriver driver = WebDriverFactory.createBrowser(System.getProperty("browser", Constants.CHROME));

        concurrentDriver.set(driver);
        concurrentDriver.get().manage().window().maximize();
        concurrentDriver.get().manage().deleteAllCookies();
    }

//    @AfterClass(alwaysRun = true, description = "Close BROWSER")
//    public void closeBrowser() {
//        getDriver().manage().deleteAllCookies();
//        getDriver().quit();
//    }
}
