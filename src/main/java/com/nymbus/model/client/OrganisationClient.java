package com.nymbus.model.client;

import com.nymbus.model.RequiredField;
import com.nymbus.model.client.basicinformation.organisation.OrganisationType;
import com.nymbus.model.client.details.organisation.OrganisationClientDetails;
import lombok.Data;

@Data
public class OrganisationClient extends Client {
    @RequiredField private OrganisationType organisationType;
    @RequiredField private OrganisationClientDetails organisationClientDetails;
}
