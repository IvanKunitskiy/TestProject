package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.transfer.ExternalLoanPaymentTransfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitMiscCreditBuilder;

import com.nymbus.newmodels.generation.transfers.TransferBuilder;
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
public class C46252_ClientTransfersExternalLoanPaymentVerifyFieldsForTransferTypeExternalLoanPayment extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private String clientID;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private ExternalLoanPaymentTransfer externalLoanPaymentTransfer;
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
        loanAccount.setDateOpened(DateTime.getDateMinusMonth(localDate, 1));
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

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        externalLoanPaymentTransfer = transferBuilder.getExternalLoanPaymentTransfer();
        externalLoanPaymentTransfer.setInternalAccount(loanAccount);


        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);

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

        Actions.loginActions().doLogOutProgrammatically();
    }

    @TestRailIssue(issueID = 46252, testRunName = TEST_RUN_NAME)
    @Test(description = "C46252, Client - Transfers: External Loan Payment: Verify fields for Transfer Type == 'External Loan Payment'")
    @Severity(SeverityLevel.CRITICAL)
    public void clientTransfersExternalLoanPaymentVerifyFieldsForTransferTypeExternalLoanPayment() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Clients page and search for the client from the preconditions");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);

        logInfo("Step 3: Open Clients Profile on the Transfers tab");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 4: Click [New Transfer] button and select 'External Loan Payment' in the 'Transfer Type' drop down");
        Pages.transfersPage().clickNewTransferButton();
        TransfersActions.addNewTransferActions().setExternalLoanPaymentTransferType(externalLoanPaymentTransfer);

        TestRailAssert.assertTrue(Pages.newTransferPage().isExpirationDateFieldVisible(),
                new CustomStepResult("'Expiration Date' is visible", "'Expiration Date' is not visible"));
        TestRailAssert.assertTrue(Pages.newTransferPage().isInternalAccountFieldVisible(),
                new CustomStepResult("'Internal Account' is visible", "'Internal Account' is not visible"));
        TestRailAssert.assertTrue(Pages.newTransferPage().isAchBankAccountTypeVisible(),
                new CustomStepResult("'ACH Bank Account Type' is visible", "'ACH Bank Account Type' is not visible"));
        TestRailAssert.assertTrue(Pages.newTransferPage().isAchBankRoutingNumberFieldVisible(),
                new CustomStepResult("'ACH Bank Routing Number' is visible", "'ACH Bank Routing Number' is not visible"));
        TestRailAssert.assertTrue(Pages.newTransferPage().isAchBankAccountNumberFieldVisible(),
                new CustomStepResult("'ACH Bank Account Number' is visible", "'ACH Bank Account Number' is not visible"));
        TestRailAssert.assertTrue(Pages.newTransferPage().isAchTransferLeadDaysFieldVisible(),
                new CustomStepResult("'ACH Transfer Lead Days' is visible", "'ACH Transfer Lead Days' is not visible"));

        logInfo("Step 5: Verify the following fields:\n" +
                "Expiration Date");
        TestRailAssert.assertFalse(Pages.newTransferPage().isExpirationDateFieldRequired(),
                new CustomStepResult("'Expiration Date' is optional", "'Expiration Date' is required"));

        logInfo("Step 6: Internal Account");
        TestRailAssert.assertTrue(Pages.newTransferPage().isInternalAccountFieldRequired(),
                new CustomStepResult("'Internal Account' is required", "'Internal Account' is not required"));

        logInfo("Step 7: ACH Bank Account Type");
        TransfersActions.addNewTransferActions().verifyAchBankAccountTypeOptions();

        logInfo("Step 8: ACH Bank Routing Number");
        TestRailAssert.assertTrue(Pages.newTransferPage().isAchBankRoutingNumberFieldRequired(),
                new CustomStepResult("'ACH Bank Routing Number", "'ACH Bank Routing Number' is not required"));

        logInfo("Step 9: ACH Bank Account Number");
        TestRailAssert.assertTrue(Pages.newTransferPage().isAchBankAccountNumberFieldRequired(),
                new CustomStepResult("'ACH Bank Account Number", "'ACH Bank Account Number' is not required"));

        logInfo("Step 10: ACH Transfer Lead Days");
        TestRailAssert.assertFalse(Pages.newTransferPage().isAchTransferLeadDaysFieldRequired(),
                new CustomStepResult("'ACH Transfer Lead Days' is optional", "'ACH Transfer Lead Days' is not required"));
    }
}
