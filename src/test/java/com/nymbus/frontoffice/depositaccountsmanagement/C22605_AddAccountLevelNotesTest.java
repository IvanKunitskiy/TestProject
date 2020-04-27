package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.notes.NotesActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22605_AddAccountLevelNotesTest extends BaseTest {

    private Client client;
    private Account chkAccount;
    private Note note;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        note = new Note().setDefaultNoteData();
        note.setResponsibleOfficer(Constants.USERNAME + " " + Constants.PASSWORD);

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Create a client with a note. Set note's 'Due Date' to current date
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22605, Add account level notes")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
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
        Pages.notesPage().typeToNewNoteTextArea(note.getNewNote());
        Pages.notesPage().setDueDateValue(note.getDueDate());
        Pages.notesPage().setExpirationDateValue(note.getExpirationDate());
        Pages.notesPage().clickSaveButton();

        logInfo("Step 5: Click [Add New Note] button again\n" +
                "Do not fill in Responsible Officer field\n" +
                "fill in all other fields including 'Severity' field with any value from the drop-down\n" +
                "and click [Save] button");
        Pages.notesPage().clickAddNewNoteButton();
        NotesActions.editActions().setSeverity(note);
        Pages.notesPage().setDueDateValue(note.getDueDate());
        Pages.notesPage().setExpirationDateValue(note.getExpirationDate());
        NotesActions.editActions().setTemplate(note);
        Pages.notesPage().clickSaveButton();

        logInfo("Step 6: Open Teller page in the new tab\n" +
                "and search for the Account from the precondition in the Source or Destination");
        Pages.aSideMenuPage().clickTellerMenuItem();
        Pages.tellerModalPage().clickEnterButton();
        Pages.tellerPage().clickMiscDebitButton();

        Pages.tellerPage().clickAccountNumberDiv(1);
        Pages.tellerPage().typeAccountNumber(1, chkAccount.getAccountNumber());
        Pages.tellerPage().clickOnAutocompleteDropDownItem(chkAccount.getAccountNumber());
        // TODO: On clarification. Currently alerts for both created notes are displayed in different way.

        logInfo("Step 7: Open Account from the precondition on Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 8: Look through the records on Maintenance History page\n" +
                "and make sure that there is information about newly created account note");
        // TODO: Implement verification at Maintenance History page
    }
}
