package com.nymbus.actions.notes;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.note.Note;
import com.nymbus.pages.Pages;

public class NotePageActions {

    public void createNote(Note note) {
        Pages.notesPage().clickAddNewNoteButton();
        NotesActions.editActions().setResponsibleOfficer(note);
        NotesActions.editActions().setSeverity(note);
        Pages.notesPage().typeToNewNoteTextArea(note.getNewNote());
        Pages.notesPage().setDueDateValue(note.getDueDate());
        Pages.notesPage().setExpirationDateValue(note.getExpirationDate());
        Pages.notesPage().clickSaveButton();
    }
}
