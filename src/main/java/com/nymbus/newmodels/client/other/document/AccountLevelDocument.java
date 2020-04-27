package com.nymbus.newmodels.client.other.document;

import com.nymbus.newmodels.client.other.File;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

public class AccountLevelDocument {
    @NonNull private String category;
    @NonNull private String docType;
    @NonNull private File file;
    private String note;
}
