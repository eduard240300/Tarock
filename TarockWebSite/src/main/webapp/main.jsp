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
    <title>Home Page</title>
    <script src="js/jquery-2.0.3.js"></script>
    <script src="js/ajax-utils.js"></script>
    <script src="./functions.js"></script>
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
    <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/perfect-scrollbar/perfect-scrollbar.css">
    <link rel="stylesheet" type="text/css" href="css/util.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <%
        //if (CookieService.getCookie(request, "sessionIDforGames") != null)
        //{
        //    Cookie newCookie = new Cookie("sessionIDforGames", "");
        //    newCookie.setMaxAge(0);
        //    response.addCookie(newCookie);
        //}
    %>
    <script>
        if (readCookie("loggedIn") !== "true")
            window.location.href = '/tarock/index.jsp';
    </script>
    <style type="text/css">
        table    { border:ridge 5px red; background-color:lightblue; color:black; }
        table td { border:inset 1px #000; }
    </style>
    <style>
        .button {
            display: inline-block;
            padding: 15px 25px;
            font-size: 24px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: #fff;
            background-color: blue;
            border: none;
            border-radius: 15px;
            box-shadow: 0 9px #999;
        }

        .button:hover {background-color: darkblue}

        .button:active {
            background-color: blue;
            box-shadow: 0 5px #666;
            transform: translateY(4px);
        }

        #outer
        {
            width:100%;
            text-align: center;
        }
        .inner
        {
            display: inline-block;
        }
    </style>
</head>
<% //" %>
<body style="background-color:rgb(78, 154, 6);">
    <h1 style="text-align: center"><br><script>document.write(getName(readCookie("name")))</script>'s Tarock Sessions : <br></h1>
    <%
        Cookie cookie = CookieService.getCookie(request, "username");
        if (cookie != null)
        {
            String username = cookie.getValue();
            List<Session> sessions = DBManager.getSessions(username);
    %>
    <h1><br></h1>
    <center>
    <% //<div class="limiter">
        //<div class="container-table100"> %>
            <div class="wrap-table100">
                <div class="table100">
                    <table><thead>
                        <tr><th>SessionID</th><th>Creator</th><th>Date Created</th><th>Date Ended</th><th>Player 1</th><th>Player 2</th><th>Player 3</th><th>Player 4</th></tr></thead>
                        <tbody>
                            <%
                                if(sessions != null){
                                    Iterator<Session> iterator = sessions.iterator();  // Iterator interface
                                    while(iterator.hasNext())  // iterate through all the data until the last record
                                    {
                                        Session details = iterator.next(); //assign individual employee record to the employee class object
                            %>
                        <tr><td><%=details.getSessionID()%></td>
                        <td><%=details.getCreator()%></td>
                        <td><%=details.getDateCreated()%></td>
                        <td><%=details.getDateEnded()%></td>
                        <td><%=details.getPlayer1()%></td>
                        <td><%=details.getPlayer2()%></td>
                        <td><%=details.getPlayer3()%></td>
                        <td><%=details.getPlayer4()%></td>
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
    <div id="outer">
        <form action="SeeSessionController" method="post">
            <div class="inner">
                <input type="text" name="seeSession_id" id="seeSession_id" class="form-control" />
            </div>
            <div class="inner">
                <input type="submit" name="seeSession" id="seeSession" class="btn btn-info" value="See session" />
            </div>
        </form>
    <br><br>
    </div>
    <div id="outer">
        <div class="inner"><button class="button" onclick="location.href='/tarock/addSession.jsp'">Add session</button></div>
        <div class="inner"><button class="button" onclick="location.href='/tarock/logout.jsp'">Logout</button></div>
    </div>
</body>
</html>