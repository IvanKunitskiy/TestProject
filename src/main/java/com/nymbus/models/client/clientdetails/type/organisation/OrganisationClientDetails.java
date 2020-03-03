package com.nymbus.models.client.clientdetails.type.organisation;

import com.nymbus.models.client.clientdetails.contactinformation.email.Email;
import com.nymbus.models.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.models.client.other.File;
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
