package com.nymbus.models.client.clientdetails.contactinformation.email;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Email {
    @NonNull private EmailType emailType;
    @NonNull private String email;
}
