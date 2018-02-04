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
    <title>Main</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel="stylesheet" type="text/css" href="${context}/resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="${context}/resources/css/main.css">
</head>
<body>
    <div class="logout">
        <a href="${context}/logout" title="En"><fmt:message key="logout.label" /></a>
    </div>
    <div class="language">
        <a href="${context}/locale/en" title="En">En</a>
        <a href="${context}/locale/ru" title="Ру">Ру</a>
    </div>

    <div>
        <a href="${context}/account"><fmt:message key="main.label.acounts" /></a>
    </div>

</body>