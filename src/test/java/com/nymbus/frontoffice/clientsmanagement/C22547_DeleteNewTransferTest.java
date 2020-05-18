package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.transfer.HighBalanceTransfer;
import com.nymbus.newmodels.client.other.transfer.Transfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22547_DeleteNewTransferTest extends BaseTest {

    private IndividualClient client1;
    private IndividualClient client2;
    private Account chkAccount1;
    private Account savingsAccount1;
    private Account chkAccount2;
    private Account savingsAccount2;
    private HighBalanceTransfer highBalanceTransfer;
    private Transfer transfer;
    private String client1_ID;
    private String client2_ID;
    private Transaction transaction;

    private String clientID_1;
    private String clientID_2;

    @BeforeMethod
    public void preCondition() {
        // Set up clients

        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();

        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client1 = individualClientBuilder.buildClient();
        client2 = individualClientBuilder.buildClient();

        // Set up accounts
        chkAccount1 = new Account().setCHKAccountData();
        savingsAccount1 = new Account().setSavingsAccountData();
        chkAccount2 = new Account().setCHKAccountData();
        savingsAccount2 = new Account().setSavingsAccountData();

        // Set up transaction
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(chkAccount2.getAccountNumber());

        // Set up transfers
        TransferBuilder transferBuilder = new TransferBuilder();

        highBalanceTransfer = transferBuilder.getHighBalanceTransfer();
        highBalanceTransfer.setFromAccount(chkAccount1);
        highBalanceTransfer.setToAccount(savingsAccount1);

        transfer = transferBuilder.getTransfer();
        transfer.setFromAccount(chkAccount2);
        transfer.setToAccount(savingsAccount2);

        /*// Set up transaction
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();*/

        // Create a client with an active CHK / Savings account and a High Balance transfer
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client1);
        ClientsActions.individualClientActions().setClientDetailsData(client1);
        ClientsActions.individualClientActions().setDocumentation(client1);

        clientID_1 = Pages.clientDetailsPage().getClientID();

        AccountActions.createAccount().createCHKAccount(chkAccount1);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount1);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();
        TransfersActions.addNewTransferActions().addNewHighBalanceTransfer(highBalanceTransfer);
        Actions.loginActions().doLogOut();

        // Create a client with an active CHK / Savings account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client2);
        ClientsActions.individualClientActions().setClientDetailsData(client2);
        ClientsActions.individualClientActions().setDocumentation(client2);

        clientID_2 = Pages.clientDetailsPage().getClientID();

        AccountActions.createAccount().createCHKAccount(chkAccount2);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount2);

        // Assign Amount to CHK account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOut();

        // Log in -> Create 'One time only' periodic transfer -> Log out
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID_2);
        TransfersActions.addNewTransferActions().addNewTransfer(transfer);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22547, Delete new transfer")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteNewTransfer() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients and search for the client from the precondition");

        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID_1);

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

        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID_2);

        logInfo("Step 9: Open Clients Profile on the Transfers tab");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 10: Search for the modified transfer in the left part of the screen and click it. Try to click [Delete] button");
        Pages.transfersPage().clickTransferInTheListByType(transfer.getTransferType().getTransferType());
        Assert.assertTrue(Pages.viewTransferPage().isEditButtonDisabled(), "Expired transfer can be edited");
    }
}
