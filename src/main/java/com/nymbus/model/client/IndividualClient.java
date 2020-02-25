package com.nymbus.model.client;

import com.nymbus.model.RequiredField;
import com.nymbus.model.client.basicinformation.individual.IndividualType;
import com.nymbus.model.client.details.individual.IndividualClientDetails;
import lombok.Data;

@Data
public class IndividualClient extends Client {
    @RequiredField private IndividualType individualType;
    @RequiredField private IndividualClientDetails individualClientDetails;
}
