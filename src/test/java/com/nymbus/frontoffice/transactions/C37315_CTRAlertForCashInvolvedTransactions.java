package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C37315_CTRAlertForCashInvolvedTransactions extends BaseTest {
    private Account savingsAccount;
    private IndividualClient client;
    private Transaction transaction;
    private Transaction transactionWithMoreLimit;
    private String clientRootId;

    @BeforeMethod
    public void preCondition() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        savingsAccount = new Account().setSavingsAccountData();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        //Create transactions
        transaction = new TransactionConstructor(new CashInDepositCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAmount(100);
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("209 - Deposit");
        transaction.getTransactionSource().setAmount(100);
        transactionWithMoreLimit = new TransactionConstructor(new CashInDepositCHKAccBuilder()).constructTransaction();
        transactionWithMoreLimit.getTransactionDestination().setAmount(11000);
        transactionWithMoreLimit.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        transactionWithMoreLimit.getTransactionDestination().setTransactionCode("209 - Deposit");
        transactionWithMoreLimit.getTransactionSource().setAmount(11000);

        //Set CTR Online Alert
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewSettingsLink();
        SettingsPage.bankControlPage().clickMiscellaneousButton();
        if (SettingsPage.bankControlPage().getOnlineSTRAlert().equals("0")) {
            SettingsPage.bankControlPage().clickEditButton();
            SettingsPage.bankControlPage().clickOnlineSTRAlert();
        } else {
            SettingsPage.bankControlPage().clickEditButton();
        }
        if (!(Integer.parseInt(SettingsPage.bankControlPage().getOnlineSTRAlert()) > 0)) {
            System.out.println("NNOOOO");
        }
        //SettingsPage.bankControlPage().clickSaveButton();
        Actions.loginActions().doLogOutProgrammatically();
    }


    @Test(description = "C37315, CTR Alert for Cash involved transactions")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select such fund types:\n" +
                "Cash In\n" +
                "Destination: Deposit and CHK/Savings account from the precondition, select 109/209 - Deposit Trancode");
        logInfo("Step 4: Set Transaction Amount < CTR limit value for both items");
        Actions.transactionActions().createTransaction(transaction);

        logInfo("Step 5: Click [Commit Transaction] button and confirm verify screen");
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();

        logInfo("Step 6: In another tab go to the WebAdmin and log in");
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().goToDRLCaches();
        WebAdminPages.drlCachesPage().clickBankingCoreRadio();
        WebAdminPages.drlCachesPage().clickKeysItemByValue("CUSTOMER_CASH");












        WebAdminActions.loginActions().doLogoutProgrammatically();

    }


}
