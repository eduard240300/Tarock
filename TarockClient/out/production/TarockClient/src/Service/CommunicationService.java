package Service;

import Domain.Declaration;
import Domain.Round;
import Domain.User;
import GUI.ConnectionForm.ConnectionForm;
import GUI.ConnectionForm.ControllerConnectionForm;
import GUI.GameForm.GameForm;
import GUI.Main;
import GUI.ScoreForm.ScoreForm;
import Repository.Repository;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
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
    public static String talonTakeDecision = "";
    public static String talonGiveDecision = "";
    public static int numberOfTarocks = -1;
    public static int cardGiven = -1;
    public static boolean canPopeAtFinish = true;
    public static boolean canPagatAtFinish = true;
    public static int round = 0;
    public static boolean shouldCompleteRound = false;

    public void runHelper () throws IOException, ClassNotFoundException, InterruptedException {
        //socket = new Socket("185.229.224.215", 9876);
        while(true) {
            try {
                socket = new Socket("localhost", 9876);
                System.out.println("Connected to localhost:9876 !");
                break;
            } catch (ConnectException connectException) {
                //System.out.println("Could not connect !");
            }
            Thread.sleep(200);
        }
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        while(true)
        {
            try {
                read();
            }
            catch(EOFException eofException)
            {
                GameForm.popUpMessage("Server forcefully closed !");
                Main.gameForm.setVisible(false);
                Main.gameForm = new GameForm();
                Main.connectionForm.setVisible(true);
                ConnectionForm.controllerConnectionForm.resetServices();
                MainService.setLoggedOut();
                stop();
                break;
            }
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
                Repository.cards.clear();
                for(int j=0;j<12;j++)
                    Repository.cards.add(Integer.parseInt(listOfObjects.get(j+1)));
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
            else if (listOfObjects.get(0).equals("team1"))
            {
                for(int j=1;j<listOfObjects.size();j++)
                {
                    Repository.teams.get(0).add(Integer.parseInt(listOfObjects.get(j)));
                }
            }
            else if (listOfObjects.get(0).equals("team2"))
            {
                for(int j=1;j<listOfObjects.size();j++)
                {
                    Repository.teams.get(1).add(Integer.parseInt(listOfObjects.get(j)));
                }
                Main.gameForm.showTeams();
            }
            else if (listOfObjects.get(0).equals("clearDeclarationTurn"))
            {
                Main.gameForm.updateNextStatus(-1, "Declaration");
            }
            else if (listOfObjects.get(0).equals("talon"))
            {
                for(int j=0;j<6;j++)
                    Repository.talon.set(j, Integer.parseInt(listOfObjects.get(j+1)));
            }
            else if (listOfObjects.get(0).equals("requestedTalonSelection"))
            {
                Repository.talonPart = (Repository.the1of2-1)%3;
                Main.talonShowingForm.setVisible(true);
            }
            else if (listOfObjects.get(0).equals("requestTalonSelection"))
            {
                if (Repository.talonPart == -1)
                    Repository.talonPart = (Repository.the1of2-1)%3;
                if (!Main.talonSelectionForm.isVisible()) {
                    Main.talonSelectionForm.setVisible(true);
                    Main.talonSelectionForm.updateCards();
                }
                Main.talonSelectionForm.updateTalonCards();
                Main.talonSelectionForm.updateTalonLabel();
                while (talonTakeDecision.equals(""))
                    Thread.sleep(1);
                sentMessage = "talonSelection ";
                sentMessage += talonTakeDecision + ";";
                talonTakeDecision = "";
            }
            else if (listOfObjects.get(0).equals("respondedTalonSelection"))
            {
                if (listOfObjects.get(1).equals("next"))
                {
                    Main.talonShowingForm.revealTalonPart();
                    Repository.talonPart = Repository.talonPart + 1;
                    Repository.talonPart = Repository.talonPart % 3;
                    Repository.the1of2++;
                    System.out.println(username + " : the1of2 = " + Repository.the1of2);
                }
            }
            else if (listOfObjects.get(0).equals("requestTalonReplacement"))
            {
                while (talonGiveDecision.equals(""))
                    Thread.sleep(1);
                sentMessage = "talonReplacement";
                sentMessage += talonGiveDecision + ";";
                talonGiveDecision = "";
            }
            else if (listOfObjects.get(0).equals("closeTalonWindow"))
            {
                if (listOfObjects.get(1).equals("show"))
                    Main.talonShowingForm.setVisible(false);
                else if (listOfObjects.get(1).equals("select"))
                    Main.talonSelectionForm.setVisible(false);
            }
            else if (listOfObjects.get(0).equals("requestNumberOfTarocks"))
            {
                Main.gameForm.switchNumberOfTarocksSelection(true);
                while (numberOfTarocks == -1)
                    Thread.sleep(1);
                sentMessage = "numberOfTarocks " + numberOfTarocks + ";";
                numberOfTarocks = -1;
                Main.gameForm.switchNumberOfTarocksSelection(false);
            }
            else if (listOfObjects.get(0).equals("beginRound"))
            {
                shouldCompleteRound = true;
                Repository.round = round;
            }
            else if (listOfObjects.get(0).equals("requestedCard"))
            {
                int requestedChair = Integer.parseInt(listOfObjects.get(1));
                Main.gameForm.updateNextStatus(requestedChair, "Status");
            }
            else if (listOfObjects.get(0).equals("requestCard"))
            {
                GameForm.canGiveCard = true;
                while (cardGiven == -1)
                    Thread.sleep(1);
                GameForm.canGiveCard = false;
                sentMessage = "card " + cardGiven + ";";
                cardGiven = -1;
            }
            else if (listOfObjects.get(0).equals("respondedCard"))
            {
                int player = Integer.parseInt(listOfObjects.get(1));
                if (shouldCompleteRound)
                {
                    shouldCompleteRound = false;
                    Repository.rounds.add(new Round(round, player));
                    Main.gameForm.resetCards();
                }
                int cardID = Integer.parseInt(listOfObjects.get(2));

                Repository.rounds.get(round).addCard(player, cardID);
                Main.gameForm.updateCard(player, cardID);
            }
            else if (listOfObjects.get(0).equals("roundFinished"))
            {
                round++;
                int playerThatWon = Repository.rounds.get(round-1).getPlayerThatWon();
                Main.gameForm.updatePreviousRoundWonByLabel(Repository.players.get(playerThatWon));
            }
            else if (listOfObjects.get(0).equals("showScoreWindow"))
            {
                Main.scoreForm.setVisible(true);
            }
            else if (listOfObjects.get(0).equals("cardsTeam1"))
            {
                for(int j=1;j<listOfObjects.size();j++)
                {
                    Repository.cardsWon.get(0).add(Integer.valueOf(listOfObjects.get(j)));
                }
            }
            else if (listOfObjects.get(0).equals("cardsTeam2"))
            {
                for(int j=1;j<listOfObjects.size();j++)
                {
                    Repository.cardsWon.get(1).add(Integer.valueOf(listOfObjects.get(j)));
                }
                ScoreForm.updateCards();
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
