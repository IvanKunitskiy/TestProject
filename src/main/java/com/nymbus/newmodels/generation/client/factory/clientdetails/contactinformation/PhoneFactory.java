package com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation;

import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.PhoneType;
import com.nymbus.util.Random;

import java.util.ArrayList;
import java.util.List;

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

    public List<Phone> getPhoneListForUpdatePurpose() {
        List<Phone> phoneList = new ArrayList<>();
        Phone mobilePhone = getPhone();
        mobilePhone.setPhoneType(PhoneType.MOBILE);
        phoneList.add(mobilePhone);
        Phone workPhone = getPhone();
        workPhone.setPhoneType(PhoneType.WORK);
        phoneList.add(workPhone);

        return phoneList;
    }
}