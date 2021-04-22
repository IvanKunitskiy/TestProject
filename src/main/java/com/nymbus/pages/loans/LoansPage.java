package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoansPage extends PageTools {

    private By loansPageHeader = By.xpath("//h1[text()='Loan']");
    private By addNewParticipantButton = By.xpath("//article[contains(string(),'Loan Participants')]//span");
    private By viewAllPartsButton = By.xpath("(//article[contains(string(),'Loan Participants')]//a)[2]");

    @Step("Wait for Loan page loaded")
    public void waitForLoanPageLoaded() {
        waitForElementVisibility(loansPageHeader);
    }

    @Step("Click the 'add New Participant' loan products link")
    public void clickAddNewParticipantLink() {
        waitForElementClickable(addNewParticipantButton);
        click(addNewParticipantButton);
    }

    @Step("Click the 'View all' loan products link")
    public void clickViewAllParticipantLink() {
        waitForElementClickable(viewAllPartsButton);
        click(viewAllPartsButton);
    }

    /**
     * Loan Product
     */

    private By viewAllLoanProductsLink = By.xpath("//a[text()='View all'][parent::node()/preceding-sibling::div[@class='header']/h2[text()='Loan Product']]");
    private By viewAllLoanParticipantsLink = By.xpath("//a[@href='#/search/bank.data.participant']");

    @Step("Click the 'View all' loan products link")
    public void clickViewAllLoanProductsLink() {
        click(viewAllLoanProductsLink);
    }

    @Step("Click the 'View all' Loan Participants link")
    public void clickViewAllLoanParticipants() {
        click(viewAllLoanParticipantsLink);
    }

    /**
     * Loan Reserve
     */

    private By viewAllLoanReserveLink = By.xpath("(//article[contains(string(),'Loan Reserve')]//a)[2]");

    @Step("Click the 'View all' loan reserve link")
    public void clickViewAllLoanReserveLink() {
        click(viewAllLoanReserveLink);
    }


}
