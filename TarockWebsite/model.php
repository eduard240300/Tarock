<?php

require_once 'DBUtils.php';
require_once 'session.php';
require_once 'game.php';

class Model {
	private $db;

	public function __construct() {
		$this->db = new DBUtils ();
	}

	public function existsUsername($username) {
		return $this->db->existsUsername($username);
	}

	public function existsSession($sessionID) {
		return $this->db->existsSession($sessionID);
	}

	public function existsSessionForUsername($username, $sessionID) {
		return $this->db->existsSessionForUsername($username, $sessionID);
	}

	public function isClosedSession($sessionID) {
		return $this->db->isClosedSession($sessionID);
	}

	public function getUser($username)
	{
		return $this->db->getUser($username);
	}

	public function addUser($name, $username, $password, $email)
	{
		return $this->db->addUser($name, $username, $password, $email);
	}

	public function deleteUser($username)
	{
		return $this->db->deleteUser($username);
	}

	public function changeUser($username, $password)
	{
		return $this->db->changeUser($username, $password);
	}

	public function addSession($creator, $player1, $player2, $player3, $player4)
	{
		return $this->db->addSession($creator, $player1, $player2, $player3, $player4);
	}

	public function addGame($game)
    {
        return $this->db->addGame($game);
    }

	public function closeSession($sessionID)
    {
        return $this->db->closeSession($sessionID);
    }

	public function getSession($sessionID) {
		$resultset = $this->db->getSession($sessionID);
		$sessions = array();
		foreach($resultset as $key=>$val) {
			$session = $val;
			$newSession = new Session($session['SessionID'], $session['Creator'], $session['DateCreated'], $session['DateClosed'], $session['Player1'], $session['Player2'], $session['Player3'], $session['Player4']);
	    	array_push($sessions, $newSession);
		}

	    return $sessions[0];
	}

	public function getGame($sessionID, $gameRow) {
        $resultset = $this->db->getGame($sessionID, $gameRow);
		$games = array();
		foreach($resultset as $key=>$val) {
			$game = $val;
			$newGame = new Game($game['GameID'], $game['SessionID'], $game['ScorePlayer1'], $game['ScorePlayer2'], $game['ScorePlayer3'], $game['ScorePlayer4'], $game['Declaration'], $game['Radler'], $game['RadlerTimes']);
	    	array_push($games, $newGame);
		}

	    return $games[0];
    }

	public function getSessions($username) {
		$resultset = $this->db->selectSessions($username);
		$sessions = array();
		foreach($resultset as $key=>$val) {
			$session = $val;
			$newSession = new Session($session['SessionID'], $session['Creator'], $session['DateCreated'], $session['DateClosed'], $session['Player1'], $session['Player2'], $session['Player3'], $session['Player4']);
	    	array_push($sessions, $newSession);
		}

	    return $sessions;
	}

	public function getAllSessions() {
		$resultset = $this->db->selectAllSessions();
		$sessions = array();
		foreach($resultset as $key=>$val) {
			$session = $val;
			$newSession = new Session($session['SessionID'], $session['Creator'], $session['DateCreated'], $session['DateClosed'], $session['Player1'], $session['Player2'], $session['Player3'], $session['Player4']);
	    	array_push($sessions, $newSession);
		}

	    return $sessions;
	}

	public function getGames($sessionID) {
        $resultset = $this->db->selectGames($sessionID);
		$games = array();
		foreach($resultset as $key=>$val) {
			$game = $val;
			$newGame = new Game($game['GameID'], $game['SessionID'], $game['ScorePlayer1'], $game['ScorePlayer2'], $game['ScorePlayer3'], $game['ScorePlayer4'], $game['Declaration'], $game['Radler'], $game['RadlerTimes']);
			array_push($games, $newGame);
		}

	    return $games;
    }

	public function getGamesSize($sessionID) {
        $resultset = $this->db->selectGames($sessionID);
		$games = array();
		foreach($resultset as $key=>$val) {
			$game = $val;
			array_push($games, $game);
		}

	    return $cnt = count($games);
    }
}

?>
