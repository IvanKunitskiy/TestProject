package com.nymbus.models.generation.client.factory.clientdetails.type;

import com.nymbus.models.client.clientdetails.type.organisation.MailCode;
import com.nymbus.models.client.clientdetails.type.organisation.OrganisationClientDetails;
import com.nymbus.models.client.other.File;
import com.nymbus.util.Random;

import java.util.Collections;

public class OrganisationClientDetailsFactory {
    public OrganisationClientDetails getOrganisationClientDetails() {
        OrganisationClientDetails organisationClientDetails = new OrganisationClientDetails();
        organisationClientDetails.setIndustry(Random.genString(10));
        organisationClientDetails.setMailCode(MailCode.MAIL);
        organisationClientDetails.setSelectOfficer(""); // TODO: Need to understand who is officer and when to set him up
        organisationClientDetails.setAKAs(Collections.singletonList(Random.genString(10)));
        organisationClientDetails.setProfilePhoto(File.PROFILE_PHOTO_PNG);
        organisationClientDetails.setTruStageMembershipOptOut(false);
        organisationClientDetails.setUserDefinedFields(Collections.singletonList(Random.genString(10)));
        organisationClientDetails.setUsingOnlineBank(false);

        organisationClientDetails.setPhones(Collections.EMPTY_SET);
        organisationClientDetails.setEmails(Collections.EMPTY_SET);

        return organisationClientDetails;
    }
}
