package com.epam.bank.dao;

import com.epam.bank.connection.ConnectionFactory;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDao {

    static final Logger LOGGER = Logger.getLogger(UserDao.class);

    private static volatile UserDao instance;

    private UserDatabase userDatabase;

    private UserDao() { }

    private UserDao(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    private static UserDao getInstance() {
        if (instance == null) {
            throw new RuntimeException("Not initialized " + UserDao.class.getName());
        }

        return instance;
    }

    public static void initialization(UserDatabase userDatabase) {
        UserDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserDao(userDatabase);
                }
            }
        }
    }

    public static UserDatabase getUserDatabase() {

        return getInstance().userDatabase;
    }

    public static List<String> getUsersByRole(Role role) {
        List<String> result = new ArrayList<>();

        Iterator<User> iterator = getInstance().userDatabase.getUsers();
        while (iterator.hasNext()) {
            User itm = iterator.next();
            if (itm.isInRole(role)) {
                result.add(itm.getUsername());
            }
        }

        return result;
    }
}
