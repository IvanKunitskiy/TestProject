package com.nymbus.pages.creditcards;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CardsManagementPage extends PageTools {

    private By editCardButton = By.xpath("//tbody[@ng-if='isHaveDebitCards']//tr[%s]//td[8]//button[1]");
    private By viewCardHistoryButton = By.xpath("//tbody[@ng-if='isHaveDebitCards']//tr[%s]//td[7]//button[1]");
    private By backToMaintenanceButton = By.xpath("//button[@data-test-id='go-maintenance']");

    @Step("Click edit button {0}")
    public void clickEditButton(int index) {
        waitForElementVisibility(editCardButton, index);
        click(editCardButton, index);
    }

    @Step("Click 'View history' button {0}")
    public void clickViewHistoryButton(int index) {
        waitForElementVisibility(viewCardHistoryButton, index);
        click(viewCardHistoryButton, index);
    }

    @Step("Click '<- Maintenance' button button")
    public void clickBackToMaintenanceButton() {
        waitForElementVisibility(backToMaintenanceButton);
        click(backToMaintenanceButton);
    }

    /**
     * Card history region
     */
    private By transactionAmount = By.xpath("//tr[@ng-repeat ='item in ctrl.cardTransactions track by $index'][%s]" +
            "//td[@amount='item.transactionamount']//span[@ng-if = 'isNeedCurrency']");
    private By transactionFeeAmount = By.xpath("//tr[@ng-repeat ='item in ctrl.cardTransactions track by $index'][%s]" +
            "//td[@amount='item.feeamount']//span[@ng-if = 'isNeedCurrency']");
    private By transactionDescription = By.xpath("//tr[@ng-repeat ='item in ctrl.cardTransactions track by $index'][%s]//td[5]");

    @Step("Get transaction amount {0}")
    public String getTransactionAmount(int index) {
        waitForElementVisibility(transactionAmount, index);
        return getElementText(transactionAmount, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get transaction fee amount {0}")
    public String getTransactionFeeAmount(int index) {
        waitForElementVisibility(transactionFeeAmount, index);
        return getElementText(transactionFeeAmount, index).replaceAll("[^0-9.]", "");
    }

    @Step("Get transaction description {0}")
    public String getTransactionDescription(int index) {
        waitForElementVisibility(transactionDescription, index);
        return getElementText(transactionDescription, index).trim();
    }
}