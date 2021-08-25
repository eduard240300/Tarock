<?php

include_once("controller.php");
require_once("game.php");

$controller = new Controller();

$_POST = json_decode(file_get_contents('php://input'), true);
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

            case "addGame":
                $username = $_POST['username'];
                $sessionID = $_POST['sessionID'];
                if ($controller->existsSessionForUsername($username, $sessionID))
                {
                    $scorePlayer1 = $_POST['scorePlayer1'];
                    $scorePlayer2 = $_POST['scorePlayer2'];
                    $scorePlayer3 = $_POST['scorePlayer3'];
                    $scorePlayer4 = $_POST['scorePlayer4'];
                    $declaration = $_POST['declaration'];
                    $radler = $_POST['radler'];
                    $radlerTimes = $_POST['radlerTimes'];
                    $newGame = new Game("0", $sessionID, $scorePlayer1, $scorePlayer2, $scorePlayer3, $scorePlayer4, $declaration, $radler, $radlerTimes);
                    $result = $controller->addGame($newGame->jsonSerialize());
                    $aResult['result'] = $controller->processName($result);
                }
                else
                {
                    $aResult['exception'] = 'Session does not exist';
                }
                break;

            case "getGamesSize":
                $username = $_POST['username'];
                $sessionID = $_POST['sessionID'];
                if ($controller->existsSessionForUsername($username, $sessionID))
                {
                    $aResult['result'] = $controller->processName($controller->getGamesSize($sessionID));
                }
                else
                {
                    $aResult['exception'] = 'Session does not exist';
                }
                break;

            case "getGame":
                $gameRow = $_POST['gameRow'];
                $sessionID = $_POST['sessionID'];
                if ($controller->existsSession($sessionID))
                {
                    $game = $controller->getGame($sessionID, $gameRow);
                    $aResult['sessionID'] = $controller->processName($game->getSessionID());
                    $aResult['scorePlayer1'] = $controller->processName($game->getScorePlayer1());
                    $aResult['scorePlayer2'] = $controller->processName($game->getScorePlayer2());
                    $aResult['scorePlayer3'] = $controller->processName($game->getScorePlayer3());
                    $aResult['scorePlayer4'] = $controller->processName($game->getScorePlayer4());
                    $aResult['declaration'] = $game->getDeclaration();
                    $aResult['radler'] = $controller->processName($game->getRadler());
                    $aResult['radlerTimes'] = $controller->processName($game->getRadlerTimes());
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