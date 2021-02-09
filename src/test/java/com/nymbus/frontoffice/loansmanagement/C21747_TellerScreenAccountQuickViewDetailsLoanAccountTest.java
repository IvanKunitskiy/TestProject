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
import com.nymbus.newmodels.account.AccountData;
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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21747_TellerScreenAccountQuickViewDetailsLoanAccountTest extends BaseTest {

    private Account loanAccount;
    private IndividualClient client;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private AccountData accountData;

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
        final int TRANSACTION_AMOUNT = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(TRANSACTION_AMOUNT);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(TRANSACTION_AMOUNT);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Set CHK product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create accounts and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        Actions.loginActions().doLogOut();

        // Perform transaction
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        accountData = getAccountDataFromWebAdmin(loanAccount.getAccountNumber());
        setAccountDataFromDetails(loanAccount, accountData);
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 21747, testRunName = TEST_RUN_NAME)
    @Test(description = "C21747, Teller Screen - Account Quick View Details - Loan Account")
    @Severity(SeverityLevel.CRITICAL)
    public void tellerScreenAccountQuickViewDetailsLoanAccount() {

        logInfo("Step 1: Log in to Nymbus");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' screen");
        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Add 'Misc Debit' fund type into source and search for loan account from preconditions");
        Pages.tellerPage().clickMiscDebitButton();
        Actions.transactionActions().fillSourceAccountNumber(loanAccount.getAccountNumber(), 1);

        logInfo("Step 5: Look at the pre-populated data in the 'Account Quick View' form on the right part of the screen");
        Assert.assertEquals(client.getIndividualType().getClientID(), Actions.transactionActions().getPIFNumber(),
                "'PIF number' value is not valid");
        Assert.assertEquals(loanAccount.getProductType(), Actions.transactionActions().getAccountType(),
                "'Account type' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getCurrentBalance()), Actions.transactionActions().getCurrentBalance(),
                "'Current balance' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getAvailableBalance()), Actions.transactionActions().getAvailableBalance(),
                "'Available balance' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getAccruedInterest()), Actions.transactionActions().getAccruedInterest(),
                "'Accrued Interest' value is not valid");
        Assert.assertEquals(accountData.getLateChangesDue(), Actions.transactionActions().getLateChargesDue(),
                "'Late Charges Due' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getActivePaymentAmount()), Actions.transactionActions().getPaymentAmount(),
                "'Payment Amount' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getPayoffAmount()), Actions.transactionActions().getPayoffAmount(),
                "'Payoff Amount' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getTotalPastDue()), Actions.transactionActions().getTotalPastDue(),
                "'Total Past Due' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getPrincipalNextDue()), Actions.transactionActions().getPrincipalNextDue(),
                "'Principal Next Due' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getInterestNextDue()), Actions.transactionActions().getInterestNextDue(),
                "'Principal Next Due' value is not valid");
        Assert.assertEquals(Double.parseDouble(accountData.getTotalNextDue()), Actions.transactionActions().getTotalNextDue(),
                "'Total Next Due' value is not valid");
        Assert.assertEquals(DateTime.getDateWithFormat(accountData.getCurrentDateDue(), "yyyy-MM-dd", "MM/dd/yyyy"),
                DateTime.getDateWithFormat(Actions.transactionActions().getCurrentDateDue(), "MMMM dd, yyyy", "MM/dd/yyyy"),
                "'Current Date Due' value is not valid");
        Assert.assertEquals(loanAccount.getLoanClassCode(), Actions.transactionActions().getLoanClassCode(),
                "'Loan Class Code' value is not valid");
    }

    private AccountData getAccountDataFromWebAdmin(String accNumber) {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        AccountData loanAccountData = WebAdminActions.webAdminUsersActions().getLoanAccountData(accNumber);

        WebAdminActions.loginActions().doLogout();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        return loanAccountData;
    }

    private void setAccountDataFromDetails(Account loanAccount, AccountData accountData) {
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        accountData.setCurrentBalance(Pages.accountDetailsPage().getCurrentBalance());
        accountData.setAvailableBalance(Pages.accountDetailsPage().getAvailableBalanceFromHeaderMenu());
        accountData.setAccruedInterest(Pages.accountDetailsPage().getAccruedInterest());
        accountData.setLateChangesDue(Pages.accountDetailsPage().getLateFeeDue());
        accountData.setLoanClassCode(Pages.accountDetailsPage().getLoanClassCode());
        Pages.accountDetailsPage().clickPaymentInfoTab();
        accountData.setActivePaymentAmount(Pages.accountPaymentInfoPage().getActivePaymentAmount());

        Actions.loginActions().doLogOut();
    }
}
