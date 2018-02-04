package com.epam.bank.dao;

import org.apache.catalina.Role;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;

public class RoleDao {

    public static final String ADMIN_ROLE_NAME = "admin";
    public static final String USER_ROLE_NAME = "user";

    public final Role ADMIN;
    public final Role USER;

    public RoleDao() {
        ADMIN = getRole(ADMIN_ROLE_NAME);
        USER = getRole(USER_ROLE_NAME);
    }

    private Role getRole(String name) {
        if (StringUtils.isNotEmpty(name)) {
            Iterator<Role> iterator = UserDao.getUserDatabase().getRoles();
            while (iterator.hasNext()) {
                Role itm = iterator.next();
                if (name.equals(itm.getRolename())) {
                    return itm;
                }
            }
        }

        return null;
    }

}
