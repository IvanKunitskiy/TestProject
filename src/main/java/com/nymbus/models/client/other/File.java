package com.nymbus.models.client.other;

public enum File {
    PROFILE_PHOTO_PNG("profilePhoto.png"),
    CLIENT_DOCUMENT_PNG("clientDocument.png"),
    CLIENT_SIGNATUTE_PNG("clientSignature.png");

    private final String fileName;

    File(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
