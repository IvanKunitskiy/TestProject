package com.nymbus.model.client;

import com.nymbus.model.RequiredField;
import com.nymbus.model.client.basicinformation.ClientType;
import com.nymbus.model.client.basicinformation.address.Address;
import com.nymbus.model.client.details.email.Email;
import com.nymbus.model.client.details.phone.Phone;
import com.nymbus.model.client.other.account.Account;
import com.nymbus.models.card.DebitCard;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public abstract class Client {
    /*Step 1 - Basic Information*/
    @RequiredField private ClientType clientType;
    @RequiredField private Set<Address> addresses; // At least one

    /*Step 2 - Client Details*/
    @RequiredField private Set<Phone> phones; // At least one
    private Set<Email> emails;

    private List<Account> accounts;
    private List<DebitCard> debitCards;
}
