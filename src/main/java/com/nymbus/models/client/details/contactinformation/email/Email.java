package com.nymbus.models.client.details.contactinformation.email;

import com.nymbus.models.RequiredField;
import lombok.Data;

@Data
public class Email {
    @RequiredField private EmailType emailType;
    @RequiredField private String email;
}
