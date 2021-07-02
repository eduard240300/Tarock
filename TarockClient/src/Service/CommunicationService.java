package Service;

import Domain.Declaration;
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
    public static String username = "unknown";
    public static int sessionID = 0;
    public static String playerMode = "";
    public static Declaration declaration = null;
    public static boolean canPopeAtFinish = true;
    public static boolean canPagatAtFinish = true;

    public void runHelper () throws IOException, ClassNotFoundException, InterruptedException {
        //socket = new Socket("185.229.224.215", 9876);
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
        System.out.println(username + " Received : " + message);
        List<String> listOfCommands = DataManipulationService.processMessage(message);
        for(int i=0;i<listOfCommands.size();i++)
        {
            List<String> listOfObjects = DataManipulationService.processCommand(listOfCommands.get(i));
            if (listOfObjects.get(0).equals("require_auth")) {
                while(user == null)
                    Thread.sleep(1);
                sentMessage = "auth " + user.getUsername() + " " + user.getPassword() + " " + sessionID + ";";
                username = user.getUsername();
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
            else if (listOfObjects.get(0).equals("chairNumber"))
            {
                Repository.chair = Integer.parseInt(listOfObjects.get(1));
                Main.gameForm.updateChair();
            }
            else if (listOfObjects.get(0).equals("cards"))
            {
                for(int j=0;j<12;j++)
                    Repository.cards.set(j, Integer.parseInt(listOfObjects.get(j+1)));
                Main.gameForm.updateCards();
            }
            else if (listOfObjects.get(0).equals("requestedPlayerMode"))
            {
                int requestedChair = Integer.parseInt(listOfObjects.get(1));
                Main.gameForm.updateNextStatus(requestedChair, "Status");
            }
            else if (listOfObjects.get(0).equals("requestPlayerMode"))
            {
                Main.gameForm.the1of2Button.setEnabled(true);
                Main.gameForm.passButton.setEnabled(true);
                Main.gameForm.cancelGameButton.setEnabled(true);
                while (playerMode.equals(""))
                    Thread.sleep(1);
                sentMessage = "playerMode " + playerMode + ";";
                playerMode = "";
            }
            else if (listOfObjects.get(0).equals("respondedPlayerMode"))
            {
                Main.gameForm.changeStatusPlayer(Integer.parseInt(listOfObjects.get(1)), listOfObjects.get(2));
            }
            else if (listOfObjects.get(0).equals("increased1of2"))
            {
                Main.gameForm.increase1of2();
            }
            else if (listOfObjects.get(0).equals("playerRequest"))
            {
                Main.gameForm.updateNextStatus(-1, "Status");
                Repository.playerRequest = Integer.parseInt(listOfObjects.get(1));
                if (Repository.playerRequest == Repository.chair)
                    Repository.isRequestPlayer = true;
                Repository.the1of2 = Integer.parseInt(listOfObjects.get(2));
            }
            else if (listOfObjects.get(0).equals("requestedDeclaration"))
            {
                int requestedChair = Integer.parseInt(listOfObjects.get(1));
                Main.gameForm.updateNextStatus(requestedChair, "Declaration");
            }
            else if (listOfObjects.get(0).equals("requestDeclaration"))
            {
                Main.gameForm.resetDeclarationSelection();
                Main.gameForm.swithDeclarationSelection(canPopeAtFinish, canPagatAtFinish, Repository.isRequestPlayer, true);
                while (declaration == null)
                    Thread.sleep(1);
                sentMessage = "declaration " + declaration.toSendableObject() + ";";
                declaration = null;
                Main.gameForm.swithDeclarationSelection(canPopeAtFinish, canPagatAtFinish, Repository.isRequestPlayer, false);
            }
            else if (listOfObjects.get(0).equals("respondedDeclaration"))
            {
                Main.gameForm.changeDeclarationPlayer(Integer.parseInt(listOfObjects.get(1)), DataManipulationService.getName(listOfObjects.get(2)));
                boolean popeAtFinish = DataManipulationService.stringToBool(listOfObjects.get(3));
                boolean pagatAtFinish = DataManipulationService.stringToBool(listOfObjects.get(4));
                if (Integer.parseInt(listOfObjects.get(1)) == Repository.playerRequest)
                {
                    int pope = Integer.parseInt(listOfObjects.get(8));
                    Repository.chosenPope = pope;
                }
                if (popeAtFinish)
                    canPopeAtFinish = false;
                if (pagatAtFinish)
                    canPagatAtFinish = false;
            }
        }
        System.out.println(username + " Sent : " + sentMessage);
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
