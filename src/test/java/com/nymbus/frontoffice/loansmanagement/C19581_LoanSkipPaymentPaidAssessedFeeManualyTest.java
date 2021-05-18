package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C19581_LoanSkipPaymentPaidAssessedFeeManualyTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        Account checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Set up deposit transaction
        int transactionAmount = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(transactionAmount);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(transactionAmount);

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

        // Create accounts
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);

        // Perform deposit transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 19581, testRunName = TEST_RUN_NAME)
    @Test(description = "C19581, Loan Skip Payment: Paid Assessed Fee Manualy")
    @Severity(SeverityLevel.CRITICAL)
    public void loanSkipPaymentPaidAssessedFeeManualyTest() {

    }

}
