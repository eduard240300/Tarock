<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="com.eduard240300.TarockWebSite.Service.DataManipulationService" %><%--
  Created by IntelliJ IDEA.
  User: eduard
  Date: 30/06/2021
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout Page</title>
</head>
<body>
<%
    Cookie loggedIn = new Cookie("loggedIn", "");
    Cookie cookieUsername = new Cookie("username", "");
    Cookie cookieName = new Cookie("name", "");
    Cookie cookieEmail = new Cookie("email", "");
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
