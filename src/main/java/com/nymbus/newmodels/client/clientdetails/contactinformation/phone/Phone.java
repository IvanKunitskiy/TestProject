package com.nymbus.newmodels.client.clientdetails.contactinformation.phone;

import com.nymbus.newmodels.client.basicinformation.address.Country;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Phone {
    @NonNull PhoneType phoneType;
    @NonNull Country country;
    @NonNull String phoneNumber;
}
