package com.nymbus.newmodels.client.verifyingmodels;

import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.util.Random;
import lombok.Data;

import java.util.List;

@Data
public class TrustAccountUpdateModel {
    private String userDefinedField1 = Random.genString(10);
    private String userDefinedField2 = Random.genString(10);
    private String userDefinedField3 = Random.genString(10);
    private String userDefinedField4 = Random.genString(10);
    private List<Phone> updatedPhones;
    private Address additionalAddress;
}