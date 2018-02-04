package com.epam.bank.servlets;

import org.apache.catalina.core.ApplicationContext;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

public class LocaleServlet extends HttpServlet {

    private final static String LANGUAGE_EN = "en_EN";
    private final static String LANGUAGE_RU = "ru_RU";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer = req.getHeader("Referer");
        if (referer.endsWith(req.getContextPath() + '/')) {
            referer += "main";
        }

        String language = LANGUAGE_EN;
        String url = req.getRequestURL().toString();
        if (url.endsWith("/ru")) {
            language = LANGUAGE_RU;
        }

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.setAttribute("userLanguage", language);
        }

        resp.sendRedirect(referer);
    }

}
