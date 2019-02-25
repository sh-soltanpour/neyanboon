<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<head>
    <meta charset="UTF-16"/>

</head>
<body>
<c:forEach var="project" items="${projects}">
    <ul>
        <li>id: ${project.id}</li>
        <li>title: ${project.title}</li>
        <li>description: ${project.description}</li>
        <li>imageUrl: <img src="${project.imageUrl}" style="width: 100px; height: 100px;"></li>
        <li>budget: ${project.budget}</li>
    </ul>
    <hr/>
</c:forEach>

</body>
</html>
