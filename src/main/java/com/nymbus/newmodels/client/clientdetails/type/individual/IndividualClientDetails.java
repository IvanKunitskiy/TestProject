package com.nymbus.newmodels.client.clientdetails.type.individual;

import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.client.clientdetails.type.organisation.MailCode;
import com.nymbus.newmodels.client.other.File;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class IndividualClientDetails {
    private String suffix;
    private String maidenFamilyName;
    private List<String> AKAs;
    @NonNull File profilePhoto;
    private Gender gender;
    private Education education;
    private Income income;
    private MaritalStatus maritalStatus;
    @NonNull private String occupation;
    private ConsumerInformationIndicator consumerInformationIndicator;
    private String jobTitle;
    private OwnOrRent ownOrRent;
    private MailCode mailCode;
    @NonNull String selectOfficer;
    private boolean serviceMember;
    private boolean truStageMembershipOptOut;
    private List<String> userDefinedFields;
    private boolean usingOnlineBank;

    @NonNull private Set<Phone> phones; // At least one
    private Set<Email> emails;


}
