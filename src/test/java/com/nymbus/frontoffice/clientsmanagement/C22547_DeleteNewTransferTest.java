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
import com.nymbus.newmodels.client.other.transfer.Transfer;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.jvm.hotspot.debugger.Page;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22547_DeleteNewTransferTest extends BaseTest {

    private Client client1;
    private Client client2;
    private Account chkAccount1;
    private Account savingsAccount1;
    private Account chkAccount2;
    private Account savingsAccount2;
    private HighBalanceTransfer highBalanceTransfer;
    private Transfer transfer;

    @BeforeMethod
    public void preCondition() {

        // Set up clients
        client1 = new Client().setDefaultClientData();
        client1.setClientStatus("Member");
        client1.setClientType("Individual");

        client2 = new Client().setDefaultClientData();
        client2.setClientStatus("Member");
        client2.setClientType("Individual");

        // Set up accounts
        chkAccount1 = new Account().setCHKAccountData();
        savingsAccount1 = new Account().setSavingsAccountData();
        chkAccount2 = new Account().setCHKAccountData();
        savingsAccount2 = new Account().setSavingsAccountData();

        // Set up transfers
        TransferBuilder transferBuilder = new TransferBuilder();

        highBalanceTransfer = transferBuilder.getHighBalanceTransfer();
        highBalanceTransfer.setFromAccount(chkAccount1);
        highBalanceTransfer.setToAccount(savingsAccount1);

        transfer = transferBuilder.getTransfer();
        transfer.setFromAccount(chkAccount2);
        transfer.setToAccount(savingsAccount2);

        // Create a client with an active CHK / Savings account and a High Balance transfer
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client1);
        client1.setClientID(Pages.clientDetailsPage().getClientID());
        AccountActions.createAccount().createCHKAccount(chkAccount1);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount1);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();
        TransfersActions.addNewTransferActions().addNewHighBalanceTransfer(highBalanceTransfer);
        Actions.loginActions().doLogOut();

        // Create a client with an active CHK / Savings account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client2);
        client2.setClientID(Pages.clientDetailsPage().getClientID());
        AccountActions.createAccount().createCHKAccount(chkAccount2);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount2);

        // Assign Amount to CHK account
        Pages.aSideMenuPage().clickTellerMenuItem();
        Pages.tellerModalPage().clickEnterButton();
        Pages.tellerPage().clickCashInButton();
        Pages.cashInModalPage().waitCashInModalWindow();
        Pages.cashInModalPage().typeToHundredsItemCountInputField("2");
        Pages.cashInModalPage().clickOkButton();
        Pages.tellerPage().clickMiscCreditButton();
        Pages.tellerPage().typeDestinationAccountNumber(1, chkAccount2.getAccountNumber());
        Pages.tellerPage().clickDestinationAccountSuggestionOption(chkAccount2.getAccountNumber());
        Pages.tellerPage().waitForCreditTransferCodeVisible();
        Pages.tellerPage().typeDestinationAmountValue(1, "20000");
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductor().waitModalWindow();
        Pages.verifyConductor().clickVerifyButton();
        Pages.transactionCompleted().waitModalWindow();
        Pages.transactionCompleted().clickCloseButton();
        Actions.loginActions().doLogOut();

        // Create One time only periodic transfer
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByID(client2);
        TransfersActions.addNewTransferActions().addNewTransfer(transfer);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22558, Delete new transfer")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteNewTransfer() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenClientByID(client1);

        logInfo("Step 3: Open Clients Profile on the Transfers tab");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 4: Search for transfer from preconditions and open it in edit mode");
        Pages.transfersPage().clickTransferInTheListByType(highBalanceTransfer.getTransferType().getTransferType());

        logInfo("Step 5: Click [Delete] button");
        Pages.viewTransferPage().clickDeleteButton();

        logInfo("Step 6: Open Client Profile on Accounts tab and search for the Account that was used as Account From or Account To in the transfer. Open account on the Maintenenace-> Maintenance History page");
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(chkAccount1.getAccountNumber());
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 7: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page

        logInfo("Step 8: Go to Clients page and search for the client from the precondition");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenClientByID(client2);

        logInfo("Step 9: Open Clients Profile on the Transfers tab");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 10: Search for the modified transfer in the left part of the screen and click it. Try to click [Delete] button");
        Pages.transfersPage().clickTransferInTheListByType(transfer.getTransferType().getTransferType());
        Assert.assertTrue(Pages.viewTransferPage().isEditButtonDisabled(), "Expired transfer can be edited");
    }
}
