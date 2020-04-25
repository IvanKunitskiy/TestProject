package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountNavigationPage extends PageTools {

    /**
     * Tabs buttons
     */

    private By maintenanceTab = By.xpath("//a[contains(text(), 'Maintenance')]");
    private By notesTab = By.xpath("//a[contains(text(), 'Notes')]");
    private By accountsTab = By.xpath("//a[contains(text(), 'Accounts')]");
    private By documentsTab = By.xpath("//a[contains(text(), 'Documents')]");
    private By transactionsTab = By.xpath("//a[contains(text(), 'Transactions')]");
    private By transfersTab = By.xpath("//a[contains(text(), 'Transfers')]");
    private By isNotesTabActive = By.xpath("//li[@ng-if='customerAccess.notes_tab' and contains(@class, 'active')]");
    private By accountsBreadCrumb = By.xpath("//a[@data-test-id='go-accounts']");

    /**
     * Bread crumbs
     */

    @Step("Click the 'Accounts' link in bread crumbs")
    public void clickAccountsInBreadCrumbs() {
        waitForElementClickable(accountsBreadCrumb);
        click(accountsBreadCrumb);
    }

    /**
     * Tabs actions
     */

    @Step("Returning if user 'Notes Tab' is active")
    public boolean isNotesTabActive() {
        return getElementAttributeValue("value", isNotesTabActive).contains("0");
    }

    @Step("Click the 'Transactions' tab")
    public void clickTransactionsTab() {
        waitForElementClickable(transactionsTab);
        click(transactionsTab);
    }

    @Step("Click the 'Notes' tab")
    public void clickNotesTab() {
        waitForElementClickable(notesTab);
        click(notesTab);
    }

    @Step("Click the 'Maintenance' tab")
    public void clickMaintenanceTab() {
        waitForElementVisibility(maintenanceTab);
        waitForElementClickable(maintenanceTab);
        click(maintenanceTab);
    }

    @Step("Click the 'Accounts' tab")
    public void clickAccountsTab() {
        waitForElementVisibility(accountsTab);
        waitForElementClickable(accountsTab);
        click(accountsTab);
    }

    @Step("Click 'Documents' tab")
    public void clickDocumentsTab() {
        waitForElementVisibility(documentsTab);
        waitForElementClickable(documentsTab);
        click(documentsTab);
    }

    @Step("Click the 'Transfers' tab")
    public void clickTransfersTab() {
        waitForElementClickable(transfersTab);
        click(transfersTab);
    }

}
