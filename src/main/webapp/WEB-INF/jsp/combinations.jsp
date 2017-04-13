<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Combinations</title>
</head>
<body>
<p>Generated dominoes:</p>
${dominoes}

<p>Combinations:</p>
<c:forEach var="chain" items="${combinations}">
    ${chain}<br>
</c:forEach>

</body>
</html>
