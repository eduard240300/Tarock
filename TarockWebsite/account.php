<?php
//account.php

require_once 'mobile_detect.php';
$detect = new Mobile_Detect;

if ($detect->isMobile() || $detect->isTablet()) {
	header("location:mobile/account.php");
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
				<img src="images/standard.jpg" width="40" height="40">
				<?php echo $_COOKIE["name"]; ?>
			</div>
			<br />
			<?php echo '<h2 align="center">Account of ' . $_COOKIE["name"] . '</h2>'; ?>
			<h4>
			<h2> </h2>
			<h2></h2>
			<style type="text/css">
				td {
					padding: 10px 10px 10px 10px;
					align: center;
				}
			</style>
			<center><h4>Name : <?php echo $_COOKIE["name"]; ?><br>
			Email : <?php echo $_COOKIE["email"]; ?></h4></center>
			<table align="center">
				<tr>
					<td><h4><center><input type="button" onclick="location.href='logout.php';" value="Logout" /></center></h4></td>
					<td><h4><center><input type="button" onclick="location.href='changePassword.php';" value="Change Password" /></center></h4></td>
					<td><h4><center><input type="button" onclick="location.href='closeAccount.php';" value="Close Account" /></center></h4></td>
				</tr>
			</table>
			</h4>
		</div>
	</body>
</html>
