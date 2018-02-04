package com.epam.bank.servlets;

import com.epam.bank.constants.AccountConstants;
import com.epam.bank.dao.AccountDao;
import com.epam.bank.dao.RoleDao;
import com.epam.bank.dao.UserDao;
import com.epam.bank.entities.Account;
import com.epam.bank.entities.types.AccountStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        try {
            doRequest(request, response, errors);
        } catch (SQLException e) {
            LOGGER.error(e);

            response.sendRedirect("/html/500.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        Account account = getNewAccount(request, errors);
        try {
            validateAccount(account, errors);
            if (errors.isEmpty()) {
                insertAccount(account, errors);
            } else {
                putInvalidAccountToRequest(account, request);
            }
            doRequest(request, response, errors);
        } catch (SQLException e) {
            LOGGER.error(e);

            response.sendRedirect("/html/500.html");
        }
    }

    private String getByUser(HttpServletRequest request) {
        RoleDao roleDao = new RoleDao();
        Principal principal = request.getUserPrincipal();
        String user = principal.getName();
        if (!UserDao.getUserDatabase().findUser(user).isInRole(roleDao.ADMIN)) {
            return user;
        }

        return null;
    }

    private String getBySort(HttpServletRequest request) {
        String sortBy = request.getParameter("sortBy");
        if (StringUtils.isNotEmpty(sortBy)
                && AccountConstants.ACCOUNT_FIELDS.contains(sortBy)) {
            return sortBy;
        }

        return null;
    }

    private List<Account> getAccounts(String byUser, String bySort) throws SQLException {
        AccountDao dao = new AccountDao();
        List<Account> result = dao.findAll(byUser, bySort);

        return result;
    }

    private List<String> getUsers() {
        RoleDao roleDao = new RoleDao();
        List<String> result = UserDao.getUsersByRole(roleDao.USER);

        return result;
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response, Map<String, String> errors)
            throws ServletException, IOException, SQLException {
        request.setAttribute("errors", errors);
        String byUser = getByUser(request);
        String bySort = getBySort(request);
        request.setAttribute("sortBy", bySort);

        List<Account> accounts = getAccounts(byUser, bySort);
        request.setAttribute("accounts", accounts);
        List<String> users = getUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(request, response);
    }

    private Account getNewAccount(HttpServletRequest request, Map<String, String> errors) {
        String user = request.getParameter(AccountConstants.ACCOUNT_USER);
        String name = request.getParameter(AccountConstants.ACCOUNT_NAME).trim();
        if (StringUtils.isEmpty(name)) {
            errors.put(AccountConstants.ACCOUNT_NAME, "account.table.name.invalid");
        }
        String number = request.getParameter(AccountConstants.ACCOUNT_NUMBER).trim();
        if (StringUtils.isEmpty(number)) {
            errors.put(AccountConstants.ACCOUNT_NUMBER, "account.table.number.invalid");
        }
        String amount = request.getParameter(AccountConstants.ACCOUNT_AMOUNT);
        BigDecimal amountValue = null;
        try {
            amountValue = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            errors.put(AccountConstants.ACCOUNT_AMOUNT, "account.table.amount.invalid");
        }

        Account result = new Account();
        result.setUser(user);
        result.setName(name);
        result.setNumber(number);
        result.setAmount(amountValue);
        result.setStatus(AccountStatus.WORK);

        return result;
    }

    private void validateAccount(Account account, Map<String, String> errors) throws SQLException {
        AccountDao dao = new AccountDao();
        Account existAccount = dao.findByNumber(account.getNumber());
        if (existAccount != null) {
            errors.put(AccountConstants.ACCOUNT_NUMBER, "account.table.number.exists");
        }
    }

    private void insertAccount(Account account, Map<String, String> errors) throws SQLException {
        AccountDao dao = new AccountDao();
        dao.insert(account);
    }

    private void putInvalidAccountToRequest(Account account, HttpServletRequest request) {
        request.setAttribute(AccountConstants.ACCOUNT_USER, account.getUser());
        request.setAttribute(AccountConstants.ACCOUNT_NAME, account.getName());
        request.setAttribute(AccountConstants.ACCOUNT_NUMBER, account.getNumber());
        request.setAttribute(AccountConstants.ACCOUNT_AMOUNT, account.getAmount());
    }

}
