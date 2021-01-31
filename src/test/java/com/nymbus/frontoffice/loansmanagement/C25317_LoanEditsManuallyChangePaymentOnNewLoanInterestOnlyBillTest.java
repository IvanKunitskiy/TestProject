package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25317_LoanEditsManuallyChangePaymentOnNewLoanInterestOnlyBillTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";

    @BeforeMethod
    public void preCondition() {
        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up Loan account
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.INTEREST_ONLY.getPaymentAmountType());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Create a client
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create loan account and logout
        AccountActions.createAccount().createLoanAccount(loanAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25317, testRunName = TEST_RUN_NAME)
    @Test(description = "C25317, Loan Edits: Manually change payment on new loan Interest Only (bill)")
    @Severity(SeverityLevel.CRITICAL)
    public void loanCreateAndFundInterestOnly() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        logInfo("Step 3: Open 'Payment info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 4: Click on the 'Edit' button in the 'Payment History' section");
        Pages.accountPaymentInfoPage().clickEditPaymentHistoryButton();

        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsEffectiveDate(), loanAccount.getDateOpened(),
                "'Effective Date' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsInactiveDate(), "",
                "'Inactive Date' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsPaymentType(), loanAccount.getPaymentAmountType(),
                "'Payment Type' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsFrequency(), loanAccount.getPaymentFrequency(),
                "'Frequency' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsAmount(), "", "'Amount' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsPercentage(), "100", "'Percentage' is not valid");
        Assert.assertTrue(Pages.accountPaymentInfoPage().getPiPaymentsRecalcFuturePymt().equalsIgnoreCase("no"),
                "'Recalc Future Pymt' is not valid");

        logInfo("Step 5: Change the value in the 'Percentage' field for 'P&I Payment' row to any other valid value");
        String newPercentage = String.valueOf(Generator.genInt(1, 100));
        Pages.accountPaymentInfoPage().typePercentageValue(newPercentage);

        logInfo("Step 6: Click the 'Save' button");
        Pages.accountPaymentInfoPage().clickSaveButton();
        Pages.accountPaymentInfoPage().waitForSaveButtonInvisibility();
        String actualPercentage = Pages.accountPaymentInfoPage().getPiPaymentsPercentage();
        Assert.assertEquals(actualPercentage, newPercentage, "'Percentage' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getActivePaymentAmountInterestOnly(), "Interest Only",
                "'Active Payment Amount' is not valid");

        logInfo("Step 7: Go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 8: Look through the Maintenance History records and make sure that there is information about editing the 'Percentage' field");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Percentage") >= 1,
                "'Percentage' row count is incorrect!");
    }
}
