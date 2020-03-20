package com.nymbus.newmodels.client.clientdetails.type.organisation;

import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
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
public class OrganisationClientDetails {
    private String industry;
    private MailCode mailCode;
    @NonNull String selectOfficer;
    private List<String> AKAs;
    @NonNull File profilePhoto;
    private boolean truStageMembershipOptOut;
    private List<String> userDefinedFields;
    private boolean usingOnlineBank;

    @NonNull private Set<Phone> phones; // At least one
    private Set<Email> emails;
}
