package com.nymbus.models.generation.client.factory.details.contactinformation;

import com.nymbus.models.client.basicinformation.address.Country;
import com.nymbus.models.client.details.contactinformation.phone.Phone;
import com.nymbus.models.client.details.contactinformation.phone.PhoneType;
import com.nymbus.util.Random;

public class PhoneFactory {
    public Phone getPhone() {
        Phone phone = new Phone();
        phone.setPhoneType(PhoneType.MOBILE);
        phone.setCountry(Country.UNITED_STATES);
        phone.setPhoneNumber(Random.genMobilePhone());

        return phone;
    }

    public Phone getPhoneWithType(PhoneType phoneType) {
        Phone phone = getPhone();
        phone.setPhoneType(phoneType);

        return phone;
    }
}
