package com.epam.bank.jstl;

import com.epam.bank.configuration.BankApplicationContextListener;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class MessageTag extends SimpleTagSupport {

    private static final Logger LOGGER = Logger.getLogger(MessageTag.class);

    private String message;
    private String language;

    public MessageTag() {
    }

    public void setMessage(String message) {
        this.message = message.toUpperCase();
    }

    public void setLanguage(String language) {
        this.language = language.toUpperCase();
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            String result = message + '_' + language;
            getJspContext().getOut().write(result);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
            // stop page from loading further by throwing SkipPageException
            throw new SkipPageException("Exception " + message + '_' + language);
        }
    }

}