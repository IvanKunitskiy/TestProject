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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.GLFunctionValue;
import com.nymbus.newmodels.transaction.verifyingModels.AccountDates;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.WebAdminTransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C22634_GLDebit_MiscCredit_SavingsAcc_Test extends BaseTest {
    private Transaction transaction;
    private BalanceData balanceData;
    private TransactionData transactionData;
    private Account savingsAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        savingsAccount = new Account().setSavingsAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceData();

        transactionData = new TransactionData(WebAdminActions.loginActions().getSystemDate(), WebAdminActions.loginActions().getSystemDate(), "+", balanceData.getCurrentBalance(),
                 transaction.getTransactionDestination().getAmount());
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22634, testRunName = TEST_RUN_NAME)
    @Test(description = "C22634, Commit transaction GL Debit -> Misc Credit")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionGLDebitMiscCredit() {

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().openProofDateLoginModalWindow();
        transactionData.setPostingDate(Pages.tellerModalPage().getProofDateValue());
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select the following fund types: \n" +
                "- Source: GL Debit \n" +
                "- Destination: Misc Credit");
        logInfo("Step 4: Fill in fields for source line item: \n" +
                "- search for any GL account (use and select any value) \n" +
                "- specify some amount (e.g. $100) \n" +
                "- expand line item and specify Note");
        logInfo("Step 5: Fill in fields for destination line item: \n" +
                "- select Savings account from preconditions \n" +
                "- specify trancode (e.g. 209 - Deposit) \n" +
                "- specify the same amount (e.g. $100)");
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        transactionData.setEffectiveDate(Pages.tellerPage().getEffectiveDate());

        logInfo("Step 6: Do not change the Effective Date and click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();

        Assert.assertEquals(Pages.tellerPage().getModalText(),
                    "Transaction was successfully completed.",
                    "Transaction doesn't commit");

        logInfo("Step 7: Close Transaction Receipt popup and \n" +
                "Go to the account used in Misc Credit item. \n" +
                "Open it on Instructions tab");
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 8: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        balanceData.addAmount(transaction.getTransactionDestination().getAmount());

        logInfo("Step 9: Open account on Details and refresh the page");
        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        BalanceData actualBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        logInfo("Step 10: Verify such fields: \n" +
                "- current balance \n" +
                "- available balance ");
        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        logInfo("Step 11: Verify such fields: \n" +
                "- Date Last Deposit \n" +
                "- Date Last Activity/Contact");
        AccountDates actualAccountDates = AccountActions.retrievingAccountData().getAccountDates();
        Assert.assertEquals(actualAccountDates.getLastDepositDate(), transactionData.getPostingDate(),
                "Date Last deposit  doesn't match");
        Assert.assertEquals(actualAccountDates.getLastActivityDate(), transactionData.getPostingDate(),
                "Date Last activity  doesn't match");

        logInfo("Step 12: Verify Number Of Deposits This Statement Cycle");
        Assert.assertEquals(actualAccountDates.getNumberOfDeposits(), 1,
                "Number Of Deposits This Statement Cycle is incorrect!");

        logInfo("Step 13: Verify Last Deposit Amount field");
        Assert.assertEquals(actualAccountDates.getLastDepositAmount(),  transaction.getTransactionSource().getAmount(),
                "Number Of Deposits This Statement Cycle is incorrect!");
        transactionData.setBalance(balanceData.getCurrentBalance());

        logInfo("Step 14: Open account on Transactions tab and verify that transaction is written on transactions history page");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");
        Actions.loginActions().doLogOut();

        logInfo("Step 15: Log in to the WebAdmin, go to RulesUI and search for the committed transaction items using its bank.data.transaction.header rootid value");
        WebAdminTransactionData webAdminTransactionData = new WebAdminTransactionData();
        webAdminTransactionData.setPostingDate(transactionData.getPostingDate());
        webAdminTransactionData.setGlFunctionValue(GLFunctionValue.DEPOSIT_ITEM);
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(savingsAccount.getAccountNumber());
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find !");

        logInfo("Step 16: Check gldatetimeposted value for Deposit (Misc Credit) item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDatePosted(2), webAdminTransactionData.getPostingDate(),
                "Posted date doesn't match!");

        logInfo("Step 17: Check glfunction value for Deposit item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(2),
                webAdminTransactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value  doesn't match!");

        logInfo("Step 18: Go to bank.data.gl.interface and verify that there is a record for Deposit (Misc Credit) transaction item");
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(2);
        webAdminTransactionData.setAmount(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(2));
        WebAdminActions.webAdminTransactionActions().goToGLInterface(transactionHeader);
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find!");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(2),
                webAdminTransactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value doesn't match!");

        logInfo("Step 19: Verify that amount and glfunction values are the same as on b.d.transaction.item level");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(2),
                webAdminTransactionData.getAmount(),
                "Amount value doesn't match!");

        logInfo("Step 20: Verify that transactionheaderid from b.d.transaction.item is written to parenttransaction field on bank.data.gl.interface");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLInterfaceTransactionHeaderIdValue(2),
                transactionHeader,
                "HeaderId value doesn't match!");
    }
}