package com.nymbus.newmodels.client.other.document;

import com.nymbus.newmodels.client.other.File;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AlienRegCard implements Document {
    @NonNull private IDType idType;
    @NonNull private File file;
    @NonNull private String idNumber;
    private String issuedBy;
    @NonNull private String country;
    private String issueDate;
    @NonNull private String expirationDate;
}
