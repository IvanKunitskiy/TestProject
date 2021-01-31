package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
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
public class C25316_LoanEditsManuallyChangePaymentOnNewLoanPIBillTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private double escrowPaymentValue;

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

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Get escrow payment value for the loan product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        escrowPaymentValue = Actions.loanProductOverviewActions().getLoanProductEscrowPaymentValue(loanProductName);

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create loan account and logout
        AccountActions.createAccount().createLoanAccount(loanAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25316, testRunName = TEST_RUN_NAME)
    @Test(description = "C25316, Loan Edits: Manually change payment on new loan P&I(bill)")
    @Severity(SeverityLevel.CRITICAL)
    public void loanEditsManuallyChangePaymentOnNewLoanPIBill() {

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
        double paymentAmount = Double.parseDouble(loanAccount.getPaymentAmount());
        double actualPaymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getPiPaymentsAmount());
        double expectedPaymentAmount = paymentAmount - escrowPaymentValue;
        Assert.assertEquals(actualPaymentAmount, expectedPaymentAmount, "'Amount' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsPercentage(), "", "'Percentage' is not valid");
        Assert.assertTrue(Pages.accountPaymentInfoPage().getPiPaymentsRecalcFuturePymt().equalsIgnoreCase("no"),
                "'Recalc Future Pymt' is not valid");

        logInfo("Step 5: Change the value in the 'Amount' field for the 'P&I Payment' row to any other valid value");
        String newAmount = "1003.00";
        Pages.accountPaymentInfoPage().typeAmountValue(newAmount);

        logInfo("Step 6: Click on the 'Save' button");
        Pages.accountPaymentInfoPage().clickSaveButton();
        Pages.accountPaymentInfoPage().waitForSaveButtonInvisibility();
        actualPaymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getPiPaymentsAmount());
        Assert.assertEquals(actualPaymentAmount, Double.parseDouble(newAmount), "'Amount' is not valid");
        double activePaymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getActivePaymentAmount());
        Assert.assertEquals(activePaymentAmount, escrowPaymentValue + Double.parseDouble(newAmount), "'Active Payment Amount' is not valid");

        logInfo("Step 7: Go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 8: Look through the Maintenance History records "
                + "and make sure that there is information about editing the 'Amount' field "
                + "and changing the value in the 'Active Payment Amount' field");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Amount") >= 1,
                "'Amount' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Amount") >= 1,
                "'Active Payment Amount' row count is incorrect!");
    }
}
