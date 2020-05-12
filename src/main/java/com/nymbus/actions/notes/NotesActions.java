package com.nymbus.actions.notes;

public class NotesActions {

    /**
     * Actions
     */

    private static EditNoteActions editNoteActions;
    private static NotePageActions notePageActions;

    /**
     * This function return an instance of `editNoteActions`
     */

    public static EditNoteActions editActions() {
        if (editNoteActions == null) {
            editNoteActions = new EditNoteActions();
        }
        return editNoteActions;
    }

    public static NotePageActions notePageActions() {
        if (notePageActions == null) {
            notePageActions = new NotePageActions();
        }
        return notePageActions;
    }

}