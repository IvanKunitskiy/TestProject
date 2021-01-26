package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Generator;
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
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21733_ViewEditNewAutomaticLoanPaymentTest extends BaseTest {

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

        // Add new loan transfer
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        TransfersActions.addNewTransferActions().addNewLoanPaymentTransfer(loanPaymentTransfer);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C21733, View / edit new automatic loan payment")
    @Severity(SeverityLevel.CRITICAL)
    public void viewEditNewAutomaticLoanPayment() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());

        logInfo("Step 3: Go to 'Transfers' page");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 4: Select 'Loan Payment' transfer and pay attention at the Loan Transfer field");
        Pages.transfersPage().clickTransferInTheListByType(loanPaymentTransfer.getTransferType().getTransferType());

        logInfo("Step 5: Click on the 'Edit' button");
        Pages.viewTransferPage().clickEditButton();

        logInfo("Step 6: Change some fields f.e.: 'From Account:', 'Advance days from due date:', 'Transfer Charge:'");
        logInfo("Step 7: Click 'Save'");
        loanPaymentTransfer.setAdvanceDaysFromDueDate(String.valueOf(Generator.genInt(1, 30)));
        Pages.editTransferPage().setAdvanceDaysFromDueDate(loanPaymentTransfer.getAdvanceDaysFromDueDate());
        loanPaymentTransfer.setTransferCharge(String.valueOf(Generator.genInt(100, 900)));
        Pages.editTransferPage().setTransferCharge(loanPaymentTransfer.getTransferCharge());
        TransfersActions.editTransferActions().setRandomEftChargeCode(loanPaymentTransfer);
        Pages.editTransferPage().clickSaveButton();

        Assert.assertEquals(Pages.viewTransferPage().getEftChargeCode(), loanPaymentTransfer.getEftChargeCode(),
                "'EFT Charge code' value is not saved after editing");
        Assert.assertEquals(Pages.viewTransferPage().getAdvanceDaysFromDueDate(), loanPaymentTransfer.getAdvanceDaysFromDueDate(),
                "'Advance Days From Due Date' value is not saved after editing");
        Assert.assertEquals(Pages.viewTransferPage().getTransferCharge(), loanPaymentTransfer.getTransferCharge(),
                "'Transfer Charge' value is not saved after editing");
    }
}
