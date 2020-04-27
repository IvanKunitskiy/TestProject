package com.nymbus.newmodels.generation.client.factory.clientdetails.type;

import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.clientdetails.type.individual.*;
import com.nymbus.newmodels.client.clientdetails.type.organisation.MailCode;
import com.nymbus.newmodels.client.other.File;
import com.nymbus.util.Random;

import java.util.Collections;

public class IndividualClientDetailsFactory {
    public IndividualClientDetails getIndividualClientDetails() {
        IndividualClientDetails individualClientDetails = new IndividualClientDetails();
        individualClientDetails.setSuffix(Random.genString(10));
        individualClientDetails.setMaidenFamilyName(Random.genString(10));
        individualClientDetails.setAKAs(Collections.singletonList(Random.genString(10)));
        individualClientDetails.setProfilePhoto(File.PROFILE_PHOTO_PNG);
        individualClientDetails.setGender(Gender.MALE);
        individualClientDetails.setEducation(Education.MASTERS_DEGREE);
        individualClientDetails.setIncome(Income.FROM_20K_TO_30K);
        individualClientDetails.setMaritalStatus(MaritalStatus.SINGLE);
        individualClientDetails.setOccupation(Random.genString(10));
        individualClientDetails.setConsumerInformationIndicator(ConsumerInformationIndicator.A1_PERSONAL_RECEIVERSHIP);
        individualClientDetails.setJobTitle(Random.genString(10));
        individualClientDetails.setOwnOrRent(OwnOrRent.OWN);
        individualClientDetails.setMailCode(MailCode.MAIL);
        individualClientDetails.setSelectOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);
        individualClientDetails.setServiceMember(false);
        individualClientDetails.setTruStageMembershipOptOut(false);
        individualClientDetails.setUserDefinedFields(Collections.singletonList(Random.genString(10)));
        individualClientDetails.setUsingOnlineBank(false);

        individualClientDetails.setPhones(Collections.EMPTY_LIST);
        individualClientDetails.setEmails(Collections.EMPTY_LIST);

        return individualClientDetails;
    }
}
