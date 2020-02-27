package com.nymbus.models.generation.client.factory.details.contactinformation;

import com.nymbus.models.client.details.contactinformation.email.Email;
import com.nymbus.models.client.details.contactinformation.email.EmailType;
import com.nymbus.util.Random;

public class EmailFactory {
    public Email getEmail() {
        Email email = new Email();
        email.setEmailType(EmailType.PRIMARY);
        email.setEmail(Random.genEmail("testemail@email.com"));

        return email;
    }

    public Email getEmailWithType(EmailType emailType) {
        Email email = getEmail();
        email.setEmailType(emailType);

        return email;
    }
}
