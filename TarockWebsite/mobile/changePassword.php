<?php
//changePassword.php

require_once("../controller.php");
$controller = new Controller();

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
 header("location:../changePassword.php");
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
  <link rel="icon" href="../images/icon.png">
  <script src="../resources/jquery.min.js"></script>
  <link rel="stylesheet" href="../resources/bootstrap.min.css" />
  <script src="../resources/bootstrap.min.js"></script>
 </head>
 <body>
 <style type="text/css">
   td {
   padding: 20px 20px 20px 20px;
   align: center;
  }
  </style>
  <br />
  <div class="w3-container">
   <br />
   <div>
    <table align="center" style="font-size:6vw;">
     <tr>
      <td><a href="sessions.php">Sessions</a></td>
      <td><a href="account.php">My Account</a></td>
      <td><a href="logout.php">Logout</a></td>
     </tr>
    </table>
   </div>
   <br />
   <h4 align="center" style="font-size:6vw;"><a href="account.php">Back to Account Page</a></h4>
   </div>
   <br />
   <div class="w3-container">
    <br />
    <div class="panel panel-default">
     <div class="panel-body">
      <span style="font-size:6vw;"><?php echo $message; ?></span>
      <form method="post">
       <div class="form-group">
        <label style="font-size:6vw;">Old password</label>
        <input type="password" name="old_password" id="old_password" class="form-control" style="font-size:7vw; height: 9vw;" />
       </div>
       <div class="form-group">
        <label style="font-size:6vw;">New password</label>
        <input type="password" name="password" id="password" class="form-control" style="font-size:7vw; height: 9vw;" />
       </div>
       <div class="form-group">
        <label style="font-size:6vw;">Repeat new password</label>
        <input type="password" name="password_repeat" id="password_repeat" class="form-control" style="font-size:7vw; height: 9vw;" />
       </div>
       <div class="form-group">
        <center><input type="submit" name="changePassword" id="changePassword" class="btn btn-info" value="Change Password" style="font-size:7vw;"/></center>
       </div>
      </form>
     </div>
    </div>
    <br />
  </div>
 </body>
</html>
