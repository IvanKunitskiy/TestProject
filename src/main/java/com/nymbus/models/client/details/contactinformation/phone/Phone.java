package com.nymbus.models.client.details.contactinformation.phone;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.basicinformation.address.Country;
import lombok.Data;

@Data
public class Phone {
    @RequiredField PhoneType phoneType;
    @RequiredField Country country;
    @RequiredField String phoneNumber;
}
