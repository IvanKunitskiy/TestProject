package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.loans.DaysBaseYearBase;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
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
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Dmytro")
public class C21737_AccruedInterestCalculationOnNewLoanTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private IndividualClient client;
    private double amount = 9792.50;

    @BeforeMethod
    public void preCondition() {
        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();

        // Set up Loan account
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setDateOpened(DateTime.getDateWithNMonthAdded(WebAdminActions.loginActions().getSystemDate(),"MM/dd/yyyy",-1));
        loanAccount.setCurrentEffectiveRate(String.valueOf(9));
        loanAccount.setDaysBaseYearBase(DaysBaseYearBase.DAYS_360_YEAR_365.getDaysBaseYearBase());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRINCIPAL_AND_INTEREST.getPaymentAmountType());
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setNextPaymentBilledDueDate(DateTime.getLocalDatePlusMonthsWithPatternAndLastDay(loanAccount.getDateOpened(), 1, "MM/dd/yyyy"));
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Set transaction data
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(12000);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(amount);

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

        // Create checking
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();

    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 21737, testRunName = TEST_RUN_NAME)
    @Test(description = "C21737, Accrued interest calculation on new loan")
    @Severity(SeverityLevel.CRITICAL)
    public void manuallyChangeInterestRateOnNewLoan() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getPassword(), userCredentials.getPassword());

        logInfo("Step 2: Open 'Client' from preconditions on the 'Accounts' tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Select \"Account\" option in the \"Add New\" drop-down");
        Pages.clientDetailsPage().clickAddNewButton();
        Pages.clientDetailsPage().clickAddNewValueOption(loanAccount.getAddNewOption());

        logInfo("Step 4: Select \"Product Type\" - \"Loan Account\"");
        AccountActions.createAccount().setProductType(loanAccount);

        logInfo("Step 5: Select the product from preconditions in the \"Product\" drop-down");
        AccountActions.createAccount().setProduct(loanAccount);

        logInfo("Step 6: Specify all required fields and set:\n" +
                "Date opened = same day in the previous month\n" +
                "Days Base/Year Base = 360/365\n" +
                "Interest Method = Simple Interest\n" +
                "Current effective rate = 9\n" +
                "Payment Amount Type = Principal & Interest\n" +
                "Adjustable Rate = No\n" +
                "and click on the \"Save & Cash or Deposit\" button");
        AccountActions.createAccount().setAccountType(loanAccount);
        Pages.addAccountPage().setAccountNumberValue(loanAccount.getAccountNumber());
        AccountActions.createAccount().setOriginatingOfficer(loanAccount);
        AccountActions.createAccount().setCurrentOfficer(loanAccount);
        AccountActions.createAccount().setMailCode(loanAccount);
        Pages.addAccountPage().setDateOpenedValue(loanAccount.getDateOpened());
        AccountActions.createAccount().setBankBranch(loanAccount);
        AccountActions.createAccount().setLoanClassCode(loanAccount);
        Pages.addAccountPage().setPaymentAmount(loanAccount.getPaymentAmount());
        AccountActions.createAccount().setPaymentAmountType(loanAccount);
        AccountActions.createAccount().setPaymentFrequency(loanAccount);
        AccountActions.createAccount().disableCycleLoanSwitch();
        AccountActions.createAccount().disableAdjustableRateSwitch();
        AccountActions.createAccount().setDaysBaseYearBase(loanAccount);
        Pages.addAccountPage().setNextPaymentBilledDueDate(loanAccount.getNextPaymentBilledDueDate());
        Pages.addAccountPage().setDateFirstPaymentDue(loanAccount.getDateFirstPaymentDue());
        Pages.addAccountPage().setPaymentBilledLeadDays(loanAccount.getPaymentBilledLeadDays());
        Pages.addAccountPage().setCurrentEffectiveRate(loanAccount.getCurrentEffectiveRate());
        AccountActions.createAccount().setInterestMethod(loanAccount);
        AccountActions.createAccount().disableAdjustableRateSwitch();
        AccountActions.createAccount().setDaysBaseYearBase(loanAccount);
        Pages.addAccountPage().setTerm(loanAccount.getTerm());
        AccountActions.createAccount().setCommitmentTypeAmt(loanAccount);
        AccountActions.createAccount().disableLocPaymentRecalculationFlagValueSwitch();
        AccountActions.createAccount().setCallClassCode(loanAccount);
        Pages.addAccountPage().clickSaveAndDepositAccountButton();

        logInfo("Step 7: Select CHK or SAV account in destination\n" +
                "Specify both amounts = 9,792.50\n" +
                "Select effective date = same day in previous month = Date opened\n" +
                "and click on the \"Commit Transaction\" button");
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().fillSourceAmount(String.format("%.2f",amount),1);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Pages.tellerPage().clickCommitButton();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Pages.tellerPage().closeModal();

        logInfo("Step 8: Open created loan account");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        logInfo("Step 9: Pay attention to the \"Daily Interest factor\" field");
        String dailyInterestAccrual = Pages.accountDetailsPage().getDailyInterestAccrual();
        TestRailAssert.assertTrue(dailyInterestAccrual.equals("2.4145"),
                new CustomStepResult("Daily accrued interests is equals",
                        "Daily accrued interests is not equals"));

        logInfo("Step 10: Pay attention to the \"Accrued interest\" field");
        String accruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        String currentBalance = Pages.accountDetailsPage().getCurrentBalance();
        String currentEffectiveRate = Pages.accountDetailsPage().getCurrentEffectiveRate();
        String daysBaseYearBase = Pages.accountDetailsPage().getDaysBaseYearBase();
        int yearBase = Integer.parseInt(daysBaseYearBase.split("/")[1].substring(0, 3));
        int daysBase = Integer.parseInt(daysBaseYearBase.split("/")[0].substring(0, 3));
        double expectedAccruedInterest = Double.parseDouble(currentBalance) * Double.parseDouble(currentEffectiveRate)/100/
                yearBase * daysBase/12;

        TestRailAssert.assertTrue(accruedInterest.equals(String.format("%.2f", expectedAccruedInterest)),
                new CustomStepResult("Accrued interests is equals",
                        "Accrued interests is not equals"));
    }
}
