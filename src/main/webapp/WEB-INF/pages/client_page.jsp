<%--ошибка--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All clients</title>
</head>
<body>
    <table border="1" cellpadding="2" cellspacing="2">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Birthdate</th>
            <th>E-mail</th>
            <th>Password</th>
            <th>Phone Number</th>
            <th>Amount of orders</th>
            <th>Role Id</th>
        </tr>
        <c:forEach var="clientVar" items="${listClient}">
            <tr>
                <td>${clientVar.id}</td>
                <td>${clientVar.name}</td>
                <td>${clientVar.surname}</td>
                <td>${clientVar.birthdate}</td>
                <td>${clientVar.email}</td>
                <td>${clientVar.password}</td>
                <td>${clientVar.phone}</td>
                <td>${clientVar.orderCounter}</td>
                <td>${clientVar.idRole}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
