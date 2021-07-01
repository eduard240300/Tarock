package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.Domain.Pair;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommunicationService extends Thread{
    private static ServerSocket server;
    private static int port = 9876;
    private static List<Pair<String, ClientService>> socketsList;

    public void createServer() throws IOException, ClassNotFoundException, InterruptedException {
        server = new ServerSocket(port);
        socketsList = new ArrayList<Pair<String, ClientService>>();
        while(true){
            Socket socket = server.accept();
            ClientService clientService = new ClientService(socket);
            System.out.println("Accepted new user");
            Pair<String, ClientService> newPair = new Pair<String, ClientService>(clientService);
            clientService.start();
            System.out.println("Started client socket");
            String username = clientService.getUsername();
            newPair.setKey(username);
            removeConnection(username);
            System.out.println("Added client " + username);
            socketsList.add(newPair);
        }
    }

    public void removeConnection(String username) throws IOException {
        for(int i=0;i<socketsList.size();i++)
        {
            if (socketsList.get(i).getKey().equals(username)) {
                try {
                    socketsList.get(i).getValue().socket.close();
                }
                catch(IOException exception)
                {}
                socketsList.remove(i);
                System.out.println("Removed client " + username);
            }
        }
    }

    public void run(){
        try {
            createServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
