package com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation;

import com.nymbus.newmodels.client.clientdetails.contactinformation.email.Email;
import com.nymbus.newmodels.client.clientdetails.contactinformation.email.EmailType;
import com.nymbus.util.Random;

public class EmailFactory {
    public Email getEmail() {
        Email email = new Email();
        email.setEmailType(EmailType.PRIMARY);
        email.setEmail(Random.genEmail("testemail@email.com"));

        return email;
    }

    public Email getAlternateEmail() {
        Email email = new Email();
        email.setEmailType(EmailType.ALTERNATE);
        email.setEmail(Random.genEmail("testemail@email.com"));

        return email;
    }

    public Email getEmailWithType(EmailType emailType) {
        Email email = getEmail();
        email.setEmailType(emailType);

        return email;
    }
}
