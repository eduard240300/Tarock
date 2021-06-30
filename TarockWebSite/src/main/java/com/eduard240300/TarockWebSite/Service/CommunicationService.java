package com.eduard240300.TarockWebSite.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationService extends Thread{
    private static ServerSocket server;
    private static int port = 9876;

    public void createServer() throws IOException {
        server = new ServerSocket(port);
        while(true){
            Socket socket = server.accept();
            ClientService clientService = new ClientService(socket);
            clientService.start();
        }
    }

    public void run(){
        try {
            createServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
