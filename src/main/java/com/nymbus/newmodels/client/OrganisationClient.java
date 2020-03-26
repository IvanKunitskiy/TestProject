package com.nymbus.newmodels.client;

import com.nymbus.newmodels.client.basicinformation.type.organisation.OrganisationType;
import com.nymbus.newmodels.client.clientdetails.type.organisation.OrganisationClientDetails;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class OrganisationClient extends Client {
    @NonNull private OrganisationType organisationType;
    @NonNull private OrganisationClientDetails organisationClientDetails;

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
