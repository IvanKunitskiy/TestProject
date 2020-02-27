package com.nymbus.models.generation.client.factory.details.type;

import com.nymbus.models.client.details.type.individual.*;
import com.nymbus.models.client.details.type.organisation.MailCode;
import com.nymbus.models.client.other.File;
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
        individualClientDetails.setSelectOfficer(""); // TODO: Need to understand who is officer and when to set him up
        individualClientDetails.setServiceMember(false);
        individualClientDetails.setTruStageMembershipOptOut(false);
        individualClientDetails.setUserDefinedFields(Collections.singletonList(Random.genString(10)));
        individualClientDetails.setUsingOnlineBank(false);

        individualClientDetails.setPhones(Collections.EMPTY_SET);
        individualClientDetails.setEmails(Collections.EMPTY_SET);

        return individualClientDetails;
    }
}
