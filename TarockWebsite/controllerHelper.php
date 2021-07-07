<?php

include_once("controller.php");
$controller = new Controller();

$aResult = array();

    if( !isset($_POST['functionName']) ) 
    {
        $aResult['error'] = 'No function name!';
    }

    if( !isset($aResult['error']) ) {

        switch($_POST['functionName']) {
            case "getUser":
                $username = $_POST['username'];
                if ($controller->existsUsername($username))
                {
                    $user = $controller->getUser($username);
                    $aResult['name'] = $user['Name'];
                    $aResult['username'] = $user['Username'];
                    $aResult['password'] = $user['Password'];
                    $aResult['email'] = $user['Email'];
                }
                else
                {
                    $aResult['exception'] = 'Username does not exist';
                }
                break;
            
            case "getSession":
                $sessionID = $_POST['sessionID'];
                if ($controller->existsSession($sessionID))
                {
                    $session = $controller->getSession($sessionID);
                    $aResult['sessionID'] = $controller->processName($session->getId());
                    $aResult['creator'] = $session->getCreator();
                    $aResult['dateCreated'] = $controller->processName($session->getDateCreated());
                    $aResult['dateClosed'] = $controller->processName($session->getDateClosed());
                    $aResult['player1'] = $session->getPlayer1();
                    $aResult['player2'] = $session->getPlayer2();
                    $aResult['player3'] = $session->getPlayer3();
                    $aResult['player4'] = $session->getPlayer4();
                }
                else
                {
                    $aResult['exception'] = 'Session does not exist';
                }
                break;

            default:
                $aResult['error'] = 'Not found function '.$_POST['functionName'].'!';
                break;
        }

    }

    echo json_encode($aResult);

?>