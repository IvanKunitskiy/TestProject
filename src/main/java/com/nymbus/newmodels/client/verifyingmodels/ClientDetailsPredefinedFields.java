package com.nymbus.newmodels.client.verifyingmodels;

import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.client.clientdetails.type.organisation.MailCode;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ClientDetailsPredefinedFields {
    @NonNull private MailCode mailCode;
    @NonNull private String selectOfficer;
    @NonNull private Phone phone;
}