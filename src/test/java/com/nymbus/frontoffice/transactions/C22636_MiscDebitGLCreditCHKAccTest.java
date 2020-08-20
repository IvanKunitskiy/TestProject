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
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.GLFunctionValue;
import com.nymbus.newmodels.transaction.verifyingModels.ExtendedBalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.WebAdminTransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22636_MiscDebitGLCreditCHKAccTest extends BaseTest {
    private Transaction miscDebitGLCreditTransaction;
    String INSTRUCTION_REASON = "Reg CC";
    String accountNumber;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();
        miscDebitGLCreditTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccount(checkAccount);

        // Set up transaction with account number
        accountNumber = checkAccount.getAccountNumber();
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        miscDebitGLCreditTransaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());

        // Perform transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(glDebitMiscCreditTransaction);

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Delete Reg CC instruction if exists
        Actions.clientPageActions().searchAndOpenClientByName(glDebitMiscCreditTransaction.getTransactionDestination().getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22636, Commit transaction Misc Debit -> GL Credit (from CHK Account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionMiscDebitGLCreditCHKAcc() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());
        ExtendedBalanceDataForCHKAcc balanceData = AccountActions.retrievingAccountData().getExtendedBalanceDataForCHKAcc();
        double averageBalance = balanceData.getAverageBalance();

        logInfo("Balance data " + balanceData.toString());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().openProofDateLoginModalWindow();
        String postingDate = Pages.tellerModalPage().getProofDateValue();
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select the following fund types:\n" +
                         "- Source: Misc Debit \n" +
                         "- Destination: GL Credit");
        logInfo("Step 4: Fill in fields for source line item: \n" +
                "- select regular account from preconditions (e.g. CHK account) \n" +
                "- specify trancode (e.g. 119 - Debit Memo) \n" +
                "- specify some amount which is less than available balance of the selected account (e.g. $90.00)");
        logInfo("Step 5: Fill in fields for destination line item: \n" +
                "- select GL account \n" +
                "- specify the same amount (e.g. $90.00) \n" +
                "- expand line item and specify Note");
        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().createMiscDebitGLCreditTransaction(miscDebitGLCreditTransaction);
        String effectiveDate = Pages.tellerPage().getEffectiveDate();
        Actions.transactionActions().clickCommitButton();
        Assert.assertEquals(Pages.tellerPage().getModalText(),
                "Transaction was successfully completed.",
                "Transaction doesn't commit");

        // Set transaction data and update balances
        balanceData.reduceAmount(miscDebitGLCreditTransaction.getTransactionSource().getAmount());
        balanceData.reduceAverageBalance(averageBalance);
        TransactionData transactionData = new TransactionData(postingDate,
                effectiveDate, "-",
                balanceData.getCurrentBalance(),
                miscDebitGLCreditTransaction.getTransactionDestination().getAmount());

        logInfo("Step 7: Close Transaction Receipt popup and \n" +
                "Go to the account used in Misc Debit item. Verify such fields: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Collected Balance \n" +
                "- Average Balance");
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());
        ExtendedBalanceDataForCHKAcc actualBalanceDate = AccountActions.retrievingAccountData().getExtendedBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceDate, balanceData, "Balance data doesn't match!");

        logInfo("Step 8: Open account on Transactions history and verify that transaction is written on transactions history page");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        Actions.loginActions().doLogOut();

        logInfo("Step 9: Log in to the WebAdmin, go to RulesUI and search for the committed transaction items using its bank.data.transaction.header rootid value");
        WebAdminTransactionData webAdminTransactionData = new WebAdminTransactionData();
        webAdminTransactionData.setPostingDate(transactionData.getPostingDate());
        webAdminTransactionData.setGlFunctionValue(GLFunctionValue.DEPOSIT_ITEM_CHK_ACC);
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(accountNumber);
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find !");

        logInfo("Step 10: Check gldatetimeposted value for Misc Debit item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDatePosted(1), webAdminTransactionData.getPostingDate(),
                "Posted date doesn't match!");

        logInfo("Step 11: Check glfunction value for Deposit item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(1),
                webAdminTransactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value  doesn't match!");

        logInfo("Step 12: Go tobank.data.gl.interface and verify that there is a record for Misc Debit item transaction item");
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(1);
        webAdminTransactionData.setAmount(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1));
        WebAdminActions.webAdminTransactionActions().goToGLInterface(transactionHeader);
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find!");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(1),
                webAdminTransactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value doesn't match!");

        logInfo("Step 13: Verify that amount and glfunction values are the same as on b.d.transaction.item level");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1),
                webAdminTransactionData.getAmount(),
                "Amount value doesn't match!");

        logInfo("Step 14: Verify that transactionheaderid from b.d.transaction.item is written to parenttransaction field on bank.data.gl.interface");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLInterfaceTransactionHeaderIdValue(1),
                transactionHeader,
                "HeaderId value doesn't match!");
    }
}