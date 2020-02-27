package com.nymbus.models.client.basicinformation.type.individual;

import com.nymbus.models.client.basicinformation.type.ClientType;

public class Employee extends IndividualType {
    public Employee() {
        this(ClientType.EMPLOYEE);
    }

    private Employee(ClientType clientType) {
        super(clientType);
    }
}
