package com.nymbus.actions.notes;

public class NotesActions {

    /**
     * Actions
     */

    private static EditNoteActions editNoteActions;

    /**
     * This function return an instance of `editNoteActions`
     */

    public static EditNoteActions editActions() {
        if (editNoteActions == null) {
            editNoteActions = new EditNoteActions();
        }
        return editNoteActions;
    }

}