package com.nymbus.pages.accounts.transactions;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TransactionsPage extends PageTools {

    private By withdrawAndCloseTransactionRowSelector = By.xpath("//table//tr[td[span[contains(text(), '127')]]]");
    private By callStatementButton = By.xpath("//button/span[contains(text(), 'Call Statement')]");

    @Step("Check that 'Withdraw & Close' transaction is written on the transaction history page")
    public boolean isWithdrawAndCloseTransactionsVisible() {
        waitForElementVisibility(withdrawAndCloseTransactionRowSelector);
        return isElementVisible(withdrawAndCloseTransactionRowSelector);
    }

    @Step("Click the 'Call statement' button")
    public void clickTheCallStatementButton() {
        waitForElementClickable(callStatementButton);
        click(callStatementButton);
    }
}