package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.clients.documents.DocumentActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.document.AccountLevelDocument;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.client.other.AccountLevelDocumentFactory;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22600_EditDeleteRestoreAccountLevelDocumentTest extends BaseTest {

    private Account checkingAccount;
    private AccountLevelDocument accountLevelDocument;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account level document factory
        accountLevelDocument = new AccountLevelDocumentFactory().getAccountLevelDocument();
        accountLevelDocument.setCategory(DocumentActions.createDocumentActions().getCategory(Constants.getEnvironment()));
        accountLevelDocument.setDocType(DocumentActions.createDocumentActions().getDocType(Constants.getEnvironment()));

        // Set up checking account
        checkingAccount = new Account().setCHKAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        checkingAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Set the product
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client with checking account and account level document
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        Pages.clientDetailsPage().openAccountByNumber(checkingAccount.getAccountNumber());
        DocumentActions.createDocumentActions().createAccountLevelDocument(accountLevelDocument);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22600, testRunName = TEST_RUN_NAME)
    @Test(description = "C22600, Edit / Delete / Restore account level document")
    @Severity(SeverityLevel.CRITICAL)
    public void editDeleteRestoreAccountLevelDocument() {

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
        accountLevelDocument.setDocType(DocumentActions.createDocumentActions().getEditedDocType(Constants.getEnvironment()));
        DocumentActions.createDocumentActions().setDocType(accountLevelDocument);
        accountLevelDocument.setNote("Note changed");
        Pages.addNewDocumentPage().typeValueToNotesField(accountLevelDocument.getNote());
        Pages.addNewDocumentPage().clickSaveChangesButton();

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page and check that records about the updated document are present. (Account Details->Maintenance->Maintenance History)");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Drag and Drop Documents here") >= 2,
                "'Drag and Drop Documents here' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Category") >= 2,
                "'Category' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Parent Category") >= 2,
                "'Parent Category' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Notes") >= 2,
                "'Notes' row count is incorrect!");

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
        AccountActions.accountMaintenanceActions().verifyEditedAccountLevelDocumentRecords();

    }
}
