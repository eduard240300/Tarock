<?php
//downloads.php

require_once 'mobile_detect.php';
$detect = new Mobile_Detect;

if ($detect->isMobile() || $detect->isTablet()) {
	header("location:mobile/downloads.php");
}

if(!isset($_COOKIE["username"]))
{
	header("location:login.php");
}

?>
<!DOCTYPE html>
<html>
	<head>
		<title>Download Page</title>
		<link rel="icon" href="images/icon.png">
		<script src="./resources/jquery.min.js"></script>
		<link rel="stylesheet" href="./resources/bootstrap.min.css" />
		<script src="./resources/bootstrap.min.js"></script>
	</head>
	<body>
		<style type="text/css">
			td {
				padding: 10px 10px 10px 10px;
				align: center;
			}
		</style>
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
			<?php echo '<h2 align="center">Downloads Page</h2>'; ?>
			<h2> </h2>
			<h2></h2>
			<center>
			<h3>
			<table align="center" border="1px">
				<thead>
					<tr>
						<td><center>Nr</center></td>
						<td><center>Name</center></td>
						<td><center>Version</center></td>
						<td><center>Operating System</center></td>
						<td><center></center></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><center>1</center></td>
						<td><center>Tarock Client</center></td>
						<td><center>1.0</center></td>
						<td><center>Windows 10 x64</center></td>
						<td><center><form method="get" action="./releases/windows/Tarock-Client-Setup.exe">
							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
					<tr>
						<td><center>2</center></td>
						<td><center>Tarock Client</center></td>
						<td><center>1.0</center></td>
						<td><center>Ubuntu 20.04 amd64</center></td>
						<td><center><form method="get" action="./releases/linux/tarock-client_1.0-1_amd64.deb">
							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
					<tr>
						<td><center>3</center></td>
						<td><center>Tarock Server</center></td>
						<td><center>1.0</center></td>
						<td><center>Windows 10 x64</center></td>
						<td><center><form method="get" action="./releases/windows/Tarock-Server-Setup.exe">
  							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
					<tr>
						<td><center>4</center></td>
						<td><center>Tarock Server</center></td>
						<td><center>1.0</center></td>
						<td><center>Ubuntu 20.04 amd64</center></td>
						<td><center><form method="get" action="./releases/linux/tarock-server_1.0-1_amd64.deb">
							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
				</tbody>
			</table>
			</h3>
			</center>
		</div>
	</body>
</html>
