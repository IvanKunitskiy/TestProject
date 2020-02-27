package com.nymbus.models.client;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.models.client.clientdetails.type.organisation.OrganisationClientDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class OrganisationClient extends Client {
    @RequiredField private OrganisationType organisationType;
    @RequiredField private OrganisationClientDetails organisationClientDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganisationClient)) return false;
        if (!super.equals(o)) return false;
        OrganisationClient that = (OrganisationClient) o;
        return Objects.equals(organisationType, that.organisationType) &&
                Objects.equals(organisationClientDetails, that.organisationClientDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), organisationType, organisationClientDetails);
    }

    @Override
    public String toString() {
        return "OrganisationClient{" +
                "organisationType=" + organisationType +
                ", organisationClientDetails=" + organisationClientDetails +
                "} " + super.toString();
    }
}
