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
    <link rel="stylesheet" type="text/css" href="${context}/resources/css/account.css">
    <script type="text/javascript">
        window.ACCOUNT_ERROR = ${not empty errors};
        window.ACCOUNT_URL = "${context}/account";
        window.QUERY = window.location.href.split('?')[1];
        window.ACCOUNT_SORT_BY = "${sortBy}";
    </script>
    <script type="text/javascript" src="${context}/resources/js/account.js"></script>
</head>
<body onload="javascript:accountLoadPage();">
    <div class="logout">
        <a href="${context}/logout" title="En"><fmt:message key="logout.label" /></a>
    </div>
    <div class="language">
        <a href="${context}/locale/en" title="En">En</a>
        <a href="${context}/locale/ru" title="Ру">Ру</a>
    </div>

    <div>
        <form id="addAccount" name="addAccount" method="post" action="${context}/account" enctype="application/x-www-form-urlencoded">
            <table border="1">
                <caption><fmt:message key="account.table.caption" /></caption>
                <tr>
                    <th>ID</th>
                    <th>USER NAME<input type="radio" name="account_sort" id="ACCOUNT_USER" onclick="javascript:accountSortClick(this);"></th>
                    <th>NAME<input type="radio" name="account_sort" id="ACCOUNT_NAME" onclick="javascript:accountSortClick(this);"></th>
                    <th>NUMBER<input type="radio" name="account_sort" id="ACCOUNT_NUMBER" onclick="javascript:accountSortClick(this);"></th>
                    <th>AMOUNT<input type="radio" name="account_sort" id="ACCOUNT_AMOUNT" onclick="javascript:accountSortClick(this);"></th>
                    <th>STATUS</th>
                    <th>TO DO</th>
                </tr>
            <c:forEach items="${accounts}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.user}</td>
                    <td>${item.name}</td>
                    <td>${item.number}</td>
                    <td>${item.amount}</td>
                    <td>${item.status}</td>
                    <td></td>
                </tr>
            </c:forEach>
<% if (request.isUserInRole("admin")) { %>
                <tr id="newAccount" hidden="false">
                    <td></td>
                    <td>
                        <select name="ACCOUNT_USER" value="${ACCOUNT_USER}">
                        <c:forEach items="${users}" var="item">
                            <option>${item}</option>
                        </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input type="text" placeholder="<fmt:message key="account.table.name.placeholder" />" name="ACCOUNT_NAME" value="${ACCOUNT_NAME}" required>
                        <div>
                            <label class="error"
                                   style="display: ${not empty errors['ACCOUNT_NAME'] ? 'block' : 'none'}">
                                <fmt:message key="${errors['ACCOUNT_NAME']}" />
                            </label>
                        </div>
                    </td>
                    <td>
                        <input type="text" placeholder="<fmt:message key="account.table.number.placeholder" />" name="ACCOUNT_NUMBER" value="${ACCOUNT_NUMBER}" required>
                        <div>
                            <label class="error"
                                   style="display: ${not empty errors['ACCOUNT_NUMBER'] ? 'block' : 'none'}">
                                <fmt:message key="${errors['ACCOUNT_NUMBER']}" />
                            </label>
                        </div>
                    </td>
                    <td>
                        <input type="text" placeholder="<fmt:message key="account.table.amount.placeholder" />" name="ACCOUNT_AMOUNT" value="${not empty ACCOUNT_AMOUNT ? ACCOUNT_AMOUNT : '0.00'}" required>
                        <div>
                            <label class="error"
                                   style="display: ${not empty errors['ACCOUNT_AMOUNT'] ? 'block' : 'none'}">
                                <fmt:message key="${errors['ACCOUNT_AMOUNT']}" />
                            </label>
                        </div>
                    </td>
                    <td></td>
                    <td><input type="image" class="postAccount" src="${context}/resources/img/ok.jpg" border="0" alt="Submit" /></td>
                </tr>
<% } %>
            </table>
        </form>
<% if (request.isUserInRole("admin")) { %>
        <input type="image" class="addAccount" src="${context}/resources/img/plus.png" onclick="javascript:accountPlus();"/>
<% } %>
    </div>

</body>