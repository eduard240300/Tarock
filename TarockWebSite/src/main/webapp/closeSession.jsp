<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Close Session</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
    <link rel="icon" href="images/icons/icon.png">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="./functions.js"></script>
    <script>
        if (readCookie("loggedIn") !== "true")
            window.location.href = '/tarock/index.jsp';
    </script>
</head>
<body>
<br />
<div class="container">
    <h2 align="center">Close session</h2>
    <br />
    <div class="panel panel-default">
        <div class="panel-heading">Add session</div>
        <div class="panel-body">
        <span><%
            Object message = session.getAttribute("closeSession_error_message");
            if (message != null)
                out.print(message);
            session.setAttribute("closeSession_error_message", null);
        %></span>
            <form action="CloseSessionController" method="post">
                <div class="form-group">
                    <label>Session ID</label>
                    <input type="text" name="closeSession_sessionID" id="closeSession_sessionID" class="form-control" />
                </div>
                <div class="form-group">
                    <input type="submit" name="closeSession" id="closeSession" class="btn btn-info" value="Close Session" />
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