package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.DBManager.DBManager;
import com.eduard240300.TarockWebSite.Domain.Pair;
import com.eduard240300.TarockWebSite.Domain.Session;
import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Exception.ConnectionException;
import com.eduard240300.TarockWebSite.Exception.DBException;
import com.eduard240300.TarockWebSite.Exception.LoginException;

import javax.naming.CommunicationException;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientService extends Thread {
    public Socket socket;
    public ObjectInputStream inputStream;
    public ObjectOutputStream outputStream;
    public String username = null;
    private boolean notExecuted = true;

    public ClientService(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void removeConnection(String username)
    {
        for(int i=0;i<CommunicationService.socketsList.size();i++)
        {
            if (CommunicationService.socketsList.get(i).getKey().equals(username)) {
                try {
                    CommunicationService.socketsList.get(i).getValue().socket.close();
                }
                catch(IOException exception)
                {}
                CommunicationService.socketsList.remove(i);
                System.out.println("Removed client " + username);
            }
        }
    }

    public void addClientToList(ClientService clientService, String username, Session session)
    {
        Pair<String, ClientService, Session> newPair = new Pair<String, ClientService, Session>(clientService);
        newPair.setKey(username);
        newPair.setSpecialValue(session);
        removeConnection(username);
        System.out.println("Added client " + username);
        CommunicationService.socketsList.add(newPair);
    }

    public List<String> write(String sendMessage) throws IOException, ClassNotFoundException {
        String message = "";
        try {
            outputStream.writeObject(sendMessage);
            message = (String) inputStream.readObject();
        }
        catch (EOFException exception)
        {
            socket.close();
            System.out.println(exception.getMessage());
            Thread.currentThread().stop();
        }
        List<String> listOfCommands = DataManipulationService.processMessage(message);
        return listOfCommands;
    }

    public void runGame(Session session) throws IOException, ClassNotFoundException {
        List<String> newListOfCommands;
        User player1 = DBManager.getUser(session.getPlayer1());
        User player2 = DBManager.getUser(session.getPlayer2());
        User player3 = DBManager.getUser(session.getPlayer3());
        User player4 = DBManager.getUser(session.getPlayer4());
        String message = "players " + DataManipulationService.processName(player1.getName()) +
                " " + DataManipulationService.processName(player2.getName()) +
                " " + DataManipulationService.processName(player3.getName()) +
                " " + DataManipulationService.processName(player4.getName()) + ";";
        newListOfCommands = write(message);
        System.out.println("Sent : " + message);
    }

    public void runAuthentication(List<String> listOfCommands) throws IOException, ClassNotFoundException, CommunicationException {
        List<String> newListOfCommands;
        for(int i=0;i<listOfCommands.size();i++)
        {
            List<String> listOfObjects = DataManipulationService.processCommand(listOfCommands.get(i));
            if (listOfObjects.get(0).equals("auth")) {
                username = listOfObjects.get(1);
                String password = listOfObjects.get(2);
                int sessionID = Integer.parseInt(listOfObjects.get(3));
                try {
                    User dbUser = DBManager.getUser(username);
                    if (BCrypt.checkpw(password, dbUser.getPassword()))
                    {
                        try{
                            DBManager.existsSession(username, sessionID);
                            Session session = DBManager.getSession(sessionID);
                            if (session.getDateEnded() != "")
                                throw new ConnectionException("Session already ended !");
                            write("successful_auth;");
                            this.addClientToList(this, username, session);
                            runGame(session);
                        }
                        catch (DBException exception)
                        {
                            write("failed_auth sessionID;");
                            newListOfCommands = write("require_auth;");
                            runAuthentication(newListOfCommands);
                        }
                        catch (ConnectionException exception)
                        {
                            write("failed_auth sessionIDEnded;");
                            newListOfCommands = write("require_auth;");
                            runAuthentication(newListOfCommands);
                        }
                    }
                    else
                    {
                        write("failed_auth password;");
                        newListOfCommands = write("require_auth;");
                        runAuthentication(newListOfCommands);
                    }
                }
                catch (DBException dbException)
                {
                    write("failed_auth username;");
                    newListOfCommands = write("require_auth;");
                    runAuthentication(newListOfCommands);
                }
            }
            else if (listOfObjects.get(0) == "ok") { }
            else
                throw new CommunicationException("Invalid authentication !");
        }
    }

    public void run(){
        try {
            System.out.println("Started thread");
            List<String> listOfCommands = write("require_auth;");
            runAuthentication(listOfCommands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
