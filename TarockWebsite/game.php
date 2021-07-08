<?php

class Game implements JsonSerializable {
	private $gameID;
    private $sessionID;
    private $scorePlayer1;
    private $scorePlayer2;
    private $scorePlayer3;
    private $scorePlayer4;
    private $declaration;
    private $radler;
	private $radlerTimes;

	public function __construct($gameID, $sessionID, $scorePlayer1, $scorePlayer2, $scorePlayer3, $scorePlayer4, $declaration, $radler, $radlerTimes) {
		$this->gameID = $gameID;
		$this->sessionID = $sessionID;
		$this->scorePlayer1 = $scorePlayer1;
		$this->scorePlayer2 = $scorePlayer2;
		$this->scorePlayer3 = $scorePlayer3;
		$this->scorePlayer4 = $scorePlayer4;
		$this->declaration = $declaration;
		$this->radler = $radler;
		$this->radlerTimes = $radlerTimes;
	}

	public function getGameID() {
		return $this->gameID;
	}
	public function getSessionID() {
		return $this->sessionID;
	}
	public function getScorePlayer1() {
		return $this->scorePlayer1;
	}
	public function getScorePlayer2() {
		return $this->scorePlayer2;
	}
	public function getScorePlayer3() {
		return $this->scorePlayer3;
	}
	public function getScorePlayer4() {
		return $this->scorePlayer4;
	}
	public function getDeclaration() {
		return $this->declaration;
	}
	public function getRadler() {
		return $this->radler;
	}
	public function getRadlerTimes() {
		return $this->radlerTimes;
	}
	public function jsonSerialize() {
        return get_object_vars($this);
    }
}
?>
