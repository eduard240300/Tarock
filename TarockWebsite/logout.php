<?php
//logout.php

if(!isset($_COOKIE["username"]))
{
 header("location:login.php");
}

/*
$jsonData = json_encode(array());
file_put_contents('shopping_cart.json', $jsonData); 
*/

setcookie("name", "", time()-3600);
setcookie("username", "", time()-3600);
setcookie("email", "", time()-3600);
setcookie("sessionID", "", time()-3600);
header("location:login.php");

?>
