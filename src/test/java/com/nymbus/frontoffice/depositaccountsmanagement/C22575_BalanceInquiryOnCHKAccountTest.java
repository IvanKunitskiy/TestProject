package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.other.transfer.Transfer;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22575_BalanceInquiryOnCHKAccountTest extends BaseTest {

    private Client client;
    private Account chkAccount1;
    private Account chkAccount2;
    private Transfer transfer;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up accounts
        chkAccount1 = new Account().setCHKAccountData();
        chkAccount2 = new Account().setCHKAccountData();

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        transfer = transferBuilder.getTransfer();
        transfer.setFromAccount(chkAccount1);
        transfer.setToAccount(chkAccount2);

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        AccountActions.createAccount().createCHKAccount(chkAccount1);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createCHKAccount(chkAccount2);
        Actions.tellerActions().assignAmountToAccount(chkAccount1, "2", "20000");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenClientByID(client);
        TransfersActions.addNewTransferActions().addNewTransfer(transfer);
        Actions.loginActions().doLogOut();

    }

    @Test(description = "C22575, 'Balance inquiry' on CHK account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount1);

        logInfo("Step 3: Click [Balance Inquiry] button");
        Pages.accountDetailsPage().clickBalanceInquiry();

        logInfo("Step 4: Pay attention to the Available Balance and Current Balance values");
        // TODO: parse receipt

        logInfo("Step 5: Click [Print] button");
        Pages.balanceInquiryModalPage().clickPrintButton();
        Pages.balanceInquiryModalPage().clickCloseButton();

        logInfo("Step 6: Open Balance Inquiry popup again");
        Pages.accountDetailsPage().clickBalanceInquiry();

        logInfo("Step 7: Click [Close] button");
        Pages.balanceInquiryModalPage().clickCloseButton();
    }
}