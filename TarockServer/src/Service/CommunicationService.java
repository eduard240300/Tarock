package Service;

import Domain.Pair;
import Domain.Session;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommunicationService extends Thread{
    public static ServerSocket server;
    public static int port = 9876;
    public static List<Pair<String, ClientService, Session>> socketsList;
    public static List<GameSessionService> gameSessions;

    public void createServer() throws IOException, ClassNotFoundException, InterruptedException {
        server = new ServerSocket(port);
        socketsList = new ArrayList<Pair<String, ClientService, Session>>();
        gameSessions = new ArrayList<GameSessionService>();
        while (true) {
            Socket socket = server.accept();
            ClientService clientService = new ClientService(socket);
            System.out.println("Accepted new user");
            clientService.start();
            System.out.println("Started client socket");
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
