package com.nymbus.frontoffice.clientsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.other.transfer.HighBalanceTransfer;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22558_EditTransferTest extends BaseTest {

    private Client client;
    private Account chkAccount;
    private Account savingsAccount;
    private HighBalanceTransfer highBalanceTransfer;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up accounts
        chkAccount = new Account().setCHKAccountData();
        savingsAccount = new Account().setSavingsAccountData();

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        highBalanceTransfer = transferBuilder.getHighBalanceTransfer();
        highBalanceTransfer.setFromAccount(chkAccount);
        highBalanceTransfer.setToAccount(savingsAccount);

        // Create a client with an active CHK and Savings account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();

        // Create a new transfer
        TransfersActions.addNewTransferActions().addNewHighBalanceTransfer(highBalanceTransfer);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22558, Edit transfer")
    @Severity(SeverityLevel.CRITICAL)
    public void editTransfer() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenClientByID(client);

        logInfo("Step 3: Open Clients Profile on the Transfers tab");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 4: Search for transfer from preconditions and open it in edit mode");
        Pages.transfersPage().clickTransferInTheListByType(highBalanceTransfer.getTransferType().getTransferType());
        Assert.assertEquals(Pages.viewTransferPage().getHighBalance(), highBalanceTransfer.getHighBalance());
        Assert.assertEquals(Pages.viewTransferPage().getMaxAmountToTransfer(), highBalanceTransfer.getMaxAmountToTransfer());
        Assert.assertEquals(Pages.viewTransferPage().getTransferCharge(), highBalanceTransfer.getTransferCharge());

        logInfo("Step 5: Make some changes in the opened transfer (e.g. change from / to account or amount) and click [Save] button");
        Pages.viewTransferPage().clickEditButton();
        TransfersActions.editTransferActions().changeTransferData(highBalanceTransfer);

        logInfo("Step 6: Search for the modified transfer in the left part of the screen and click it to see its details");
        Pages.transfersPage().clickTransferInTheListByType(highBalanceTransfer.getTransferType().getTransferType());
        Assert.assertEquals(Pages.viewTransferPage().getHighBalance(), highBalanceTransfer.getHighBalance());
        Assert.assertEquals(Pages.viewTransferPage().getMaxAmountToTransfer(), highBalanceTransfer.getMaxAmountToTransfer());
        Assert.assertEquals(Pages.viewTransferPage().getTransferCharge(), highBalanceTransfer.getTransferCharge());

        logInfo("Step 7: Open Client Profile on Accounts tab and search for the Account that was used as Account From or Account To in the transfer. Open account on the Maintenenace-> Maintenance History page");
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(chkAccount.getAccountNumber());
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 8: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}