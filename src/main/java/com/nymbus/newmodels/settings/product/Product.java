package com.nymbus.newmodels.settings.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Product {
    BASIC_BUSINESS_CHECKING("Basic Business Checking");

    private final String product;
}
