package com.nymbus.model.client.other.document;

import com.nymbus.model.RequiredField;

import java.io.File;
import java.util.Objects;

public abstract class Document {
    @RequiredField private IDType idType;
    @RequiredField private File file;

    public IDType getIdType() {
        return idType;
    }

    public void setIdType(IDType idType) {
        this.idType = idType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document document = (Document) o;
        return idType == document.idType &&
                Objects.equals(file, document.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idType, file);
    }
}
