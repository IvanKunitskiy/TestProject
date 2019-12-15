package com.nymbus.util;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static ArrayList<String> sortItems(final List<String> list) {
        list
                .stream()
                .sorted((e1, e2) -> {
                            String[] s1 = e1.split("\\.");
                            String[] s2 = e2.split("\\.");

                            int result = s1[0].compareTo(s2[0]);
                            if (result != 0) return result;

                            return Integer.valueOf(s1[1]).compareTo(Integer.valueOf(s2[1]));
                        });
        return (ArrayList<String>) list;
    }
}
