package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.maintenance.Tool;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25418_LoanPayoffChargesSetUpPayoffChargesTest extends BaseTest {

    private Account loanAccount;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

        // Set transaction data
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(12000);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(12000);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Get escrow payment value for the loan product
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

        // Perform transaction
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

    @TestRailIssue(issueID = 25418, testRunName = TEST_RUN_NAME)
    @Test(description = "C25418, Loan Payoff Charges: Set up payoff charges")
    @Severity(SeverityLevel.CRITICAL)
    public void loanPayoffChargesSetUpPayoffCharges() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickAmountDueInquiryButton();
        Pages.amountDueInquiryModalPage().clickCloseButton();

        logInfo("Step 3: Go to maintenance -> Tool -> Loan Payoff Charges");
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_PAYOFF_CHARGES);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Steep 4: Click '+ Add New Payoff Charge'");
        Pages.loanPayoffChargesModalPage().clickAddNewPayoffChargeButton();
        Pages.loanPayoffChargesModalPage().waitForModalWindowVisibility();

        logInfo("Step 5: Specify all fields and click 'Done'");
        String chargeTypeDescription = Generator.genString(10);
        Pages.loanPayoffChargesModalPage().typeChargeDescription(chargeTypeDescription);

        double chargeAmount = 1.00;
        Pages.loanPayoffChargesModalPage().typeChargeAmount(String.format("%.2f", chargeAmount));

        Pages.loanPayoffChargesModalPage().typeGlOffset("fee");
        List<String> glOffsetList = Pages.loanPayoffChargesModalPage().getGlOffsetList();
        Assert.assertTrue(glOffsetList.size() > 0, "There are no 'GL Offset' options available");
        String randomGlOffsetOption = glOffsetList.get(new Random().nextInt(glOffsetList.size())).trim();
        Pages.loanPayoffChargesModalPage().clickGlOffsetOption(randomGlOffsetOption);
        Pages.loanPayoffChargesModalPage().clickDoneButton();
        Pages.loanPayoffChargesModalPage().clickCloseButton();

        logInfo("Step 6: Go to 'Details' tab -> click on the 'Amount Due Inquiry' button and note 'Payoff Charges' field");
        Pages.accountDetailsPage().clickDetailsTab();
        Pages.accountDetailsPage().clickAmountDueInquiryButton();
        double payoffAfterCharges = Double.parseDouble(Pages.amountDueInquiryModalPage().getPayoffCharges());

        Assert.assertEquals(chargeAmount, payoffAfterCharges, "'Payoff Charges' value is not valid");
    }
}