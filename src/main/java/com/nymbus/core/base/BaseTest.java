package com.nymbus.core.base;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.allure.AllureLogger;
import com.nymbus.core.config.SelenideConfig;
import com.nymbus.core.utils.Constants;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class})
public class BaseTest extends AllureLogger {

    String user;


    @BeforeMethod(alwaysRun = true, description = "Opening web browser...")
    public void setUp() {
        logInfo("Creating web driver configuration...");
        SelenideConfig.createBrowserConfig(System.getProperty("selenide.browser", "chrome"));

        logInfo("Open browser...");
        Selenide.open(/*PropertyLoader.get("base_url")*/Constants.URL);

        user = Constants.USERS.pop();

        Constants.USERNAME = user;
        Constants.PASSWORD = user;
        Constants.FIRST_NAME = user;
        Constants.LAST_NAME = user;

    }

    @AfterMethod(alwaysRun = true, description = "Closing web browser...")
    public void tearDown() {
        Constants.USERS.add(user);
        Selenide.closeWebDriver();
        logInfo("Web driver closed!");
    }
}
