<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<title>Login page</title>
<body>
<H2>Welcome to login page</H2>
<form action="${pageContext.request.contextPath}/login" method="post">
            <c:if test="${not empty errors}">
                <c:forEach items="${errors}" var="error">
                    <c:out value="${error}"/>
                </c:forEach>
            </c:if>
        <br>
    Name:
        <br>
            <input type="text" name="name" required maxlength="15" pattern="^[a-zA-Z0-9_.-]*$" placeholder="Only eng letters">
        <br>
    Password:
        <br>
            <input type="password" name="password" required maxlength="9" pattern="^[a-zA-Z0-9]+$" placeholder="Only eng letters and numbs">
        <br>
        <br>
            <input type="submit" value="Login">
</body>
</html>