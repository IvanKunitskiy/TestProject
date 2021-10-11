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
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C19437_CDCashInWithCashDrawer extends BaseTest {
    private IndividualClient client;
    private Account savingsAccount;
    private double countedCashValue;
    private BalanceData balanceData;
    private TransactionData transactionData;
    private final TransactionSource cashInSource = SourceFactory.getCashInSource();
    private final TransactionDestination depositDestination = DestinationFactory.getDepositDestination();

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
        balanceData.setAvailableBalance(100.00);
        balanceData.setCurrentBalance(100.00);
        Actions.loginActions().doLogOut();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+",
                depositDestination.getAmount(),
                depositDestination.getAmount());
    }


    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 19437, testRunName = TEST_RUN_NAME)
    @Test(description = "C19437, [CD] Cash In with Cash Drawer")
    @Severity(SeverityLevel.CRITICAL)
    public void cDCashInWithCashDrawer() {

        logInfo("Step 1: Log in to the system as the user from preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().openProofDateLoginModalWindow();
        String postingDate = Pages.tellerModalPage().getProofDateValue();
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select Cash In Fund Type");
        logInfo("Step 4: Fill in Cash Denominations with any amount and click OK button");
        Actions.transactionActions().setCashInSource(cashInSource);

        logInfo("Step 5: Verify GL account populated for Cash in item");
        TestRailAssert.assertEquals(Pages.tellerPage().getAccountNumber(1), "0-0-Dummy",
                new CustomStepResult("GL account valid", "GL account is not valid"));

        logInfo("Step 6: Select the following fund type in the destination\n" +
                "- Destination: Deposit");
        logInfo("Step 7: Select account from preconditions in Destination item and specify amount the same as it was specified for Cash In item");
        Actions.transactionActions().setDepositDestination(depositDestination, 0);

        logInfo("Step 8: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 9: Specify any client in the opened verify popup (e.g. owner of the deposit account) and submit verify screen");
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerModalPage().clickCloseButton();

        logInfo("Step 10: Go to cash drawer and verify its:\n" +
                "- denominations\n" +
                "- total cash in");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        double actualCashValue = Actions.cashDrawerAction().getCountedCashValueWithoutFormat();
        TestRailAssert.assertEquals(actualCashValue,countedCashValue + depositDestination.getAmount(),
                new CustomStepResult("Cash balance is valid","Cash balance is not valid"));

        logInfo("Step 11: Go to account used in deposit item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsAccount);
        TestRailAssert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(), balanceData.getCurrentBalance(),
                new CustomStepResult("CHK account current balance is correct", "CHK account current balance is not correct!"));
        TestRailAssert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(), balanceData.getAvailableBalance(),
                new CustomStepResult("CHK account available balance is correct", "CHK account available balance is not correct!"));

        logInfo("Step 12: Open account on the Transactions tab and verify the committed transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        TestRailAssert.assertEquals(actualTransactionData, transactionData, new CustomStepResult("Transaction data is matching", "Transaction data doesn't match!"));
    }

}
