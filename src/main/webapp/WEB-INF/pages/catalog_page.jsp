<%--
  Created by IntelliJ IDEA.
  User: Павел
  Date: 04.10.2017
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Catalog of games</title>
</head>
<body>

<a href="${pageContext.request.contextPath}/catalog/add">Add new goods</a>
<br>
<table border="1" cellpadding="2" cellspacing="2">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Number of players</th>
        <th>Game duration</th>
        <th>Complexity of the rule</th>
        <th>Amount</th>
        <th>Is Visible</th>
        <th>Description</th>
        <th>Img</th>
        <th>Category</th>
        <th>Rules</th>
        <th>Option</th>
    </tr>
    <c:forEach var="goodsVar" items="${listGoods}">
        <tr>
            <td>${goodsVar.id}</td>
            <td>${goodsVar.name}</td>
            <td>${goodsVar.price}</td>
            <td>${goodsVar.numberOfPlayers}</td>
            <td>${goodsVar.duration}</td>
            <td>${goodsVar.rule.name}</td>
            <td>${goodsVar.amount}</td>
            <td>${goodsVar.visible}</td>
            <td>${goodsVar.description}</td>
            <td>${goodsVar.img}</td>
            <td>${goodsVar.category.name}</td>
            <td>${goodsVar.rule.name}</td>
            <td>
                <a href="${pageContext.request.contextPath}/catalog/delete/${goodsVar.id}" onclick="return confirm('Are you sure?')">Delete</a> |
                <a href="${pageContext.request.contextPath}/catalog/edit/${goodsVar.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
