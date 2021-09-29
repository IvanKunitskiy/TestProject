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
import com.nymbus.newmodels.generation.transactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.transactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21743_Process433PayoffTransactionTest extends BaseTest {

    private String loanAccountNumber;
    private final TransactionSource transactionSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination transactionDestination = DestinationFactory.getMiscCreditDestination();
    private final String localDate = DateTime.getLocalDateWithPattern("MM/dd/yyyy");

    @BeforeMethod
    public void preCondition() {

        // Get loan account number
        loanAccountNumber = getLoanAccountNumberFromWebAdmin();

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();

        // Set up the transaction data
        transactionSource.setAccountNumber(checkingAccount.getAccountNumber());
        transactionSource.setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transactionDestination.setAccountNumber(loanAccountNumber);
        transactionDestination.setTransactionCode(TransactionCode.PAYOFF_433.getTransCode());

        // Login to the system and set the chk product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create checking, loan account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C21743, Process 433 payoff transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void manuallyChangeInterestRateOnNewLoan() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getPassword(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' screen");
        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        String postingDate = Pages.tellerModalPage().getProofDateValue();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: \t\n" +
                "Fill in the following fields and click the [Commit Transaction] :\n" +
                "\n" +
                "Sources -> Misc Debit:\n" +
                "\n" +
                "\"Account Number\" - active CHK or SAV account from precondition\n" +
                "\"Transaction Code\" - specify trancode (\"114 - Loan Payment\" for CHK or \"214 - Loan Payment\" for SAV)\n" +
                "Amount - specify the same amount as \"Amount\" from Destinations\n" +
                "Destinations -> Misc Credit:\n" +
                "\n" +
                "Account number - Loan account from precondition\n" +
                "\"Transaction Code\" - \"433 - Payoff\"\n" +
                "\"Amount\" - amount from \"Payoff Amount\" field on the \"Account Quick View\" screen\n" +
                "Effective Date = Current Business Date");
        logInfo("Step 5: Close Transaction Receipt popup");
        int tempIndex = 1;

        Pages.tellerPage().clickMiscDebitButton();
        Actions.transactionActions().fillSourceAccountNumber(transactionSource.getAccountNumber(), tempIndex);
        Actions.transactionActions().fillSourceAccountCode(transactionSource.getTransactionCode(), tempIndex);

        Pages.tellerPage().clickMiscCreditButton();
        Actions.transactionActions().fillDestinationAccountNumber(transactionDestination.getAccountNumber(), tempIndex);
        Actions.transactionActions().fillDestinationAccountCode(transactionDestination.getTransactionCode(), tempIndex);

        double payoffAmount = Actions.transactionActions().getPayoffAmount();
        TransactionData transactionData = new TransactionData(postingDate, localDate, "", 0.00, payoffAmount);

        Actions.transactionActions().fillSourceAmount(String.valueOf(payoffAmount), tempIndex);
        Actions.transactionActions().fillDestinationAmount(String.valueOf(payoffAmount), tempIndex);
        Pages.tellerPage().setEffectiveDate(localDate);
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.supervisorModalPage().inputLogin(userCredentials.getUserName());
        Pages.supervisorModalPage().inputPassword(userCredentials.getPassword());
        Pages.supervisorModalPage().clickEnter();

        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        logInfo("Step 6: Open loan account on Transactions tab and verify that transaction is written on the transactions history page");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccountNumber);
        Pages.accountDetailsPage().clickTransactionsTab();

        int index = 1;
        String postingDateValue = Pages.accountTransactionPage().getPostingDateValue(index);
        String effectiveDateValue = Pages.accountTransactionPage().getEffectiveDateValue(index);
        String balanceIntegerPart = Pages.accountTransactionPage().getBalanceValueForLoanAccount(index);
        String balanceFractionalPart = Pages.accountTransactionPage().getBalanceFractionalValueForLoanAccountForLoanAccount(index);
        double balance = Double.parseDouble(balanceIntegerPart + balanceFractionalPart);
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(index);

        Assert.assertEquals(transactionData.getPostingDate(), postingDateValue);
        Assert.assertEquals(transactionData.getEffectiveDate(), effectiveDateValue);
        Assert.assertEquals(transactionData.getBalance(), balance);
        Assert.assertEquals(transactionDestination.getTransactionCode(), transactionCode);

        logInfo("Step 7: Open loan account on Details tab and verify:\n" +
                "- Current Balance\n" +
                "- Accrued Interest\n" +
                "- Account Status\n" +
                "- Payoff Amount");
        Pages.accountDetailsPage().clickDetailsTab();
        Assert.assertEquals(Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance()), 0.00, "'Current balance' is not valid");
        Assert.assertEquals(Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest()), 0.00, "'Accrued interest' is not valid");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountStatus(), "Closed", "'Account status' is not valid");
        Pages.accountDetailsPage().clickAmountDueInquiryButton();
        Assert.assertEquals(Double.parseDouble(Pages.amountDueInquiryModalPage().getPayoffAmount()), 0.00, "'Payoff amount' is not valid");
        Pages.amountDueInquiryModalPage().clickCloseButton();
        Pages.amountDueInquiryModalPage().waitForModalWindowInVisibility();

        logInfo("Step 8: Go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 9: Look through the Maintenance History records and make sure that there is information about interest rate change");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Status") >= 1,
                "'Account Status' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Account Status", 1),
                "Closed", "'Account Status' is not 'Closed'");
    }

    private String getLoanAccountNumberFromWebAdmin() {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        String accountNumber = WebAdminActions.webAdminUsersActions().getLoanAccountNumber();

        WebAdminActions.loginActions().doLogout();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        return accountNumber;
    }
}
