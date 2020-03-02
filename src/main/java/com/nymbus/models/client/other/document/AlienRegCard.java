package com.nymbus.models.client.other.document;

import com.nymbus.models.client.other.File;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AlienRegCard implements Document {
    @NonNull private IDType idType;
    @NonNull private File file;
    @NonNull private String idNumber;
    private String issuedBy;
    @NonNull private String country;
    private String issueDate;
    @NonNull private String expirationDate;
}
