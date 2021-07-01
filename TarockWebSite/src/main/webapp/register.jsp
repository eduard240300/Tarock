<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
    <link rel="icon" href="images/icons/icon.png">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="./functions.js"></script>
    <script>
        if (readCookie("loggedIn") === "true")
            window.location.href = '/tarock/main.jsp';
    </script>
</head>
<body>
<br />
<div class="container">
    <h2 align="center">Register</h2>
    <br />
    <div class="panel panel-default">
        <div class="panel-heading">Register</div>
        <div class="panel-body">
            <span><%
                Object message = session.getAttribute("register_error_message");
                if (message != null)
                    out.print(message);
                session.setAttribute("register_error_message", null);
            %></span>
            <form action="RegisterController" method="post">
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" name="register_name" id="register_name" class="form-control" />
                </div>
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="register_username" id="register_username" class="form-control" />
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="register_password" id="register_password" class="form-control" />
                </div>
                <div class="form-group">
                    <label>Repeat password</label>
                    <input type="password" name="register_password_repeat" id="register_password_repeat" class="form-control" />
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="text" name="register_email" id="register_email" class="form-control" />
                </div>
                <div class="form-group">
                    <input type="submit" name="register" id="register" class="btn btn-info" value="Register" />
                </div>
                <div class="from-group">
                    <a href="/tarock/index.jsp">Login</a>
                </div>
            </form>
        </div>
    </div>
    <br />
</div>
</body>
</html>