package com.epam.bank.dao;

import com.epam.bank.connection.ConnectionFactory;
import com.epam.bank.entities.Account;
import com.epam.bank.mappers.AccountMapper;
import com.epam.bank.sql.AccountSQL;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDao {

    private static final Logger LOGGER = Logger.getLogger(AccountDao.class);

    public List<Account> findAll(String user, String sortBy) throws SQLException {
        String sql = AccountSQL.getSelect();
        if (StringUtils.isNotEmpty(user)) {
            sql += " and user_id = '" + user + "'";
        }
        if (StringUtils.isNotEmpty(sortBy)) {
            sql += " order by " + sortBy + " asc";
        }
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            LOGGER.debug("SQL: " + sql);

            ResultSet resultSet = statement.executeQuery();
            return AccountMapper.mapper(resultSet);
        } catch (SQLException e) {
            throw e;
        }
    }

    public Account findByNumber(String number) throws SQLException {
        String sql = AccountSQL.getSelect() + " and number = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, number);
            LOGGER.debug("SQL: " + sql);

            ResultSet resultSet = statement.executeQuery();
            List<Account> result = AccountMapper.mapper(resultSet);
            return result.size() > 0
                    ? result.get(0)
                    : null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void insert(Account account) throws SQLException {
        String sql = AccountSQL.getInsert();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getUser());
            statement.setString(2, account.getName());
            statement.setString(3, account.getNumber());
            statement.setBigDecimal(4, account.getAmount());
            statement.setString(5, account.getStatus().toString());
            LOGGER.debug("SQL: " + sql);

            int rec = statement.executeUpdate();
            if (rec == 0) {
                throw new SQLException("Account hasn't beуn saved");
            }
        } catch (SQLException e) {
            throw e;
        }
    }

}
