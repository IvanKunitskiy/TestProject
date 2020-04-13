package com.nymbus.newmodels.generation.client.other;

import com.nymbus.newmodels.client.other.File;
import com.nymbus.newmodels.client.other.document.AccountLevelDocument;
import com.nymbus.util.Random;

public class AccountLevelDocumentFactory {
    public AccountLevelDocument getAccountLevelDocument() {
        AccountLevelDocument document = new AccountLevelDocument();

        document.setFile(File.CLIENT_DOCUMENT_PNG);
        document.setNote(Random.genString(20));

        return document;
    }
}