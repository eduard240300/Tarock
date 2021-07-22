package com.Tarock.Client.Service;

import com.Tarock.Common.Domain.Declaration;
import com.Tarock.Common.Domain.Round;
import com.Tarock.Common.Domain.User;
import com.Tarock.Client.GUI.ConnectionForm.ConnectionForm;
import com.Tarock.Client.GUI.ConnectionForm.ControllerConnectionForm;
import com.Tarock.Client.GUI.GameForm.GameForm;
import com.Tarock.Client.GUI.Main;
import com.Tarock.Client.GUI.ScoreForm.ScoreForm;
import com.Tarock.Client.GUI.TalonSelectionForm.TalonSelectionForm;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Common.Service.DataManipulationService;

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

@SuppressWarnings({"BusyWait", "deprecation"})
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

    public void runHelper () throws IOException, ClassNotFoundException, InterruptedException {
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

    public String caseRequireAuth() throws InterruptedException {
        while (user == null)
            Thread.sleep(1);
        String sentMessage = "auth " + user.getUsername() + " " + user.getPassword() + " " + sessionID + ";";
        username = user.getUsername();
        user = null;
        return sentMessage;
    }
    public void caseFailedAuth(List<String> listOfObjects) {
        String errorMessage;
        if (listOfObjects.get(1).equals("sessionIDEnded"))
            errorMessage = "Session already ended !";
        else
            errorMessage = "Wrong " + listOfObjects.get(1) + " !";
        ConnectionForm.popUpMessage(errorMessage);
    }
    public void casePlayers(List<String> listOfObjects) {
        for (int j = 0; j < 4; j++)
            Repository.players.set(j, DataManipulationService.getName(listOfObjects.get(j + 1)));
        Main.gameForm.resetPlayerNames();
        ScoreForm.resetPlayerNames();
    }
    public void caseChairNumber(List<String> listOfObjects) {
        Repository.chair = Integer.parseInt(listOfObjects.get(1));
        Main.gameForm.updateChair();
    }
    public void caseBeginGame() {
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
    }
    public void caseCards(List<String> listOfObjects) {
        Repository.cards.clear();
        for (int j = 0; j < 12; j++)
            Repository.cards.add(Integer.parseInt(listOfObjects.get(j + 1)));
        Main.gameForm.updateCards();
    }
    public void caseRequestedPlayerModeOrCard(List<String> listOfObjects) {
        int requestedChair = Integer.parseInt(listOfObjects.get(1));
        Main.gameForm.updateNextStatus(requestedChair, "Status");
    }
    public String caseRequestPlayerMode() throws InterruptedException {
        GameForm.the1of2Button.setEnabled(true);
        GameForm.passButton.setEnabled(true);
        if (Repository.canCancelGame())
            GameForm.cancelGameButton.setEnabled(true);
        while (playerMode.equals(""))
            Thread.sleep(1);
        String sentMessage = "playerMode " + playerMode + ";";
        playerMode = "";
        return sentMessage;
    }
    public void caseRespondedPlayerMode(List<String> listOfObjects) {
        Repository.resetCardsWon();
        Main.gameForm.changeStatusPlayer(Integer.parseInt(listOfObjects.get(1)), listOfObjects.get(2));
    }
    public void casePlayerRequest(List<String> listOfObjects) {
        Main.gameForm.updateNextStatus(-1, "Status");
        Repository.playerRequest = Integer.parseInt(listOfObjects.get(1));
        if (Repository.playerRequest == Repository.chair)
            Repository.isRequestPlayer = true;
        Repository.the1of2 = Integer.parseInt(listOfObjects.get(2));
    }
    public void caseRequestedDeclaration(List<String> listOfObjects) {
        int requestedChair = Integer.parseInt(listOfObjects.get(1));
        Main.gameForm.updateNextStatus(requestedChair, "Declaration");
    }
    public String caseRequestDeclaration() throws InterruptedException {
        Main.gameForm.resetDeclarationSelection();
        Main.gameForm.switchDeclarationSelection(canPopeAtFinish, canPagatAtFinish, canAllPopes, canTrull, true);
        while (declaration == null)
            Thread.sleep(1);
        String sentMessage = "declaration " + declaration.toSendableObject() + ";";
        declaration = null;
        Main.gameForm.switchDeclarationSelection(canPopeAtFinish, canPagatAtFinish, canAllPopes, canTrull, false);
        return sentMessage;
    }
    public void caseRespondedDeclaration(List<String> listOfObjects) {
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
    }
    public void caseTeam(List<String> listOfObjects, int team) {
        for (int j = 1; j < listOfObjects.size(); j++) {
            Repository.teams.get(team).add(Integer.parseInt(listOfObjects.get(j)));
        }
    }
    public void caseTalon(List<String> listOfObjects) {
        for (int j = 0; j < 6; j++) {
            Repository.talon.set(j, Integer.parseInt(listOfObjects.get(j + 1)));
        }
    }
    public void caseRequestedTalonSelection() {
        Repository.talonPart = (Repository.the1of2 - 1) % 3;
        Main.talonShowingForm.setVisible(true);
    }
    public String caseRequestTalonSelection() throws IOException, InterruptedException {
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
        String sentMessage = "talonSelection ";
        sentMessage += talonTakeDecision + ";";
        talonTakeDecision = "";
        return sentMessage;
    }
    public void caseRespondedTalonSelection(List<String> listOfObjects) {
        if (listOfObjects.get(1).equals("next")) {
            Main.talonShowingForm.revealTalonPart();
            Repository.talonPart = Repository.talonPart + 1;
            Repository.talonPart = Repository.talonPart % 3;
            Repository.the1of2++;
        }
    }
    public String caseRequestTalonReplacement() throws InterruptedException {
        while (talonGiveDecision.equals(""))
            Thread.sleep(1);
        String sentMessage = "talonReplacement";
        sentMessage += talonGiveDecision + ";";
        talonGiveDecision = "";
        return sentMessage;
    }
    public void caseCloseTalonWindows(List<String> listOfObjects) {
        if (listOfObjects.get(1).equals("show"))
            Main.talonShowingForm.setVisible(false);
        else if (listOfObjects.get(1).equals("select"))
            Main.talonSelectionForm.setVisible(false);
    }
    public String caseRequestPope() throws InterruptedException {
        Main.gameForm.switchPopeSelection(true);
        while (pope == -1)
            Thread.sleep(1);
        String sentMessage = "pope " + pope + ";";
        pope = -1;
        Main.gameForm.switchPopeSelection(false);
        return sentMessage;
    }
    public void caseBeginRound() {
        shouldCompleteRound = true;
        Repository.round = round;
    }
    public String caseRequestCard() throws InterruptedException {
        GameForm.canGiveCard = true;
        while (cardGiven == -1)
            Thread.sleep(1);
        GameForm.canGiveCard = false;
        String sentMessage = "card " + cardGiven + ";";
        cardGiven = -1;
        return sentMessage;
    }
    public void caseRespondedCard(List<String> listOfObjects) {
        int player = Integer.parseInt(listOfObjects.get(1));
        if (shouldCompleteRound) {
            shouldCompleteRound = false;
            Repository.rounds.add(new Round(player));
            Main.gameForm.resetCards();
        }
        int cardID = Integer.parseInt(listOfObjects.get(2));

        Repository.rounds.get(round).addCard(player, cardID);
        Main.gameForm.updateCard(player, cardID);
    }
    public void caseRoundFinished() {
        round++;
        int playerThatWon = Repository.rounds.get(round - 1).getPlayerThatWon();
        Main.gameForm.updatePreviousRoundWonByLabel(Repository.players.get(playerThatWon));
    }
    public void caseCardsTeam(List<String> listOfObjects, int team) {
        for (int j = 1; j < listOfObjects.size(); j++) {
            Repository.cardsWon.get(team).add(Integer.valueOf(listOfObjects.get(j)));
        }
    }
    public void triggerCardsTeam() throws IOException {
        ScoreForm.previousTeam1Button.setEnabled(false);
        ScoreForm.previousTeam2Button.setEnabled(false);

        ScoreForm.offset1 = 0;
        ScoreForm.offset2 = 0;

        ScoreForm.nextTeam1Button.setEnabled(Repository.cardsWon.get(0).size() > 6);
        ScoreForm.nextTeam2Button.setEnabled(Repository.cardsWon.get(1).size() > 6);

        ScoreForm.updateCards();
    }
    public void caseScore(List<String> listOfObjects) {
        int score1 = Integer.parseInt(listOfObjects.get(1));
        int score2 = Integer.parseInt(listOfObjects.get(2));
        int score3 = Integer.parseInt(listOfObjects.get(3));
        int score4 = Integer.parseInt(listOfObjects.get(4));
        String declaration = DataManipulationService.getName(listOfObjects.get(5));
        boolean isRadler = DataManipulationService.stringToBool(listOfObjects.get(6));
        Main.gameForm.addToScoreTable(score1, score2, score3, score4, declaration);
        if (isRadler) {
            GameForm.scoreTable.setRowColor(GameForm.scoreTable.getRowCount() - 1, new Color(168, 216, 231));
        }
    }
    public void caseScoreDetailed(List<String> listOfObjects) {
        List<String> declaredOrDone = new ArrayList<>();
        List<Integer> points = new ArrayList<>();
        for (int j = 1; j < 13; j++) {
            declaredOrDone.add(DataManipulationService.getName(listOfObjects.get(j)));
            points.add(Integer.parseInt(listOfObjects.get(j + 12)));
        }
        String teamThatWon = DataManipulationService.getName(listOfObjects.get(25));
        ScoreForm.updateTeamThatWon(teamThatWon);
        ScoreForm.updateTable(declaredOrDone, points);
    }

    public void read() throws IOException, ClassNotFoundException, InterruptedException {
        String message = (String) inputStream.readObject();
        String sentMessage = "ok;";
        System.out.println(username + " Received : " + message);
        List<String> listOfCommands = DataManipulationService.processMessage(message);
        for (String listOfCommand : listOfCommands) {
            List<String> listOfObjects = DataManipulationService.processCommand(listOfCommand);
            switch (listOfObjects.get(0)) {
                case "require_auth":
                    sentMessage = caseRequireAuth();
                    break;
                case "failed_auth":
                    caseFailedAuth(listOfObjects);
                    break;
                case "successful_auth":
                    ControllerConnectionForm.finishLogin();
                    break;
                case "players":
                    casePlayers(listOfObjects);
                    break;
                case "chairNumber":
                    caseChairNumber(listOfObjects);
                    break;
                case "beginGame":
                    caseBeginGame();
                    break;
                case "cards":
                    caseCards(listOfObjects);
                    break;
                case "requestedPlayerMode":
                case "requestedCard":
                    caseRequestedPlayerModeOrCard(listOfObjects);
                    break;
                case "requestPlayerMode":
                    sentMessage = caseRequestPlayerMode();
                    break;
                case "respondedPlayerMode":
                    caseRespondedPlayerMode(listOfObjects);
                    break;
                case "increased1of2":
                    Main.gameForm.increase1of2();
                    break;
                case "playerRequest":
                    casePlayerRequest(listOfObjects);
                    break;
                case "requestedDeclaration":
                    caseRequestedDeclaration(listOfObjects);
                    break;
                case "requestDeclaration":
                    sentMessage = caseRequestDeclaration();
                    break;
                case "respondedDeclaration":
                    caseRespondedDeclaration(listOfObjects);
                    break;
                case "team1":
                    caseTeam(listOfObjects, 0);
                    break;
                case "team2":
                    caseTeam(listOfObjects, 1);
                    Main.gameForm.showTeams();
                    break;
                case "clearDeclarationTurn":
                    Main.gameForm.updateNextStatus(-1, "Declaration");
                    break;
                case "talon":
                    caseTalon(listOfObjects);
                    break;
                case "requestedTalonSelection":
                    caseRequestedTalonSelection();
                    break;
                case "requestTalonSelection":
                    sentMessage = caseRequestTalonSelection();
                    break;
                case "respondedTalonSelection":
                    caseRespondedTalonSelection(listOfObjects);
                    break;
                case "requestTalonReplacement":
                    sentMessage = caseRequestTalonReplacement();
                    break;
                case "closeTalonWindow":
                    caseCloseTalonWindows(listOfObjects);
                    break;
                case "requestPope":
                    sentMessage = caseRequestPope();
                    break;
                case "beginRound":
                    caseBeginRound();
                    break;
                case "requestCard":
                    sentMessage = caseRequestCard();
                    break;
                case "respondedCard":
                    caseRespondedCard(listOfObjects);
                    break;
                case "roundFinished":
                    caseRoundFinished();
                    break;
                case "showScoreWindow":
                    Main.scoreForm.setVisible(true);
                    break;
                case "cardsTeam1":
                    caseCardsTeam(listOfObjects, 0);
                    break;
                case "cardsTeam2":
                    caseCardsTeam(listOfObjects, 1);
                    triggerCardsTeam();
                    break;
                case "score":
                    caseScore(listOfObjects);
                    break;
                case "scoreDetailed":
                    caseScoreDetailed(listOfObjects);
                    break;
                default:
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
