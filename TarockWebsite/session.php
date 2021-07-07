<?php

class Session implements JsonSerializable {
	private $id;
	private $creator;
	private $dateCreated;
	private $dateClosed;
	private $player1;
	private $player2;
	private $player3;
	private $player4;

	public function __construct($id, $creator, $dateCreated, $dateClosed, $player1, $player2, $player3, $player4) {
		$this->id = $id;
		$this->creator = $creator;
		$this->dateCreated = $dateCreated;
		$this->dateClosed = $dateClosed;
		$this->player1 = $player1;
		$this->player2 = $player2;
		$this->player3 = $player3;
		$this->player4 = $player4;
	}

	public function getId() {
		return $this->id;
	}
	public function getCreator() {
		return $this->creator;
	}
	public function getDateCreated() {
		return $this->dateCreated;
	}
	public function getDateClosed() {
		return $this->dateClosed;
	}
	public function getPlayer1() {
		return $this->player1;
	}
	public function getPlayer2() {
		return $this->player2;
	}
	public function getPlayer3() {
		return $this->player3;
	}
	public function getPlayer4() {
		return $this->player4;
	}
	public function jsonSerialize() {
        return get_object_vars($this);
    }
}
?>
