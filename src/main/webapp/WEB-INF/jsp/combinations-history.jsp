<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type"  content="text/html; charset=UTF-8" />
<html>
<head>
    <title>History</title>
</head>
<body>
    <p>History:</p>
    <c:if test="${empty history}">
        <c:out value="History is empty"/>
    </c:if>

    <c:forEach var="chain" items="${history}">
        ${chain}<br>
    </c:forEach>
</body>
</html>
