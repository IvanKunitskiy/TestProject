package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.GLFunctionValue;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.WebAdminTransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22634_GLDebit_MiscCredit_SavingsAcc_Test extends BaseTest {
    private Transaction transaction;
    private BalanceData balanceData;
    private TransactionData transactionData;
    Account savingsAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        savingsAccount = new Account().setSavingsAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceData();

        transactionData = new TransactionData(WebAdminActions.loginActions().getSystemDate(), WebAdminActions.loginActions().getSystemDate(), "+", balanceData.getCurrentBalance(),
                 transaction.getTransactionDestination().getAmount());
    }

    @Test(description = "C22634, Commit transaction GL Debit -> Misc Credit")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionGLDebitMiscCredit() {
        Actions.transactionActions().loginTeller();

        Actions.transactionActions().goToTellerPage();

        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);

        Actions.transactionActions().clickCommitButton();

        Assert.assertEquals(Pages.tellerPage().getModalText(),
                    "Transaction was successfully completed.",
                    "Transaction doesn't commit");

        Pages.tellerPage().closeModal();

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();
        AccountActions.createInstruction().deleteInstruction(1);

        balanceData.addAmount(transaction.getTransactionDestination().getAmount());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);

        BalanceData actualBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        Assert.assertEquals(Pages.accountDetailsPage().getDateLastDepositValue(), WebAdminActions.loginActions().getSystemDate(),
                "Date Last deposit  doesn't match");

        Assert.assertEquals(Pages.accountDetailsPage().getDateLastActivityValue(), WebAdminActions.loginActions().getSystemDate(),
                "Date Last activity  doesn't match");


        transactionData.setBalance(balanceData.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();

        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();

        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        Actions.loginActions().doLogOut();

        WebAdminTransactionData transactionData = new WebAdminTransactionData();
        String date = WebAdminActions.loginActions().getSystemDate();
        transactionData.setPostingDate(date);
        transactionData.setGlFunctionValue(GLFunctionValue.DEPOSIT_ITEM);

        Selenide.open(Constants.WEB_ADMIN_URL);

        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(savingsAccount.getAccountNumber());

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find !");

        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDatePosted(1), transactionData.getPostingDate(),
                "Posted date doesn't match!");

        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(1),
                transactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value  doesn't match!");

        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(1);

        transactionData.setAmount(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1));

        WebAdminActions.webAdminTransactionActions().goToGLInterface(transactionHeader);

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find!");

        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(1),
                transactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value doesn't match!");

        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1),
                transactionData.getAmount(),
                "Amount value doesn't match!");

        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLInterfaceTransactionHeaderIdValue(1),
                transactionHeader,
                "HeaderId value doesn't match!");
    }
}