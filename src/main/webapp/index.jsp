<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    Generate domino tiles:
    <div id="generate_dominoes_form">
        <form id="form" action="combinations" method="post">
            <input type="number" name="numberOfDominoes">
            <input type="submit" value="Submit">
        </form>
    </div>
    <a href="combinations">Last combinations</a><br>
    <a href="history">History</a><br>
</body>
</html>
