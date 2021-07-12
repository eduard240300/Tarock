<?php
//games.php

include_once("../controller.php");

$controller = new Controller();

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
 header("location:../games.php");
}

if(!isset($_COOKIE["username"]))
{
 header("location:login.php");
}

if(!isset($_COOKIE["sessionID"]))
{
 header("location:sessions.php");
}

?>
<!DOCTYPE html>
<html>
 <head>
  <title>Games for Session <?php echo $_COOKIE["sessionID"]; ?></title>
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
    echo '<h2 align="center" style="font-size:7vw;">List of Games for Session ' . $_COOKIE["sessionID"] . '</h2>';
   ?>
   <?php
    $sessionID = $_COOKIE["sessionID"];
    $games = $controller->getGames($sessionID);
    $session = $controller->getSession($sessionID);
    $player1 = $controller->getUser($session->getPlayer1())["Name"];
    $player2 = $controller->getUser($session->getPlayer2())["Name"];
    $player3 = $controller->getUser($session->getPlayer3())["Name"];
    $player4 = $controller->getUser($session->getPlayer4())["Name"];
    $currentID = 1;
    $cnt = count($games);
   ?>
   <?php if ($cnt > 0): ?>
   <h1>
   <style type="text/css">
    td {
    padding: 10px 10px 10px 10px;
    align: center;
   }
   </style>
   <style>
    .radler{ 
     background:lightblue
    } 
    .noRadler{
     background:white
    } 
   </style>
   <table align="center" border="1">
    <thead>
     <tr>
      <td><b><center>GameID</center></b></td>
      <td><b><center>Score <?php echo $player1; ?></center></b></td>
      <td><b><center>Score <?php echo $player2; ?></center></b></td>
      <td><b><center>Score <?php echo $player3; ?></center></b></td>
      <td><b><center>Score <?php echo $player4; ?></center></b></td>
      <td><b><center>Declaration</center></b></td>
     </tr>
    </thead>
    <tbody>
     <?php foreach ($games as $game) { ?>
     <?php $controller->echoTr($game); ?>
      <td><b><center><?php echo $currentID; $currentID=$currentID+1; ?></center></b></td>
      <td><center><?php echo $game->getScorePlayer1(); ?></center></td>
      <td><center><?php echo $game->getScorePlayer2(); ?></center></td>
      <td><center><?php echo $game->getScorePlayer3(); ?></center></td>
      <td><center><?php echo $game->getScorePlayer4(); ?></center></td>
      <td><center><?php echo $controller->processDeclaration($game->getDeclaration()); ?></center></td>
     </tr>
     <?php } ?>
    </tbody>
   </table>
   <?php endif; ?>
   </h4>
   <h2><br></h2>
   <center><h4 style="font-size:4vw;">Legend : Blue = Radler</h4></center>
  </div>
 </body>
</html>