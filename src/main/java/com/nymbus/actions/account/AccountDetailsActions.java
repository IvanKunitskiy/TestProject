package com.nymbus.actions.account;

import com.nymbus.pages.Pages;
import org.testng.Assert;

public class AccountDetailsActions {

    public void clickMoreButton() {
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
    }

    public void clickLessButtonAndVerifyMoreIsVisible() {
        if (Pages.accountDetailsPage().isLessButtonVisible()) {
            Pages.accountDetailsPage().clickLessButton();
            Assert.assertTrue(Pages.accountDetailsPage().isMoreButtonVisible(), "More button is not visible");
        }
    }
}
