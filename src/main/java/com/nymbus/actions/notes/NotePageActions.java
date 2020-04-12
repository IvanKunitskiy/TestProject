package com.nymbus.actions.notes;

import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;

public class NotePageActions {

    public void createNote(Note note) {
        if (!Pages.accountNavigationPage().isNotesTabActive()) {
            Pages.accountNavigationPage().clickNotesTab();
        }
        Pages.notesPage().clickAddNewNoteButton();
        // add responsible officer
        Pages.notesPage().typeToNewNoteTextArea(note.getNewNote());
        // add severity
        Pages.notesPage().setDueDateValue(note.getDueDate());
        // set expiration date
        // set template
        Pages.notesPage().clickSaveButton();
    }
}
