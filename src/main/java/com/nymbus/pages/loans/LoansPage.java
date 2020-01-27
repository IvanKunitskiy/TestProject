package com.nymbus.pages.loans;

import com.nymbus.base.BasePage;
import com.nymbus.locator.Locator;
import com.nymbus.locator.XPath;
import io.qameta.allure.Step;

public class LoansPage extends BasePage {

    private Locator loansPageHeader = new XPath("//h1[text()='Loan']");

    @Step("Wait for Loan page loaded")
    public void waitForLoanPageLoaded(){
        waitForElementVisibility(loansPageHeader);
    }

}
