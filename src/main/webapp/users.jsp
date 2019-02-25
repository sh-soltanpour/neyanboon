<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<head>
    <meta charset="UTF-8"/>
</head>
<body>
<c:forEach var="user" items="${users}">
    <ul>
        <li>id: ${user.id}</li>
        <li>first name: ${user.firstName}</li>
        <li>last name: ${user.lastName}</li>
        <li>jobTitle: ${user.jobTitle}</li>
        <li>bio: ${user.bio}</li>
    </ul>
    <hr/>
</c:forEach>

</body>
</html>