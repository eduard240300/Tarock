<%@ page import="com.eduard240300.TarockWebSite.Service.CookieService" %>
<%@ page import="com.eduard240300.TarockWebSite.Domain.Session" %>
<%@ page import="java.util.List" %>
<%@ page import="com.eduard240300.TarockWebSite.DBManager.DBManager" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add Session</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
    <link rel="icon" href="images/icon.ico">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="./functions.js"></script>
    <%
        //if (CookieService.getCookie(request, "sessionIDforGames") != null)
        //{
        //    Cookie newCookie = new Cookie("sessionIDforGames", "");
        //    newCookie.setMaxAge(0);
        //    response.addCookie(newCookie);
       // }
    %>
    <script>
        if (readCookie("loggedIn") !== "true")
            window.location.href = '/tarock/index.jsp';
    </script>
</head>
<% //" %>
<body>
<br />
<div class="container">
    <h2 align="center">Add session</h2>
    <br />
    <div class="panel panel-default">
        <div class="panel-heading">Add session</div>
        <div class="panel-body">
        <span><%
            Object message = session.getAttribute("addSession_error_message");
            if (message != null)
                out.print(message);
            session.setAttribute("addSession_error_message", null);
        %></span>
            <form action="AddSessionController" method="post">
                <div class="form-group">
                    <label>Player 1</label>
                    <input type="text" name="addSession_player1" id="addSession_player1" class="form-control" />
                </div>
                <div class="form-group">
                    <label>Player 2</label>
                    <input type="text" name="addSession_player2" id="addSession_player2" class="form-control" />
                </div>
                <div class="form-group">
                    <label>Player 3</label>
                    <input type="text" name="addSession_player3" id="addSession_player3" class="form-control" />
                </div>
                <div class="form-group">
                    <label>Player 4</label>
                    <input type="text" name="addSession_player4" id="addSession_player4" class="form-control" />
                </div>
                <div class="form-group">
                    <input type="submit" name="addSession" id="addSession" class="btn btn-info" value="Add Session" />
                </div>
                <div class="from-group">
                    <a href="/tarock/main.jsp">Home Page</a>
                </div>
            </form>
        </div>
    </div>
    <br />
</div>
</body>
</html>