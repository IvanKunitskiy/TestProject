package com.nymbus.core.utils;

import java.io.File;

public class Functions {

    public static String getOnlyDigitsFromString(String s) {
        return s.replaceAll("[^0-9?!.]", "");
    }

    public static String getFilePathByName(String fileName) {
        return new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "client" + File.separator + fileName).getAbsolutePath();
    }
}
