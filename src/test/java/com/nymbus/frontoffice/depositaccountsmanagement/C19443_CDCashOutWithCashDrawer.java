package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.checkerframework.checker.units.qual.C;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C19443_CDCashOutWithCashDrawer extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private Transaction creditTransaction;
    private Transaction debitTransaction;
    private Address seasonalAddress;
    private String date;
    private String cashRecycler;


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
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        Account chkAccount = new Account().setCHKAccountData();

        // Set up transactions
        final double depositTransactionAmount = 1001.00;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(depositTransactionAmount);
        depositTransaction.getTransactionSource().setAmount(depositTransactionAmount);

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
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 19443, testRunName = TEST_RUN_NAME)
    @Test(description = "C19443, [CD] Cash Out with Cash Drawer")
    @Severity(SeverityLevel.CRITICAL)
    public void cdCashOutWithCashDrawer() {
        logInfo("Step 1: Log in to the system as the user from preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select Cash Out Fund Type");
        Pages.tellerPage().clickCashOutButton();

        logInfo("Step 4: Fill in Cash Denominations that is > $0.00 with amount < or equal to Available Balance of Account's from Precondition#2\n" +
                "and click OK button");
        Pages.cashInOutModalWindowPage().typeHundredsAmountValue("100.00");
        Pages.cashInOutModalWindowPage().typeFiftiesAmountValue("100.00");

        Pages.cashInOutModalWindowPage().clickOKButton();

        logInfo("Step 5: Verify GL account populated for Cash out item");
        TestRailAssert.assertEquals(Pages.tellerPage().getDestinationAccountNumber(), "0-0-Dummy",
                new CustomStepResult("Destination account number is valid", "Destination account number is not valid"));

        logInfo("Step 6: Select the following fund type in the Source\n" +
                "- Source: Withdrawal");
        Pages.tellerPage().clickWithdrawalButton();
        Actions.transactionActions().fillSourceAccountNumber(chkAccount.getAccountNumber(), 1);

        logInfo("Step 7: Select account from preconditions and specify amount = Cash Out item amount, that can be covered by money in cash drawer's denominations");
        Actions.transactionActions().fillSourceAmount("150.00", 1);

        logInfo("Step 8: Click [Commit Transaction] button");
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductor().waitModalWindow();

        logInfo("Step 9: Specify any client in the opened verify popup (e.g. owner of the withdrawal account) and submit verify screen");
        Pages.verifyConductor().clickVerifyButton();
        Pages.tellerPage().waitForPrintReceipt();

        logInfo("Step 10: Go to cash drawer and verify its:\n" +
                "- denominations\n" +
                "- total cash out");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        SelenideTools.sleep(3);

        logInfo("Step 11: Go to account used in withdrawal item and verify its:\n" +
                "- current balance\n" +
                "- available balance");

        logInfo("Step 12: Open account on the Transactions tab and verify the committed transaction");

    }
}
