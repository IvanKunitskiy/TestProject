package com.nymbus.models.client.details.type.organisation;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.details.contactinformation.email.Email;
import com.nymbus.models.client.details.contactinformation.phone.Phone;
import com.nymbus.models.client.other.File;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class OrganisationClientDetails {
    private String industry;
    private MailCode mailCode;
    @RequiredField String selectOfficer;
    private List<String> AKAs;
    @RequiredField File profilePhoto;
    private boolean truStageMembershipOptOut;
    private List<String> userDefinedFields;
    private boolean usingOnlineBank;

    @RequiredField private Set<Phone> phones; // At least one
    private Set<Email> emails;
}
