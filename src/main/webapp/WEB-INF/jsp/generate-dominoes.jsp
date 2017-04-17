<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="Content-Type"  content="text/html; charset=UTF-8" />
<html>
<head>
    <title>Generate dominoes</title>
</head>
<body>
    Number of domino tiles:
    <form action="combinations" method="post">
        <label>
            <input type="number" name="numberOfDominoes">
        </label>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
