package com.nymbus.models.client.clientdetails.contactinformation.phone;

import com.nymbus.models.client.basicinformation.address.Country;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Phone {
    @NonNull PhoneType phoneType;
    @NonNull Country country;
    @NonNull String phoneNumber;
}
