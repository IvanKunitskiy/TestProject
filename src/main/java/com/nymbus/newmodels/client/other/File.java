package com.nymbus.newmodels.client.other;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum File {
    PROFILE_PHOTO_PNG("profilePhoto.png"),
    CLIENT_DOCUMENT_PNG("clientDocument.png"),
    CLIENT_SIGNATURE_PNG("clientSignature.png");

    private final String fileName;
}
