<?php
//account.php

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
 header("location:../account.php");
}

if(!isset($_COOKIE["username"]))
{
 header("location:login.php");
}

?>
<!DOCTYPE html>
<html>
 <head>
  <title>Account of <?php echo $_COOKIE["name"] ?></title>
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
   <?php
    echo '<h2 align="center" style="font-size:7vw;">Account of ' . $_COOKIE["name"] . '</h2>';
    echo '<h2 align="center" style="font-size:5vw;">Name : ' . $_COOKIE["name"] . '</h2>';
    echo '<h2 align="center" style="font-size:5vw;">Email : ' . $_COOKIE["email"] . '</h2>';
   ?>
   <h2><br style="font-size:5vw;"></h2>
   <center><input type="button" onclick="location.href='logout.php';" value="Logout" style="font-size:5vw;"/><h2><br style="font-size:5vw;"></h2></center>
   <center><input type="button" onclick="location.href='changePassword.php';" value="Change Password" style="font-size:5vw;"/><h2><br style="font-size:5vw;"></h2></center>
   <center><input type="button" onclick="location.href='closeAccount.php';" value="Close Account" style="font-size:5vw;"/></center>
  </div>
 </body>
</html>
