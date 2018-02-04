<%@ page import="java.util.Locale" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>

<%
    String language = "en_EN";
    if (session != null
            && session.getAttribute("userLanguage") != null) {
        language = session.getAttribute("userLanguage").toString();
    }
%>

<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="language" value="<%=language%>" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html>
<head>
    <title>Login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel="stylesheet" type="text/css" href="${context}/resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="${context}/resources/css/login.css">
</head>
<body>
    <form id="login_form" name="login_form" method="post" action="j_security_check" enctype="application/x-www-form-urlencoded">
        <div class="language">
            <a href="${context}/locale/en" title="En">En</a>
            <a href="${context}/locale/ru" title="Ру">Ру</a>

        </div>
        <div class="imgcontainer">
            <img src="${context}/resources/img/bank.jpg" alt="Bank" class="avatar" height="200px" width="200px">
        </div>

        <div class="container">
            <label><b><fmt:message key="login.label.username" /></b></label>
            <input type="text" placeholder="<fmt:message key="login.label.username.placeholder" />" name="j_username" required>

            <label><b><fmt:message key="login.label.password" /></b></label>
            <input type="password" placeholder="<fmt:message key="login.label.password.placeholder" />" name="j_password" required>

            <label class="error"><b><fmt:message key="login.label.invalid" /></b></label>

            <button type="submit"><fmt:message key="login.label.login" /></button>
            <label>
                <input type="checkbox" checked="checked"><fmt:message key="login.label.remembe.me" />
            </label>
        </div>
    </form>
</body>