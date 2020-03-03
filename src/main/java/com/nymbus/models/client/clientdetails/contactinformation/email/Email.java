package com.nymbus.models.client.clientdetails.contactinformation.email;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Email {
    @NonNull private EmailType emailType;
    @NonNull private String email;
}
