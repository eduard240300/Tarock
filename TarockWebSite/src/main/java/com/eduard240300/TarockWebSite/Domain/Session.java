package com.eduard240300.TarockWebSite.Domain;

import java.sql.Timestamp;

public class Session {
    private int sessionID;
    private String creator;
    private String dateCreated;
    private String dateEnded;
    private String player1;
    private String player2;
    private String player3;
    private String player4;

    public Session(int sessionID, String creator, String dateCreated, String dateEnded, String player1, String player2, String player3, String player4)
    {
        this.sessionID = sessionID;
        this.creator = creator;
        this.dateCreated = dateCreated;
        this.dateEnded = dateEnded;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
    }

    public Session(String creator, String dateCreated, String player1, String player2, String player3, String player4)
    {
        this.creator = creator;
        this.dateCreated = dateCreated;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
    }


    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(String dateEnded) {
        this.dateEnded = dateEnded;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer4() {
        return player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
    }
}
