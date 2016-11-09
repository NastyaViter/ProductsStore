<%--
  Created by IntelliJ IDEA.
  User: Ксения
  Date: 08.11.2016
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/products">products</a>
<table border="1" width="80%">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>price</th>
        <th>num</th>
        <th>add</th>
        <th>delete</th>
    </tr>
    <c:forEach items="${cartList}" var="item">
        <tr>
            <td>${item.getProduct().getId()} </td>
            <td>${item.getProduct().getName()} </td>
            <td>${item.getProduct().getPrice()} </td>
            <td>${item.getNumber()}</td>
            <td><a href="/addCartProduct/${item.getProduct().getId()}">+</a></td>
            <td><a href="/deleteCartProduct/${item.getProduct().getId()}">-</a></td>
        </tr>
    </c:forEach>
</table>
<h1>Total: ${total}</h1>
</body>
</html>