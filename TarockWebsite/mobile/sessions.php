<?php
//sessions.php

include_once("../controller.php");

$controller = new Controller();

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
	header("location:../sessions.php");
}

if(!isset($_COOKIE["username"]))
{
	header("location:login.php");
}

?>
<!DOCTYPE html>
<html>
	<head>
		<title>Sessions of <?php echo $_COOKIE["name"] ?></title>
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
				<table align="center" style="font-size:5vw;">
					<tr>
						<td><a href="sessions.php">Sessions</a></td>
						<td><a href="downloads.php">Downloads</a></td>
						<td><a href="account.php">Account</a></td>
						<td><a href="logout.php">Logout</a></td>
					</tr>
				</table>
			</div>
			<br />
			<h2 align="center">List of Sessions</h2>
			<?php
				$username = $_COOKIE["username"];
				$sessions = $controller->getAllSessions();
				$cnt = count($sessions);
			?>
			<?php if ($cnt > 0): ?>
			<h1>
			<style type="text/css">
				td {
					padding: 10px 10px 10px 10px;
					align: center;
				}
			</style>
			<table align="center" border="1">
				<thead>
					<tr>
						<td><b><center>ID</center></b></td>
						<td><b><center>Time Created</center></b></td>
						<td><b><center>Time Closed</center></b></td>
						<td><b><center>Player1</center></b></td>
						<td><b><center>Player2</center></b></td>
						<td><b><center>Player3</center></b></td>
						<td><b><center>Player4</center></b></td>
						<td><b><center>Creator</center></b></td>
					</tr>
				</thead>
				<tbody>
					<?php foreach ($sessions as $session) { ?>
					<tr>
						<td><b><center><?php echo $session->getId(); ?></center></b></td>
						<td><center><?php echo $session->getDateCreated(); ?></center></td>
						<td><center><?php echo $session->getDateClosed(); ?></center></td>
						<td><center><?php echo $session->getPlayer1(); ?></center></td>
						<td><center><?php echo $session->getPlayer2(); ?></center></td>
						<td><center><?php echo $session->getPlayer3(); ?></center></td>
						<td><center><?php echo $session->getPlayer4(); ?></center></td>
						<td><center><?php echo $session->getCreator(); ?></center></td>
					</tr>
					<?php } ?>
				</tbody>
			</table>
			<?php endif; ?>
			</h1>
			<h2></h2>
			<h2><br style="font-size:5vw;"></h2>
			<center><input type="button" onclick="location.href='addSession.php';" value="Add Session" style="font-size:5vw;"/><h2><br style="font-size:5vw;"></h2></center>
			<center><input type="button" onclick="location.href='seeGames.php';" value="See Games for Session" style="font-size:5vw;"/><h2><br style="font-size:5vw;"></h2></center>
			<center><input type="button" onclick="location.href='closeSession.php';" value="Close Session" style="font-size:5vw;"/></center>
		</div>
	</body>
</html>