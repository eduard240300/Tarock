<?php
//register.php

require_once("controller.php");
$controller = new Controller();

require_once 'mobile_detect.php';
$detect = new Mobile_Detect;

if ($detect->isMobile() || $detect->isTablet()) {
	header("location:mobile/register.php");
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
		<link rel="icon" href="images/icon.png">
		<script src="./resources/jquery.min.js"></script>
		<link rel="stylesheet" href="./resources/bootstrap.min.css" />
		<script src="./resources/bootstrap.min.js"></script>
	</head>
	<body>
		<br />
		<div class="container">
			<h2 align="center">Register</h2>
			<br />
			<div class="panel panel-default">
				<div class="panel-heading">Register</div>
					<div class="panel-body">
						<span><?php echo $message; ?></span>
						<form method="post">
							<div class="form-group">
								<label>Display Name</label>
								<input type="text" name="name" id="name" class="form-control" />
							</div>
							<div class="form-group">
								<label>Username</label>
								<input type="text" name="username" id="username" class="form-control" />
							</div>
							<div class="form-group">
								<label>Password</label>
								<input type="password" name="password" id="password" class="form-control" />
							</div>
							<div class="form-group">
								<label>Repeat password</label>
								<input type="password" name="password_repeat" id="password_repeat" class="form-control" />
							</div>
							<div class="form-group">
								<label>Email</label>
								<input type="text" name="email" id="email" class="form-control" />
							</div>
							<div class="form-group">
								<input type="submit" name="register" id="register" class="btn btn-info" value="Register" />
							</div>
							<div class="form-group">
								<a href="login.php">Login</a>
							</div>
						</form>
					</div>
				</div>
				<br />
			</div>
		</div>
	</body>
</html>
