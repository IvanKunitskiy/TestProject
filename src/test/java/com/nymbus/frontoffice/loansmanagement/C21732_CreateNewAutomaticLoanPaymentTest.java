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
import com.nymbus.newmodels.client.other.transfer.LoanPaymentTransfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21732_CreateNewAutomaticLoanPaymentTest extends BaseTest {

    private Account loanAccount;
    private IndividualClient client;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private double escrowPaymentValue;
    private LoanPaymentTransfer loanPaymentTransfer;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

        // Set transaction data
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(100);

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        loanPaymentTransfer = transferBuilder.getLoanPaymentTransfer();

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Get escrow payment value for the loan product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        escrowPaymentValue = Actions.loanProductOverviewActions().getLoanProductEscrowPaymentValue(loanProductName);

        // Set the product
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
    }

    @Test(description = "C21732, Create new automatic loan payment")
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
        Pages.transfersPage().clickTransferInTheListByType(loanPaymentTransfer.getTransferType().getTransferType());

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
    }
}
