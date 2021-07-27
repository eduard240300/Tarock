package com.Tarock.Server.Service;

import com.Tarock.Common.Domain.Session;
import com.Tarock.Common.Domain.Triple;
import com.Tarock.Server.GUI.StatusForm;
import com.Tarock.Server.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommunicationService extends Thread {
    public static ServerSocket server;
    public static int port = 9876;
    public static List<Triple<String, ClientService, Session>> socketsList;
    public static List<GameSessionService> gameSessions;

    public void createServer() throws IOException {
        server = new ServerSocket(port);
        socketsList = new ArrayList<>();
        gameSessions = new ArrayList<>();
        //noinspection InfiniteLoopStatement
        while (true) {
            Socket socket = server.accept();
            ClientService clientService = new ClientService(socket);
            if (Main.noGUI)
                System.out.println("Accepted new user");
            else
                StatusForm.addToStatusTextArea("Accepted new user");
            clientService.start();
            if (Main.noGUI)
                System.out.println("Started client socket");
            else
                StatusForm.addToStatusTextArea("Started client socket");
        }
    }

    public void run() {
        try {
            createServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
