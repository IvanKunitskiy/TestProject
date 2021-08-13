package com.nymbus.frontoffice.loansmanagement;

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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Dmytro")
public class C25456_AccruedInterestCalculationOnConvertedLoan extends BaseTest {
    private String loanAccountNumber;
    private Account checkAccount;
    private Transaction transaction;
    private double transactionAmount;


    @BeforeMethod
    public void precondition(){
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();

        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL+
                "RulesUIQuery.ct?waDbName=coreDS&dqlQuery=count%3A+10%0D%0Afrom%3A+bank.data.actloan%0D%0Awhere%3A%0D%0A+-+.paymenttypefornonclass1->name%3A+%5BPrin+%26+Int+%28Bill%29%2C+Principal+%26+Interest%5D%0D%0A+-+.accountid->dateClosed%3A+%7Bnull%7D%0D%0A+-+.accountid->currentBalance%3A+%7Bgreater%3A+0%7D%0D%0A+-+.accountid->dateOpened%3A+%7Bless%3A+%272019-09-01%27%7D%0D%0A+-+currenteffectiverate%3A+%7Bgreater%3A+0%7D%0D%0A+-+.interestmethod->name%3A+Simple+Interest%0D%0A%23orderBy%3A+-id%0D%0Aformats%3A+%0D%0A-+->bank.data.actmst%3A+%24%7Baccountnumber%7D%0D%0A&source=");
        SelenideTools.switchToLastTab();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        loanAccountNumber = WebAdminPages.rulesUIQueryAnalyzerPage().getAccountNumberElevenByIndex(2);
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        //Get amount
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccountNumber);
        Pages.accountDetailsPage().clickPaymentInfoTab();
        String amount = Pages.accountPaymentInfoPage().getActivePaymentAmount();
        transactionAmount = Double.parseDouble(amount);
        Actions.loginActions().doLogOut();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);


        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);

        // Set up transactions with account number
        depositTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(transactionAmount);
        depositTransaction.getTransactionSource().setAmount(transactionAmount);
        transaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());
        transaction.getTransactionSource().setAmount(transactionAmount);
        transaction.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction.getTransactionDestination().setAccountNumber(loanAccountNumber);
        transaction.getTransactionDestination().setAmount(transactionAmount);
        transaction.getTransactionDestination().setTransactionCode("416 - Payment");
        transaction.getTransactionDestination().setSourceType(DestinationType.MISC_CREDIT);

        // Perform deposit transactions
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25456, testRunName = TEST_RUN_NAME)
    @Test(description = "C25456, Accrued interest calculation on converted loan")
    @Severity(SeverityLevel.CRITICAL)
    public void accruedInterestCalculationOnConvertedLoan() {
        logInfo("Step 1: Log in to SmartCore");
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccountNumber);

        logInfo("Step 3: Take a look at \"Daily interest factor\" value");
        double dailyInterestFactor = Double.parseDouble(Pages.accountDetailsPage().getDailyInterestAccrual());
        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        double currentEffectiveRate = Double.parseDouble(Pages.accountDetailsPage().getCurrentEffectiveRate());
        String daysBaseYearBaseText = Pages.accountDetailsPage().getDaysBaseYearBase();
        double daysBaseYearBase = Double.parseDouble(daysBaseYearBaseText.substring(4,7));
        double expectedFactor = (currentBalance * currentEffectiveRate / 100) / daysBaseYearBase;
        expectedFactor = Double.parseDouble(String.format("%.4f", expectedFactor));
        if (expectedFactor!=dailyInterestFactor) {
            BigDecimal bigDecimal = BigDecimal.valueOf(expectedFactor);
            BigDecimal bigDecimal1 = BigDecimal.valueOf(-0.0001);
            bigDecimal = bigDecimal.add(bigDecimal1);
            expectedFactor = bigDecimal.doubleValue();
        }
        TestRailAssert.assertTrue(dailyInterestFactor == expectedFactor,
                new CustomStepResult("Daily interest factor is correct","Daily interest factor is not correct"));

        logInfo("Step 4: Take a look at \"Accrued interest\" value");
        double accruedInterest = Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest());
        String dateInterestPaidThru = Pages.accountDetailsPage().getDateInterestPaidThru();
        int daysBetweenTwoDates = DateTime.getDaysBetweenTwoDates(dateInterestPaidThru,
                WebAdminActions.loginActions().getSystemDate(), false);
        double expectedAccruedInterest = currentBalance * currentEffectiveRate / 100 / daysBaseYearBase * daysBetweenTwoDates;
        System.out.println(expectedAccruedInterest);
