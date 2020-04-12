package com.nymbus.actions.clients.documents;

public class DocumentActions {

    /**
     * Actions
     */
    private static CreateDocumentActions createDocumentActions;

    /**
     * This function return an instance of `CreateDocumentActions`
     */
    public static CreateDocumentActions createDocumentActions() {
        if (createDocumentActions == null) {
            createDocumentActions = new CreateDocumentActions();
        }
        return createDocumentActions;
    }
}
