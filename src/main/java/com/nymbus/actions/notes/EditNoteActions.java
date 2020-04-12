package com.nymbus.actions.notes;

import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class EditNoteActions {

    public void setResponsibleOfficer(Note note) {
        Pages.notesPage().clickResponsibleOfficerSelectorButton();
        List<String> listOfResponsibleOfficer = Pages.notesPage().getResponsibleOfficerList();

        Assert.assertTrue(listOfResponsibleOfficer.size() > 0, "There are no options available");
        if (note.getResponsibleOfficer() == null) {
            note.setResponsibleOfficer(listOfResponsibleOfficer.get(new Random().nextInt(listOfResponsibleOfficer.size())).trim());
        }
        Pages.notesPage().clickResponsibleOfficerSelectorOption(note.getResponsibleOfficer());
    }

    public void selectResponsibleOfficer(Note note) {
        if (Pages.notesPage().isResponsibleOfficerSelected()) {
            Pages.notesPage().clickClearResponsibleOfficerIcon();
        }
        Pages.notesPage().waitResponsibleOfficerInput();
        NotesActions.editActions().setResponsibleOfficer(note);
    }

    public void setSeverity(Note note) {
        Pages.notesPage().clickSeveritySelectorButton();
        List<String> listOfSeverity = Pages.notesPage().getSeverityList();

        Assert.assertTrue(listOfSeverity.size() > 0, "There are no options available");
        if (note.getSeverity() == null) {
            note.setSeverity(listOfSeverity.get(new Random().nextInt(listOfSeverity.size())).trim());
        }
        Pages.notesPage().clickSeveritySelectorOption(note.getSeverity());
    }
}
