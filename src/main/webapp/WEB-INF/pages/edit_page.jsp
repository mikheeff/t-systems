<%--
  Created by IntelliJ IDEA.
  User: Павел
  Date: 09.10.2017
  Time: 1:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Editing goods</title>
</head>
<body>
<spring:form method="post" commandName="goods" action="../edit">
    <table border="0" cellpadding="2" cellspacing="2">
        <tr>
            <td>Id</td>
            <td><spring:input path="id" readonly="true"/></td>
        </tr><tr>
            <td>Name</td>
            <td><spring:input path="name"/></td>
        </tr>
        <tr>
            <td>Price</td>
            <td><spring:input path="price"/></td>
        </tr>
        <tr>
            <td>Number of players</td>
            <td><spring:input path="numberOfPlayers"/></td>
        </tr>
        <tr>
            <td>Game duration</td>
            <td><spring:input path="duration"/></td>
        </tr>
        <tr>
            <td>Complexity of the rule</td>
            <td><spring:input path="rule.id"/></td>
        </tr>
        <tr>
            <td>Amount</td>
            <td><spring:input path="amount"/></td>
        </tr>
        <tr>
            <td>Is visible</td>
            <td><spring:input path="visible"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><spring:textarea path="description" cols="20" rows="5"/></td>
        </tr>
        <tr>
            <td>Category Id</td>
            <td><spring:input path="category.id"/></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" value="Save"/></td>
        </tr>
    </table>
</spring:form>
</body>
</html>