package com.nymbus.newmodels.client.clientdetails.contactinformation.documents;

import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.address.State;
import com.nymbus.newmodels.client.other.document.IDType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class StateDriverLicense{
    @NonNull
    private IDType idType;
    private String idNumber;
    private State issuedBy;
    private Country country;
    private String issueDate;
    private String expirationDate;
}
