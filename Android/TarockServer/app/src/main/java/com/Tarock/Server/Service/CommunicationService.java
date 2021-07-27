package com.Tarock.Server.Service;

import android.app.Activity;

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

    public static void updateText(String text)
    {
        ScrollingActivity.activity.runOnUiThread(() -> ScrollingActivity.binding.textView.append(text + "\n"));
    }

    public void createServer() throws IOException {
        server = new ServerSocket(port);
        socketsList = new ArrayList<>();
        gameSessions = new ArrayList<>();
        //noinspection InfiniteLoopStatement
        while (true) {
            Socket socket = server.accept();
            ClientService clientService = new ClientService(socket);
            updateText("Accepted new user");
            clientService.start();
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
