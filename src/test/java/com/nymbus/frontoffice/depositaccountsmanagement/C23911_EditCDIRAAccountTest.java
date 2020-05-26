package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23911_EditCDIRAAccountTest extends BaseTest {

    private IndividualClient client;
    private Account cdIRAAccount;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account (required to point the 'Corresponding Account')
        checkingAccount = new Account().setCHKAccountData();

        // Set up CD IRA account
        cdIRAAccount = new Account().setCDIRAAccountData();
        cdIRAAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user
        cdIRAAccount.setApplyInterestTo("Remain in Account");
        cdIRAAccount.setMaturityDate(DateTime.getDateWithNMonthAdded(cdIRAAccount.getDateOpened(), "MM/dd/yyyy", Integer.parseInt(cdIRAAccount.getTermType())));
        cdIRAAccount.setDateNextInterest(DateTime.getDateWithNMonthAdded(cdIRAAccount.getDateOpened(), "MM/dd/yyyy", 3)); // 3 month added as 'Interest Frequency' is set to 'Quarterly'

        // Login to the system and create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK and CD IRA accounts and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountDetailsPage().clickAccountsLink();
        AccountActions.createAccount().createCDAccount(cdIRAAccount);
        Pages.accountDetailsPage().waitForFullProfileButton();
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C23911, Edit CD IRA Account")
    @Severity(SeverityLevel.CRITICAL)
    public void editCDIRAAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD account from the precondition and open it in Edit mode");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdIRAAccount);

        logInfo("Step 2: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

        // TODO: Accounts page is under reconstruction. Check elements presence for commented out lines and delete or uncomment respectively.
        logInfo("Step 4: Look at the fields and verify that such fields are disabled for editing");
        Assert.assertTrue(Pages.editAccountPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
//        Assert.assertTrue(Pages.editAccountPage().isProductFieldDisabledInEditMode(), "'Product' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountNumberFieldDisabledInEditMode(), "'Account Number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountTypeFieldDisabledInEditMode(), "'Account Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginatingOfficerFieldDisabledInEditMode(), "'Originating Officer' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountStatusFieldDisabledInEditMode(), "'Account Status' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOpenedFieldDisabledInEditMode(), "'Date Opened' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateClosedFieldDisabledInEditMode(), "'Date Closed field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isCurrentBalanceDisabledInEditMode(), "'Current Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginalBalanceDisabledInEditMode(), "'Original Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountLastInterestPaidDisabledInEditMode(), "'Amount Last Interest Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastInterestPaidDisabledInEditMode(), "'Date Last Interest Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAnnualPercentageYieldFieldDisabledInEditMode(), "'Annual Percentage Yield' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateNextInterestDisabledInEditMode(), "'Date next interest' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isNextInterestPaymentAmountDisabledInEditMode(), "' Next Interest Payment Amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isMaturityDateDisabledInEditMode(), "'Maturity Date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestPaidYTDDisabledInEditMode(), "'Interest Paid Year to date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccruedInterestDisabledInEditMode(), "'Accrued Interest' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPenaltyAmountYTDDisabledInEditMode(), "'Penalty Amount Year-to-date' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDailyInterestAccrualDisabledInEditMode(), "'Daily Interest Accrual' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceAtRenewalDisabledInEditMode(), "'Balance At Renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOfRenewalDisabledInEditMode(), "'Date of renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestRateAtRenewalDisabledInEditMode(), "'Interest rate at renewal' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isRenewalAmountDisabledInEditMode(), "'Renewal amount' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isSpecialMailingInstructionsDisabledInEditMode(), "'Special Mailing Instructions' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTaxesWithheldYTDDisabledInEditMode(), "'Taxes Withheld YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBalanceAtEndOfYearDisabledInEditMode(), "'Balance at end of year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccruedInterestAtEndOfYearDisabledInEditMode(), "'Accrued interest at end of year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isIraPlanNumberDisabledInEditMode(), "'IRA plan number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isInterestPaidLastYearDisabledInEditMode(), "'Interest Paid Last Year' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isPrintInterestNoticeOverrideDisabledInEditMode(), "'Print interest notice override' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isIraDistributionAccountNumberDisabledInEditMode(), "'IRA Distribution Account Number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBankRoutingNumberForIRADistrDisabledInEditMode(), "'Bank Routing Number for IRA Distr' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isBankAccountNumberForIRADistrDisabledInEditMode(), "'Bank Account Number for IRA Distr' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountLastIRADistributionDisabledInEditMode(), "'Amount last IRA distribution' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastIRADistributionDisabledInEditMode(), "'Date last IRA distribution' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isIRADistributionsYTDDisabledInEditMode(), "'IRA distributions YTD' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalEarningsDisabledInEditMode(), "'Total Earnings for Life of Account' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isTotalContributionsDisabledInEditMode(), "'Total Contributions for Life of Account' field is not disabled");

        logInfo("Step 5: Select data in Federal W/H reason drop-down field");
        AccountActions.editAccount().setFederalWHReason(cdIRAAccount);

        logInfo("Step 6: Fill in such text fields that were not displayed in Add new mode");
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringCDIRAAccountCreation(cdIRAAccount);

        logInfo("Step 7: Select any other value in such fields");
        cdIRAAccount.setApplyInterestTo("CHK Acct");
        AccountActions.editAccount().selectValuesInDropdownFieldsRequiredForCDIRAAccount(cdIRAAccount);

        logInfo("Step 8: Set switcher Transactional Account = YES");
        if (!Pages.editAccountPage().getTransactionalAccountInEditMode().equals("YES")) {
            Pages.editAccountPage().clickTransactionalAccountSwitch();
        }

        logInfo("Step 9: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 10: Pay attention to CD IRA account fields");
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHReason(), cdIRAAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankAccountNumberInterestOnCD(), cdIRAAccount.getBankAccountNumberInterestOnCD(), "'Bank Account Number Interest On CD' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankRoutingNumberInterestOnCD(), cdIRAAccount.getBankRoutingNumberInterestOnCD(), "'Bank Routing Number Interest On CD' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), cdIRAAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), cdIRAAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), cdIRAAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), cdIRAAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), cdIRAAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), cdIRAAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), cdIRAAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getTransactionalAccount(), cdIRAAccount.getTransactionalAccount(), "'Transactional Account' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), cdIRAAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getApplyInterestTo(), cdIRAAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), cdIRAAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");

        logInfo("Step 11: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getFederalWHReasonInEditMode(), cdIRAAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankAccountNumberInterestOnCD(), cdIRAAccount.getBankAccountNumberInterestOnCD(), "'Bank Account Number Interest On CD' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankRoutingNumberInterestOnCD(), cdIRAAccount.getBankRoutingNumberInterestOnCD(), "'Bank Routing Number Interest On CD' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField1(), cdIRAAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField2(), cdIRAAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField3(), cdIRAAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField4(), cdIRAAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), cdIRAAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), cdIRAAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), cdIRAAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getTransactionalAccountInEditMode(), cdIRAAccount.getTransactionalAccount(), "'Transactional Account' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), cdIRAAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getApplyInterestTo(), cdIRAAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), cdIRAAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");

        logInfo("Step 12: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 13: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Product") == 1,
                "'Product' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("accounttype") == 1,
                "'accounttype' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Title") == 1,
                "'Account Title' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current Officer") == 1,
                "'Current Officer' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Branch") == 1,
                "'Bank Branch' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Opened") == 1,
                "'Date Opened' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Type") == 1,
                "'Interest Type' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency") == 1,
                "'Interest Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Statement Cycle") == 1,
                "'Statement Cycle' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Charge or analyze") == 1,
                "'Charge or analyze' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account analysis") == 1,
                "'Account analysis' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Call Class Code") == 1,
                "'Call Class Code' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Corresponding Account") == 1,
                "'Corresponding Account' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Frequency") == 1,
                "'IRA Distribution Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Code") == 1,
                "'IRA Distribution Code' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Apply Interest To") == 1,
                "'Apply Interest To' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 1") == 1,
                "'User Defined Field 1' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 2") == 1,
                "'User Defined Field 2' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 3") == 1,
                "'User Defined Field 3' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 4") == 1,
                "'User Defined Field 4' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Routing Number Interest On CD") == 1,
                "'Bank Routing Number Interest On CD' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Account Number Interest On CD") == 1,
                "'Bank Account Number Interest On CD' row count is incorrect!");
    }
}
