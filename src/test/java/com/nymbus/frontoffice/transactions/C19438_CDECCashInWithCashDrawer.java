package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.transactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C19438_CDECCashInWithCashDrawer extends BaseTest {
    private IndividualClient client;
    private Account savingsAccount;
    private double countedCashValue;
    private BalanceData balanceData;
    private TransactionData transactionData;
    private final TransactionSource cashInSource = SourceFactory.getCashInSource();
    private final TransactionDestination depositDestination = DestinationFactory.getDepositDestination();
    private String transactionNumber;

    @BeforeMethod
    public void setUo() {
        //Check CFMIntegrationEnabled
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(),userCredentials.getPassword());
        if (WebAdminActions.webAdminUsersActions().isCFMIntegrationEnabled()) {
            throw new SkipException("CFMIntegrationEnabled = 1");
        }
        WebAdminActions.loginActions().doLogout();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        //Set up sav account
        savingsAccount = new Account().setSavingsAccountData();

        //Set up transaction
        cashInSource.setAccountNumber("0-0-Dummy");
        cashInSource.setTransactionCode(TransactionCode.CASH_IN_850.getTransCode());
        depositDestination.setAccountNumber(savingsAccount.getAccountNumber());
        depositDestination.setTransactionCode(TransactionCode.NORMAL_DEPOSIT_211.getTransCode());
        depositDestination.setAmount(cashInSource.getAmount());

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the account data
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();

        String bankBranch = SettingsPage.viewUserPage().getBankBranch();

        savingsAccount.setBankBranch(bankBranch);
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        Actions.loginActions().doLogOut();


        // Create a client
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK and Savings account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();
        countedCashValue = Actions.cashDrawerAction().getCountedCashValueWithoutFormat();

        Actions.loginActions().doLogOutProgrammatically();

        // Retrieve the current and available balance from account after transaction is performed
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsAccount);
        balanceData = AccountActions.retrievingAccountData().getBalanceData();
        balanceData.setAvailableBalance(0.00);
        balanceData.setCurrentBalance(0.00);
        Actions.loginActions().doLogOut();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-",
                0.0,
                depositDestination.getAmount());

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.transactionActions().openProofDateLoginModalWindow();
        String postingDate = Pages.tellerModalPage().getProofDateValue();
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().setCashInSource(cashInSource);
        Actions.transactionActions().setDepositDestination(depositDestination, 0);
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        transactionNumber = WebAdminActions.webAdminTransactionActions().getTransactionNumber(userCredentials, savingsAccount);
        Pages.tellerModalPage().clickCloseButton();
        Actions.loginActions().doLogOutProgrammatically();
    }


    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 19438, testRunName = TEST_RUN_NAME)
    @Test(description = "C19438, [CD] EC Cash In with Cash Drawer")
    @Severity(SeverityLevel.CRITICAL)
    public void cDCashInWithCashDrawer() {

        logInfo("Step 1: Log in to the system as the user from preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Journal screen and log in to proof date with teller + cash drawer mentioned above");
        Actions.transactionActions().openProofDateLoginModalWindow();
        Actions.transactionActions().doLoginProofDate();
        Pages.aSideMenuPage().clickJournalMenuItem();

        logInfo("Step 3: Search for transaction from preconditions and open its details");
        Actions.journalActions().applyFilterByTransactionNumber(transactionNumber);
        Pages.journalPage().waitForMainSpinnerInvisibility();

        Actions.journalActions().clickLastTransaction();
        Pages.journalPage().waitForMainSpinnerInvisibility();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        Pages.journalDetailsPage().waitForLoadingSpinnerInvisibility();
        TestRailAssert.assertEquals(Actions.journalActions().getTransactionState(),"Void",
                new CustomStepResult("Transaction state hasn't changed", "Transaction state has changed"));

        logInfo("Step 5: Go to cash drawer and verify its:\n" +
                "- denominations\n" +
                "- total cash in");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        double actualCashValue = Actions.cashDrawerAction().getCountedCashValueWithoutFormat();
        TestRailAssert.assertEquals(actualCashValue,countedCashValue ,
                new CustomStepResult("Cash balance is valid","Cash balance is not valid"));

        logInfo("Step 6: Go to account used in deposit item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsAccount);
        TestRailAssert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                balanceData.getCurrentBalance(), new CustomStepResult("CHK account current balance is correct!",
                        "CHK account current balance is not correct!"));
        TestRailAssert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                balanceData.getAvailableBalance(), new CustomStepResult("CHK account available balance is correct!",
                        "CHK account available balance is not correct!"));

        logInfo("Step 7: Open account on the Transactions tab and verify the committed transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getEcColumnValueForJournal(1).equals("EC"),
                new CustomStepResult("'EC' is in 'EC' column", "'EC' is not in 'EC' column"));
        TestRailAssert.assertEquals(actualTransactionData, transactionData, new CustomStepResult("Transaction data match!",
                "Transaction data doesn't match!"));
    }
}
