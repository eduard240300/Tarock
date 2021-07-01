package com.eduard240300.TarockWebSite.DBManager;

import com.eduard240300.TarockWebSite.Domain.Game;
import com.eduard240300.TarockWebSite.Domain.Session;
import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Exception.DBException;
import com.eduard240300.TarockWebSite.Service.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public static boolean notConnected = true;
    public static Statement stmt;
    public static String ipAddress = "185.229.224.215";

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + ipAddress + "/Tarock", "root", "0000");
            stmt = con.createStatement();
            notConnected = false;
        } catch(Exception ex) {
            throw new DBException(ex.getMessage());
        }
    }

    public static User getUser(String username) {
        ResultSet rs;
        User user = new User(null, null);
        try {
            String statement = "SELECT * FROM Users WHERE Username = " + "'" + username + "'";
            rs = stmt.executeQuery(statement);
            try{
                rs.next();
                String password = rs.getString("Password");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                user = new User(name, username, password, email);
            }
            catch (Exception e)
            {
                throw new DBException("Username does not exist !");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return user;
    }

    public static void registerUser(User inputUser) {
        int error;
        try {
            String statement = "INSERT INTO Users(Name, Username, Password, Email)\nVALUES(" +
                    "'" + inputUser.getName() + "', " +
                    "'" + inputUser.getUsername() + "', " +
                    "'" + BCrypt.hashpw(inputUser.getPassword(), BCrypt.gensalt(12)) + "', " +
                    "'" + inputUser.getEmail() + "'" +
                    ")";
            error = stmt.executeUpdate(statement);
        } catch (SQLException e) {
            throw new DBException("Username already exists !");
        }
    }

    public static List<Session> getSessions(String username) {
        ResultSet rs;
        List<Session> sessions= new ArrayList<Session>();
        try {
            String statement = "SELECT * FROM Sessions WHERE Creator = " + "'" + username + "'";
            rs = stmt.executeQuery(statement);
            try{
                while(rs.next()) {
                    int SessionID = rs.getInt("SessionID");
                    String DateCreated = rs.getTimestamp("DateCreated").toString();

                    String DateEnded;
                    try {
                        DateEnded = rs.getTimestamp("DateEnded").toString();
                    }
                    catch (Exception e)
                    {
                        DateEnded = "";
                    }

                    String Player1;
                    try {
                        Player1 = rs.getString("Player1");
                    }
                    catch (Exception e)
                    {
                        Player1 = "";
                    }

                    String Player2;
                    try {
                        Player2 = rs.getString("Player2");
                    }
                    catch (Exception e)
                    {
                        Player2 = "";
                    }

                    String Player3;
                    try {
                        Player3 = rs.getString("Player3");
                    }
                    catch (Exception e)
                    {
                        Player3 = "";
                    }

                    String Player4;
                    try {
                        Player4 = rs.getString("Player4");
                    }
                    catch (Exception e)
                    {
                        Player4 = "";
                    }

                    Session session = new Session(SessionID, username, DateCreated, DateEnded, Player1, Player2, Player3, Player4);
                    sessions.add(session);
                }
            }
            catch (Exception e)
            {
                throw new DBException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return sessions;
    }

    public static void addSession(Session session) {
        int error;
        try {
            String statement = "INSERT INTO Sessions(Creator, DateCreated, Player1, Player2, Player3, Player4" +
                    ")\nVALUES(" +
                    "'" + session.getCreator() + "', " +
                    "'" + session.getDateCreated() + "', " +
                    "'" + session.getPlayer1() + "', " +
                    "'" + session.getPlayer2() + "'," +
                    "'" + session.getPlayer3() + "'," +
                    "'" + session.getPlayer4() + "'" +
                    ")";
            error = stmt.executeUpdate(statement);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public static void existsSession(String username, int sessionID) {
        ResultSet rs;
        try {
            String statement = "SELECT * FROM Sessions WHERE Creator = " + "'" + username + "' AND " +
                    "SessionID = " + "'" + String.valueOf(sessionID) + "';";
            rs = stmt.executeQuery(statement);
            try{
                rs.next();
                int SessionID = rs.getInt("SessionID");
            }
            catch (Exception e)
            {
                throw new DBException("Session does not exist !");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public static List<Game> getGames(int sessionID) {
        ResultSet rs;
        int currentGameID = 1;
        List<Game> games = new ArrayList<Game>();
        try {
            String statement = "SELECT * FROM Games WHERE SessionID = " + "'" + String.valueOf(sessionID) + "' ORDER BY GameID ASC;";
            rs = stmt.executeQuery(statement);
            try{
                while(rs.next()) {
                    int GameID = currentGameID;
                    currentGameID++;
                    int SessionID = rs.getInt("SessionID");
                    int ScorePlayer1 = rs.getInt("ScorePlayer1");
                    int ScorePlayer2 = rs.getInt("ScorePlayer2");
                    int ScorePlayer3 = rs.getInt("ScorePlayer3");
                    int ScorePlayer4 = rs.getInt("ScorePlayer4");
                    String Declaration = rs.getString("Declaration");
                    int potentialRadler = rs.getInt("Radler");
                    boolean Radler = true;
                    if (potentialRadler == 0) Radler = false;

                    Game game = new Game(GameID, SessionID, ScorePlayer1, ScorePlayer2, ScorePlayer3, ScorePlayer4, Declaration, Radler);
                    games.add(game);
                }
            }
            catch (Exception e)
            {
                throw new DBException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return games;
    }

    public static Session getSession(int sessionID) {
        ResultSet rs;
        Session session = null;
        try {
            String statement = "SELECT * FROM Sessions WHERE SessionID = " + "'" + String.valueOf(sessionID) + "'";
            rs = stmt.executeQuery(statement);
            try{
                rs.next();
                int SessionID = rs.getInt("SessionID");
                String Creator = rs.getString("Creator");
                String DateCreated = rs.getTimestamp("DateCreated").toString();

                String DateEnded;
                try {
                    DateEnded = rs.getTimestamp("DateEnded").toString();
                }
                catch (Exception e)
                {
                    DateEnded = "";
                }

                String Player1;
                try {
                    Player1 = rs.getString("Player1");
                }
                catch (Exception e)
                {
                    Player1 = "";
                }

                String Player2;
                try {
                    Player2 = rs.getString("Player2");
                }
                catch (Exception e)
                {
                    Player2 = "";
                }

                String Player3;
                try {
                    Player3 = rs.getString("Player3");
                }
                catch (Exception e)
                {
                    Player3 = "";
                }

                String Player4;
                try {
                    Player4 = rs.getString("Player4");
                }
                catch (Exception e)
                {
                    Player4 = "";
                }

                session = new Session(SessionID, Creator, DateCreated, DateEnded, Player1, Player2, Player3, Player4);            }
            catch (Exception e)
            {
                throw new DBException("Session does not exist !");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return session;
    }

    public static void closeSession(int sessionID, String closeTime) {
        int error;
        try {
            String statement = "UPDATE Sessions SET DateEnded = " +
                    "'" + closeTime + "'" +
                    " WHERE SessionID = " +
                    "'" + sessionID + "';";
            error = stmt.executeUpdate(statement);
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }
}