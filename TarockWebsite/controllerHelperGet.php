<?php

include_once("controller.php");
require_once("game.php");

$controller = new Controller();

$aResult = array();

    if( !isset($_GET['functionName']) ) 
    {
        $aResult['error'] = 'No function name!';
    }

    if( !isset($aResult['error']) ) {

        switch($_GET['functionName']) {
            case "getUser":
                $username = $_GET['username'];
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
                $sessionID = $_GET['sessionID'];
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

            case "addGame":
                $username = $_GET['username'];
                $sessionID = $_GET['sessionID'];
                if ($controller->existsSessionForUsername($username, $sessionID))
                {
                    $scorePlayer1 = $_GET['scorePlayer1'];
                    $scorePlayer2 = $_GET['scorePlayer2'];
                    $scorePlayer3 = $_GET['scorePlayer3'];
                    $scorePlayer4 = $_GET['scorePlayer4'];
                    $declaration = $_GET['declaration'];
                    $radler = $_GET['radler'];
                    $newGame = new Game("0", $sessionID, $scorePlayer1, $scorePlayer2, $scorePlayer3, $scorePlayer4, $declaration, $radler);
                    $result = $controller->addGame($newGame->jsonSerialize());
                    $aResult['result'] = $controller->processName($result);
                }
                else
                {
                    $aResult['exception'] = 'Session does not exist';
                }
                break;

            case "getGamesSize":
                $username = $_GET['username'];
                $sessionID = $_GET['sessionID'];
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
                $gameRow = $_GET['gameRow'];
                $sessionID = $_GET['sessionID'];
                if ($controller->existsSession($sessionID))
                {
                    $game = $controller->getGame($sessionID, $gameRow);
                    $aResult['gameID'] = $controller->processName($game->getGameID());
                    $aResult['sessionID'] = $controller->processName($game->getSessionID());
                    $aResult['scorePlayer1'] = $controller->processName($game->getScorePlayer1());
                    $aResult['scorePlayer2'] = $controller->processName($game->getScorePlayer2());
                    $aResult['scorePlayer3'] = $controller->processName($game->getScorePlayer3());
                    $aResult['scorePlayer4'] = $controller->processName($game->getScorePlayer4());
                    $aResult['declaration'] = $game->getDeclaration();
                    $aResult['radler'] = $controller->processName($game->getRadler());
                }
                else
                {
                    $aResult['exception'] = 'Session does not exist';
                }
                break;

            default:
                $aResult['error'] = 'Not found function '.$_GET['functionName'].'!';
                break;
        }

    }

    echo json_encode($aResult);

?>