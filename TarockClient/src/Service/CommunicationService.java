package Service;

import Domain.Declaration;
import Domain.Round;
import Domain.User;
import GUI.ConnectionForm.ConnectionForm;
import GUI.ConnectionForm.ControllerConnectionForm;
import GUI.GameForm.GameForm;
import GUI.Main;
import GUI.ScoreForm.ScoreForm;
import GUI.TalonSelectionForm.TalonSelectionForm;
import Repository.Repository;

import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class CommunicationService extends Thread{
    public static String ipAddress = "";
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
    public static int pope = -1;
    public static int cardGiven = -1;
    public static boolean canPopeAtFinish = true;
    public static boolean canPagatAtFinish = true;
    public static boolean canAllPopes = true;
    public static boolean canTrull = true;
    public static int round = 0;
    public static boolean shouldCompleteRound = false;
    public static boolean connected = false;

    @SuppressWarnings({"deprecation", "BusyWait"})
    public void runHelper () throws IOException, ClassNotFoundException, InterruptedException {
        //socket = new Socket("localhost", 9876);
        while(true) {
            try {
                if (!ipAddress.equals("")) {
                    socket = new Socket(ipAddress, 9876);
                    connected = true;
                    System.out.println("Connected to " + ipAddress + ":9876 !");
                    break;
                }
            } catch (ConnectException connectException) {
                ipAddress = "";
                ConnectionForm.popUpMessage("Could not connect to server !");
            }
            catch (UnknownHostException unknownHostException)
            {
                ipAddress = "";
                ConnectionForm.popUpMessage("Server is down or IP is incorrect !");
            }
            Thread.sleep(200);
        }
        if (connected)
        {
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
                    Repository.logout();
                    stop();
                    break;
                }
            }
        }
    }

    @SuppressWarnings("BusyWait")
    public void read() throws IOException, ClassNotFoundException, InterruptedException {
        String message = (String) inputStream.readObject();
        String sentMessage = "ok;";
        System.out.println(username + " Received : " + message);
        List<String> listOfCommands = DataManipulationService.processMessage(message);
        for (String listOfCommand : listOfCommands) {
            List<String> listOfObjects = DataManipulationService.processCommand(listOfCommand);
            switch (listOfObjects.get(0)) {
                case "require_auth":
                    while (user == null)
                        Thread.sleep(1);
                    sentMessage = "auth " + user.getUsername() + " " + user.getPassword() + " " + sessionID + ";";
                    username = user.getUsername();
                    user = null;
                    break;
                case "failed_auth":
                    String errorMessage;
                    if (listOfObjects.get(1).equals("sessionIDEnded"))
                        errorMessage = "Session already ended !";
                    else
                        errorMessage = "Wrong " + listOfObjects.get(1) + " !";
                    ConnectionForm.popUpMessage(errorMessage);
                    break;
                case "successful_auth":
                    ControllerConnectionForm.finishLogin();
                    break;
                case "players":
                    for (int j = 0; j < 4; j++)
                        Repository.players.set(j, DataManipulationService.getName(listOfObjects.get(j + 1)));
                    Main.gameForm.resetPlayerNames();
                    ScoreForm.resetPlayerNames();
                    break;
                case "chairNumber":
                    Repository.chair = Integer.parseInt(listOfObjects.get(1));
                    Main.gameForm.updateChair();
                    break;
                case "beginGame":
                    playerMode = "";
                    declaration = null;
                    talonTakeDecision = "";
                    talonGiveDecision = "";
                    pope = -1;
                    cardGiven = -1;
                    canPopeAtFinish = true;
                    canPagatAtFinish = true;
                    canAllPopes = true;
                    canTrull = true;
                    round = 0;
                    shouldCompleteRound = false;

                    GameForm.the1of2 = 1;
                    GameForm.typeOfActivation = "";
                    GameForm.canGiveCard = false;
                    GameForm.the1of2Button.setEnabled(false);
                    GameForm.passButton.setEnabled(false);
                    GameForm.cancelGameButton.setEnabled(false);
                    GameForm.submitButton.setEnabled(false);
                    Repository.resetRepository();

                    TalonSelectionForm.nextButton.setEnabled(true);
                    TalonSelectionForm.takeButton.setEnabled(true);
                    TalonSelectionForm.giveButton.setEnabled(false);
                    Main.talonSelectionForm.updateGivenCards();
                    Main.talonShowingForm.updateCards();

                    Main.gameForm.resetCards();
                    Main.gameForm.resetPlayerNames();
                    Main.gameForm.updateCards();
                    Main.gameForm.reset1of2();
                    Main.gameForm.resetTeams();
                    Main.gameForm.updateNextStatus(-1, "Status");
                    Main.gameForm.updateNextStatus(-1, "Declaration");
                    Main.gameForm.resetDeclarationSelection();
                    Main.gameForm.updatePreviousRoundWonByLabel("");

                    ScoreForm.offset1 = 0;
                    ScoreForm.offset2 = 0;
                    break;
                case "cards":
                    Repository.cards.clear();
                    for (int j = 0; j < 12; j++)
                        Repository.cards.add(Integer.parseInt(listOfObjects.get(j + 1)));
                    Main.gameForm.updateCards();
                    break;
                case "requestedPlayerMode":
                case "requestedCard": {
                    int requestedChair = Integer.parseInt(listOfObjects.get(1));
                    Main.gameForm.updateNextStatus(requestedChair, "Status");
                    break;
                }
                case "requestPlayerMode":
                    GameForm.the1of2Button.setEnabled(true);
                    GameForm.passButton.setEnabled(true);
                    if (Repository.canCancelGame())
                        GameForm.cancelGameButton.setEnabled(true);
                    while (playerMode.equals(""))
                        Thread.sleep(1);
                    sentMessage = "playerMode " + playerMode + ";";
                    playerMode = "";
                    break;
                case "respondedPlayerMode":
                    Repository.resetCardsWon();
                    Main.gameForm.changeStatusPlayer(Integer.parseInt(listOfObjects.get(1)), listOfObjects.get(2));
                    break;
                case "increased1of2":
                    Main.gameForm.increase1of2();
                    break;
                case "playerRequest":
                    Main.gameForm.updateNextStatus(-1, "Status");
                    Repository.playerRequest = Integer.parseInt(listOfObjects.get(1));
                    if (Repository.playerRequest == Repository.chair)
                        Repository.isRequestPlayer = true;
                    Repository.the1of2 = Integer.parseInt(listOfObjects.get(2));
                    break;
                case "requestedDeclaration": {
                    int requestedChair = Integer.parseInt(listOfObjects.get(1));
                    Main.gameForm.updateNextStatus(requestedChair, "Declaration");
                    break;
                }
                case "requestDeclaration":
                    Main.gameForm.resetDeclarationSelection();
                    Main.gameForm.switchDeclarationSelection(canPopeAtFinish, canPagatAtFinish, canAllPopes, canTrull, true);
                    while (declaration == null)
                        Thread.sleep(1);
                    sentMessage = "declaration " + declaration.toSendableObject() + ";";
                    declaration = null;
                    Main.gameForm.switchDeclarationSelection(canPopeAtFinish, canPagatAtFinish, canAllPopes, canTrull, false);
                    break;
                case "respondedDeclaration":
                    Main.gameForm.changeDeclarationPlayer(Integer.parseInt(listOfObjects.get(1)), DataManipulationService.getName(listOfObjects.get(2)));
                    boolean popeAtFinish = DataManipulationService.stringToBool(listOfObjects.get(3));
                    boolean pagatAtFinish = DataManipulationService.stringToBool(listOfObjects.get(4));
                    boolean allPopes = DataManipulationService.stringToBool(listOfObjects.get(5));
                    boolean trull = DataManipulationService.stringToBool(listOfObjects.get(6));
                    if (Integer.parseInt(listOfObjects.get(1)) == Repository.playerRequest) {
                        Repository.chosenPope = Integer.parseInt(listOfObjects.get(8));
                    }
                    if (popeAtFinish)
                        canPopeAtFinish = false;
                    if (pagatAtFinish)
                        canPagatAtFinish = false;
                    if (allPopes)
                        canAllPopes = false;
                    if (trull)
                        canTrull = false;
                    break;
                case "team1":
                    for (int j = 1; j < listOfObjects.size(); j++) {
                        Repository.teams.get(0).add(Integer.parseInt(listOfObjects.get(j)));
                    }
                    break;
                case "team2":
                    for (int j = 1; j < listOfObjects.size(); j++) {
                        Repository.teams.get(1).add(Integer.parseInt(listOfObjects.get(j)));
                    }
                    Main.gameForm.showTeams();
                    break;
                case "clearDeclarationTurn":
                    Main.gameForm.updateNextStatus(-1, "Declaration");
                    break;
                case "talon":
                    for (int j = 0; j < 6; j++)
                        Repository.talon.set(j, Integer.parseInt(listOfObjects.get(j + 1)));
                    break;
                case "requestedTalonSelection":
                    Repository.talonPart = (Repository.the1of2 - 1) % 3;
                    Main.talonShowingForm.setVisible(true);
                    break;
                case "requestTalonSelection":
                    if (Repository.talonPart == -1)
                        Repository.talonPart = (Repository.the1of2 - 1) % 3;
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
                    break;
                case "respondedTalonSelection":
                    if (listOfObjects.get(1).equals("next")) {
                        Main.talonShowingForm.revealTalonPart();
                        Repository.talonPart = Repository.talonPart + 1;
                        Repository.talonPart = Repository.talonPart % 3;
                        Repository.the1of2++;
                    }
                    break;
                case "requestTalonReplacement":
                    while (talonGiveDecision.equals(""))
                        Thread.sleep(1);
                    sentMessage = "talonReplacement";
                    sentMessage += talonGiveDecision + ";";
                    talonGiveDecision = "";
                    break;
                case "closeTalonWindow":
                    if (listOfObjects.get(1).equals("show"))
                        Main.talonShowingForm.setVisible(false);
                    else if (listOfObjects.get(1).equals("select"))
                        Main.talonSelectionForm.setVisible(false);
                    break;
                case "requestPope":
                    Main.gameForm.switchPopeSelection(true);
                    while (pope == -1)
                        Thread.sleep(1);
                    sentMessage = "pope " + pope + ";";
                    pope = -1;
                    Main.gameForm.switchPopeSelection(false);
                    break;
                case "beginRound":
                    shouldCompleteRound = true;
                    Repository.round = round;
                    break;
                case "requestCard":
                    GameForm.canGiveCard = true;
                    while (cardGiven == -1)
                        Thread.sleep(1);
                    GameForm.canGiveCard = false;
                    sentMessage = "card " + cardGiven + ";";
                    cardGiven = -1;
                    break;
                case "respondedCard":
                    int player = Integer.parseInt(listOfObjects.get(1));
                    if (shouldCompleteRound) {
                        shouldCompleteRound = false;
                        Repository.rounds.add(new Round(player));
                        Main.gameForm.resetCards();
                    }
                    int cardID = Integer.parseInt(listOfObjects.get(2));

                    Repository.rounds.get(round).addCard(player, cardID);
                    Main.gameForm.updateCard(player, cardID);
                    break;
                case "roundFinished":
                    round++;
                    int playerThatWon = Repository.rounds.get(round - 1).getPlayerThatWon();
                    Main.gameForm.updatePreviousRoundWonByLabel(Repository.players.get(playerThatWon));
                    break;
                case "showScoreWindow":
                    Main.scoreForm.setVisible(true);
                    break;
                case "cardsTeam1":
                    for (int j = 1; j < listOfObjects.size(); j++) {
                        Repository.cardsWon.get(0).add(Integer.valueOf(listOfObjects.get(j)));
                    }
                    break;
                case "cardsTeam2":
                    for (int j = 1; j < listOfObjects.size(); j++) {
                        Repository.cardsWon.get(1).add(Integer.valueOf(listOfObjects.get(j)));
                    }

                    ScoreForm.previousTeam1Button.setEnabled(true);
                    ScoreForm.previousTeam2Button.setEnabled(true);

                    ScoreForm.nextTeam1Button.setEnabled(Repository.cardsWon.get(0).size() > 6);
                    ScoreForm.nextTeam2Button.setEnabled(Repository.cardsWon.get(1).size() > 6);

                    ScoreForm.updateCards();
                    break;
                case "score":
                    int score1 = Integer.parseInt(listOfObjects.get(1));
                    int score2 = Integer.parseInt(listOfObjects.get(2));
                    int score3 = Integer.parseInt(listOfObjects.get(3));
                    int score4 = Integer.parseInt(listOfObjects.get(4));
                    String declaration = DataManipulationService.getName(listOfObjects.get(5));
                    boolean isRadler = DataManipulationService.stringToBool(listOfObjects.get(6));
                    Main.gameForm.addToScoreTable(score1, score2, score3, score4, declaration);
                    if (isRadler) {
                        GameForm.scoreTable.setRowColor(GameForm.scoreTable.getRowCount() - 1, Color.RED);
                    }
                    break;
                case "scoreDetailed":
                    List<String> declaredOrDone = new ArrayList<>();
                    List<Integer> points = new ArrayList<>();
                    for (int j = 1; j < 13; j++) {
                        declaredOrDone.add(DataManipulationService.getName(listOfObjects.get(j)));
                        points.add(Integer.parseInt(listOfObjects.get(j + 12)));
                    }
                    String teamThatWon = DataManipulationService.getName(listOfObjects.get(25));
                    ScoreForm.updateTeamThatWon(teamThatWon);
                    ScoreForm.updateTable(declaredOrDone, points);
                    break;
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
