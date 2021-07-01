<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout Page</title>
    <link rel="icon" href="images/icons/icon.png">
</head>
<body>
<%
    Cookie loggedIn = new Cookie("loggedIn", "");
    Cookie cookieUsername = new Cookie("username", "");
    Cookie cookieName = new Cookie("name", "");
    Cookie cookieEmail = new Cookie("email", "");
    if (session.getAttribute("sessionID") != null)
        session.removeAttribute("sessionID");
    loggedIn.setMaxAge(0);
    cookieUsername.setMaxAge(0);
    cookieName.setMaxAge(0);
    cookieEmail.setMaxAge(0);
    response.addCookie(loggedIn);
    response.addCookie(cookieUsername);
    response.addCookie(cookieName);
    response.addCookie(cookieEmail);

%>
<script>
    window.location.href = '/tarock/index.jsp';
</script>
</body>
</html>
