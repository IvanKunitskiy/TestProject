package com.nymbus.model.client.details.organisation;

import com.nymbus.model.RequiredField;
import lombok.Data;

import java.util.List;

@Data
public class OrganisationClientDetails {
    private String industry;
    private MailCode mailCode;
    @RequiredField String selectOfficer;
    private List<String> AKAs;
    @RequiredField String profilePhoto;
    private boolean truStageMembershipOptOut;
    private List<String> userDefinedFields;
    private boolean usingOnlineBank;
}
