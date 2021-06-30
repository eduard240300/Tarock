<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Login Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
    <link rel="icon" href="images/icon.ico">
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
    <h2 align="center">Login</h2>
    <br />
    <div class="panel panel-default">
      <div class="panel-heading">Login</div>
      <div class="panel-body">
        <span><%
          Object message = session.getAttribute("login_error_message");
          if (message != null)
            out.print(message);
          session.setAttribute("login_error_message", null);
        %></span>
        <form action="LoginController" method="post">
          <div class="form-group">
            <label>Username</label>
            <input type="text" name="login_username" id="login_username" class="form-control" />
          </div>
          <div class="form-group">
            <label>Password</label>
            <input type="password" name="login_password" id="login_password" class="form-control" />
          </div>
          <div class="form-group">
            <input type="submit" name="login" id="login" class="btn btn-info" value="Login" />
          </div>
          <div class="from-group">
            <a href="/tarock/register.jsp">Register</a>
          </div>
        </form>
      </div>
    </div>
    <br />
  </div>
  </body>
</html>
