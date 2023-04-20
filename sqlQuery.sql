mySQL:

CREATE TABLE Users_Tarock (
  Name VARCHAR(100) NOT NULL,
  Username VARCHAR(100) PRIMARY KEY,
  Password VARCHAR(300) NOT NULL,
  Email VARCHAR(100) NOT NULL
);

CREATE TABLE Sessions (
  SessionID INT PRIMARY KEY AUTO_INCREMENT,
  Creator VARCHAR(100) NOT NULL,
  DateCreated DATETIME NOT NULL,
  DateClosed DATETIME,
  Player1 VARCHAR(100),
  Player2 VARCHAR(100),
  Player3 VARCHAR(100),
  Player4 VARCHAR(100),
  FOREIGN KEY(Creator) REFERENCES Users_Tarock(Username),
  FOREIGN KEY(Player1) REFERENCES Users_Tarock(Username),
  FOREIGN KEY(Player2) REFERENCES Users_Tarock(Username),
  FOREIGN KEY(Player3) REFERENCES Users_Tarock(Username),
  FOREIGN KEY(Player4) REFERENCES Users_Tarock(Username)
);

CREATE TABLE Games (
  GameID INT PRIMARY KEY AUTO_INCREMENT,
  SessionID INT NOT NULL,
  ScorePlayer1 INT NOT NULL,
  ScorePlayer2 INT NOT NULL,
  ScorePlayer3 INT NOT NULL,
  ScorePlayer4 INT NOT NULL,
  Declaration VARCHAR(200) NOT NULL,
  Radler INT NOT NULL,
  RadlerTimes INT NOT NULL,
  FOREIGN KEY(SessionID) REFERENCES Sessions(SessionID)
);

postgreSQL:

CREATE TABLE Users_Tarock (
  Name VARCHAR(100) NOT NULL,
  Username VARCHAR(100) PRIMARY KEY,
  Password VARCHAR(300) NOT NULL,
  Email VARCHAR(100) NOT NULL
);

CREATE TABLE Sessions (
  SessionID SERIAL PRIMARY KEY,
  Creator VARCHAR(100) NOT NULL,
  DateCreated TIMESTAMP NOT NULL,
  DateClosed TIMESTAMP,
  Player1 VARCHAR(100),
  Player2 VARCHAR(100),
  Player3 VARCHAR(100),
  Player4 VARCHAR(100),
  FOREIGN KEY(Creator) REFERENCES Users_Tarock(Username),
  FOREIGN KEY(Player1) REFERENCES Users_Tarock(Username),
  FOREIGN KEY(Player2) REFERENCES Users_Tarock(Username),
  FOREIGN KEY(Player3) REFERENCES Users_Tarock(Username),
  FOREIGN KEY(Player4) REFERENCES Users_Tarock(Username)
);

CREATE TABLE Games (
  GameID SERIAL PRIMARY KEY,
  SessionID INT NOT NULL,
  ScorePlayer1 INT NOT NULL,
  ScorePlayer2 INT NOT NULL,
  ScorePlayer3 INT NOT NULL,
  ScorePlayer4 INT NOT NULL,
  Declaration VARCHAR(200) NOT NULL,
  Radler INT NOT NULL,
  RadlerTimes INT NOT NULL,
  FOREIGN KEY(SessionID) REFERENCES Sessions(SessionID)
);

