package com.nymbus.core.utils;

public abstract class Mapper {

     public static String mapAllowForeignTransactions(boolean allowForeignTransactions) {
         if (allowForeignTransactions)
             return "1";
         else
             return "0";
     }
}
