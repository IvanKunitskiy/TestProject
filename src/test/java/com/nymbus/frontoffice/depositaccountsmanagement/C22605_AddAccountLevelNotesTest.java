package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.notes.NotesActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22605_AddAccountLevelNotesTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private Note note1;
    private Note note2;

    @BeforeMethod
    public void preCondition() {

        // Set up a client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up a note
        note1 = new Note().setDefaultNoteData();
        note2 = new Note().setDefaultNoteData();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account and logout
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22605, Add account level notes")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickNotesTab();

        logInfo("Step 3: Click [Add New Note] button");
        Pages.notesPage().clickAddNewNoteButton();

        logInfo("Step 4: Fill in such fields with any valid data:\n" +
                "- New Note - any alphanumeric value\n" +
                "- Due Date - any ≥ current date\n" +
                "- Expiration Date - any > Due Date\n" +
                "and click [Save] button");
        Pages.notesPage().typeToNewNoteTextArea(note1.getNewNote());
        Pages.notesPage().setDueDateValue(note1.getDueDate());
        Pages.notesPage().setExpirationDateValue(note1.getExpirationDate());
        Pages.notesPage().clickSaveButton();

        logInfo("Step 5: Click [Add New Note] button again\n" +
                "Do not fill in Responsible Officer field\n" +
                "fill in all other fields including 'Severity' field with any value from the drop-down\n" +
                "and click [Save] button");
        Pages.notesPage().clickAddNewNoteButton();
        Pages.notesPage().typeToNewNoteTextArea(note2.getNewNote());
        NotesActions.editActions().setSeverity(note2);
        Pages.notesPage().setDueDateValue(note2.getDueDate());
        Pages.notesPage().setExpirationDateValue(note2.getExpirationDate());
        Pages.notesPage().clickSaveButton();
        Pages.notesPage().waitForAddNewNoteButtonIsClickable();

        logInfo("Step 6: Open Teller page in the new tab\n" +
                "and search for the Account from the precondition in the Source or Destination");
        Pages.aSideMenuPage().clickTellerMenuItem();
        Pages.tellerModalPage().clickEnterButton();
        Pages.tellerPage().clickMiscDebitButton();
        Pages.tellerPage().clickAccountNumberDiv(1);
        Pages.tellerPage().typeAccountNumber(1, chkAccount.getAccountNumber());
        Pages.tellerPage().clickOnAutocompleteDropDownItem(chkAccount.getAccountNumber());
        Assert.assertTrue(Pages.tellerPage().isAlertWithTextVisible("Account | " + chkAccount.getAccountNumber() + " | " + note2.getNewNote()), "Alert for second note is not visible");

        logInfo("Step 7: Open Account from the precondition on Maintenance -> Maintenance History page");
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.attentionModalPage().clickYesButton();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 8: Look through the records on Maintenance History page\n" +
                "and make sure that there is information about newly created account note");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Due Date") >= 1,
                "'Due Date' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Expiration Date") >= 1,
                "'Expiration Date' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Subject") >= 1,
                "'Subject' row count is incorrect!");
    }
}
