<?php
//logout.php

setcookie("name", "", time()-3600);
setcookie("username", "", time()-3600);
setcookie("email", "", time()-3600);
setcookie("sessionID", "", time()-3600);
header("location:login.php");

?>
