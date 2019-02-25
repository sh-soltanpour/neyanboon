<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

</head>
<body>

<c:if test="${1 < 2}">
    <c:out value="${'<b>This is a <c:out> example </b>'}" escapeXml="false"/>
    <br><br>
</c:if>

<table>
    <tbody>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Role</th>
    </tr>
    <c:forEach var="emp" items="${requestScope.empList}">
        <tr>
            <td><c:out value="${emp.id}"/></td>
            <td><c:out value="${emp.name}"/></td>
            <td>${emp.role}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>
</html>
