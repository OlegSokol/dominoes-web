<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type"  content="text/html; charset=UTF-8" />
<html>
<head>
    <title>Combinations</title>
</head>
<body>

    <p>Generated dominoes:</p>
    <c:out value="${dominoes}"/>

    <p>Longest chain:</p>
    <c:if test="${empty longestChain}">
        <c:out value="Combinations is not exist"/>
    </c:if>
    <c:out value="${longestChain}"/>

    <p>Combinations:</p>
    <c:if test="${empty combinations}">
        <c:out value="Combinations is not exist"/>
    </c:if>
    <c:forEach var="chain" items="${combinations}">
        ${chain}<br>
    </c:forEach>

</body>
</html>
