package com.nymbus.model.client.details.individual;

import com.nymbus.model.RequiredField;
import lombok.Data;

import java.util.List;

@Data
public class IndividualClientDetails {
    private String suffix;
    private String maidenFamilyName;
    private List<String> AKAs;
    @RequiredField String profilePhoto;
    private Gender gender;
    private Education education;
    private Income income;
    private MaritalStatus maritalStatus;
    @RequiredField private String occupation;
}
