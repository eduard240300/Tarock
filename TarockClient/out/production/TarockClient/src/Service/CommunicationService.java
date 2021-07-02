package Service;

import Domain.User;
import GUI.ConnectionForm.ConnectionForm;
import GUI.ConnectionForm.ControllerConnectionForm;
import GUI.Main;
import Repository.Repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class CommunicationService extends Thread{
    public static Socket socket;
    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;
    public static User user = null;
    public static int sessionID = 0;

    public void runHelper () throws IOException, ClassNotFoundException, InterruptedException {
        socket = new Socket("localhost", 9876);
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        while(true)
        {
            read();
        }
    }

    public void read() throws IOException, ClassNotFoundException, InterruptedException {
        String message = (String) inputStream.readObject();
        String sentMessage = "ok;";
        System.out.println("Received : " + message);
        List<String> listOfCommands = DataManipulationService.processMessage(message);
        for(int i=0;i<listOfCommands.size();i++)
        {
            List<String> listOfObjects = DataManipulationService.processCommand(listOfCommands.get(i));
            if (listOfObjects.get(0).equals("require_auth")) {
                while(user == null)
                    Thread.sleep(1);
                sentMessage = "auth " + user.getUsername() + " " + user.getPassword() + " " + sessionID + ";";
                user = null;
            } else if (listOfObjects.get(0).equals("failed_auth")) {
                String errorMessage;
                if (listOfObjects.get(1).equals("sessionIDEnded"))
                    errorMessage = "Session already ended !";
                else
                    errorMessage = "Wrong " + listOfObjects.get(1) + " !";
                ConnectionForm.popUpMessage(errorMessage);
            } else if (listOfObjects.get(0).equals("successful_auth")) {
                ControllerConnectionForm.finishLogin();
            }
            else if (listOfObjects.get(0).equals("players"))
            {
                for(int j=0;j<4;j++)
                    Repository.players.set(j, DataManipulationService.getName(listOfObjects.get(j+1)));
                Main.gameForm.changePlayerNames();
            }
        }
        System.out.println("Sent : " + sentMessage);
        outputStream.writeObject(sentMessage);
    }

    public void run()
    {
        try{
            runHelper();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
