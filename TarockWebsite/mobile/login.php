<?php
//login.php

include_once("../controller.php");

$controller = new Controller();

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
	header("location:../login.php");
}

if(isset($_COOKIE["username"]))
{
	header("location:sessions.php");
}

$message = '';

if(isset($_POST["login"]))
{
	if(empty($_POST["username"]) || empty($_POST["password"]))
	{
		$message = "<div class='alert alert-danger'>Both fields are required</div>";
	}
	else
	{
		$existsUsername = $controller->existsUsername($_POST["username"]);
		if($existsUsername)
		{
			$user = $controller->getUser($_POST["username"]);
			if(password_verify($_POST["password"], $user["Password"]))
			{
				setcookie("name", $user["Name"], time()+60*60*24);
				setcookie("username", $user["Username"], time()+60*60*24);
				setcookie("email", $user["Email"], time()+60*60*24);
				header("location:sessions.php");
			}
			else
			{
				$message = '<div class="alert alert-danger">Wrong Password</div>';
			}
		}
		else
		{
			$message = "<div class='alert alert-danger'>Wrong Username</div>";
		}
	}
}

?>

<!DOCTYPE html>
<html>
	<head>
		<title>Login Page</title>
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
		<h4 align="center" style="font-size:10vw;">Login</h4>
		<br />
		<div class="w3-container">
			<br />
			<div class="panel panel-default">
				<div class="panel-body">
					<span style="font-size:6vw;"><?php echo $message; ?></span>
					<form method="post">
						<div class="form-group">
							<label style="font-size:6vw;">Username</label>
 							<input type="text" name="username" id="username" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<label style="font-size:6vw;">Password</label>
							<input type="password" name="password" id="password" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<center><input type="submit" name="login" id="login" class="btn btn-info" value="Login" style="font-size:7vw;"/></center>
						</div>
						<div class="form-group">
							<h2 style="font-size:6vw;"><a href="register.php"><u>Register</u></a></h2>
						</div>
					</form>
				</div>
			</div>
			<br />
		</div>
	</body>
</html>
