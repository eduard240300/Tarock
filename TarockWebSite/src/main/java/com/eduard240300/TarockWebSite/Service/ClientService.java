package com.eduard240300.TarockWebSite.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientService extends Thread {
    public static Socket socket;
    public static ObjectInputStream inputStream;
    public static ObjectOutputStream outputStream;

    public ClientService(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void runClient() throws IOException, ClassNotFoundException {
        boolean exitStatus = false;
        while(!exitStatus)
        {
            String message = (String) inputStream.readObject();
            List<String> listOfCommands = DataManipulationService.processMessage(message);
            int i;
            for(i=0;i<listOfCommands.size();i++)
            {
                List<String> listOfObjects = DataManipulationService.processCommand(listOfCommands.get(i));
                if (listOfObjects.get(0) == "exit")
                    exitStatus = true;
                else
                    System.out.println(listOfObjects);
            }
            outputStream.writeObject("OK");
        }
        System.out.println("Exited client ...");
    }

    public void run(){
        try {
            runClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
