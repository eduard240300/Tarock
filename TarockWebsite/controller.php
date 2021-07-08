<?php

require_once 'model.php';

class Controller
{
    private $model;	

    public function __construct(){
    	$this->model = new Model ();
    }

    public function hasSpaces($string)
    {
        $result = str_split($string);
        foreach($result as $char)
        {
            if ($char === " ")
                return True;
        }
        return False;
    }

    public function processName($string)
    {
        $result = "";
        $iter = str_split($string);
        foreach($iter as $char)
        {
            if ($char === " ")
                $result = $result . "_";
            else if ($char === ":")
                $result = $result . ".";
            else
                $result = $result . $char;
        }
        return $result;
    }

    public function existsUsername($username) {
		return $this->model->existsUsername($username);
	}

    public function existsSession($sessionID) {
		return $this->model->existsSession($sessionID);
	}

    public function existsSessionForUsername($username, $sessionID) {
		return $this->model->existsSessionForUsername($username, $sessionID);
	}

    public function isClosedSession($sessionID) {
		return $this->model->isClosedSession($sessionID);
	}

	public function getUser($username)
	{
		return $this->model->getUser($username);
	}

	public function addUser($name, $username, $password, $email)
	{
		return $this->model->addUser($name, $username, $password, $email);
	}

    public function addSession($creator, $player1, $player2, $player3, $player4)
    {
        return $this->model->addSession($creator, $player1, $player2, $player3, $player4);
    }

    public function addGame($game)
    {
        return $this->model->addGame($game);
    }

    public function closeSession($sessionID)
    {
        return $this->model->closeSession($sessionID);
    }

    public function addSessionDateEnded($dateEnded)
    {
        $this->model->addSessionDateEnded($dateEnded);
    }

    public function getSession($sessionID) {
        return $this->model->getSession($sessionID);
    }

    public function getGame($sessionID, $gameRow) {
        return $this->model->getGame($sessionID, $gameRow);
    }

    public function getSessions($username) {
       return $this->model->getSessions($username);
    }

    public function getGames($sessionID) {
        return $this->model->getGames($sessionID);
    }

    public function getGamesSize($sessionID) {
        return $this->model->getGamesSize($sessionID);
    }
}

?>
