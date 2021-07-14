package com.TarockServer.Service;

import com.TarockServer.ConnectionManager.PHPConnection;
import com.TarockServer.Domain.Session;
import com.TarockServer.Domain.Triple;
import com.TarockServer.Domain.User;
import com.TarockServer.Exception.ConnectionException;
import com.TarockServer.Exception.LoginException;
import com.TarockServer.Exception.PHPException;
import com.TarockServer.GUI.StatusForm;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public void removeConnection(String username)
    {
        for(int i=0;i<CommunicationService.socketsList.size();i++)
        {
            if (CommunicationService.socketsList.get(i).getKey().equals(username)) {
                try {
                    CommunicationService.socketsList.get(i).getValue1().socket.close();
                }
                catch(IOException ignored)
                {}
                CommunicationService.socketsList.remove(i);
                StatusForm.addToStatusTextArea("Removed client " + username);
            }
        }
    }

    public void addClientToList(ClientService clientService, String username, Session session)
    {
        Triple<String, ClientService, Session> newPair = new Triple<>(username);
        newPair.setValue1(clientService);
        newPair.setValue2(session);
        removeConnection(username);
        StatusForm.addToStatusTextArea("Added client " + username);
        CommunicationService.socketsList.add(newPair);
    }

    public ClientService getClientInList(String username)
    {
        for(int i=0;i<CommunicationService.socketsList.size();i++)
        {
            if (CommunicationService.socketsList.get(i).getKey().equals(username)) {
                return CommunicationService.socketsList.get(i).getValue1();
            }
        }
        return null;
    }

    public Session getSessionInList(String username)
    {
        for(int i=0;i<CommunicationService.socketsList.size();i++)
        {
            if (CommunicationService.socketsList.get(i).getKey().equals(username)) {
                return CommunicationService.socketsList.get(i).getValue2();
            }
        }
        return null;
    }

    public void log(String message)
    {
        if ((message.charAt(0) == 'c') && (message.charAt(1) == 'a') & (message.charAt(2) == 'r'))
        {
            message = "sentCards;";
        }
        StatusForm.addToStatusTextArea("Sent (" + username + ") : " + message);
    }

    @SuppressWarnings({"deprecation", "SuspiciousListRemoveInLoop"})
    public List<String> write(String sendMessage) throws IOException, ClassNotFoundException {
        log(sendMessage);
        String message = "";
        try {
            outputStream.writeObject(sendMessage);
            message = (String) inputStream.readObject();
        }
        catch (EOFException exception)
        {
            socket.close();
            Session session = getSessionInList(username);
            StatusForm.addToStatusTextArea(exception.getMessage());
            for(int i=0;i<CommunicationService.gameSessions.size();i++)
            {
                if (CommunicationService.gameSessions.get(i).session.getSessionID() == session.getSessionID())
                {
                    CommunicationService.gameSessions.get(i).stop();
                    CommunicationService.gameSessions.remove(i);
                }
            }
            Thread.currentThread().stop();
        }
        if (!message.equals("ok;"))
            StatusForm.addToStatusTextArea("Received (" + username + ") : " + message);
        return DataManipulationService.processMessage(message);
    }

    public int getChairNumber(Session session, String username)
    {
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
        User player1 = PHPConnection.getUser(session.getPlayer1());
        User player2 = PHPConnection.getUser(session.getPlayer2());
        User player3 = PHPConnection.getUser(session.getPlayer3());
        User player4 = PHPConnection.getUser(session.getPlayer4());
        List<String> players = new ArrayList<>();
        players.add(player1.getName());
        players.add(player2.getName());
        players.add(player3.getName());
        players.add(player4.getName());
        List<String> playersUsername = new ArrayList<>();
        playersUsername.add(player1.getUsername());
        playersUsername.add(player2.getUsername());
        playersUsername.add(player3.getUsername());
        playersUsername.add(player4.getUsername());

        StringBuilder message = new StringBuilder("players");
        for(int i=0;i<4;i++)
        {
            message.append(" ").append(players.get(i));
        }
        message.append(";");
        write(message.toString());

        getClientInList(username).write("chairNumber " + getChairNumber(session, username) + ";");

        boolean startGameSession = true;

        for(int i=0;i<4;i++)
        {
            if (getClientInList(playersUsername.get(i)) == null)
                startGameSession = false;
            if (getSessionInList(playersUsername.get(i)).getSessionID() != session.getSessionID())
                startGameSession = false;
        }

        if (startGameSession)
        {
            List<ClientService> clientServiceList = new ArrayList<>();
            clientServiceList.add(getClientInList(playersUsername.get(0)));
            clientServiceList.add(getClientInList(playersUsername.get(1)));
            clientServiceList.add(getClientInList(playersUsername.get(2)));
            clientServiceList.add(getClientInList(playersUsername.get(3)));
            GameSessionService newGameService = new GameSessionService(clientServiceList, session, players);
            CommunicationService.gameSessions.add(newGameService);
            newGameService.start();
        }
    }

    public void runAuthentication(List<String> listOfCommands) throws IOException, ClassNotFoundException {
        List<String> newListOfCommands;
        for (String listOfCommand : listOfCommands) {
            List<String> listOfObjects = DataManipulationService.processCommand(listOfCommand);
            if (listOfObjects.get(0).equals("auth")) {
                username = listOfObjects.get(1);
                String password = listOfObjects.get(2);
                int sessionID = Integer.parseInt(listOfObjects.get(3));
                try {
                    try {
                        PHPConnection.getUser(username);
                    } catch (PHPException phpException) {
                        throw new LoginException(phpException.getMessage());
                    }
                    if (PHPConnection.verifyPassword(username, password)) {
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
                            newListOfCommands = write("require_auth;");
                            runAuthentication(newListOfCommands);
                        } catch (ConnectionException exception) {
                            write("failed_auth sessionIDEnded;");
                            newListOfCommands = write("require_auth;");
                            runAuthentication(newListOfCommands);
                        }
                    } else {
                        write("failed_auth password;");
                        newListOfCommands = write("require_auth;");
                        runAuthentication(newListOfCommands);
                    }
                } catch (LoginException dbException) {
                    write("failed_auth username;");
                    newListOfCommands = write("require_auth;");
                    runAuthentication(newListOfCommands);
                }
            } else
                throw new ConnectionException("Invalid authentication !");
        }
    }

    public void run(){
        try {
            StatusForm.addToStatusTextArea("Started thread");
            List<String> listOfCommands = write("require_auth;");
            runAuthentication(listOfCommands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
