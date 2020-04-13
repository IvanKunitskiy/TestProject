package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.clients.documents.DocumentActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.other.document.AccountLevelDocument;
import com.nymbus.newmodels.generation.client.other.AccountLevelDocumentFactory;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22600_EditDeleteRestoreAccountLevelDocumentTest extends BaseTest {

    private Client client;
    private Account checkingAccount;
    private AccountLevelDocument accountLevelDocument;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up account level document factory
        accountLevelDocument = new AccountLevelDocumentFactory().getAccountLevelDocument();
        accountLevelDocument.setCategory("ACH Forms");
        accountLevelDocument.setDocType("Direct Deposit Forms");

        // Set up checking account
        checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client with checking account and account level document
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        Pages.clientDetailsPage().openAccountByNumber(checkingAccount.getAccountNumber());
        DocumentActions.createDocumentActions().createAccountLevelDocument(accountLevelDocument);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22600, Edit / Delete / Restore account level document")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSavingsAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the account from the precondition and open it on the Documents tab (Account Details->Documents)");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        Pages.accountNavigationPage().clickDocumentsTab();

        logInfo("Step 3: Click [3 dots menu] icon and click [Edit] button");
        Pages.documentsPage().clickEditButtonByDocumentType(accountLevelDocument.getDocType());

        logInfo("Step 4: Pay attention to the Image section");
        Assert.assertTrue(Pages.addNewDocumentPage().isReplaceDocumentButtonVisible(), "'Replace Document' button not visible");
        Assert.assertTrue(Pages.addNewDocumentPage().isZoomInButtonVisible(), "'Zoom In' button not visible");
        Pages.addNewDocumentPage().clickZoomInButton();
        Assert.assertTrue(Pages.addNewDocumentPage().isResetButtonVisible(), "'Reset' button not visible");
        Pages.addNewDocumentPage().clickResetButton();

        logInfo("Step 5: Make changes within the form");
        Pages.addNewDocumentPage().replaceDocument(Functions.getFilePathByName(accountLevelDocument.getFile().getFileName()));
        accountLevelDocument.setDocType("Unauthorized ACH Forms");
        DocumentActions.createDocumentActions().setDocType(accountLevelDocument);
        accountLevelDocument.setNote("Note changed");
        Pages.addNewDocumentPage().typeValueToNotesField(accountLevelDocument.getNote());
        Pages.addNewDocumentPage().clickSaveChangesButton();

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page and check that records about the updated document are present. (Account Details->Maintenance->Maintenance History)");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        // TODO: Implement verification at Maintenance History page

        logInfo("Step 7: Go back to Documents tab and check the checkbox next to the document record");
        Pages.accountNavigationPage().clickDocumentsTab();

        logInfo("Step 8: Click [Delete] button");
        Pages.documentsPage().clickCheckboxByDocumentType(accountLevelDocument.getDocType());
        Pages.documentsPage().clickDeleteButton();

        logInfo("Step 9: Click on the 'Restore document' popup");
        Pages.documentsPage().clickRestoreTooltip();
        Assert.assertTrue(Pages.documentsPage().isDocumentIdPresentInTheList(accountLevelDocument.getDocType()));

        logInfo("Step 10: Go to Account Maintenance-> Maintenance History page and check that records about document delete and restore are written to account Maintenance History");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        // TODO: Implement verification at Maintenance History page

    }
}
