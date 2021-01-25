package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.transfer.Transfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Severity(SeverityLevel.CRITICAL)
@Owner("Petro")
public class C21734_CreateNewPeriodicLoanPaymentTest extends BaseTest {

    private IndividualClient client;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private Transfer transfer;

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
        transfer = transferBuilder.getTransfer();
        transfer.setFromAccount(checkingAccount);
        transfer.setToAccount(loanAccount);

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

    @Test(description = "C21734, Create new periodic loan payment")
    @Severity(SeverityLevel.CRITICAL)
    public void createNewPeriodicLoanPayment() {

        logInfo("Step 1: Log in to NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open 'Client' from preconditions on the 'Accounts' tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());

        logInfo("Step 3: Go to 'Transfers' page");
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 4: Click 'New Transfer'");
        Pages.transfersPage().clickNewTransferButton();

        logInfo("Step 5: Select 'Transfer Type:' = Loan Payment");
        TransfersActions.addNewTransferActions().setTransferType(transfer);

        logInfo("Step 6: Specify:\n" +
                "Next Date Of Transfer = the date when transfer will occur\n" +
                "Expiration Date (optional) = any date (is not editable if Frequency = One Time Only)\n" +
                "From Account = CHK or SAV account from preconditions with enough money for transfer\n" +
                "To Account = Loan account from preconditions\n" +
                "Frequency = any value from drop-down\n" +
                "Amount = any amount (need to set up for periodic transfer)\n" +
                "EFT charge code (optional) = description for Transfer charge\n" +
                "Transfer Charge (optional) = any amount (charge amount for transfer)");
        TransfersActions.addNewTransferActions().setTransferFromAccount(transfer);
        TransfersActions.addNewTransferActions().setTransferToAccount(transfer);
        TransfersActions.addNewTransferActions().setTransferFrequency(transfer);
        Pages.newTransferPage().setAmount(transfer.getAmount());
        TransfersActions.addNewTransferActions().setEftChargeCode(transfer);
        Pages.newTransferPage().setTransferCharge(transfer.getTransferCharge());

        logInfo("Step 7: Click 'Save'");
        Pages.newTransferPage().clickSaveButton();
        Assert.assertTrue(Pages.viewTransferPage().getFromAccount().contains(transfer.getFromAccount().getAccountNumber()),
                "'From Account' is not valid");
        Assert.assertTrue(Pages.viewTransferPage().getToAccount().contains(transfer.getToAccount().getAccountNumber()),
                "'To Account' is not valid");
        Assert.assertEquals(Pages.viewTransferPage().getFrequency(), transfer.getFrequency(),
                "'Frequency' is not equal to current date");
        Assert.assertEquals(Pages.viewTransferPage().getAmount(), transfer.getAmount(),
                "'Amount' is not equal to current date");
        Assert.assertEquals(Pages.viewTransferPage().getTransferCharge(), transfer.getTransferCharge(),
                "'Transfer Charge' is not valid");
        Assert.assertEquals(Pages.viewTransferPage().getEftChargeCode(), transfer.getEftChargeCode(),
                "'EFT charge code' is not equal to current date");
    }
}
