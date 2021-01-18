package com.nymbus.frontoffice.loansmanagement;

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
@Feature("Loans Management")
@Owner("Petro")
public class C25413_ChangeMaturityDateForLoanAccountTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up loan account
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);

        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create loan account and logout
        AccountActions.createAccount().createLoanAccount(loanAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C25413, Change Maturity Date for loan account")
    @Severity(SeverityLevel.CRITICAL)
    public void changeMaturityDateForLoanAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Open loan account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        logInfo("Step 3: Pay attention at the loan account fields");
        Assert.assertTrue(Pages.accountDetailsPage().isBalanceAndInterestVisible(), "'Balance and Interest' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isAccountSettingsVisible(), "'Account Settings' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isCreditLimitVisible(), "'Credit Limit' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isRatePaymentChangeVisible(), "'Rate/Payment Change' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isLateChargeVisible(), "'Late Charge' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isLoanCodesReportingVisible(), "'Loan Codes & Reporting' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isUserDefinedFieldsVisible(), "'User Defined Fields' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isAccountInfoStatisticsVisible(), "'Account Info/Statistics' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isCollectionsVisible(), "'Collections' group is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isOtherLoanSettingsVisible(), "'Other Loan Settings' group is not visible");

        logInfo("Step 4: Click on the 'Edit' button");
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 5: Find 'Maturity Date' field and change the date");
        loanAccount.setMaturityDate(DateTime.getDateWithFormatPlusDays(loanAccount.getDateOpened(), "MM/dd/yyy", "MM/dd/yyy", 1));
        Pages.editAccountPage().setMaturityDate(loanAccount.getMaturityDate());

        logInfo("Step 6: Click on the 'Save' button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 7: Find the 'Maturity Date' field on the 'Details' page");
        Assert.assertEquals(Pages.accountDetailsPage().getMaturityDate(), loanAccount.getMaturityDate(),
                "'Maturity date' is not changed after editing");

        logInfo("Step 8: Go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 9: Look through the Maintenance History records and make sure that there is a record about editing the 'Maturity Date' field");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Maturity Date") >= 1,
                "'Product' row count is incorrect!");
    }
}
