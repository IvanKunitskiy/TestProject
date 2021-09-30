package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.transactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
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
@Owner("Dmytro")
public class C22775_LoanInsurancePlanCreateLoanInsurancePolicy extends BaseTest {

    private Account loanAccount;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String code = "autotest";
    private final String company = "Debt Protection";


    @BeforeMethod
    public void precondition() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());
        loanAccount.setPaymentBilledLeadDays(String.valueOf(1));
        String loanProductName = "Test Loan Product";
        loanAccount.setProduct(loanProductName);
        loanAccount.setEscrow("$ 0.00");
        loanAccount.setCycleCode(Generator.genInt(1, 20) + "");
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        checkAccount.setDateOpened(loanAccount.getDateOpened());

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        String loanProductInitials = "TLP";
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);

        // Set up transactions with account number
        depositTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        double transactionAmount = 1001.00;
        depositTransaction.getTransactionDestination().setAmount(transactionAmount);
        depositTransaction.getTransactionSource().setAmount(transactionAmount);
        depositTransaction.setTransactionDate(loanAccount.getDateOpened());
        int balance = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(balance);
        miscCreditDestination.setAccountNumber(checkAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(balance);

        // Perform deposit transactions
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        //Check Insurance and Plan Setup
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        String glAccount = "2600800";
        Actions.insuranceCompaniesActions().createInsuranceCompanyIfNotExists(code, glAccount);
        Actions.loanInsurancePlanSetupActions().createLoanInsurancePlanSetupIfNotExists(code, company,
                loanAccount.getDateOpened());
        Actions.loginActions().doLogOutProgrammatically();
    }


    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 22775, testRunName = TEST_RUN_NAME)
    @Test(description = "C22775, Loan Insurance Plan: Create Loan Insurance Policy")
    @Severity(SeverityLevel.CRITICAL)
    public void loanInsurancePlanCreateLoanInsurancePolicy() {
        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open account from preconditions on the 'Maintenance' page");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 3: Go to 'Loan Insurance Policies' screen");
        Pages.accountDetailsPage().clickLoanInsurancePoliciesTab();

        logInfo("Step 4: Click 'Add New'");
        Pages.accountLoanInsurancePoliciesPage().clickAddNewButton();
        TestRailAssert.assertTrue(Pages.loanInsurancePolicyModalPage().getCountOfColumns()==18,
                new CustomStepResult("All fields is exists", "Some fields not exists"));
        TestRailAssert.assertTrue(Pages.loanInsurancePolicyModalPage().getDateIssuedDisabled()
                        .equals(DateTime.getDatePlusMonth(loanAccount.getDateOpened(),1)),
                new CustomStepResult("Date issued is disabled","Date issued is not disabled"));
        TestRailAssert.assertTrue(Pages.loanInsurancePolicyModalPage().getMaturityDateDisabled()
                        .equals(DateTime.getDatePlusMonth(loanAccount.getDateOpened(),12)),
                new CustomStepResult("Date maturity is disabled","Date maturity is not disabled"));
        TestRailAssert.assertTrue(Pages.loanInsurancePolicyModalPage().getPremiumDisabled().equals(""),
                new CustomStepResult("Premium is disabled","Premium is not disabled"));
        TestRailAssert.assertTrue(Pages.loanInsurancePolicyModalPage().getPremiumEarnedDisabled().equals("$ 0.00"),
                new CustomStepResult("Premium Earned is disabled","Premium Earned is not disabled"));
        TestRailAssert.assertTrue(Pages.loanInsurancePolicyModalPage().getPremiumRefundedDisabled().equals("$ 0.00"),
                new CustomStepResult("Premium Refunded is disabled","Premium Refunded is not disabled"));

        logInfo("Step 5: Select 'Insurance Company' from precondition 1\n" +
                "Then select any 'Type' in the dropdown (Credit Life or Debt Protection)\n" +
                "Then select any 'Plan' in the dropdown");
        Pages.loanInsurancePolicyModalPage().inputCompany(code);
        Pages.loanInsurancePolicyModalPage().inputType(company);
        Pages.loanInsurancePolicyModalPage().inputPlan(code);

        logInfo("Step 6: Click 'Save'");
        Pages.loanInsurancePolicyModalPage().clickSave();
        TestRailAssert.assertTrue(Pages.accountLoanInsurancePoliciesPage().isCompanyVisible(),
                new CustomStepResult("Item is created", "Item is not created"));
    }
}
