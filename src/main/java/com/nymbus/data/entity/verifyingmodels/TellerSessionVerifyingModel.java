package com.nymbus.data.entity.verifyingmodels;

import lombok.Data;

@Data
public class TellerSessionVerifyingModel {
    private boolean isUserSessionExist;
    private String CFMIntegrationEnabled;
    private int CFMIntegrationEnabledSettingValue;
}