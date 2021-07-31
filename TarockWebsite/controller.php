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

    public function getPope($char)
    {
        if ($char === "0")
        {
            $unicodeChar = "\u{2665}";
        }
        else if ($char === "1")
        {
            $unicodeChar = "\u{2666}";
        }
        else if ($char === "2")
        {
            $unicodeChar = "\u{2663}";
        }
        else
        {
            $unicodeChar = "\u{2660}";
        }
        return $unicodeChar;
    }

    public function processDeclaration($string)
    {
        $result = "";
        $iter = str_split($string);
        $cursor = 0;
        foreach($iter as $char)
        {
            if ($cursor == 4)
            {
                $result = $result . $this->getPope($char);
            }
            else if ($char === "_")
                $result = $result . " ";
            else if ($char === "*")
                $result = $result . ",";
            else
                $result = $result . $char;
            $cursor = $cursor + 1;
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

    public function deleteUser($username)
	{
		return $this->model->deleteUser($username);
	}

    public function changeUser($username, $password)
	{
		return $this->model->changeUser($username, $password);
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

    public function getSession($sessionID) {
        return $this->model->getSession($sessionID);
    }

    public function getGame($sessionID, $gameRow) {
        return $this->model->getGame($sessionID, $gameRow);
    }

    public function getSessions($username) {
       return $this->model->getSessions($username);
    }

    public function getAllSessions() {
        return $this->model->getAllSessions($username);
    }

    public function getGames($sessionID) {
        return $this->model->getGames($sessionID);
    }

    public function getGamesSize($sessionID) {
        return $this->model->getGamesSize($sessionID);
    }

    public function echoTr($game)
    {
        if ($game->getRadler() == "1"){
            echo '<tr class="radler">';
        }
        else
        {
            echo '<tr class="noRadler">';
        }
    }
}

?>
