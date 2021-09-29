package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
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
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.GLFunctionValue;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.WebAdminTransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22671_CommitSavingsIRACurrentYearContributionTest extends BaseTest {
    private Transaction transaction;
    private BalanceDataForCHKAcc balanceData;
    private TransactionData transactionData;
    private Account savingsIRAAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        savingsIRAAccount = new Account().setSavingsIraAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionDestination().setTransactionCode(TransactionCode.CUR_Yr_Contribution_2330.getTransCode());

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        savingsIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());
        savingsIRAAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.IRA, RateType.TIER));
        savingsIRAAccount.setApplyInterestTo("CHK Acct");

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createIRAAccount(savingsIRAAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsIRAAccount.getAccountNumber());

        Actions.clientPageActions().searchAndOpenClientByName(savingsIRAAccount.getAccountNumber());
        balanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        transactionData = new TransactionData(transaction.getTransactionDate(), transaction.getTransactionDate(), "+", balanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22671, testRunName = TEST_RUN_NAME)
    @Test(description = "C22671, Commit Savings IRA current year contribution")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionGLDebitMiscCredit() {
        logInfo("Step 1: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        transactionData.setPostingDate(Pages.tellerModalPage().getProofDateValue());
        Actions.transactionActions().doLoginProofDate();

        logInfo("Step 3: Select the following fund types: Source: GL Debit, Destination: Misc Credit");
        logInfo("Step 4: Fill in fields for source line item: select GL account, specify some amount, expand line item and specify Note");
        logInfo("Step 5: Fill in fields for destination line item: select regular account from preconditions, specify trancode, specify the same amount");
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        transactionData.setEffectiveDate(Pages.tellerPage().getEffectiveDate());

        logInfo("Step 6: Do not change the Effective Date and click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();
        Assert.assertEquals(Pages.tellerPage().getModalText(),
                "Transaction was successfully completed.",
                "Transaction doesn't commit");
        Pages.tellerPage().closeModal();
        balanceData.addAmount(transaction.getTransactionDestination().getAmount());

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 7: Close Transaction Receipt popup and" +
                "Go to the account used in Misc Credit item. Verify such fields: current balance, Original Balance, Total Contributions for Life of Account");
        Actions.clientPageActions().searchAndOpenClientByName(savingsIRAAccount.getAccountNumber());
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), balanceData.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), balanceData.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 8: Verify 'Date Of First Deposit' field value if this transaction was the first transaction for account");
        String actualDateOfFirstDeposit = Pages.accountDetailsPage().getDateOfFirstDeposit();
        Assert.assertEquals(actualDateOfFirstDeposit, transactionData.getPostingDate(), "'Date Of First Deposit' field doesn't match!");

        logInfo("Step 9: Open account on Transactions tab and verify that transaction is written on transactions history page");
        transactionData.setBalance(balanceData.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        logInfo("Step 10: Log in to the WebAdmin, go to RulesUI and search for the committed transaction items using its bank.data.transaction.header rootid value");
        WebAdminTransactionData webAdminTransactionData = new WebAdminTransactionData();
        webAdminTransactionData.setPostingDate(transactionData.getPostingDate());
        webAdminTransactionData.setGlFunctionValue(GLFunctionValue.DEPOSIT_ITEM);
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(savingsIRAAccount.getAccountNumber());
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find !");

        logInfo("Step 11: Check gldatetimeposted value for Deposit (Misc Credit) item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDatePosted(2), webAdminTransactionData.getPostingDate(),
                "Posted date doesn't match!");

        logInfo("Step 12: Check glfunction value for Deposit item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(2),
                webAdminTransactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value  doesn't match!");

        logInfo("Step 13: Go to bank.data.gl.interface and verify that there is a record for Deposit (Misc Credit) transaction item");
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(2);
        webAdminTransactionData.setAmount(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(2));
        WebAdminActions.webAdminTransactionActions().goToGLInterface(transactionHeader);
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find!");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(2),
                webAdminTransactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value doesn't match!");

        logInfo("Step 14: Verify that amount and glfunction values are the same as on b.d.transaction.item level");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(2),
                webAdminTransactionData.getAmount(),
                "Amount value doesn't match!");

        logInfo("Step 15: Verify that transactionheaderid from b.d.transaction.item is written to parenttransaction field on bank.data.gl.interface");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLInterfaceTransactionHeaderIdValue(2),
                transactionHeader,
                "HeaderId value doesn't match!");
    }
}