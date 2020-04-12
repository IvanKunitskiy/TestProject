package com.nymbus.newmodels.note;

import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;

public class Note {

    private String responsibleOfficer;
    private String newNote;
    private String severity;
    private String dueDate;
    private String expirationDate;
    private String template;

    public Note setDefaultNoteData() {
        Note note = new Note();

        note.setResponsibleOfficer("");
        note.setNewNote(Generator.genString(20));
        note.setSeverity("");
        note.setDueDate(DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"));
        note.setExpirationDate("");
        note.setTemplate("");

        return note;
    }

    public String getResponsibleOfficer() {
        return responsibleOfficer;
    }

    public void setResponsibleOfficer(String responsibleOfficer) {
        this.responsibleOfficer = responsibleOfficer;
    }

    public String getNewNote() {
        return newNote;
    }

    public void setNewNote(String newNote) {
        this.newNote = newNote;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }



}
