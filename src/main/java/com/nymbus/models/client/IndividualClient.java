package com.nymbus.models.client;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.basicinformation.type.individual.IndividualType;
import com.nymbus.models.client.clientdetails.type.individual.IndividualClientDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class IndividualClient extends Client {
    @RequiredField private IndividualType individualType;
    @RequiredField private IndividualClientDetails individualClientDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndividualClient)) return false;
        if (!super.equals(o)) return false;
        IndividualClient that = (IndividualClient) o;
        return Objects.equals(individualType, that.individualType) &&
                Objects.equals(individualClientDetails, that.individualClientDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), individualType, individualClientDetails);
    }

    @Override
    public String toString() {
        return "IndividualClient{" +
                "individualType=" + individualType +
                ", individualClientDetails=" + individualClientDetails +
                "} " + super.toString();
    }
}