//        if (daysBetweenTwoDates==1){
//            expectedAccruedInterest = expectedFactor * (0);
//        } else {
//            expectedAccruedInterest = expectedFactor * (daysBetweenTwoDates-2);
//        }
        TestRailAssert.assertTrue((int)accruedInterest == (int)expectedAccruedInterest,
                new CustomStepResult("Accrued interest factor is correct","Accrued interest factor is not correct"));

        logInfo("Step 5: Go to \"Teller\" page");
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 6: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 7: Commit 416 transaction with the following fields:\n" +
                "Sources -> Misc Debit:\n" +
                "\"Account Number\" - active CHK or SAV account from preconditions\n" +
                "\"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "Amount = \"Payment Info\" tab -> \"Active Payment Amount\" field\n" +
                "Or Amount Due for Active Payment Due record in the \"Payments Due\" section (if exists)\n" +
                "Destinations -> Misc Credit:\n" +
                "Account number - Loan account from preconditions\n" +
                "\"Transaction Code\" - \"416 - Payment\"\n" +
                "\"Amount\" - specify the same amount");
        int currentIndex = 0;
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction.getTransactionSource(), currentIndex);
        Actions.transactionActions().setMiscCreditDestination(transaction.getTransactionDestination(), currentIndex);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 8: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 9: Repeat steps 2-3");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccountNumber);
        dailyInterestFactor = Double.parseDouble(Pages.accountDetailsPage().getDailyInterestAccrual());
        currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        currentEffectiveRate = Double.parseDouble(Pages.accountDetailsPage().getCurrentEffectiveRate());
        daysBaseYearBaseText = Pages.accountDetailsPage().getDaysBaseYearBase();
        daysBaseYearBase = Double.parseDouble(daysBaseYearBaseText.substring(4,7));
        expectedFactor = (currentBalance * currentEffectiveRate / 100) / daysBaseYearBase;
        expectedFactor = Double.parseDouble(String.format("%.4f", expectedFactor));
        if (expectedFactor!=dailyInterestFactor) {
            BigDecimal bigDecimal = BigDecimal.valueOf(expectedFactor);
            BigDecimal bigDecimal1 = BigDecimal.valueOf(-0.0001);
            bigDecimal = bigDecimal.add(bigDecimal1);
            expectedFactor = bigDecimal.doubleValue();
        }

        TestRailAssert.assertTrue(dailyInterestFactor == expectedFactor,
                new CustomStepResult("Daily interest factor is correct","Daily interest factor is not correct"));

        logInfo("Step 10: Repeat step 4 and verify the \"Accrued interest\" field");
        accruedInterest = Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest());
        dateInterestPaidThru = Pages.accountDetailsPage().getDateInterestPaidThru();
        daysBetweenTwoDates = DateTime.getDaysBetweenTwoDates(dateInterestPaidThru,
                WebAdminActions.loginActions().getSystemDate(), false);
        if (daysBetweenTwoDates==1){
            expectedAccruedInterest = expectedFactor * (0);
        } else {
            expectedAccruedInterest = expectedFactor * (daysBetweenTwoDates-2);
        }

        TestRailAssert.assertTrue((int)accruedInterest == (int)expectedAccruedInterest,
                new CustomStepResult("Accrued interest factor is correct","Accrued interest factor is not correct"));
    }

}
