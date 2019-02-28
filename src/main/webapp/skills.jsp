<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<head>
    <meta charset="UTF-8"/>
</head>
<body>
<ul>
    <c:forEach var="skill" items="${skills}">
        <li><span>${skill.name}
    <c:if test="${!hasSkills.contains(skill.name)}">
        <form action="/skills" method="post">
            <input  type="hidden" name="skillName" value="${skill.name}"/>
            <input type="submit" value="Add to my skills"/>
        </form>
    </c:if>
    </span>
        </li>
    </c:forEach>
</ul>
</body>
</html>
