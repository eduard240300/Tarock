<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home Page</title>
    <script src="js/jquery-2.0.3.js"></script>
    <script src="js/ajax-utils.js"></script>
    <script src="./functions.js"></script>
    <script>
        if (readCookie("loggedIn") !== "true")
            window.location.href = '/website/index.jsp';
    </script>
</head>

<body style="background-color:rgb(78, 154, 6);">
    <h1 style="text-align: center">User <script>document.write(getName(readCookie("name")))</script></h1>
</body>
</html>