package com.nymbus.newmodels.client.verifyingmodels;

import com.nymbus.newmodels.client.basicinformation.address.AddressType;
import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import lombok.Data;
import lombok.NonNull;

@Data
public class TrustAccountPredefinedField {
    @NonNull private TaxPayerIDType taxPayerIDType;
    @NonNull private AddressType addressType;
    @NonNull private Country country;
}
