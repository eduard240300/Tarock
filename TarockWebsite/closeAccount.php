<?php
//closeAccount.php

require_once("controller.php");
$controller = new Controller();

require_once 'mobile_detect.php';
$detect = new Mobile_Detect;

if ($detect->isMobile() || $detect->isTablet()) {
	header("location:mobile/closeAccount.php");
}

if(!isset($_COOKIE["username"]))
{
	header("location:login.php");
}

$message = '';

if(isset($_POST["closeAccount"]))
{
	$username = $_COOKIE["username"];
	$password = $_POST["password"];
	$user = $controller->getUser($username);

	if(empty($password))
	{
		$message = "<div class='alert alert-danger'>All Fields are required</div>";
	}
	else if ($controller->hasSpaces($password))
	{
		$message = "<div class='alert alert-danger'>Fields can't contain spaces</div>";
	}
	else
	{
		if(password_verify($password, $user["Password"]))
		{
			$executed = $controller->deleteUser($username);
			if($executed){
				header("location:logout.php");
			} else{
				$message = "<div class='alert alert-danger'>User not closed successfully</div>";
			}
		}
		else{
			$message = "<div class='alert alert-danger'>Password is incorrect</div>";
		}
	}
}

?>

<!DOCTYPE html>
<html>
	<head>
		<title>Close Account Page</title>
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
			<h4 align="center"><a href="account.php">Back to Account Page</a></h4>
		</div>
		<br />
		<div class="container">
			<h2 align="center">Close Account</h2>
			<br />
			<div class="panel panel-default">
				<div class="panel-heading">Close Account : Input password to confirm</div>
					<div class="panel-body">
						<span><?php echo $message; ?></span>
						<form method="post">
							<div class="form-group">
								<label>Password</label>
								<input type="password" name="password" id="password" class="form-control" />
							</div>
							<div class="form-group">
								<input type="submit" name="closeAccount" id="closeAccount" class="btn btn-info" value="Close account" />
							</div>
						</form>
					</div>
				</div>
				<br />
			</div>
		</div>
	</body>
</html>
