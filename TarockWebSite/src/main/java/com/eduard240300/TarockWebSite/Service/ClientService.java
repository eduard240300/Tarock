package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.DBManager.DBManager;
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

    public String getUsername() throws InterruptedException {
        while(notExecuted)
            Thread.sleep(1);
        return username;
    }

    public List<String> read(String sendMessage) throws IOException, ClassNotFoundException {
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

    public void runClient(List<String> listOfCommands) throws IOException, ClassNotFoundException, CommunicationException {
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
                                throw new LoginException("Session already ended !");
                            List<String> newListOfCommands = read("successful_auth;");
                            System.out.println(newListOfCommands);
                            notExecuted = false;
                        }
                        catch (RuntimeException exception)
                        {
                            List<String> newListOfCommands = read("failed_auth sessionID;");
                            newListOfCommands = read("require_auth;");
                            runClient(newListOfCommands);
                        }
                    }
                    else
                    {
                        List<String> newListOfCommands = read("failed_auth password;");
                        newListOfCommands = read("require_auth;");
                        runClient(newListOfCommands);
                    }
                }
                catch (DBException dbException)
                {
                    List<String> newListOfCommands = read("failed_auth username;");
                    newListOfCommands = read("require_auth;");
                    runClient(newListOfCommands);
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
            List<String> listOfCommands = read("require_auth;");
            runClient(listOfCommands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
