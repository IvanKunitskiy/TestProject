package com.nymbus.tests.navigation;

import com.nymbus.base.BaseTest;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Start page")
@Feature("Navigation")
@Story("Main page Title")
public class FirstTest extends BaseTest {

    @Test(description = "Test case - 1, Opening main page")
    @Severity(SeverityLevel.CRITICAL)
    public void firstTest() {
        navigateToUrl(Constants.URL);

        Assert.assertEquals(BaseTest.getDriver().getTitle(), "Google",
                "Expected 'Title' is not visible");
    }
}
