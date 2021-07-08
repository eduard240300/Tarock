<?php

require_once("game.php");

class DBUtils {
	private $host = '127.0.0.1';
	private $db   = 'Tarock';
	private $user = 'root';
	private $pass = '';
	private $charset = 'utf8';	

	private $pdo;
	private $error;

	public function __construct () {
		$dsn = "mysql:host=$this->host;dbname=$this->db;charset=$this->charset";
		$opt = array(PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
			PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
			PDO::ATTR_EMULATE_PREPARES   => false);
		try {
			$this->pdo = new PDO($dsn, $this->user, $this->pass, $opt);		
		} // Catch any errors
		catch(PDOException $e){
			$this->error = $e->getMessage();
			echo "Error connecting to DB: " . $this->error;
		}
	}

	public function existsUsername($username) {
		$query="SELECT * FROM Users_Tarock
		WHERE Username = :username";
		$statement = $this->pdo->prepare($query);
		$statement->execute(
		 array(
		  'username' => $username
		 )
		);
		$count = $statement->rowCount();
		return ($count == 1);
	}

	public function existsSession($sessionID)
	{
		$query="SELECT * FROM Sessions
		WHERE SessionID = :sessionID";
		$statement = $this->pdo->prepare($query);
		$statement->execute(
		 array(
		  'sessionID' => $sessionID
		 )
		);
		$count = $statement->rowCount();
		return ($count == 1);
	}

	public function existsSessionForUsername($username, $sessionID)
	{
		$query="SELECT * FROM Sessions
		WHERE Creator = :username AND SessionID = :sessionID";
		$statement = $this->pdo->prepare($query);
		$statement->execute(
		 array(
		  'sessionID' => $sessionID,
		  'username' => $username
		 )
		);
		$count = $statement->rowCount();
		return ($count == 1);
	}

	public function isClosedSession($sessionID)
	{
		$query="SELECT * FROM Sessions
		WHERE SessionID = :sessionID AND DateClosed IS NOT NULL";
		$statement = $this->pdo->prepare($query);
		$statement->execute(
		 array(
		  'sessionID' => $sessionID
		 )
		 );
		$count = $statement->rowCount();
		return ($count == 1);
	}

	public function getUser($username) {
		$query="SELECT * FROM Users_Tarock
		WHERE Username = :username";
		$statement = $this->pdo->prepare($query);
		$statement->execute(
			array(
			 'username' => $username
			)
		);
		return $statement->fetchAll()[0];
	}

	public function addUser($name, $username, $password, $email)
	{
		$query = "INSERT INTO Users_Tarock(Name, Username, Password, Email)
		VALUES(:name, :username, :password, :email)";

		$statement = $this->pdo->prepare($query);
		return $statement->execute(array(
				'name' => $name,
				'username' => $username,
				'password' => password_hash($password, PASSWORD_DEFAULT),
				'email' => $email
		));
	}

	public function addSession($creator, $player1, $player2, $player3, $player4)
	{
		$dateCreated = new DateTime();
		$timezone = new DateTimeZone('Europe/Bucharest');
		$dateCreated->setTimezone($timezone);
		$dateCreatedString = $dateCreated->format('Y-m-d H:i:s');

		$query = "INSERT INTO Sessions(Creator, DateCreated, Player1, Player2, Player3, Player4)
		VALUES(:creator, :dateCreated, :player1, :player2, :player3, :player4)";

		$statement = $this->pdo->prepare($query);
		return $statement->execute(array(
				'creator' => $creator,
				'dateCreated' => $dateCreatedString,
				'player1' => $player1,
				'player2' => $player2,
				'player3' => $player3,
				'player4' => $player4
		));
	}

	public function addGame($game)
	{
		$query = "INSERT INTO Games(SessionID, ScorePlayer1, ScorePlayer2, ScorePlayer3, ScorePlayer4, Declaration, Radler)
		VALUES(";
		$query = $query . $game["sessionID"] . ", ";
		$query = $query . $game["scorePlayer1"] . ", ";
		$query = $query . $game["scorePlayer2"] . ", ";
		$query = $query . $game["scorePlayer3"] . ", ";
		$query = $query . $game["scorePlayer4"] . ", ";
		$query = $query . "'" . $game["declaration"] . "'" . ", ";
		$query = $query . $game["radler"] . ");";

		$statement = $this->pdo->prepare($query);
		return $statement->execute();
	}

	public function closeSession($sessionID)
	{
		$dateClosed = new DateTime();
		$timezone = new DateTimeZone('Europe/Bucharest');
		$dateClosed->setTimezone($timezone);
		$dateClosedString = $dateClosed->format('Y-m-d H:i:s');

		$query = "UPDATE Sessions
		SET DateClosed = :dateClosed
		WHERE SessionID = :sessionID";

		$statement = $this->pdo->prepare($query);
		return $statement->execute(array(
				'sessionID' => $sessionID,
				'dateClosed' => $dateClosedString
		));

		return True;
	}

	public function getSession($sessionID)
	{
		$query="SELECT * FROM Sessions
		WHERE SessionID = :sessionID";
		$statement = $this->pdo->prepare($query);
		$statement->execute(
			array(
			 'sessionID' => $sessionID
			)
		);
		
        return $statement->fetchAll();
	}

	public function getGame($sessionID, $gameRow)
	{
		$query="SELECT * FROM Games WHERE SessionID = " . $sessionID . " ORDER BY GameID ASC LIMIT 1 OFFSET " . $gameRow . ";";
		
		$statement = $this->pdo->prepare($query);
		$statement->execute();
		
        return $statement->fetchAll();
	}

	public function selectSessions($username) {
		$query="SELECT * FROM Sessions
		WHERE Creator = :username";
		$statement = $this->pdo->prepare($query);
		$statement->execute(
			array(
			 'username' => $username
			)
		);
		
        return $statement->fetchAll(PDO::FETCH_ASSOC);
    }

	public function selectGames($sessionID) {
		$query="SELECT * FROM Games
		WHERE SessionID = :sessionID";
		$statement = $this->pdo->prepare($query);
		$statement->execute(
			array(
			 'sessionID' => $sessionID
			)
		);
		
        return $statement->fetchAll(PDO::FETCH_ASSOC);
    }
}

?>

