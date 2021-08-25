package com.Tarock.Server.Service;

import com.Tarock.Common.Domain.Session;
import com.Tarock.Common.Domain.Triple;
import com.Tarock.Common.Domain.User;
import com.Tarock.Common.Exception.ConnectionException;
import com.Tarock.Common.Exception.LoginException;
import com.Tarock.Common.Exception.PHPException;
import com.Tarock.Common.Service.DataManipulationService;
import com.Tarock.Server.ConnectionManager.PHPConnection;
import com.Tarock.Server.ScrollingActivity;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SuspiciousListRemoveInLoop")
public class ClientService extends Thread {
    public Socket socket;
    public ObjectInputStream inputStream;
    public ObjectOutputStream outputStream;
    public String username = null;

    public ClientService(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void removeConnection(String username) {
        for (int i = 0; i < CommunicationService.socketsList.size(); i++) {
            if (CommunicationService.socketsList.get(i).getKey().equals(username)) {
                try {
                    CommunicationService.socketsList.get(i).getValue1().socket.close();
                } catch (IOException ignored) {
                }
                CommunicationService.socketsList.remove(i);
                ScrollingActivity.getInstance().log("Removed client " + username + "\n");
            }
        }
    }

    public void addClientToList(ClientService clientService, String username, Session session) {
        Triple<String, ClientService, Session> newPair = new Triple<>(username);
        newPair.setValue1(clientService);
        newPair.setValue2(session);
        removeConnection(username);
        ScrollingActivity.getInstance().log("Added client " + username + "\n");
        CommunicationService.socketsList.add(newPair);
    }

    public ClientService getClientInList(String username) {
        for (int i = 0; i < CommunicationService.socketsList.size(); i++) {
            if (CommunicationService.socketsList.get(i).getKey().equals(username)) {
                return CommunicationService.socketsList.get(i).getValue1();
            }
        }
        return null;
    }

    public Session getSessionInList(String username) {
        for (int i = 0; i < CommunicationService.socketsList.size(); i++) {
            if (CommunicationService.socketsList.get(i).getKey().equals(username)) {
                return CommunicationService.socketsList.get(i).getValue2();
            }
        }
        return null;
    }

    public void logSent(String message) {
        String processedMessage = message;
        if (message.startsWith("car")) {
            processedMessage = "sentCards;";
        }
        String finalMessage = "Sent (" + username + ") : " + processedMessage;
        ScrollingActivity.getInstance().log(finalMessage + "\n");
    }

    public void logReceived(String message) {
        ScrollingActivity.getInstance().log("Received (" + username + ") : " + message + "\n");
    }

    @SuppressWarnings({"deprecation"})
    public void closeSession(EOFException exception) {
        Session session = getSessionInList(username);
        ScrollingActivity.getInstance().log(exception.getMessage() + "\n");
        for (GameSessionService gameSession : CommunicationService.gameSessions) {
            if (gameSession.session.getSessionID() == session.getSessionID()) {
                gameSession.stop();
                CommunicationService.gameSessions.remove(gameSession);
            }
        }
    }

    @SuppressWarnings({"deprecation"})
    public List<String> write(String sendMessage) throws IOException, ClassNotFoundException {
        logSent(sendMessage);
        String message = "";
        try {
            outputStream.writeObject(sendMessage);
            message = (String) inputStream.readObject();
        } catch (EOFException exception) {
            socket.close();
            closeSession(exception);
            Thread.currentThread().stop();
        }

        if (!message.equals("ok;")) {
            logReceived(message);
        }

        return DataManipulationService.processMessage(message);
    }

    public int getChairNumber(Session session, String username) {
        int chair = -1;
        if (session.getPlayer1().equals(username))
            chair = 0;
        else if (session.getPlayer2().equals(username))
            chair = 1;
        else if (session.getPlayer3().equals(username))
            chair = 2;
        else if (session.getPlayer4().equals(username))
            chair = 3;
        return chair;
    }

    public void runGame(Session session) throws IOException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        users.add(PHPConnection.getUser(session.getPlayer1()));
        users.add(PHPConnection.getUser(session.getPlayer2()));
        users.add(PHPConnection.getUser(session.getPlayer3()));
        users.add(PHPConnection.getUser(session.getPlayer4()));
        List<String> players = new ArrayList<>();
        players.add(users.get(0).getName());
        players.add(users.get(1).getName());
        players.add(users.get(2).getName());
        players.add(users.get(3).getName());
        List<String> playersUsername = new ArrayList<>();
        playersUsername.add(users.get(0).getUsername());
        playersUsername.add(users.get(1).getUsername());
        playersUsername.add(users.get(2).getUsername());
        playersUsername.add(users.get(3).getUsername());

        StringBuilder message = new StringBuilder("players");
        for (int i = 0; i < 4; i++) {
            message.append(" ").append(players.get(i));
        }
        message.append(";");
        write(message.toString());

        getClientInList(username).write("chairNumber " + getChairNumber(session, username) + ";");

        boolean startGameSession = true;

        for (int i = 0; i < 4; i++) {
            if (getClientInList(playersUsername.get(i)) == null)
                startGameSession = false;
            else if (getSessionInList(playersUsername.get(i)).getSessionID() != session.getSessionID())
                startGameSession = false;
        }

        if (startGameSession) {
            List<ClientService> clientServiceList = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                clientServiceList.add(getClientInList(playersUsername.get(i)));
            GameSessionService newGameService = new GameSessionService(clientServiceList, session, players);
            CommunicationService.gameSessions.add(newGameService);
            newGameService.start();
        }
    }

