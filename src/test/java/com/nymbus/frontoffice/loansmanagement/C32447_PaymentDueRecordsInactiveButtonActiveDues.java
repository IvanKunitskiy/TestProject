package com.nymbus.frontoffice.loansmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
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
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C32447_PaymentDueRecordsInactiveButtonActiveDues extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private String clientRootId;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final String TEST_RUN_NAME = "Loans Management";

    @BeforeMethod
    public void preConditions() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        chkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());

        // Set proper dates
        String localDate = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
        loanAccount.setDateOpened(DateTime.getDateMinusMonth(localDate, 2));
        System.out.println(loanAccount.getDateOpened() + " ------------");
        loanAccount.setNextPaymentBilledDueDate(DateTime.getDatePlusMonth(loanAccount.getDateOpened(), 1));
        loanAccount.setPaymentBilledLeadDays("1");
        loanAccount.setCycleLoan(false);
        chkAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);

        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Set up deposit transaction
        int depositTransactionAmount = 12000;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(depositTransactionAmount);
        depositTransaction.getTransactionSource().setAmount(depositTransactionAmount);

        // 109 - transaction
        int transaction109Amount = 12000;
        Transaction transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        // Perform deposit transaction
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Re-login to refresh teller session
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform '109 - deposit' transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(transaction_109);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();

        // Generate 2 Payment Due record
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);
        Actions.loginActions().doLogOutProgrammatically();
    }

    @TestRailIssue(issueID = 32447, testRunName = TEST_RUN_NAME)
    @Test(description = "C32447, Payment Due Records: 'Inactive' button - Active Dues")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordsInactiveButtonActiveDues() {
        logInfo("Step 1: Log in to the NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Find and select Loan from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        logInfo("Step 3: Go to the 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 4: Click on the Oldest Payment Due record in the 'Payment Due' section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecordByIndex(2);
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().isInactiveButtonVisible(),
                new CustomStepResult("'Inactive' button is visible", "'Inactive' button is not visible"));


        logInfo("Step 5: Click on the 'Inactive' button");
        Pages.accountPaymentInfoPage().clickInactiveButton();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().isMakePaymentDueInactiveModalVisible(),
                new CustomStepResult("'Are you sure you want to make this payment due inactive?' modal visible",
                        "'Are you sure you want to make this payment due inactive?' modal is not visible"));

        logInfo("Step 6: Click on the 'Yes' button");
        Pages.accountPaymentInfoPage().clickYesButtonOnMakePaymentDueInactive();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getErrorText().equals("Future active payment due exists"),
                new CustomStepResult("Error message is present", "Error message is not present"));

        logInfo("Step 7: Click on the Latest Payment Due record in the 'Payment Due' section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecordByIndex(1);
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().isInactiveButtonVisible(),
                new CustomStepResult("'Inactive' button is visible", "'Inactive' button is not visible"));

        logInfo("Step 8: Click on the 'Inactive' button");
        Pages.accountPaymentInfoPage().clickInactiveButton();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().isMakePaymentDueInactiveModalVisible(),
                new CustomStepResult("'Are you sure you want to make this payment due inactive?' modal visible",
                        "'Are you sure you want to make this payment due inactive?' modal is not visible"));

        logInfo("Step 9: Click on the 'Yes' button");
        Pages.accountPaymentInfoPage().clickYesButtonOnMakePaymentDueInactive();
        Selenide.sleep(Constants.FIVE_SECONDS_TIMEOUT);
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getSpecificDueStatus(1).equals("Inactive"),
                new CustomStepResult("Error message is present", "Error message is not present"));

        String dueDate1 = Pages.accountPaymentInfoPage().getDateDueOfSpecificRecord(1);

        logInfo("Step 10: Go to the 'Details' tab");
        Pages.accountDetailsPage().clickDetailsTab();

        logInfo("Step 11: Check the 'Next Payment Billed Due Date' field");
        String nextPaymentBilledDueDate1 = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        TestRailAssert.assertTrue(nextPaymentBilledDueDate1.equals(dueDate1),
                new CustomStepResult("'Next Payment Billed Due Date' is  valid", "'Next Payment Billed Due Date' is not valid"));

        logInfo("Step 12: Go to the 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 13: Click on the following Active Payment Due record in the \"Payment Due\" section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecordByIndex(2);
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().isInactiveButtonVisible(),
                new CustomStepResult("'Inactive' button is visible", "'Inactive' button is not visible"));


        logInfo("Step 14: Click on the 'Inactive' button");
        Pages.accountPaymentInfoPage().clickInactiveButton();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().isMakePaymentDueInactiveModalVisible(),
                new CustomStepResult("'Are you sure you want to make this payment due inactive?' modal visible",
                        "'Are you sure you want to make this payment due inactive?' modal is not visible"));

        logInfo("Step 15: Click on the 'Yes' button");
        Pages.accountPaymentInfoPage().clickYesButtonOnMakePaymentDueInactive();
        Selenide.sleep(Constants.FIVE_SECONDS_TIMEOUT);
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getSpecificDueStatus(2).equals("Inactive"),
                new CustomStepResult("Error message is present", "Error message is not present"));

        String dueDate2 = Pages.accountPaymentInfoPage().getDateDueOfSpecificRecord(2);

        logInfo("Step 16: Go to the 'Details' tab");
        Pages.accountDetailsPage().clickDetailsTab();

        logInfo("Step 17: Check the 'Next Payment Billed Due Date' field");
        String nextPaymentBilledDueDate2 = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        TestRailAssert.assertTrue(nextPaymentBilledDueDate2.equals(dueDate2),
                new CustomStepResult("'Next Payment Billed Due Date' is  valid", "'Next Payment Billed Due Date' is not valid"));
    }
}
