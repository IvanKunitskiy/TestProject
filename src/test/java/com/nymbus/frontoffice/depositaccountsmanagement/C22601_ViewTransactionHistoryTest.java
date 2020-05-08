package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.client.other.DebitCardFactory;
import com.nymbus.newmodels.generation.settings.BinControlFactory;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class C22601_ViewTransactionHistoryTest extends BaseTest {
    private List<Transaction> transactions;
    private String accountNumber;
    private String accountNumber2;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        Account savingsAccount = new Account().setSavingsAccountData();

        Account chkAccount = new Account().setCHKAccountData();

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set up credit card
        DebitCardFactory debitCardFactory = new DebitCardFactory();
        BinControlFactory binControlFactory = new BinControlFactory();

        BinControl binControl = binControlFactory.getBinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        DebitCard debitCard = debitCardFactory.getDebitCard();
        debitCard.setBinControl(binControl);
        debitCard.setATMDailyDollarLimit(binControl.getATMDailyDollarLimit());
        debitCard.setATMTransactionLimit(binControl.getATMTransactionLimit());
        debitCard.setDBCDailyDollarLimit(binControl.getDBCDailyDollarLimit());
        debitCard.setDBCTransactionLimit(binControl.getDBCTransactionLimit());
        debitCard.getAccounts().add(chkAccount.getAccountNumber());

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create savings account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);

        //Create debit card
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        String expirationDate = Actions.debitCardModalWindowActions().getExpirationDate();
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
        String debitCardNumber = Actions.debitCardModalWindowActions().getCardNumber(1);
        Pages.debitCardModalWindow().clickTheCloseModalIcon();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();

        // Set up account number
        accountNumber = savingsAccount.getAccountNumber();
        accountNumber2 = chkAccount.getAccountNumber();

        // Set up nonTeller transaction data
        NonTellerTransactionData nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setCardNumber(debitCardNumber);
        nonTellerTransactionData.setAmount(40.00);
        nonTellerTransactionData.setExpirationDate(expirationDate);

        // Set up transactions
        transactions = getTransactionList(accountNumber);

        // Create nonTellerTransactions
        Actions.nonTellerTransactionActions().performDepositTransactions(10, nonTellerTransactionData);

        // Create transactions
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().performTransactionList(transactions);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "22601, Account Transactions: View transaction history")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionHistory() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Account#1 from the precondition and open it on the Transactions tab");
        Actions.clientPageActions().searchAndOpenClientByName(accountNumber);
        double currentBalance = AccountActions.retrievingAccountData().getCurrentBalance();
        AccountActions.retrievingAccountData().goToTransactionsTab();
        double actualCurrentBalance =  AccountActions.retrievingAccountData().getBalanceValue(1);
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 10,
                        "Transaction items count don't match!");

        logInfo("Step 3: Apply some filter to display specific transactions (e.g. Start Date filter)");
        Assert.assertTrue(AccountActions.accountTransactionActions().isTransactionsSymbolRight(accountNumber, transactions));

        logInfo("Step 4: Open account details in another tab and compare the Account Current Balance and Transaction History-> Balance for the last committed transaction");
        Assert.assertEquals(actualCurrentBalance, currentBalance, "Account current balance doesn't match!");

        logInfo("Step 5: Click [Expand All] button");
        Pages.accountTransactionPage().clickExpandAllButton();
        Assert.assertTrue(AccountActions.accountTransactionActions().isAllImageVisible(), "Transaction image isn't displayed!");

        logInfo("Step 6: Search for the Account#2 from the precondition and open it on the Transactions tab");
        Actions.clientPageActions().searchAndOpenClientByName(accountNumber2);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 10,
                "Transaction items count don't match!");

        Assert.assertFalse(Pages.accountTransactionPage().isExpandAllButtonVisible(), "Expand All Button is visible!");
    }

    private List<Transaction> getTransactionList(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        TransactionConstructor constructor = new TransactionConstructor(new GLDebitMiscCreditBuilder());
        for (int i = 0; i < 5; i++) {
            Transaction transaction = constructor.constructTransaction();
            transaction.getTransactionDestination().setAccountNumber(accountNumber);
            transactions.add(transaction);
        }
        constructor.setBuilder(new MiscDebitGLCreditTransactionBuilder());
        for (int i = 0; i < 5; i++) {
            Transaction transaction = constructor.constructTransaction();
            transaction.getTransactionSource().setAccountNumber(accountNumber);
            transactions.add(transaction);
        }
        return transactions;
    }
}