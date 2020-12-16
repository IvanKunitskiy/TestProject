package com.nymbus.newmodels.cashier;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayeeType {
    BUSINESS("Business"),
    PERSON("Person");

    private String type;
}
