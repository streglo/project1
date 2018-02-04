package com.epam.bank.sql;

import java.util.ResourceBundle;

public class AccountSQL {

    private static final ResourceBundle RESOURCE_BUNDLE;

    static {
        RESOURCE_BUNDLE = ResourceBundle.getBundle("accountSQL");
    }

    private static String selectByNumber;

    public static String getSelect() {
        return RESOURCE_BUNDLE.getString("SELECT");
    }

    public static String getInsert() {
        return RESOURCE_BUNDLE.getString("INSERT");
    }

}
