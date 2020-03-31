package com.nymbus.newmodels.client.other.document;

import com.nymbus.newmodels.client.other.File;

public interface Document {
    public IDType getIdType();
    public File getFile();
    public DocumentDetails getDocumentDetails();
}
