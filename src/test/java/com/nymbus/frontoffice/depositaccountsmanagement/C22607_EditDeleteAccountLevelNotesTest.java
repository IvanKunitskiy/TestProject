package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.notes.NotesActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.data.entity.User;
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
public class C22607_EditDeleteAccountLevelNotesTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private Note note;

    @BeforeMethod
    public void preCondition() {

        // Set up a client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up a note
        note = new Note().setDefaultNoteData();
        note.setResponsibleOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(chkAccount);

        // Create a note
        Pages.accountNavigationPage().clickNotesTab();
        Pages.notesPage().clickAddNewNoteButton();
        Pages.notesPage().typeToNewNoteTextArea(note.getNewNote());
        Pages.notesPage().setDueDateValue(note.getDueDate());
        Pages.notesPage().setExpirationDateValue(note.getExpirationDate());
        Pages.notesPage().clickSaveButton();
        Pages.notesPage().waitForAddNewNoteButton();
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22607, Edit / delete account level notes")
    @Severity(SeverityLevel.CRITICAL)
    public void editDeleteAccountLevelNotesTest() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for account from the precondition and open it on Notes tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickNotesTab();

        logInfo("Step 3: Select any note and click [Edit] button.\n" +
                "Make the following changes:\n" +
                "- Responsible Officer - assign to current User\n" +
                "- change the Severity (if it was not blank) or select any Severity from the drop-down if it was blank\n" +
                "- add some alphanumeric char. to the Note text field\n" +
                "- select another Due Date - any ≥ current date\n" +
                "- select another Expiration Date- any >Due Date\n" +
                "click [Save] button");
        Pages.notesPage().clickNoteByName(note.getNewNote());
        Pages.notesPage().clickEditButton();
        if (Pages.notesPage().isResponsibleOfficerSelected()) {
            Pages.notesPage().clickClearResponsibleOfficerIcon();
        }
        Pages.notesPage().waitResponsibleOfficerInput();
        NotesActions.editActions().setResponsibleOfficer(note);
        NotesActions.editActions().setSeverity(note);
        Pages.notesPage().addRandomTextToNewNoteTextArea(10);
        Pages.notesPage().setDueDateValue(DateTime.getDateTodayPlusDaysWithFormat(1,"MM/dd/yyyy"));
        Pages.notesPage().setExpirationDateValue(DateTime.getDateTodayPlusDaysWithFormat(2,"MM/dd/yyyy"));
        Pages.notesPage().clickSaveButton();

        logInfo("Step 4: Refresh the page and pay attention to the colored bar in the header");
        SelenideTools.refresh();
        Assert.assertTrue(Pages.notesPage().isNoteAlertAppeared("Account | " + chkAccount.getAccountNumber() +
                " | " + note.getNewNote()), "Note alert not appeared on the page");

        logInfo("Step 5: Click bell icon in the top right of the header and pay attention to the Alerts panel");
        Pages.navigationPage().clickAlertNotificationsButton();
        Pages.alerts().waitForAlertsSidePanelVisible();
        Assert.assertTrue(Pages.alerts().isNoteAlertVisible(chkAccount.getAccountNumber() + " - CHK Account", note.getNewNote()));
        Pages.alerts().clickCloseButton();

        logInfo("Step 6: Select any note that has an Expiration Date field value and try to click [Delete] button");
        Pages.notesPage().clickNoteByName(note.getNewNote());
        Assert.assertTrue(Pages.notesPage().isDeleteButtonDisabled(), "'Delete' button is not disabled");

        logInfo("Step 7: Open the note in Edit mode and remove the value from the 'Expiration Date' field.\n" +
                "Click [Save] button");
        Pages.notesPage().clickEditButton();
        Pages.notesPage().setExpirationDateValue("");
        Pages.notesPage().clickSaveButton();

        logInfo("Step 8: Click on the note and click [Delete] button");
        Pages.notesPage().clickNoteByName(note.getNewNote());
        Pages.notesPage().clickDeleteButton();

        logInfo("Step 9: Refresh the page and pay attention to the colored bar in the header");
        SelenideTools.refresh();
        Assert.assertFalse(Pages.notesPage().isNoteAlertVisible("Account | " + chkAccount.getAccountNumber() +
                " | " + note.getNewNote()), "Note alert appeared on the page");

        logInfo("Step 10: Click bell icon in the top right of the header and pay attention to the Alerts panel");
        Pages.navigationPage().clickAlertNotificationsButton();
        Pages.alerts().waitForAlertsSidePanelVisible();
        Pages.alerts().isNoteAlertVisible(chkAccount.getAccountNumber() + " - CHK Account", note.getNewNote());
        Assert.assertFalse(Pages.alerts().isNoteAlertVisible(chkAccount.getAccountNumber() + " - CHK Account", note.getNewNote()));

        logInfo("Step 11: Open Clients profile on Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 12: Look through the records on Maintenance History page and make sure that there is information about editing note and deleting the note");
        // TODO: Implement verification at Maintenance History page
    }
}
