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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21729_EditLoanAccountDropdownValueIfItsNoLongerAvailableForSelectionTest extends BaseTest {

    private Account loanAccount;
    private String callClassCode;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";

    @BeforeMethod
    public void preCondition() {

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

        // Create a client
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create account and logout
        AccountActions.createAccount().createLoanAccount(loanAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 21729, testRunName = TEST_RUN_NAME)
    @Test(description = "C21729, Edit loan account (dropdown value if it's no longer available for selection)")
    @Severity(SeverityLevel.CRITICAL)
    public void editLoanAccountDropdownValueIfItsNoLongerAvailableForSelection() {

        logInfo("Step 1: Log in to NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Settings -> Call Class Codes tool");
        logInfo("Step 3: Find call code that is used for loan account from preconditions");
        logInfo("Step 4: Open found call code and clear 'Product Type' field");
        logInfo("Step 5: Save call code");
        callClassCode = loanAccount.getCallClassCode().split("-")[0].trim();
        Actions.callClassCodesActions().removeProductTypeFromCallCode(callClassCode);

        logInfo("Step 6: Perform re-login");
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 7: Open loan account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        logInfo("Step 8: Click on the 'Edit' button");
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 9: Pay attention at the 'Call Code' field");
        Assert.assertTrue(Pages.editAccountPage().isCallClassCodeNotValid(), "Call class code value is still valid");

        logInfo("Step 10: Click 'Save' button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
    }

    @AfterMethod(description = "Set back the 'Loan Account' value to call code")
    public void postCondition() {
        String productType = "Loan Account";
        System.out.println(callClassCode);
        Actions.callClassCodesActions().setCallCodeProductType(callClassCode, productType);
    }
}
