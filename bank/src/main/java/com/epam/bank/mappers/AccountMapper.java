package com.epam.bank.mappers;

import com.epam.bank.constants.AccountConstants;
import com.epam.bank.entities.Account;
import com.epam.bank.entities.types.AccountStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public static List<Account> mapper(ResultSet resultSet) throws SQLException {
        List<Account> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(getAccount(resultSet));
        }

        return result;
    }

    public static Account getAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt(AccountConstants.ACCOUNT_ID));
        account.setUser(resultSet.getString(AccountConstants.ACCOUNT_USER));
        account.setName(resultSet.getString(AccountConstants.ACCOUNT_NAME));
        account.setNumber(resultSet.getString(AccountConstants.ACCOUNT_NUMBER));
        account.setAmount(resultSet.getBigDecimal(AccountConstants.ACCOUNT_AMOUNT));
        account.setStatus(AccountStatus.valueOf(resultSet.getString(AccountConstants.ACCOUNT_STATUS)));

        return account;
    }

}
