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
    private By isNotesTabActive = By.xpath("//li[@ng-if='customerAccess.notes_tab' and contains(@class, 'active')]");

    /**
     * Tabs actions
     */

    @Step("Returning if user 'Notes Tab' is active")
    public boolean isNotesTabActive() {
        return getElementAttributeValue("value", isNotesTabActive).contains("0");
    }

    @Step("Click the 'Notes' tab")
    public void clickNotesTab() {
        waitForElementClickable(maintenanceTab);
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

}
