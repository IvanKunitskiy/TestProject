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
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.GLFunctionValue;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.WebAdminTransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C23914_GLDebitMiscCreditCHKAccTest extends BaseTest {
    private Transaction transaction;
    private BalanceDataForCHKAcc balanceData;
    private TransactionData transactionData;
    private  Account checkAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        transactionData = new TransactionData(transaction.getTransactionDate(), transaction.getTransactionDate(), "+", balanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());
    }

    @Test(description = "23914, Commit transaction GL Debit -> Misc Credit(on CHK Account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionGLDebitMiscCredit() {
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();

        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        transactionData.setEffectiveDate(Pages.tellerPage().getEffectiveDate());

        Actions.transactionActions().clickCommitButton();

        Assert.assertEquals(Pages.tellerPage().getModalText(),
                "Transaction was successfully completed.",
                "Transaction doesn't commit");

        logInfo("Step 7: Close Transaction Receipt popup and \n" +
                "Go to the account used in Misc Credit item. \n" +
                "Open it on Instructions tab");
        Pages.tellerPage().closeModal();
        Actions.clientPageActions().searchAndOpenClientByName(checkAccount.getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 8: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        balanceData.addAmount(transaction.getTransactionDestination().getAmount());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);

        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        Assert.assertEquals(Pages.accountDetailsPage().getDateLastDepositValue(), transaction.getTransactionDate(),
                "Date Last deposit  doesn't match");

        Assert.assertEquals(Pages.accountDetailsPage().getDateLastActivityValue(), transaction.getTransactionDate(),
                "Date Last activity  doesn't match");

        transactionData.setBalance(balanceData.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();

        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();

        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        logInfo("Step 15: Log in to the WebAdmin, go to RulesUI and search for the committed transaction items using its bank.data.transaction.header rootid value");
        WebAdminTransactionData transactionData = new WebAdminTransactionData();
        String date = WebAdminActions.loginActions().getSystemDate();
        transactionData.setPostingDate(date);
        transactionData.setGlFunctionValue(GLFunctionValue.DEPOSIT_ITEM_CHK_ACC);
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(checkAccount.getAccountNumber());
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find !");

        logInfo("Step 16: Check gldatetimeposted value for Deposit (Misc Credit) item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDatePosted(1), transactionData.getPostingDate(),
                "Posted date doesn't match!");

        logInfo("Step 17: Check glfunction value for Deposit item");
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(1),
                transactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value  doesn't match!");

        logInfo("Step 18: Go to bank.data.gl.interface and verify that there is a record for Deposit (Misc Credit) transaction item");
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(1);
        transactionData.setAmount(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1));
        WebAdminActions.webAdminTransactionActions().goToGLInterface(transactionHeader);
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "Transaction items doesn't find!");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLFunctionValue(1),
                transactionData.getGlFunctionValue().getGlFunctionValue(),
                "Function value doesn't match!");

        logInfo("Step 19: Verify that amount and glfunction values are the same as on b.d.transaction.item level");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1),
                transactionData.getAmount(),
                "Amount value doesn't match!");

        logInfo("Step 20: Verify that transactionheaderid from b.d.transaction.item is written to parenttransaction field on bank.data.gl.interface");
        Assert.assertEquals( WebAdminPages.rulesUIQueryAnalyzerPage().getGLInterfaceTransactionHeaderIdValue(1),
                transactionHeader,
                "HeaderId value doesn't match!");
    }
}