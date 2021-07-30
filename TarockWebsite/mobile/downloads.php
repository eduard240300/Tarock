<?php
//downloads.php

require_once '../mobile_detect.php';
$detect = new Mobile_Detect;

if ( (!$detect->isMobile()) and (!$detect->isTablet())) {
    header("location:../downloads.php");
}

if(!isset($_COOKIE["username"]))
{
    header("location:login.php");
}

?>
<!DOCTYPE html>
<html>
    <head>
        <title>Downloads Page</title>
        <link rel="icon" href="images/icon.png">
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
            <h1 align="center" style="font-size:5vw;">Downloads Page</h1>
            <center><h1>
            <style type="text/css">
                td {
                    padding: 10px 10px 10px 10px;
                    align: center;
                }
            </style>
            <table align="center" border="1px">
                <thead>
                    <tr>
                        <td><center>Nr</center></td>
                        <td><center>Name</center></td>
                        <td><center>Ver.</center></td>
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
						<td><center><form method="get" action="../resources/releases/windows/Tarock-Client-Setup.exe">
							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
					<tr>
						<td><center>2</center></td>
						<td><center>Tarock Client</center></td>
						<td><center>1.0</center></td>
						<td><center>Ubuntu 20.04 amd64</center></td>
						<td><center><form method="get" action="../resources/releases/linux/tarock-client_1.0-1_amd64.deb">
							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
					<tr>
						<td><center>3</center></td>
						<td><center>Tarock Client</center></td>
						<td><center>1.0</center></td>
						<td><center>Latest Android OS</center></td>
						<td><center><form method="get" action="../resources/releases/android/tarock-client.apk">
							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
					<tr>
						<td><center>4</center></td>
						<td><center>Tarock Server</center></td>
						<td><center>1.0</center></td>
						<td><center>Windows 10 x64</center></td>
						<td><center><form method="get" action="../resources/releases/windows/Tarock-Server-Setup.exe">
  							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
					<tr>
						<td><center>5</center></td>
						<td><center>Tarock Server</center></td>
						<td><center>1.0</center></td>
						<td><center>Ubuntu 20.04 amd64</center></td>
						<td><center><form method="get" action="../resources/releases/linux/tarock-server_1.0-1_amd64.deb">
							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
					<tr>
						<td><center>6</center></td>
						<td><center>Tarock Server</center></td>
						<td><center>1.0</center></td>
						<td><center>Latest Android OS</center></td>
						<td><center><form method="get" action="../resources/releases/android/tarock-server.apk">
							<button type="submit">Download</button>
							</form></center>
						</td>
					</tr>
                </tbody>
            </table>
            </h1></center>
        </div>
    </body>
</html>
