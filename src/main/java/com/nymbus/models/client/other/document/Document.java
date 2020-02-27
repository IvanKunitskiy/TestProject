package com.nymbus.models.client.other.document;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.other.File;
import lombok.Data;

@Data
public abstract class Document {
    @RequiredField private IDType idType;
    @RequiredField private File file;
}
