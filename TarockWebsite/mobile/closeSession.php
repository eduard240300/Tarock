<?php
//closeSession.php

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
 header("location:../closeSession.php");
}

if(!isset($_COOKIE["username"]))
{
 header("location:login.php");
}

include_once("../controller.php");

$controller = new Controller();

$message = "";

if(isset($_POST["close"]))
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
      if (!$controller->isClosedSession($sessionID))
      {
        $closedSession = $controller->closeSession($sessionID);
        if($closedSession){
          $message = "<div class='alert alert-success'>Session closed successfully</div>";
        } else{
          $message = "<div class='alert alert-danger'>Session not closed successfully</div>";
        }
      }
      else
      {
        $message = "<div class='alert alert-danger'>Session is already closed</div>";  
      }
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
  <title>Close Session</title>
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
   <h4 align="center" style="font-size:6vw;"><a href="sessions.php">Back to Home Page</a></h4>
   </div>
   <br />
   <div class="w3-container">
    <br />
    <div class="panel panel-default">
     <div class="panel-body">
      <span style="font-size:6vw;"><?php echo $message; ?></span>
      <form method="post">
       <div class="form-group">
        <label style="font-size:6vw;">Session ID</label>
        <input type="text" name="sessionID" id="sessionID" class="form-control" style="font-size:7vw; height: 9vw;" />
       </div>
       <div class="form-group">
        <center><input type="submit" name="close" id="close" class="btn btn-info" value="Close Session" style="font-size:7vw;"/></center>
       </div>
      </form>
     </div>
    </div>
    <br />
  </div>
 </body>
</html>
