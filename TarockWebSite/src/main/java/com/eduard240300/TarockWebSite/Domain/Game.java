package com.eduard240300.TarockWebSite.Domain;

public class Game {
    private int gameID;
    private int sessionID;
    private int scorePlayer1;
    private int scorePlayer2;
    private int scorePlayer3;
    private int scorePlayer4;
    private String declaration;
    private boolean radler;

    public Game(int gameID, int sessionID, int scorePlayer1, int scorePlayer2, int scorePlayer3, int scorePlayer4, String declaration, boolean radler)
    {
        this.gameID = gameID;
        this.sessionID = sessionID;
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
        this.scorePlayer3 = scorePlayer3;
        this.scorePlayer4 = scorePlayer4;
        this.declaration = declaration;
        this.radler = radler;
    }

    public Game() { }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public int getScorePlayer3() {
        return scorePlayer3;
    }

    public void setScorePlayer3(int scorePlayer3) {
        this.scorePlayer3 = scorePlayer3;
    }

    public int getScorePlayer4() {
        return scorePlayer4;
    }

    public void setScorePlayer4(int scorePlayer4) {
        this.scorePlayer4 = scorePlayer4;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public boolean getRadler() {
        return radler;
    }

    public void setRadler(boolean radler) {
        this.radler = radler;
    }

    public void setScorePlayer(int player, int score)
    {
        if (player == 0)
            setScorePlayer1(score);
        else if (player == 1)
            setScorePlayer2(score);
        else if (player == 2)
            setScorePlayer3(score);
        else if (player == 3)
            setScorePlayer4(score);
    }

    public int getScorePlayer(int player)
    {
        if (player == 0)
            return getScorePlayer1();
        else if (player == 1)
            return getScorePlayer2();
        else if (player == 2)
            return getScorePlayer3();
        else if (player == 3)
            return getScorePlayer4();
        return 0;
    }
}
