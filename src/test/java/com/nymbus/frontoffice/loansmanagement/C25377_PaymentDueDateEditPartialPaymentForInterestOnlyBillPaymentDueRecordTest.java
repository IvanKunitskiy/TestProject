package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.CommitmentTypeAmt;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.MiscDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import com.nymbus.util.Random;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25377_PaymentDueDateEditPartialPaymentForInterestOnlyBillPaymentDueRecordTest extends BaseTest {

    private Account loanAccount;
    private Transaction transaction_416;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private double transaction416Amount;
    private final int diff = Random.genInt(1, 5);
    private double amountDue;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        //Set up accounts
        Account chkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.INTEREST_ONLY.getPaymentAmountType());
        loanAccount.setCommitmentTypeAmt(CommitmentTypeAmt.NONE.getCommitmentTypeAmt());

        loanAccount.setCallClassCode("223322 - John sotka");
        chkAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // 109 - transaction
        int transaction109Amount = 12000;
        Transaction transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        // 416 - transaction
        transaction_416 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_416.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_416.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_416.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_416.getTransactionDestination().setTransactionCode(TransactionCode.PAYMENT_416.getTransCode());

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Set CHK product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create accounts
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        String clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Perform '109 - deposit' transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(transaction_109);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();

        // Perform non-teller transaction
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);

        // Get accrued interest
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickPaymentInfoTab();
        amountDue = Double.parseDouble(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1));
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        double interest = Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledInterest());
        double escrow = Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledEscrow());
        double min = Math.min(interest, escrow);

        // assign amount to 416 transaction more than due date
        transaction416Amount = min - diff;
        transaction_416.getTransactionDestination().setAmount(transaction416Amount);
        transaction_416.getTransactionSource().setAmount(transaction416Amount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25377, testRunName = TEST_RUN_NAME)
    @Test(description = "C25377, Payment Due Date: Edit Partial Payment for Interest Only (Bill) payment due record")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueDateEditPartialPaymentForInterestOnlyBillPaymentDueRecord() {

        logInfo("Step 1: Log in to Nymbus");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' screen");
        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Commit 416 transaction with the following fields:\n" +
                "Sources -> Misc Debit:\n" +
                "\"Account Number\" - active CHK or SAV account from preconditions\n" +
                "\"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "Amount < Payment Info -> Payments Due record from preconditions -> Amount Due\n" +
                "Destinations -> Misc Credit:\n" +
                "Account number - Loan account from preconditions\n" +
                "\"Transaction Code\" - \"416 - Payment\"\n" +
                "\"Amount\" - specify the same amount");
        Actions.transactionActions().createTransaction(transaction_416);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 7: Go to 'Payment Info'");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Check Payment Due record in the 'Payments Due' section");
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPaymentDueTypeFromRecordByIndex(1).equals(PaymentAmountType.INTEREST_ONLY.getPaymentAmountType()),
                new CustomStepResult("'Payment Due Type' is not valid", "'Payment Due Type' is valid"));
        String actualAmountDue = Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1);
        TestRailAssert.assertTrue(actualAmountDue.equals(Functions.roundAmountToTwoDecimals(amountDue - transaction416Amount)),
                new CustomStepResult("'Amount Due' is not valid", "'Amount Due' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Partially Paid"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));

        logInfo("Step 9: Select P&I payment due record from and click on the 'Edit' button in the 'Payment Due Details' section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        Pages.accountPaymentInfoPage().clickTheEditPaymentDueDetailsButton();

        logInfo("Step 10: Change 'Principal', 'Interest', 'Escrow' to the value < paid amount from step 4 and click 'Save'");
        Pages.accountPaymentInfoPage().typeEscrow(Functions.getStringValueWithOnlyDigits(transaction416Amount - diff));
        Pages.accountPaymentInfoPage().clickSavePaymentDueDetailsButton();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Partially Paid"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));

        logInfo("Step 11: Edit this record one more time and change 'Principal', 'Interest', 'Escrow' to the value > paid amount from step 4 and click 'Save'");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        Pages.accountPaymentInfoPage().clickTheEditPaymentDueDetailsButton();
        Pages.accountPaymentInfoPage().typeEscrow(Functions.getStringValueWithOnlyDigits(transaction416Amount + diff + 1));
        Pages.accountPaymentInfoPage().clickSavePaymentDueDetailsButton();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Partially Paid"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));

        logInfo("Step 12: Edit this record one more time and change 'Principal', 'Interest', 'Escrow' to the value = paid amount from step 4 and click 'save'");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        Pages.accountPaymentInfoPage().clickTheEditPaymentDueDetailsButton();
        Pages.accountPaymentInfoPage().typeInterest(Functions.getStringValueWithOnlyDigits(0.00));
        Pages.accountPaymentInfoPage().typeEscrow(Functions.getStringValueWithOnlyDigits(transaction416Amount));
        Pages.accountPaymentInfoPage().clickSavePaymentDueDetailsButton();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Paid"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));
        Pages.accountDetailsPage().clickDetailsTab();
        String nextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();

        logInfo("Step 13: Log in to the WebAdmin");
        logInfo("Step 14: Go to RulesUI Query Analyzer");
        logInfo("Step 15: Search with DQL");
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        String interestNextPaymentDate = WebAdminActions.webAdminUsersActions().getInterestNextPaymentDate(loanAccount.getAccountNumber());
        String dateWithFormat = DateTime.getDateWithFormat(interestNextPaymentDate, "yyyy-MM-dd", "MM/dd/yyyy");
        TestRailAssert.assertTrue(dateWithFormat.equals(nextPaymentBilledDueDate),
                new CustomStepResult("'principalnextpaymentdate' is not valid", "'principalnextpaymentdate' is valid"));
    }
}
