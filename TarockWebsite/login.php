<?php

//login.php

include_once("controller.php");

$controller = new Controller();

if(isset($_COOKIE["username"]))
{
 header("location:sessions.php");
}

$message = '';

if(isset($_POST["login"]))
{
 if(empty($_POST["username"]) || empty($_POST["password"]))
 {
  $message = "<div class='alert alert-danger'>Both fields are required</div>";
 }
 else
 {
  $existsUsername = $controller->existsUsername($_POST["username"]);
  if($existsUsername)
  {
   $user = $controller->getUser($_POST["username"]);
   if(password_verify($_POST["password"] , $user["Password"]))
   {
    setcookie("name", $user["Name"], time()+60*60*24);
    setcookie("username", $user["Username"], time()+60*60*24);
    setcookie("email", $user["Email"], time()+60*60*24);
    header("location:sessions.php");
   }
   else
   {
    $message = '<div class="alert alert-danger">Wrong Password</div>';
   }
  }
  else
  {
   $message = "<div class='alert alert-danger'>Wrong Username</div>";
  }
 }
}

?>

<!DOCTYPE html>
<html>
 <head>
  <title>Login Page</title>
  <link rel="icon" href="images/icon.png">
  <script src="./resources/jquery.min.js"></script>
  <link rel="stylesheet" href="./resources/bootstrap.min.css" />
  <script src="./resources/bootstrap.min.js"></script>
 </head>
 <body>
 <br />
 <div class="container">
  <h2 align="center">Login</h2>
  <br />
  <div class="panel panel-default">
   <div class="panel-heading">Login</div>
   <div class="panel-body">
    <span><?php echo $message; ?></span>
    <form method="post">
     <div class="form-group">
      <label>Username</label>
      <input type="text" name="username" id="username" class="form-control" />
     </div>
     <div class="form-group">
      <label>Password</label>
      <input type="password" name="password" id="password" class="form-control" />
     </div>
     <div class="form-group">
      <input type="submit" name="login" id="login" class="btn btn-info" value="Login" />
     </div>
     <div class="form-group">
      <a href="register.php">Register</a>
     </div>
    </form>
   </div>
  </div>
  <br />
 </div>
 </body>
</html>
