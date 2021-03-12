package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.transfer.LoanPaymentTransfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21732_CreateNewAutomaticLoanPaymentTest extends BaseTest {

    private IndividualClient client;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private LoanPaymentTransfer loanPaymentTransfer;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        Account loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setNextPaymentBilledDueDate(DateTime.getLocalDatePlusMonthsWithPatternAndLastDay(loanAccount.getDateOpened(), 1, "MM/dd/yyyy"));
        String dateOpened = loanAccount.getDateOpened();
        loanAccount.setDateOpened(DateTime.getDateMinusDays(dateOpened, 1));
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Set transaction data
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionSource().setAmount(200);
        transaction.getTransactionDestination().setAccountNumber(checkingAccount.getAccountNumber());
        transaction.getTransactionDestination().setAmount(200);
        transaction.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        loanPaymentTransfer = transferBuilder.getLoanPaymentTransfer();
        loanPaymentTransfer.setFromAccount(checkingAccount);
        loanPaymentTransfer.setToAccount(loanAccount);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Set the product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create checking account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        Actions.loginActions().doLogOut();

        // Perform transaction
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 21732, testRunName = TEST_RUN_NAME)
    @Test(description = "C21732, Create new automatic loan payment")
    @Severity(SeverityLevel.CRITICAL)
    public void createNewAutomaticLoanPayment() {

        logInfo("Step 1: Log in to NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open 'Client' from preconditions on the 'Accounts' tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());

        logInfo("Step 3: Go to 'Transfers' page");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 4: Click 'New Transfer'");
        Pages.transfersPage().clickNewTransferButton();

        logInfo("Step 5: Select 'Transfer Type:' = Loan Payment");
        TransfersActions.addNewTransferActions().setLoanPaymentTransferType(loanPaymentTransfer);

        logInfo("Step 6: Specify:\n" +
                "- Expiration Date (optional) = should be after payment date (f.e. after maturity date on loan account)\n" +
                "- From Account = CHK or SAV account from preconditions with enough money for transfer\n" +
                "- To Account = Loan account from preconditions\n" +
                "- Advance days from due date = any number of days if need to make a payment in advance\n" +
                "- Amount = no need to populate (the system will take \"Total Next Due\" amount)\n" +
                "- EFT charge code (optional) = EFT charge\n" +
                "- Transfer Charge (optional) = any amount (charge amount for transfer)");
        Pages.newTransferPage().setExpirationDate(loanPaymentTransfer.getExpirationDate());
        TransfersActions.addNewTransferActions().setLoanPaymentFromAccount(loanPaymentTransfer);
        TransfersActions.addNewTransferActions().setLoanPaymentToAccount(loanPaymentTransfer);
        Pages.newTransferPage().setAdvanceDaysFromDueDate(loanPaymentTransfer.getAdvanceDaysFromDueDate());
        TransfersActions.addNewTransferActions().setEftChargeCode(loanPaymentTransfer);
        Pages.newTransferPage().setTransferCharge(loanPaymentTransfer.getTransferCharge());

        logInfo("Step 7: Click 'Save'");
        Pages.newTransferPage().clickSaveButton();
        Assert.assertTrue(Pages.viewTransferPage().getFromAccount().contains(loanPaymentTransfer.getFromAccount().getAccountNumber()),
                "'From Account' is not valid");
        Assert.assertTrue(Pages.viewTransferPage().getToAccount().contains(loanPaymentTransfer.getToAccount().getAccountNumber()),
                "'To Account' is not valid");
        Assert.assertEquals(Pages.viewTransferPage().getTransferCharge(), loanPaymentTransfer.getTransferCharge(),
                "'Transfer Charge' is not valid");
        Assert.assertEquals(Pages.viewTransferPage().getCreationDate(), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "'Creation Date' is not equal to current date");
        Assert.assertEquals(Pages.viewTransferPage().getAdvanceDaysFromDueDate(), loanPaymentTransfer.getAdvanceDaysFromDueDate(),
                "'Advance Days From Due Date' is not equal to current date");
        Assert.assertEquals(Pages.viewTransferPage().getEftChargeCode(), loanPaymentTransfer.getEftChargeCode(),
                "'EFT charge code' is not equal to current date");
    }
}
