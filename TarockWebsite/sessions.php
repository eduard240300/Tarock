<?php
//sessions.php

include_once("controller.php");
$controller = new Controller();

require_once 'mobile_detect.php';
$detect = new Mobile_Detect;

if ($detect->isMobile() || $detect->isTablet()) {
	header("location:mobile/sessions.php");
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
				<a href="downloads.php">Downloads</a>
				<a href="account.php">My Account</a>
				<a href="logout.php">Logout</a>
				<img src="images/standard.png" width="40" height="40">
				<?php
					echo $_COOKIE["name"];
				?>
			</div>
			<br />
			<?php
				if(isset($_COOKIE["username"]))
				{
					echo '<h2 align="center">' . $_COOKIE["name"] . '\'s List of Sessions</h2>';
				}
			?>
			<h2> </h2>
			<?php
				$username = $_COOKIE["username"];
				$sessions = $controller->getSessions($username);
				$cnt = count($sessions);
			?>
			<?php if ($cnt > 0): ?>
			<h4>
			<style type="text/css">
				td {
					padding: 10px 10px 10px 10px;
					align: center;
				}
			</style>
			<table align="center" border="1">
				<thead>
					<tr>
						<td><b><center>SessionID</center></b></td>
						<td><b><center>Time Created</center></b></td>
						<td><b><center>Time Closed</center></b></td>
						<td><b><center>Player 1</center></b></td>
						<td><b><center>Player 2</center></b></td>
						<td><b><center>Player 3</center></b></td>
						<td><b><center>Player 4</center></b></td>
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
					</tr>
					<?php } ?>
				</tbody>
			</table>
			<?php endif; ?>
			</h4>
			<h2></h2>
			<h4>
			<table align="center">
				<tr>
					<td><input type="button" onclick="location.href='addSession.php';" value="Add Session" /></td>
					<td><input type="button" onclick="location.href='seeGames.php';" value="See Games for Session" /></td>
					<td><input type="button" onclick="location.href='closeSession.php';" value="Close Session" /></td>
				</tr>
			</table>
			</h4>
		</div>
	</body>
</html>