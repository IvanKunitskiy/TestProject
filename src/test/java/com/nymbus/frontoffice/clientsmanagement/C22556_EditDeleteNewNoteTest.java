package com.nymbus.frontoffice.clientsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.notes.NotesActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.User;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22556_EditDeleteNewNoteTest extends BaseTest {

    private User user;
    private Note note;
    private Client client;

    @BeforeMethod
    public void preCondition() {
        user = new User().setDefaultUserData();
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        note = new Note().setDefaultNoteData();
        note.setResponsibleOfficer(user.getFirstName() + " " + user.getLastName());

        // Create user and set him a password
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.usersActions().createUser(user);
        Actions.loginActions().doLogOut();
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setUserPassword(user);
        WebAdminActions.loginActions().doLogout();

        // Create a client with a note. Set note's 'Due Date' to current date
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        Pages.accountNavigationPage().clickNotesTab();
        Pages.notesPage().clickAddNewNoteButton();
        Pages.notesPage().typeToNewNoteTextArea(note.getNewNote());
        Pages.notesPage().clickSaveButton();
        Pages.notesPage().waitForAddNewNoteButton();
        Actions.loginActions().doLogOut();

    }

    @Test(description = "C22556, Edit/Delete New Note")
    @Severity(SeverityLevel.CRITICAL)
    public void editDeleteNewNote() {

        logInfo("Step 1: Log in to the system as User1 from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenClientByID(client);

        logInfo("Step 3: Open Client's Profile on Notes tab");
        Pages.accountNavigationPage().clickNotesTab();

        logInfo("Step 4: Click the note from preconditions and click [Edit] button");
        Pages.notesPage().clickNoteByName(note.getNewNote());
        Pages.notesPage().clickEditButton();

        logInfo("Step 5: Make any changes in the opened form (e.g. change priority / due date) and Save changes");
        if (Pages.notesPage().isResponsibleOfficerSelected()) {
            Pages.notesPage().clickClearResponsibleOfficerIcon();
        }
        Pages.notesPage().waitResponsibleOfficerInput();
        NotesActions.editActions().setResponsibleOfficer(note);
        Pages.notesPage().setDueDateValue(note.getDueDate());
        Pages.notesPage().clickSaveButton();

        logInfo("Step 6: Verify that updated data is displayed for the note in the notes list");
        Assert.assertEquals(Pages.notesPage().getDueDateOfTheActiveNote(), note.getDueDate(), "'Due Date' value does not match");

        logInfo("Step 7: Click the updated note and verify its data in view mode");
        Pages.notesPage().clickNoteByName(note.getNewNote());
        Assert.assertEquals(Pages.notesPage().getDueDateValueInViewMode(), note.getDueDate(), "'Due Date' value does not match");
        Assert.assertEquals(Pages.notesPage().getResponsibleOfficerValueInViewMode(), note.getResponsibleOfficer(), "'Responsible officer' value does not match");

        logInfo("Step 8: Click [Delete] button");
        Pages.notesPage().clickDeleteButton();

        logInfo("Step 9: Open Clients profile on Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 10: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page

    }
}
