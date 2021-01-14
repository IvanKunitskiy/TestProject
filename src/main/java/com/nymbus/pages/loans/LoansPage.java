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

    /**
     * Loan Product
     */

    private By viewAllLoanProductsLink = By.xpath("//a[text()='View all'][parent::node()/preceding-sibling::div[@class='header']/h2[text()='Loan Product']]");

    @Step("Click the 'View all' loan products link")
    public void clickViewAllLoanProductsLink() {
        click(viewAllLoanProductsLink);
    }

}
