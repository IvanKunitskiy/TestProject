package com.nymbus.newmodels.client.other.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    CORPORATION("Corporation"),
    DIRECTOR("Director"),
    EMPLOYEE("Employee"),
    ESTATE("Estate"),
    FEDERAL_GOVERNMENT("Federal Government"),
    INDIVIDUAL("Individual"),
    JOINT_ACCOUNT("Joint Account"),
    NONPROFIT_ORGANIZATION("Nonprofit/Organization"),
    OFFICER("Officer"),
    PARTNERSHIP("Partnership"),
    SOLE_PROPRIETOR("Sole Proprietor"),
    STATE_COUNTRY_MUNICIPALITY("State/County/Municipality"),
    TRUST_ACCOUNT("Trust Account");

    private final String accountType;
}
