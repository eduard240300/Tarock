package com.Tarock.Server.ConnectionManager;

import com.Tarock.Common.Domain.Game;
import com.Tarock.Common.Domain.Pair;
import com.Tarock.Common.Domain.Session;
import com.Tarock.Common.Domain.User;
import com.Tarock.Common.Exception.ConnectionException;
import com.Tarock.Common.Exception.PHPException;
import com.Tarock.Common.Service.DataManipulationService;
import com.Tarock.Server.ScrollingActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class PHPConnection {
    private static String ipAddress;

    public static void initPHPConnection(){
        ipAddress = "194.36.88.249";
    }

    public static String read(String inputString) {

        try {
            byte[] post = inputString.getBytes();

            URL u = new URL("http://" + ipAddress + "/tarock/controllerHelper.php");
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();
            out.write(post);
            out.close();
            if (con.getResponseCode() != 200) {
                throw new ConnectionException("Server returned bad response code: " + con.getResponseCode() + " " + con.getResponseMessage());
            }
            InputStream in = con.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[8192];
            int cur;
            while ((cur = in.read(buff)) > 0) {
                outputStream.write(buff, 0, cur);
            }
            in.close();
            ScrollingActivity.getInstance().log("Received from website : " + outputStream + '\n');
            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUser(String username) {
        User user = new User();
        String post = "functionName=getUser&username=" + username;
        List<Pair<String, String>> userJSON = DataManipulationService.JSONtoList(Objects.requireNonNull(PHPConnection.read(post)));
        if (userJSON.get(0).getKey().equals("exception")) {
            throw new PHPException(userJSON.get(0).getValue());
        } else {
            for (Pair<String, String> stringStringPair : userJSON) {
                switch (stringStringPair.getKey()) {
                    case "name":
                        user.setName(stringStringPair.getValue());
                        break;
                    case "username":
                        user.setUsername(stringStringPair.getValue());
                        break;
                    case "password":
                        user.setPassword(stringStringPair.getValue());
                        break;
                    default:
                        break;
                }
            }
            return user;
        }
    }

    public static Session getSession(int sessionID) {
        Session session = new Session();
        String post = "functionName=getSession&sessionID=" + sessionID;
        List<Pair<String, String>> sessionJSON = DataManipulationService.JSONtoList(Objects.requireNonNull(PHPConnection.read(post)));
        if (sessionJSON.get(0).getKey().equals("exception")) {
            throw new PHPException(sessionJSON.get(0).getValue());
        } else {
            for (Pair<String, String> stringStringPair : sessionJSON) {
                switch (stringStringPair.getKey()) {
                    case "sessionID":
                        session.setSessionID(Integer.parseInt(stringStringPair.getValue()));
                        break;
                    case "creator":
                        session.setCreator(stringStringPair.getValue());
                        break;
                    case "dateClosed":
                        session.setDateClosed(DataManipulationService.getName(stringStringPair.getValue()));
                        break;
                    case "player1":
                        session.setPlayer1(stringStringPair.getValue());
                        break;
                    case "player2":
                        session.setPlayer2(stringStringPair.getValue());
                        break;
                    case "player3":
                        session.setPlayer3(stringStringPair.getValue());
                        break;
                    case "player4":
                        session.setPlayer4(stringStringPair.getValue());
                        break;
                    default:
                        break;
                }
            }
            return session;
        }
    }

    public static boolean verifyPassword(String username, String password) {
        User user = getUser(username);
        return (BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verify(Bytes.from(password).array(), Bytes.from(user.getPassword()).array()).verified);
    }

    public static void addGame(String username, Game game) {
        StringBuilder post = new StringBuilder("functionName=addGame&username=" + username);
        post.append("&sessionID=").append(game.getSessionID());
        for (int i = 0; i < 4; i++) {
            post.append("&scorePlayer").append(i + 1).append("=").append(game.getScorePlayer(i));
        }
        post.append("&declaration=").append(DataManipulationService.processDeclaration(game.getDeclaration()));
        post.append("&radler=").append(DataManipulationService.boolToString(game.getRadler()));
        post.append("&radlerTimes=").append(game.getRadlerTimes());
        List<Pair<String, String>> sessionJSON = DataManipulationService.JSONtoList(Objects.requireNonNull(PHPConnection.read(post.toString())));
        if (sessionJSON.get(0).getKey().equals("exception")) {
            throw new PHPException(sessionJSON.get(0).getValue());
        }
    }

    public static List<Game> getGames(String creator, int sessionID) {
        int gamesSize = 0;
        List<Game> games = new ArrayList<>();
        String post = "functionName=getGamesSize&username=" + creator;
        post += "&sessionID=" + sessionID;
        List<Pair<String, String>> gameJSON = DataManipulationService.JSONtoList(Objects.requireNonNull(PHPConnection.read(post)));
        if (gameJSON.get(0).getKey().equals("result")) {
            gamesSize = Integer.parseInt(gameJSON.get(0).getValue());
        }
        for (int i = 0; i < gamesSize; i++) {
            post = "functionName=getGame&sessionID=" + sessionID;
            post += "&gameRow=" + i;
            gameJSON = DataManipulationService.JSONtoList(Objects.requireNonNull(PHPConnection.read(post)));
            Game game = new Game();
            for (Pair<String, String> stringStringPair : gameJSON) {
                switch (stringStringPair.getKey()) {
                    case "sessionID":
                        game.setSessionID(Integer.parseInt(stringStringPair.getValue()));
                        break;
                    case "scorePlayer1":
                        game.setScorePlayer1(Integer.parseInt(stringStringPair.getValue()));
                        break;
                    case "scorePlayer2":
                        game.setScorePlayer2(Integer.parseInt(stringStringPair.getValue()));
                        break;
                    case "scorePlayer3":
                        game.setScorePlayer3(Integer.parseInt(stringStringPair.getValue()));
                        break;
                    case "scorePlayer4":
                        game.setScorePlayer4(Integer.parseInt(stringStringPair.getValue()));
                        break;
                    case "declaration":
                        game.setDeclaration(stringStringPair.getValue());
                        break;
                    case "radler":
                        game.setRadler(DataManipulationService.stringToBool(stringStringPair.getValue()));
                        break;
                    case "radlerTimes":
                        game.setRadlerTimes(Integer.parseInt(stringStringPair.getValue()));
                        break;
                    default:
                        break;
                }
            }
            games.add(game);
        }
        return games;
    }
}
