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
        <li>
            skills:
            <ul>
                <c:forEach var="skill" items="${user.skills}">
                    <li>${skill.name} : ${skill.point}
                        <c:if test="${isCurrentUser}">
                        <form action="/delete-skill" method="post">
                            <input type="hidden" name="skillName" value="${skill.name}"/>
                            <input type="submit" value="Delete skill"/>
                            </c:if>
                    </li>
                </c:forEach>
            </ul>
        </li>
    </ul>
    <hr/>
</c:forEach>

</body>
</html>
