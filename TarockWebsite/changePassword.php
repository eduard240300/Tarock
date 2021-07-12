<?php
//changePassword.php

require_once("controller.php");
$controller = new Controller();

require_once 'mobile_detect.php';
$detect = new Mobile_Detect;

if ($detect->isMobile() || $detect->isTablet()) {
 header("location:mobile/changePassword.php");
}

if(!isset($_COOKIE["username"]))
{
  header("location:login.php");
}

$message = '';

if(isset($_POST["changePassword"]))
{
  $username = $_COOKIE["username"];
  $old_password = $_POST["old_password"];
  $password = $_POST["password"];
  $password_repeat = $_POST["password_repeat"];
  $user = $controller->getUser($username);

  if(empty($old_password) || empty($password) || empty($password_repeat))
  {
    $message = "<div class='alert alert-danger'>All Fields are required</div>";
  }
  else if ($controller->hasSpaces($old_password) || $controller->hasSpaces($password) || $controller->hasSpaces($password_repeat))
  {
    $message = "<div class='alert alert-danger'>Fields can't contain spaces</div>";
  }
  else
  {
    if(password_verify($old_password, $user["Password"]))
    {
      if ($password == $password_repeat)
      {
        $executed = $controller->changeUser($username, $password);
        if($executed){
          $message = "<div class='alert alert-success'>Password changed successfully</div>";
        } else{
          $message = "<div class='alert alert-danger'>Password not changed successfully</div>";
        }
      }
      else {
        $message = "<div class='alert alert-danger'>Password fields should match</div>";
      }
    }
    else{
      $message = "<div class='alert alert-danger'>Old password is incorrect</div>";
    }
  }
}
?>

<!DOCTYPE html>
<html>
 <head>
  <title>Change Password Page</title>
  <link rel="icon" href="images/icon.png">
  <script src="./resources/jquery.min.js"></script>
  <link rel="stylesheet" href="./resources/bootstrap.min.css" />
  <script src="./resources/bootstrap.min.js"></script>
 </head>
 <body>
 <br />
  <div class="container">
   <br />
   <div align="right">
    <a href="sessions.php">Sessions</a>
    <a href="account.php">My Account</a>
    <a href="logout.php">Logout</a>
    <img src="images/standard.png" width="40" height="40">
    <?php
     echo $_COOKIE["name"];
    ?>
   </div>
   <br />
   <h4 align="center"><a href="account.php">Back to Account Page</a></h4>
   </div>
   <br />
  <div class="container">
   <h2 align="center">Change Password</h2>
   <br />
   <div class="panel panel-default">
   <div class="panel-heading">Change Password</div>
    <div class="panel-body">
     <span><?php echo $message; ?></span>
   <form method="post">
   <div class="form-group">
     <label>Old password</label>
     <input type="password" name="old_password" id="old_password" class="form-control" />
   </div>
   <div class="form-group">
     <label>New password</label>
     <input type="password" name="password" id="password" class="form-control" />
   </div>
   <div class="form-group">
    <label>Repeat new password</label>
    <input type="password" name="password_repeat" id="password_repeat" class="form-control" />
   </div>
   <div class="form-group">
    <input type="submit" name="changePassword" id="changePassword" class="btn btn-info" value="Change password" />
   </div>
   </form>
  </div>
  </div>
  <br />
  </div>
 </body>
</html>
