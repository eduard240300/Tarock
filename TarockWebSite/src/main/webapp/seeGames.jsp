<%@ page import="com.eduard240300.TarockWebSite.Service.CookieService" %>
<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="com.eduard240300.TarockWebSite.Domain.Session" %>
<%@ page import="com.eduard240300.TarockWebSite.Domain.Game" %>
<%@ page import="java.util.List" %>
<%@ page import="com.eduard240300.TarockWebSite.DBManager.DBManager" %>
<%@ page import="com.eduard240300.TarockWebSite.Domain.User" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Games List</title>
    <script src="js/jquery-2.0.3.js"></script>
    <script src="js/ajax-utils.js"></script>
    <script src="./functions.js"></script>
    <link rel="icon" href="images/icons/icon.png">
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
    <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/perfect-scrollbar/perfect-scrollbar.css">
    <link rel="stylesheet" type="text/css" href="css/util.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/button.css">
    <script>
        if (readCookie("loggedIn") !== "true")
            window.location.href = '/tarock/index.jsp';
    </script>
    <style type="text/css">
        table    { border:ridge 5px red; background-color:lightblue; color:black; }
        table td { border:inset 1px #000; }
    </style>
</head>
<body style="background-color:rgb(78, 154, 6);">
<%
    int sessionID = 0;
    Object sessionObject = session.getAttribute("sessionID");
    if (sessionObject != null)
        sessionID = Integer.parseInt(sessionObject.toString());
    else
        response.sendRedirect("/tarock/main.jsp");
%>
<h1 style="text-align: center"><br>Games List for Session <% out.print(sessionID); %><br></h1>
<%
    Cookie cookieUsername = CookieService.getCookie(request, "username");
    if ((cookieUsername != null) && (sessionObject != null))
    {
        String username = cookieUsername.getValue();
        List<Game> games = DBManager.getGames(sessionID);
        Session newSession = DBManager.getSession(sessionID);
        User player1 = DBManager.getUser(newSession.getPlayer1());
        User player2 = DBManager.getUser(newSession.getPlayer2());
        User player3 = DBManager.getUser(newSession.getPlayer3());
        User player4 = DBManager.getUser(newSession.getPlayer4());
        String namePlayer1 = player1.getName();
        String namePlayer2 = player2.getName();
        String namePlayer3 = player3.getName();
        String namePlayer4 = player4.getName();
%>
<h1><br></h1>
<center>
    <% //<div class="limiter">
        //<div class="container-table100"> %>
    <div class="wrap-table100">
        <div class="table100">
            <table><thead>
            <tr><th>GameID</th><th>Score <% out.print(namePlayer1); %></th><th>Score <% out.print(namePlayer2); %></th><th>Score <% out.print(namePlayer3); %></th><th>Score <% out.print(namePlayer4); %></th><th>Declaration</th><th>Radler</th></tr></thead>
                <tbody>
                <%
                    if(games != null){
                        Iterator<Game> iterator = games.iterator();  // Iterator interface
                        while(iterator.hasNext())  // iterate through all the data until the last record
                        {
                            Game details = iterator.next(); //assign individual employee record to the employee class object
                %>
                <tr><td><%=details.getGameID()%></td>
                    <td><%=details.getScorePlayer1()%></td>
                    <td><%=details.getScorePlayer2()%></td>
                    <td><%=details.getScorePlayer3()%></td>
                    <td><%=details.getScorePlayer4()%></td>
                    <td><%=details.getDeclaration()%></td>
                    <td><%=details.getRadler()%></td>
                </tr></tbody>
                <%
                            }
                        }
                    }
                %>
            </table>
        </div>
    </div>
    <% //</div>
        //</div> %>
</center>
<br><br>
</div>
<div id="outer">
    <div class="inner"><button class="button" onclick="location.href='/tarock/main.jsp'">Go back to home page</button></div>
</div>
</body>
</html>