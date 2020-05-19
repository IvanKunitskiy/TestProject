package com.nymbus.pages.accounts.transactions;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TransactionsPage extends PageTools {

    private By withdrawAndCloseTransactionRowSelector = By.xpath("//table//tr[td[span[contains(text(), '127')]]]");
    private By callStatementButton = By.xpath("//button/span[contains(text(), 'Call Statement')]");
    private By transactionInListSelector = By.xpath("//dn-context-menu[@data-test-id='contextmenu-transaction-%s']//button[@data-test-id='action-showContextMenu']");
    private By editButton = By.xpath("//*[contains(text(), 'Edit')]");
    private By descriptionInListSelector = By.xpath("//tr[1]//td/span[contains(text(), '%s')]");

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

    @Step("Click the 'Edit' option")
    public void clickEditOption() {
        waitForElementClickable(editButton);
        click(editButton);
    }

    @Step("Click the 'Three Dots' button fot transaction by number in the list")
    public void clickThreeDotsIconForTransactionByNumberInList(int number) {
        waitForElementClickable(transactionInListSelector, number - 1);
        click(transactionInListSelector, number - 1);
    }

    @Step("Get 'Description' value for transaction by number in the list")
    public boolean isTransactionWithDescriptionVisibleInList(String description) {
        waitForElementVisibility(descriptionInListSelector, description);
        return isElementVisible(descriptionInListSelector, description);
    }
}
