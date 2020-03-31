package com.nymbus.newmodels.generation.client;

import com.nymbus.newmodels.client.other.document.IDType;
import lombok.Data;

import java.util.Map;

@Data
public class OrganisationClientSettings {
    private int addressCount;
    private int phonesCount;
    private int emailCount;
    private Map<IDType, Integer> documents;
}
