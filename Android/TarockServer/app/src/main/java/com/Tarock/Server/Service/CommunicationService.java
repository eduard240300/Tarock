package com.Tarock.Server.Service;

import com.Tarock.Common.Domain.Session;
import com.Tarock.Common.Domain.Triple;
import com.Tarock.Server.ScrollingActivity;

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
            ScrollingActivity.getInstance().log("Accepted new user" + "\n");
            clientService.start();
            ScrollingActivity.getInstance().log("Started client socket" + "\n");
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
