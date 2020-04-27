package com.nymbus.newmodels.generation.client.factory.clientdetails.type;

import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.clientdetails.type.organisation.MailCode;
import com.nymbus.newmodels.client.clientdetails.type.organisation.OrganisationClientDetails;
import com.nymbus.newmodels.client.other.File;
import com.nymbus.util.Random;

import java.util.Collections;

public class OrganisationClientDetailsFactory {
    public OrganisationClientDetails getOrganisationClientDetails() {
        OrganisationClientDetails organisationClientDetails = new OrganisationClientDetails();
        organisationClientDetails.setIndustry("334515");
        organisationClientDetails.setMailCode(MailCode.MAIL);
        organisationClientDetails.setSelectOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);
        organisationClientDetails.setAKAs(Collections.singletonList(Random.genString(10)));
        organisationClientDetails.setProfilePhoto(File.PROFILE_PHOTO_PNG);
        organisationClientDetails.setTruStageMembershipOptOut(false);
        organisationClientDetails.setUserDefinedFields(Collections.singletonList(Random.genString(10)));
        organisationClientDetails.setUsingOnlineBank(false);

        organisationClientDetails.setPhones(Collections.EMPTY_LIST);
        organisationClientDetails.setEmails(Collections.EMPTY_LIST);

        return organisationClientDetails;
    }
}
