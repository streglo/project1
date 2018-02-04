package com.epam.bank.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface AccountConstants {

    String ACCOUNT_ID = "ACCOUNT_ID";
    String ACCOUNT_USER = "ACCOUNT_USER";
    String ACCOUNT_NAME = "ACCOUNT_NAME";
    String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
    String ACCOUNT_AMOUNT = "ACCOUNT_AMOUNT";
    String ACCOUNT_STATUS = "ACCOUNT_STATUS";

    Set<String> ACCOUNT_FIELDS = new HashSet<String>(Arrays.asList(
            ACCOUNT_ID,
            ACCOUNT_USER,
            ACCOUNT_NAME,
            ACCOUNT_NUMBER,
            ACCOUNT_AMOUNT,
            ACCOUNT_STATUS
    ));

}
