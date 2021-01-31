package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.transfer.HighBalanceTransfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22546_AddNewClientTransferTest extends BaseTest {

    private Account chkAccount;
    private HighBalanceTransfer highBalanceTransfer;
    private String clientID;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        chkAccount = new Account().setCHKAccountData();
        Account savingsAccount = new Account().setSavingsAccountData();

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        highBalanceTransfer = transferBuilder.getHighBalanceTransfer();
        highBalanceTransfer.setFromAccount(chkAccount);
        highBalanceTransfer.setToAccount(savingsAccount);

        // Create client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set products
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create CHK, Savings account and logout
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Clients Management";

    @TestRailIssue(issueID = 22546, testRunName = TEST_RUN_NAME)
    @Test(description = "C22546, Add new client transfer ")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewClientTransfer() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);

        logInfo("Step 3: Open Clients Profile on the Transfers tab");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 4: Click [New Transfer] button and select any 'Transfer Type' except Transfer -> One Time Only transfer (e.g. High Balance Transfer)");
        Pages.transfersPage().clickNewTransferButton();
        TransfersActions.addNewTransferActions().setHighBalanceTransferType(highBalanceTransfer);

        logInfo("Step 5: Select any account as From Account");
        TransfersActions.addNewTransferActions().setHighBalanceFromAccount(highBalanceTransfer);
        TransfersActions.addNewTransferActions().setHighBalanceToAccount(highBalanceTransfer);
        Pages.newTransferPage().setHighBalance(highBalanceTransfer.getHighBalance());
        Pages.newTransferPage().setMaxAmount(highBalanceTransfer.getMaxAmountToTransfer());
        Pages.newTransferPage().setTransferCharge(highBalanceTransfer.getTransferCharge());

        logInfo("Step 6: Click [Save] button");
        Pages.newTransferPage().clickSaveButton();

        logInfo("Step 7: Click on the newly created transfer from the list of transfers and check the data");
        Pages.transfersPage().clickTransferInTheListByType(highBalanceTransfer.getTransferType().getTransferType());
        Assert.assertTrue(Pages.viewTransferPage().getFromAccount().contains(highBalanceTransfer.getFromAccount().getAccountNumber()));
        Assert.assertTrue(Pages.viewTransferPage().getToAccount().contains(highBalanceTransfer.getToAccount().getAccountNumber()));
        Assert.assertEquals(Pages.viewTransferPage().getHighBalance(), highBalanceTransfer.getHighBalance());
        Assert.assertEquals(Pages.viewTransferPage().getMaxAmountToTransfer(), highBalanceTransfer.getMaxAmountToTransfer());
        Assert.assertEquals(Pages.viewTransferPage().getTransferCharge(), highBalanceTransfer.getTransferCharge());

        logInfo("Step 8: Open Client Profile on Accounts tab and search for the Account that was used as Account From or Account To in the transfer. Open account on the Maintenenace-> Maintenance History page");
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(chkAccount.getAccountNumber());
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 9: Look through the records on the Maintenance History page and verify that records about the newly created transfer are present on the Maintenance History page");
        AccountActions.accountMaintenanceActions().verifyClientTransferRecords();
    }
}