    public void authSession(int sessionID) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;

        try {
            Session session = PHPConnection.getSession(sessionID);
            if ((!session.getPlayer1().equals(username)) && (!session.getPlayer2().equals(username)) &&
                    (!session.getPlayer3().equals(username)) && (!session.getPlayer4().equals(username)))
                throw new PHPException("Session does not include " + username);
            if (!session.getDateClosed().equals(""))
                throw new ConnectionException("Session already ended !");
            write("successful_auth;");
            this.addClientToList(this, username, session);
            runGame(session);
        } catch (PHPException exception) {
            write("failed_auth sessionID;");
            listOfCommands = write("require_auth;");
            runAuthentication(listOfCommands);
        } catch (ConnectionException exception) {
            write("failed_auth sessionIDEnded;");
            listOfCommands = write("require_auth;");
            runAuthentication(listOfCommands);
        }
    }

    public void authPassword(String password, int sessionID) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;

        try {
            PHPConnection.getUser(username);
        } catch (PHPException phpException) {
            throw new LoginException(phpException.getMessage());
        }

        if (PHPConnection.verifyPassword(username, password)) {
            authSession(sessionID);
        } else {
            write("failed_auth password;");
            listOfCommands = write("require_auth;");
            runAuthentication(listOfCommands);
        }
    }

    public void authUsername(String password, int sessionID) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        try {
            authPassword(password, sessionID);
        } catch (LoginException dbException) {
            write("failed_auth username;");
            listOfCommands = write("require_auth;");
            runAuthentication(listOfCommands);
        }
    }

    public void runAuthentication(List<String> listOfCommands) throws IOException, ClassNotFoundException {
        for (String listOfCommand : listOfCommands) {
            List<String> listOfObjects = DataManipulationService.processCommand(listOfCommand);
            if (listOfObjects.get(0).equals("auth")) {
                username = listOfObjects.get(1);
                String password = listOfObjects.get(2);
                int sessionID = Integer.parseInt(listOfObjects.get(3));
                authUsername(password, sessionID);
            } else
                throw new ConnectionException("Invalid authentication !");
        }
    }

    public void run() {
        try {
            ScrollingActivity.getInstance().log("Started thread" + "\n");
            List<String> listOfCommands = write("require_auth;");
            runAuthentication(listOfCommands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
