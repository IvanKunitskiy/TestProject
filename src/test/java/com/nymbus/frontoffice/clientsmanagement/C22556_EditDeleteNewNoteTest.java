package com.nymbus.frontoffice.clientsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.notes.NotesActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.User;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.verifyingmodels.MaintenanceHistoryDebitCardVerifyingModel;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22556_EditDeleteNewNoteTest extends BaseTest {

    private User user;
    private Note note;
    private String clientID;
    private MaintenanceHistoryDebitCardVerifyingModel verifyingModel;

    @BeforeMethod
    public void preCondition() {
        // Set up a user
        user = new User().setDefaultUserData();

        // Set up a client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        verifyingModel = new MaintenanceHistoryDebitCardVerifyingModel();
        verifyingModel.getRow().setFieldName("Due Date");

        // Set up a note
        note = new Note().setDefaultNoteData();
        note.setResponsibleOfficer(user.getFirstName() + " " + user.getLastName());

        // Create user and set him a password
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.usersActions().createUser(user);
        Actions.loginActions().doLogOut();
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setUserPassword(user);
        WebAdminActions.loginActions().doLogoutProgrammatically();

        // Create a client with a note and logout
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();
        Pages.accountNavigationPage().clickNotesTab();
        Actions.clientPageActions().closeAllNotifications();
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
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);

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
        verifyingModel.getRow().setNewValue(note.getDueDate());

        logInfo("Step 6: Verify that updated data is displayed for the note in the notes list");
        Assert.assertEquals(Pages.notesPage().getDueDateOfTheActiveNote(), note.getDueDate(), "'Due Date' value does not match");

        logInfo("Step 7: Click the updated note and verify its data in view mode");
        Pages.notesPage().clickNoteByName(note.getNewNote());
        Assert.assertEquals(Pages.notesPage().getDueDateValueInViewMode(), note.getDueDate(), "'Due Date' value does not match");
        Assert.assertEquals(Pages.notesPage().getResponsibleOfficerValueInViewMode(), note.getResponsibleOfficer(), "'Responsible officer' value does not match");

        logInfo("Step 8: Click [Delete] button");
        Pages.notesPage().clickDeleteButton();
        verifyingModel.getRow().setNewValue("");

        logInfo("Step 9: Open Clients profile on Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 10: Look through the records on the Maintenance History page and verify \n" +
                "that records about editing/deleting the Note are present on the Maintenance History page");
        AccountActions.accountMaintenanceActions().expandAllRows();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowsCountByFieldName("Due Date"), 2,
                "Due Date row's count doesn't match!");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowsCountByFieldName("Subject"), 2,
                "Subject row's count doesn't match!");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowsCountByFieldName("Initials"), 2,
                "Initials row's count doesn't match!");
        softAssert.assertEquals(Pages.accountMaintenancePage().getRowsCountByFieldName("Tellers"), 2,
                "Tellers row's count doesn't match!");
        softAssert.assertAll();
    }
}