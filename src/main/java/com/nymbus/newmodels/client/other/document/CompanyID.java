package com.nymbus.newmodels.client.other.document;

import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.other.File;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CompanyID implements Document {
    @NonNull private IDType idType;
    @NonNull private File file;
    private String idNumber;
    private State issuedBy;
    private Country country;
    private String issueDate;
    private String expirationDate;
}
