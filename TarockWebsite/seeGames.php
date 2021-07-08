<?php
//add.php

if(!isset($_COOKIE["username"]))
{
 header("location:login.php");
}

include_once("controller.php");

$controller = new Controller();

$message = "";

if(isset($_POST["see"]))
{
  $username = $_COOKIE["username"];
  $sessionID = $_POST["sessionID"];
  if (empty($sessionID))
  {
    $message = "<div class='alert alert-danger'>Session ID field is required</div>";  
  }
  else
  {
    if ($controller->existsSessionForUsername($username, $sessionID))
    {
      setcookie("sessionID", $sessionID, time()+60*10);
      header("location:games.php");
    }
    else
    {
      $message = "<div class='alert alert-danger'>Session does not exist</div>";  
    }
  }
}
else{
  $message = "";
}


?>
<!DOCTYPE html>
<html>
 <head>
  <title>See Games for Session</title>
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
   <h4 align="center"><a href="index.php">Back to Home Page</a></h4>
   </div>
   <br />
   <div class="container">
    <h2 align="center">See Games for Session</h2>
    <br />
    <div class="panel panel-default">
     <div class="panel-heading">See Games for Session</div>
     <div class="panel-body">
      <span><?php echo $message; ?></span>
      <form method="post">
       <div class="form-group">
        <label>SessionID</label>
        <input type="text" name="sessionID" id="sessionID" class="form-control" />
       </div>
       <div class="form-group">
        <input type="submit" name="see" id="see" class="btn btn-info" value="See Games" />
       </div>
      </form>
     </div>
    </div>
    <br />
  </div>
 </body>
</html>
