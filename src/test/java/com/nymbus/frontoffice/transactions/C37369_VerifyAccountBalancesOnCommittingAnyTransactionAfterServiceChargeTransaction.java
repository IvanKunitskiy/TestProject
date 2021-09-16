package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C37369_VerifyAccountBalancesOnCommittingAnyTransactionAfterServiceChargeTransaction extends BaseTest {
    private String accountNumber;
    private Transaction transaction;
    private double amount = 100.00;
    private double balance;

    @BeforeMethod
    public void preCondition() {
        //Get account number with transaction
        accountNumber = WebAdminActions.webAdminUsersActions().getAccountNumberWithTransactionFor20(userCredentials);

        //Get balance
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(accountNumber);
        balance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        String productTypeValue = Pages.accountDetailsPage().getProductTypeValue();
        Actions.loginActions().doLogOut();

        //Create transactions
        transaction = new TransactionConstructor(new CashInMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAmount(amount);
        transaction.getTransactionDestination().setAccountNumber(accountNumber);
        if (productTypeValue.contains("CD")){
            transaction.getTransactionDestination().setTransactionCode("303 -");
        } else {
            transaction.getTransactionDestination().setTransactionCode("09 -");
        }
        transaction.getTransactionSource().setAmount(amount);
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 37369, testRunName = TEST_RUN_NAME)
    @Test(description = "C37369, Verify Account Balances on committing any transaction after Service Charge transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAccountBalancesOnCommittingAnyTransactionAfterIntPayment() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Commit any transaction with CHK/Savings/CD account from the precondition");
        Actions.transactionActions().createTransaction(transaction);
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        if (Pages.supervisorModalPage().isModalWindowVisible()) {
            Pages.supervisorModalPage().inputLogin(userCredentials.getUserName());
            Pages.supervisorModalPage().inputPassword(userCredentials.getPassword());
            Pages.supervisorModalPage().clickEnter();
        }
        Pages.tellerModalPage().clickCloseButton();

        logInfo("Step 4: Open account that was used in the transaction on Transactions tab and verify the committed transaction\n" +
                "Make sure that all balances are correct");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenClientByName(accountNumber);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        double transactionAmount = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(transactionAmount == amount,
                new CustomStepResult("'Amount' is valid", String.format("'Amount' is not valid. Expected %s, found - %s",
                        amount, transactionAmount)));
        double balanceValue = AccountActions.retrievingAccountData().getBalanceValue(1);
        double actualCurrentBalance = balance + amount;
        TestRailAssert.assertTrue(actualCurrentBalance == balanceValue,
                new CustomStepResult("'Balance' is valid", String.format("'Balance' is not valid. Expected %s, found - %s",
                        actualCurrentBalance, balanceValue)));
    }
}
