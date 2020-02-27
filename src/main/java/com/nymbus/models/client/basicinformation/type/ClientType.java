package com.nymbus.models.client.basicinformation.type;

public enum ClientType {
    CORPORATION("Corporation"),
    DIRECTOR("Director"),
    EMPLOYEE("Employee"),
    ESTATE("Estate"),
    FEDERAL_GOVERNMENT("Federal Government"),
    INDIVIDUAL("Individual"),
    INTERNAL_FI_OWNED("Internal FI Owned"),
    IOLTA("IOLTA"),
    JOINT_ACCOUNT("Joint Account"),
    NONPROFIT_ORGANIZATION("Nonprofit/Organization"),
    OFFICER("Officer"),
    PARTNERSHIP("Partnership"),
    SOLE_PROPRIETOR("Sole Proprietor"),
    STATE_COUNTRY_MUNICIPALITY("State/County/Municipality"),
    TRUST_ACCOUNT("Trust Account");

    private final String clientType;

    ClientType(String type) {
        this.clientType = type;
    }

    public String getClientType() {
        return clientType;
    }
}
