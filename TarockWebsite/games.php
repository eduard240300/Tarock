<?php
//games.php

include_once("controller.php");

$controller = new Controller();

require_once 'mobile_detect.php';
$detect = new Mobile_Detect;

if ($detect->isMobile() || $detect->isTablet()) {
	header("location:mobile/games.php");
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
					echo '<h2 align="center">List of Games for Session ' . $_COOKIE["sessionID"] . '</h2>';
				}
			?>
			<h2> </h2>
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
			<h4>
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
			<h2></h2>
			<center><h4>Legend : Blue = Radler</h4></center>
		</div>
	</body>
</html>