package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.verifyingmodels.SafeDepositKeyValues;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCDAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class C22692_AccountsDetailsBlockSafeDepositBoxAccount extends BaseTest {
    private Account safeDBAccount;
    private IndividualClient client;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        safeDBAccount = new Account().setSafeDepositBoxData();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitMiscCreditCDAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        //safeDBAccount.setProduct(Actions.productsActions().getProduct(Products.CD_PRODUCTS, AccountType.CD, RateType.FIXED));

        // Get safeDepositKeyValues
        Actions.usersActions().openSafeDepositBoxSizesPage();
        List<SafeDepositKeyValues> safeDepositKeyValues = Actions.usersActions().getSafeDepositBoxValues();
        Assert.assertTrue(safeDepositKeyValues.size() > 0, "Safe deposits values are not set!");

        // Set box size and amount
        AccountActions.verifyingAccountDataActions().setSafeDepositBoxSizeAndRentalAmount(safeDBAccount, safeDepositKeyValues);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());


        // Create account
        AccountActions.createAccount().createSafeDepositBoxAccount(safeDBAccount);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22692, Accounts Details block - Safe Deposit Box Account")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyNSFTransaction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select any fund type related to regular account (e.g. Misc Debit)");
        Pages.tellerPage().clickMiscDebitButton();

        logInfo("Step 4: Select Safe Deposit Box account from precondition");
        Actions.transactionActions().inputAccountNumber(safeDBAccount.getAccountNumber());

        logInfo("Step 5: Look at the information displayed for CHK account in \"Account Quick View\" section");
        String pifNumber = Actions.transactionActions().getPIFNumber();
        Assert.assertEquals(pifNumber, client.getIndividualType().getClientID(), "Client ID doesn't match");
        String accountType = Actions.transactionActions().getAccountType();
        Assert.assertEquals(accountType, safeDBAccount.getProductType(), "Account Type doesn't match");
        double currentBalance = Actions.transactionActions().getCurrentBalance();
        Assert.assertEquals(currentBalance, 0,
                "Current balance not correct");
        String dateOpened = Actions.transactionActions().getDateOpened();
        String date = DateTime.getDateWithFormat(dateOpened, "MMMMMMM dd,yyyy", "MM/dd/yyyy");
        Assert.assertEquals(date, safeDBAccount.getDateOpened(), "Date opened doesn't match");

        String boxSize = Actions.transactionActions().getBoxSize();
        Assert.assertEquals(boxSize, safeDBAccount.getBoxSize(), "Account Type doesn't match");
        String rentalAmount = Actions.transactionActions().getRentalAmount();
        Assert.assertEquals(rentalAmount, safeDBAccount.getRentalAmount(), "Account Type doesn't match");

        logInfo("Step 5: Click [Details] button");
        Pages.tellerPage().clickDetailsButton();
        SelenideTools.switchToLastTab();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }
}
