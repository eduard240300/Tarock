<?php
//addSession.php

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
	header("location:../addSesion.php");
}

if(!isset($_COOKIE["username"]))
{
	header("location:login.php");
}

include_once("../controller.php");
$controller = new Controller();

$message = "";

if(isset($_POST["add"]))
{
	$username = $_COOKIE["username"];
	$player1 = $_POST["player1"];
	$player2 = $_POST["player2"];
	$player3 = $_POST["player3"];
	$player4 = $_POST["player4"];
	if (empty($player1) || empty($player2) || empty($player3) || empty($player4))
	{
		$message = "<div class='alert alert-danger'>All fields are required</div>";	
	}
	else
	{
		if (($player1 == $player2) || ($player1 == $player3) || ($player1 == $player4) ||
		($player2 == $player3) || ($player2 == $player4) || ($player3 == $player4))
		{
			$message = "<div class='alert alert-danger'>The players need to be different from eachother !</div>";
		}
		else
		{
			$exists = True;
			$exists = $exists & $controller->existsUsername($player1);
			$exists = $exists & $controller->existsUsername($player2);
			$exists = $exists & $controller->existsUsername($player3);
			$exists = $exists & $controller->existsUsername($player4);
			if (!$exists)
			{
				$message = "<div class='alert alert-danger'>The players need to be existent accounts !</div>";
			}
			else
			{
				$added = $controller->addSession($username, $player1, $player2, $player3, $player4);
				if($added)
				{
					$message = "<div class='alert alert-success'>Session added successfully</div>";
				}
				else
				{
					$message = "<div class='alert alert-danger'>Session not added successfully</div>";
				}
			}
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
		<title>Add Session</title>
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
							<label style="font-size:6vw;">Player 1</label>
							<input type="text" name="player1" id="player1" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<label style="font-size:6vw;">Player 2</label>
							<input type="text" name="player2" id="player2" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<label style="font-size:6vw;">Player 3</label>
							<input type="text" name="player3" id="player3" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<label style="font-size:6vw;">Player 4</label>
							<input type="text" name="player4" id="player4" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<center><input type="submit" name="add" id="add" class="btn btn-info" value="Add Session" style="font-size:7vw;"/></center>
						</div>
					</form>
				</div>
			</div>
			<br />
		</div>
	</body>
</html>