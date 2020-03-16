package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoansPage extends PageTools {

    private By loansPageHeader = By.xpath("//h1[text()='Loan']");

    @Step("Wait for Loan page loaded")
    public void waitForLoanPageLoaded() {
        waitForElementVisibility(loansPageHeader);
    }

}
