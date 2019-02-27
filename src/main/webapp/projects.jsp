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
    <c:choose>
        <c:when test="${!bidRequested.get(project.id)}">
            <form action="/bid" method="POST">
                <h2>درخواست بید</h2>
                <input required type="number" name="amount"/>
                <input type="hidden" name="projectId" value="${project.id}"/>
                <input type="submit" value="ارسال درخواست"/>
            </form>
        </c:when>
        <c:otherwise>
            <p>درخواست شما برای این پروژه ثبت شده</p>
        </c:otherwise>
    </c:choose>
    <hr/>
</c:forEach>

</body>
</html>
