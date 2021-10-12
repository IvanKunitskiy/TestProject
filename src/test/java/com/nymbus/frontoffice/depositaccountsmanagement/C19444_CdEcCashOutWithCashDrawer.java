package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.transactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.CashDrawerData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C19444_CdEcCashOutWithCashDrawer extends BaseTest {

    IndividualClient client;
    private Account chkAccount;
    private TransactionData transactionData;
    BalanceData balanceData;
    CashDrawerData cashDrawerData;
    private final TransactionSource withdrawalSource = SourceFactory.getWithdrawalSource();
    private final TransactionDestination cashOutDestination = DestinationFactory.getCashOutDestination();

    @BeforeMethod
    public void preConditions(){
        //Check CFMIntegrationEnabled
//        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
//        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(),userCredentials.getPassword());
//        if (WebAdminActions.webAdminUsersActions().isCFMIntegrationEnabled()) {
//            throw new SkipException("CFMIntegrationEnabled = 1");
//        }
//        WebAdminActions.loginActions().doLogout();
//        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up transactions
        cashOutDestination.setAccountNumber("0-0-Dummy");
        cashOutDestination.setTransactionCode(TransactionCode.CASH_OUT_855.getTransCode());
        withdrawalSource.setAccountNumber(chkAccount.getAccountNumber());
        withdrawalSource.setTransactionCode(TransactionCode.WITHDRAWAL_116.getTransCode());
        withdrawalSource.setAmount(cashOutDestination.getAmount());

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccount(chkAccount);

        // Set up deposit transaction
        int depositTransactionAmount = 12000;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(depositTransactionAmount);
        depositTransaction.getTransactionSource().setAmount(depositTransactionAmount);

        // Perform deposit transactions
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Get 'Current Balance' and 'Available Balance' before test
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        balanceData = AccountActions.retrievingAccountData().getBalanceData();
        Actions.loginActions().doLogOutProgrammatically();

        // Perform 'Cash Out' transaction
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().openProofDateLoginModalWindow();
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().goToTellerPage();

        Actions.transactionActions().setCashOutDestination(cashOutDestination);
        Actions.transactionActions().setWithDrawalSource(withdrawalSource, 0);
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductor().waitModalWindow();
        Pages.verifyConductor().clickVerifyButton();
        Pages.tellerPage().waitForPrintReceipt();
        Pages.tellerPage().closeModal();

        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        cashDrawerData = Actions.cashDrawerAction().getCashDrawerData();

        Actions.loginActions().doLogOutProgrammatically();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+",
                depositTransactionAmount,
                cashOutDestination.getAmount());
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 19444, testRunName = TEST_RUN_NAME)
    @Test(description = "C19444, [CD] EC Cash Out with Cash Drawer")
    @Severity(SeverityLevel.CRITICAL)
    public void cdEcCashOutWithCashDrawer() {

        logInfo("Step 1: Log in to the system as the user from preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Journal screen and log in to proof date with teller + cash drawer mentioned above");
        Actions.journalActions().goToJournalPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Search for transaction from preconditions and open its details");
        Actions.journalActions().applyFilterByAccountNumber(chkAccount.getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        TestRailAssert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                new CustomStepResult("Transaction state has changed", "Transaction state hasn't changed"));

        logInfo("Step 5: Go to Cash Drawer screen, search for cash drawer used in teller session and verify its:\n" +
                "- denominations\n" +
                "- total cash out");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);

        CashDrawerData actualCashDrawerData = Actions.cashDrawerAction().getCashDrawerData();

        TestRailAssert.assertEquals(actualCashDrawerData.getFiftiesAmount() + 100.00, cashDrawerData.getFiftiesAmount(),
                new CustomStepResult("'Fifties' denomination is valid", "'Fifties' denomination is not valid"));

        logInfo("Step 6: Go to account used in withdrawal item and verify its:\n" +
                "- current balance\n" +
                "- available balance\n" +
                "- Transactions history");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);

        TestRailAssert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                balanceData.getCurrentBalance(),
                new CustomStepResult("CHK account current balance is correct", "CHK account current balance is not correct!"));
        TestRailAssert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                balanceData.getAvailableBalance(),
                new CustomStepResult("CHK account available balance is correct", "CHK account available balance is not correct!"));

        logInfo("Step 7: Open account on the Transactions tab and verify the Error corrected transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        TestRailAssert.assertEquals(actualTransactionData, transactionData,
                new CustomStepResult("Transaction data is matching", "Transaction data doesn't match!"));
        TestRailAssert.assertEquals(Pages.accountTransactionPage().getCheckingAccountTransactionEcColumnValue(1), "EC",
                new CustomStepResult("'EC' is in 'EC' column", "'EC' is not in 'EC' column"));

    }

}
