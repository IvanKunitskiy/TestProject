package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoansReservePage extends PageTools {

    private final By loadMoreResults = By.xpath("//a[@title='Next Page']");
    private final By loanCode = By.xpath("//td[@data-column-id='code' and text()='%s']");
    private final By loansPageHeader = By.xpath("//h1[text()='Loan']");
    private final By addNewButton = By.xpath("//a[text()='Add New']");
    private final By authorizationCode = By.xpath("//input[@name='field[(databean)code]']");
    private final By balanceDefinition = By.xpath("//input[@name='field[balancedef]']/../input");
    private final By balanceDefinitionOption = By.xpath("//input[@name='field[balancedef]']/../ul/li/a[contains(string(),'%s')]");
    private final By saveButton = By.xpath("//span[text()='Save Changes']");
    private final By amortizationType = By.xpath("//input[@name='field[reservepremiumamorttype]']/../input");
    private final By amortizationTypeOption = By.xpath("//input[@name='field[reservepremiumamorttype]']/../ul/li/a[contains(string(),'%s')]");
    private final By premiumType = By.xpath("//input[@name='field[reservepremiumtype]']/../input");
    private final By premiumTypeOption = By.xpath("//input[@name='field[reservepremiumtype]']/../ul/li/a[contains(string(),'%s')]");


    @Step("Check is 'Load More Results' button visible")
    public boolean isLoadMoreResultsButtonVisible() {
        return isElementVisible(loadMoreResults);
    }

    @Step("Click 'Load More Results' button")
    public void clickLoadMoreResultsButton() {
        click(loadMoreResults);
    }

    @Step("Check that row with reserve code is visible")
    public boolean isRowWithReserveCodeVisible(String reserveCode) {
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return isElementVisible(loanCode, reserveCode);
    }

    @Step("Wait for Loan reserve page loaded")
    public void waitForLoanReservePageLoaded() {
        waitForElementVisibility(loansPageHeader);
    }

    @Step("Click 'Add new' button")
    public void clickAddNewButton() {
        waitForElementVisibility(addNewButton);
        click(addNewButton);
    }

    @Step("Input Authorization Code")
    public void inputAuthorizationCode(String code) {
        waitForElementVisibility(authorizationCode);
        type(code, authorizationCode);
    }

    @Step("Input Balance definition")
    public void inputBalanceDefinition(String code) {
        waitForElementVisibility(balanceDefinition);
        click(balanceDefinition);
        waitForElementVisibility(balanceDefinitionOption,code);
        click(balanceDefinitionOption, code);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton() {
        waitForElementVisibility(saveButton);
        click(saveButton);
    }

    @Step("Input Amortization Type")
    public void inputAmortizationType(String type) {
        waitForElementVisibility(amortizationType);
        click(amortizationType);
        waitForElementVisibility(amortizationTypeOption, type);
        click(amortizationTypeOption, type);
    }

    @Step("Input Premium Type")
    public void inputPremiumType(String type) {
        waitForElementVisibility(premiumType);
        click(premiumType);
        waitForElementVisibility(premiumTypeOption, type);
        click(premiumTypeOption, type);
    }


}
