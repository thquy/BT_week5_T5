<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sqlStatement == null}">
    <c:set var="sqlStatement" value="select * from Users" />
</c:if>

<html>
<head>
    <title>SQL Gateway</title>
</head>
<body>
<h1>The SQL Gateway</h1>
<p>Enter an SQL statement and click the Execute button.</p>

<p><b>SQL statement:</b></p>
<form action="sqlGateway" method="post">
    <textarea name="sqlStatement" cols="60" rows="8">${sqlStatement}</textarea><br/>
    <input type="submit" value="Execute"/>
</form>

<p><b>SQL result:</b></p>
${sqlResult}
</body>
</html>
