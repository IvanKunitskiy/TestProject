package com.nymbus.models.client.details.type.individual;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.details.contactinformation.email.Email;
import com.nymbus.models.client.details.contactinformation.phone.Phone;
import com.nymbus.models.client.details.type.organisation.MailCode;
import com.nymbus.models.client.other.File;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class IndividualClientDetails {
    private String suffix;
    private String maidenFamilyName;
    private List<String> AKAs;
    @RequiredField File profilePhoto;
    private Gender gender;
    private Education education;
    private Income income;
    private MaritalStatus maritalStatus;
    @RequiredField private String occupation;
    private ConsumerInformationIndicator consumerInformationIndicator;
    private String jobTitle;
    private OwnOrRent ownOrRent;
    private MailCode mailCode;
    @RequiredField String selectOfficer;
    private boolean serviceMember;
    private boolean truStageMembershipOptOut;
    private List<String> userDefinedFields;
    private boolean usingOnlineBank;

    @RequiredField private Set<Phone> phones; // At least one
    private Set<Email> emails;


}
