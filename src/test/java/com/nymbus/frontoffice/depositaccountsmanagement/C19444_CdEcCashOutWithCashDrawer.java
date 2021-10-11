package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
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
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C19444_CdEcCashOutWithCashDrawer extends BaseTest {

    IndividualClient client;
    private Account chkAccount;
    private TransactionData transactionData;
    BalanceData balanceData;
    private final TransactionSource withdrawalSource = SourceFactory.getWithdrawalSource();
    private final TransactionDestination cashOutDestination = DestinationFactory.getCashOutDestination();

    @BeforeMethod
    public void preConditions(){
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
        Actions.transactionActions().setCashOutDestination(cashOutDestination);
        Actions.transactionActions().setWithDrawalSource(withdrawalSource, 0);
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductor().waitModalWindow();
        Pages.verifyConductor().clickVerifyButton();
        Pages.tellerPage().waitForPrintReceipt();
        Pages.tellerPage().closeModal();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-",
                depositTransactionAmount - cashOutDestination.getAmount(),
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

        logInfo("Step 3: Search for transaction from preconditions and open its details");

        logInfo("Step 4: Click [Error Correct] button");

        logInfo("Step 5: Go to Cash Drawer screen, search for cash drawer used in teller session and verify its:\n" +
                "- denominations\n" +
                "- total cash out");

        logInfo("Step 6: Go to account used in withdrawal item and verify its:\n" +
                "- current balance\n" +
                "- available balance\n" +
                "- Transactions history");

        logInfo("Step 7: Open account on the Transactions tab and verify the Error corrected transaction");

    }

}
