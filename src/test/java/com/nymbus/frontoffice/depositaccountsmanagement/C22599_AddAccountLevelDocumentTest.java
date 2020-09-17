package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.clients.documents.DocumentActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.document.AccountLevelDocument;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.client.other.AccountLevelDocumentFactory;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22599_AddAccountLevelDocumentTest extends BaseTest {

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
        accountLevelDocument.setCategory("ACH Forms");
        accountLevelDocument.setDocType("Direct Deposit Forms");

        // Set up checking account
        checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client with checking account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22599, Add Account Level Document")
    @Severity(SeverityLevel.CRITICAL)
    public void addAccountLevelDocument() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the account from the precondition and open it on the Documents tab (Account Details->Documents)");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);

        logInfo("Step 3: Open Clients profile on the Documents tab");
        Pages.accountNavigationPage().clickDocumentsTab();

        logInfo("Step 4: Click [Add New Document] button");
        Pages.documentsPage().clickAddNewDocumentButton();

        logInfo("Step 5: Drag & drop some file (e.g. picture or PDF), fill in all the displayed fields with some valid data and click [Save Changes] button");
        Pages.addNewDocumentPage().uploadNewAccountDocument(Functions.getFilePathByName(accountLevelDocument.getFile().getFileName()));
        DocumentActions.createDocumentActions().setCategory(accountLevelDocument);
        DocumentActions.createDocumentActions().setDocType(accountLevelDocument);
        Pages.addNewDocumentPage().typeValueToNotesField(accountLevelDocument.getNote());
        Pages.addNewDocumentPage().clickSaveChangesButton();
        Assert.assertTrue(Pages.documentsPage().isDocumentCategoryPresentInTheList(accountLevelDocument.getCategory()), "Created document is not visible in the documents list");
        Assert.assertTrue(Pages.documentsPage().isDocumentTypePresentInTheList(accountLevelDocument.getDocType()), "Created document is not visible in the documents list");

        logInfo("Step 6: Open Clients profile on Maintenance->Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 7: Look through the records on the Maintenance History page and verify that records about newly created Document are present on the Maintenance History page");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Drag and Drop Documents here") >= 1,
                "'Drag and Drop Documents here' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Category") >= 1,
                "'Category' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Parent Category") >= 1,
                "'Parent Category' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Notes") >= 1,
                "'Notes' row count is incorrect!");
    }

}
