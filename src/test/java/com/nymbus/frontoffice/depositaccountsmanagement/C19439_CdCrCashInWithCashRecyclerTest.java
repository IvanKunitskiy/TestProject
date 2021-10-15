package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
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
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C19439_CdCrCashInWithCashRecyclerTest extends BaseTest {
    private String cashRecycler;
    IndividualClient client;
    private Account chkAccount;
    private TransactionData transactionData;
    BalanceData balanceData;
    CashDrawerData cashDrawerData;
    CashDrawerData cashRecyclerData;
    private final TransactionDestination depositDestination = DestinationFactory.getDepositDestination();
    private final TransactionSource cashInSource = SourceFactory.getCashInSource();

    @BeforeMethod
    public void preConditions() {

        //Check CFMIntegrationEnabled
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        if (WebAdminActions.webAdminUsersActions().isCFMIntegrationEnabled()) {
            throw new SkipException("CFMIntegrationEnabled = 1");
        }

        cashRecycler = WebAdminActions.webAdminUsersActions().getCashRecyclerNameByRawIndex(1);
        System.out.println(WebAdminActions.webAdminUsersActions().getCashRecyclerNameByRawIndex(1) + " -----------");
        WebAdminActions.loginActions().doLogout();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up transactions
        depositDestination.setAccountNumber(chkAccount.getAccountNumber());
        depositDestination.setAmount(cashInSource.getAmount());
        depositDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

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

        // Get cash recycler data and cash drawer data
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);

        Actions.cashDrawerAction().selectSpecificCashDrawer(cashRecycler);
        cashRecyclerData = Actions.cashDrawerAction().getCashDrawerData();

        Actions.cashDrawerAction().selectSpecificCashDrawer(Constants.USERNAME);
        cashDrawerData = Actions.cashDrawerAction().getCashDrawerData();
        Actions.loginActions().doLogOutProgrammatically();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+",
                depositTransactionAmount,
                cashInSource.getAmount());
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 19439, testRunName = TEST_RUN_NAME)
    @Test(description = "C19439, [CD+CR] Cash In with Cash Recycler")
    @Severity(SeverityLevel.CRITICAL)
    public void cdCrCashInWithCashRecycler() {
        logInfo("Step 1: Log in to the system as the user from preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Teller screen and log in to proof date with:\n" +
                "- cash drawer of the current user\n" +
                "- cash recycler from preconditions (select any side)");
        Actions.transactionActions().goToTellerPage();
        Pages.tellerModalPage().clickCashRecycler();
        Pages.tellerModalPage().clickCashRecyclerItem(cashRecycler);
        Pages.tellerModalPage().clickSide();
        Pages.tellerModalPage().clickLeftSide();
        Pages.tellerModalPage().clickEnterButton();

        logInfo("Step 3: Select Cash In Fund Type and verify that Cash Machine radio button is checked by default");
        logInfo("Step 4: Fill in Cash Denominations with any amount and click OK button");
        Actions.transactionActions().setCashInSource(cashInSource);

        logInfo("Step 5: Verify GL account populated for Cash in item");
        TestRailAssert.assertEquals(Pages.tellerPage().getAccountNumber(1), "6000100-Machine Service",
                new CustomStepResult("Source account number is valid", "Source account number is not valid"));

        logInfo("Step 6: Select the following fund type in the destination\n" +
                "- Destination: Deposit");
        logInfo("Step 7: Select account from preconditions in Destination item and specify amount the same as it was specified for Cash In item");
        Actions.transactionActions().setDepositDestination(depositDestination, 0);

        logInfo("Step 8: Click [Commit Transaction] button");
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductor().waitModalWindow();

        logInfo("Step 9: Specify any client in the opened verify popup (e.g. owner of the deposit account) and submit verify screen");
        Pages.verifyConductor().clickVerifyButton();
        Pages.tellerPage().waitForPrintReceipt();
        Pages.tellerPage().closeModal();

        logInfo("Step 10: Go to Cash Drawer screen, search for cash recycler used in teller session and verify its:\n" +
                "- denominations\n" +
                "- total cash in");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);

        Actions.cashDrawerAction().selectSpecificCashDrawer(cashRecycler);
        System.out.println((cashRecyclerData.getCashIn()) + 100 + " ---------------");
        System.out.println((cashRecyclerData.getHundredsAmount() + 100) + " ---------------");
        System.out.println(Actions.cashDrawerAction().getCashDrawerData().getCashIn() + " ---------------");
        System.out.println(Actions.cashDrawerAction().getCashDrawerData().getHundredsAmount() + " ---------------");

        TestRailAssert.assertEquals(cashRecyclerData.getCashIn() + 100, Actions.cashDrawerAction().getCashDrawerData().getCashIn(),
                new CustomStepResult("'Cash In' is valid", "'Cash In' is invalid"));
        TestRailAssert.assertEquals(cashRecyclerData.getHundredsAmount() + 100, Actions.cashDrawerAction().getCashDrawerData().getHundredsAmount(),
                new CustomStepResult("Denominations is valid", "Denominations is not valid"));

        logInfo("Step 11: Go to Cash Drawer screen, search for cash drawer of User that was logged in to teller session and verify its:\n" +
                "- denominations\n" +
                "- total cash in");
        System.out.println(cashDrawerData.getCashIn() + " ---------------");
        System.out.println(cashDrawerData.getHundredsAmount() + " ---------------");
        System.out.println(Actions.cashDrawerAction().getCashDrawerData().getCashIn() + " ---------------");
        System.out.println(Actions.cashDrawerAction().getCashDrawerData().getHundredsAmount() + " ---------------");

        Actions.cashDrawerAction().selectSpecificCashDrawer(Constants.USERNAME);
        TestRailAssert.assertEquals(cashDrawerData.getCashIn(), Actions.cashDrawerAction().getCashDrawerData().getCashIn(),
                new CustomStepResult("'Cash In' is not changed", "'Cash In' is changed!"));
        TestRailAssert.assertEquals(cashDrawerData.getHundredsAmount(), Actions.cashDrawerAction().getCashDrawerData().getHundredsAmount(),
                new CustomStepResult("Denominations is not changed", "Denominations is changed!"));

        logInfo("Step 12: Go to account used in deposit item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);

        TestRailAssert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                balanceData.getCurrentBalance() + cashInSource.getAmount(),
                new CustomStepResult("CHK account current balance is correct", "CHK account current balance is not correct!"));
        TestRailAssert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                balanceData.getAvailableBalance() + cashInSource.getAmount(),
                new CustomStepResult("CHK account available balance is correct", "CHK account available balance is not correct!"));

        logInfo("Step 13: Open account on the Transactions tab and verify the committed transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        TestRailAssert.assertEquals(actualTransactionData, transactionData,
                new CustomStepResult("Transaction data is matching", "Transaction data doesn't match!"));
    }

}
