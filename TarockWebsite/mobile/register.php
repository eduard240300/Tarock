<?php
//register.php

require_once("../controller.php");
$controller = new Controller();

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
	header("location:../register.php");
}

if(isset($_COOKIE["username"]))
{
	header("location:sessions.php");
}

$message = '';

if(isset($_POST["register"]))
{
	$name = $_POST["name"];
	$username = $_POST["username"];
	$password = $_POST["password"];
	$password_repeat = $_POST["password_repeat"];
	$email = $_POST["email"];
	if(empty($name) || empty($username) || empty($password) || empty($password_repeat) || empty($email))
	{
		$message = "<div class='alert alert-danger'>All Fields are required</div>";
	}
	else if ($controller->hasSpaces($name) || $controller->hasSpaces($username) || $controller->hasSpaces($email))
	{
		$message = "<div class='alert alert-danger'>Fields can't contain spaces</div>";
	}
	else if (strlen($name) > 10)
	{
		$message = "<div class='alert alert-danger'>Name can't be longer than 10 characters</div>";
	}
	else
	{
		$existsUsername = $controller->existsUsername($username);
		if($existsUsername)
		{
			$message = "<div class='alert alert-danger'>Username already exists</div>";
		}
		else
		{
			if ($password == $password_repeat)
			{
				$executed = $controller->addUser($name, $username, $password, $email);
				if($executed){
					$message = "<div class='alert alert-success'>Account created successfully</div>";
				} else{
					$message = "<div class='alert alert-danger'>Account not created successfully</div>";
				}
			}
			else {
				$message = "<div class='alert alert-danger'>Password fields should match</div>";
			}
		}
	}
}

?>

<!DOCTYPE html>
<html>
	<head>
		<title>Register Page</title>
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
		<h4 align="center" style="font-size:10vw;">Register</h4>
		<br />
		<div class="w3-container">
			<br />
			<div class="panel panel-default">
				<div class="panel-body">
					<span style="font-size:6vw;"><?php echo $message; ?></span>
 					<form method="post">
						<div class="form-group">
							<label style="font-size:6vw;">Display Name</label>
							<input type="text" name="name" id="name" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<label style="font-size:6vw;">Username</label>
							<input type="text" name="username" id="username" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<label style="font-size:6vw;">Password</label>
							<input type="password" name="password" id="password" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<label style="font-size:6vw;">Repeat password</label>
							<input type="password" name="password_repeat" id="password_repeat" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<label style="font-size:6vw;">Email</label>
							<input type="text" name="email" id="email" class="form-control" style="font-size:7vw; height: 9vw;" />
						</div>
						<div class="form-group">
							<center><input type="submit" name="register" id="register" class="btn btn-info" value="Register" style="font-size:7vw;"/></center>
						</div>
						<div class="form-group">
							<h2 style="font-size:6vw;"><a href="login.php"><u>Login</u></a></h2>
						</div>
					</form>
				</div>
			</div>
			<br />
		</div>
	</body>
</html>
