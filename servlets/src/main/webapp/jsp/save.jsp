<%--
  Created by IntelliJ IDEA.
  User: znadz
  Date: 22.10.2026
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Save users</title>
</head>
<body>
<h1>Добавление пользователей</h1>

<form action="${pageContext.request.contextPath}/save" method="post">
    <input type="hidden" name="count" value="${count}">

    <c:forEach begin="1" end="${count}" var="i">
        <fieldset>
            <legend>Пользователь ${i}</legend>
            <input name="firstName_${i}" placeholder="First name">
            <input name="lastName_${i}" placeholder="Last name">
            <input name="email_${i}" placeholder="Email">
            <input name="age_${i}" type="number" placeholder="Age">
            <input name="city_${i}" placeholder="City">
            <input name="phone_${i}" placeholder="Phone">
        </fieldset>
    </c:forEach>

    <button type="submit">Сохранить</button>
</form>
</body>
</html>