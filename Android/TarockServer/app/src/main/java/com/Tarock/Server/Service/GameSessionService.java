package com.Tarock.Server.Service;

import com.Tarock.Common.Domain.*;
import com.Tarock.Server.ConnectionManager.PHPConnection;
import com.Tarock.Common.Service.DataManipulationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSessionService extends Thread {
    public List<ClientService> listOfClients;
    public Session session;
    public List<String> players;
    public int offsetGameNumber = -1;
    public int gameNumber = -1;
    public List<Integer> pope;
    public List<List<Integer>> cards;
    public List<List<List<Integer>>> teams;
    public List<List<List<Integer>>> cardsPlayers;
    public List<List<Integer>> talon;
    public List<List<List<Integer>>> cardsWon;
    public List<List<Round>> rounds;
    public List<List<String>> playersStatus;
    public List<List<Declaration>> declarations;
    public boolean showTeamsAfterTalonSelection = false;
    public boolean popeInTalon = false;
    public int radlerTimes = 0;
    public List<Integer> talonPart; //the part of talon that is chosen
    public List<Integer> playerRequest; //player that requested the 1of2
    public List<Integer> the1of2;
    public List<Game> games;
    public List<Score> scores;
    public boolean neverInitializedGames = true;

    public GameSessionService(List<ClientService> listOfClients, Session session, List<String> players) {
        this.listOfClients = listOfClients;
        this.session = session;
        this.players = players;
        playersStatus = new ArrayList<>();
        cards = new ArrayList<>();
        cardsPlayers = new ArrayList<>();
        talon = new ArrayList<>();
        declarations = new ArrayList<>();
        teams = new ArrayList<>();
        rounds = new ArrayList<>();
        cardsWon = new ArrayList<>();
        pope = new ArrayList<>();
        talonPart = new ArrayList<>();
        playerRequest = new ArrayList<>();
        the1of2 = new ArrayList<>();
        scores = new ArrayList<>();
    }

    public List<Integer> Cards() {
        return cards.get(gameNumber);
    }
    
    public List<List<Integer>> CardsWon() {
        return cardsWon.get(gameNumber);
    }

    public List<List<Integer>> CardsPlayers() {
        return cardsPlayers.get(gameNumber);
    }

    public Integer PlayerRequest() {
        return playerRequest.get(gameNumber);
    }

    public List<String> PlayerStatus() {
        return playersStatus.get(gameNumber);
    }

    public List<List<Integer>> Teams() {
        return teams.get(gameNumber);
    }

    public Integer Pope() {
        return pope.get(gameNumber);
    }

    public List<Integer> Talon() {
        return talon.get(gameNumber);
    }

    public Integer TalonPart() {
        return talonPart.get(gameNumber);
    }

    public Integer The1of2() {
        return the1of2.get(gameNumber);
    }
    
    public List<Declaration> Declarations() {
        return declarations.get(gameNumber);
    }
    
    public List<Round> Rounds(){
        return rounds.get(gameNumber);
    }

    public void sendAllPlayersWithoutPlayerRequest(String message) throws IOException, ClassNotFoundException {
        for (int i = 0; i < 4; i++) {
            if (i != PlayerRequest()) {
                listOfClients.get(i).write(message);
            }
        }
    }

    public void sendAllPlayers(String message) throws IOException, ClassNotFoundException {
        for (int i = 0; i < 4; i++) {
            listOfClients.get(i).write(message);
        }
    }

    public List<String> sendPlayer(int player, String message) throws IOException, ClassNotFoundException {
        return listOfClients.get(player).write(message);
    }

    public List<String> getListOfObjectsFromPlayer(int player, String message) throws IOException, ClassNotFoundException {
        List<String> listOfCommands = sendPlayer(player, message);
        return DataManipulationService.processCommand(listOfCommands.get(0));
    }

    public void getAllPreviousGames() throws IOException, ClassNotFoundException {
        String creator = session.getCreator();
        games = PHPConnection.getGames(creator, session.getSessionID());
        offsetGameNumber = games.size();
        for (Game game : games) {
            sendAllPlayers(DataManipulationService.getScore(game));
        }
        if (games.size() > 0)
            radlerTimes = games.get(games.size() - 1).getRadlerTimes();
    }

    public int getNumberOf1of2Sent() {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            if (PlayerStatus().get(i).equals("1/2"))
                result++;
        }
        return result;
    }

    public void computeTeams() {
        Teams().get(0).add(PlayerRequest());
        
        int playerThatHasPope = -1;
        for (int i = 0; i < 4; i++) {
            for (Integer card : CardsPlayers().get(i)) {
                if (card == 30 + Pope() * 8) {
                    playerThatHasPope = i;
                    break;
                }
            }
        }

        if (playerThatHasPope != -1) Teams().get(0).add(playerThatHasPope);

        for(int i=0;i<=3;i++) {
            if (i != PlayerRequest() && i != playerThatHasPope){
                Teams().get(1).add(i);
            }
        }
    }

    public void sendTeams() throws IOException, ClassNotFoundException {
        String message1 = DataManipulationService.getListOfInteger(Teams().get(0), "team1");
        String message2 = DataManipulationService.getListOfInteger(Teams().get(1), "team2");
        sendAllPlayers(message1 + message2);
    }

    public void talonSelectionRespondedNext() {
        for (int i = 0; i < 2; i++) {
            if (Talon().get(TalonPart() * 2 + i) == 30 + Pope() * 8) {
                showTeamsAfterTalonSelection = true;
                break;
            }
        }
        talonPart.set(gameNumber, TalonPart() + 1);
        the1of2.set(gameNumber, The1of2() + 1);
        talonPart.set(gameNumber, TalonPart() % 3);
    }

    public void attemptTalonSelection() throws IOException, ClassNotFoundException {
        boolean talonSelected = false;
        List<String> listOfObjects;

        listOfObjects = getListOfObjectsFromPlayer(PlayerRequest(), "requestTalonSelection;");
        if (listOfObjects.get(0).equals("talonSelection")) {
            if (listOfObjects.get(1).equals("next")) {
                talonSelectionRespondedNext();
            } else if (listOfObjects.get(1).equals("take")) {
                talonSelected = true;
            }
            sendAllPlayersWithoutPlayerRequest("respondedTalonSelection " + listOfObjects.get(1) + ";");
        }

        if (!talonSelected)
            attemptTalonSelection();
    }

    public void replaceCardsPlayerRequest(int card1, int card2){
        CardsWon().get(0).add(card1);
        CardsWon().get(0).add(card2);

        for (int i = 0; i < 2; i++) {
            CardsPlayers().get(PlayerRequest()).add(Talon().get(TalonPart() * 2 + i));
        }

        CardsPlayers().get(PlayerRequest()).remove((Object) card1);
        CardsPlayers().get(PlayerRequest()).remove((Object) card2);
    }

    public void addRestOfTalonToCardsWonSecondTeam() {
        for (int i = 0; i < Talon().size(); i++) {
            if ((i != TalonPart() * 2) && (i != TalonPart() * 2 + 1)) {
                if (Talon().get(i) == 30 + Pope() * 8) {
                    popeInTalon = true;
                }
                CardsWon().get(1).add(Talon().get(i));
            }
        }
    }

    public void talonSelection() throws IOException, ClassNotFoundException {
        List<String> listOfObjects;

        sendAllPlayers(DataManipulationService.getListOfInteger(Talon(), "talon"));
        sendAllPlayersWithoutPlayerRequest("requestedTalonSelection " + PlayerRequest() + ";");
        attemptTalonSelection();

        listOfObjects = getListOfObjectsFromPlayer(PlayerRequest(), "requestTalonReplacement;");

        if (listOfObjects.get(0).equals("talonReplacement")) {
            int card1 = Integer.parseInt(listOfObjects.get(1));
            int card2 = Integer.parseInt(listOfObjects.get(2));
            replaceCardsPlayerRequest(card1, card2);
            addRestOfTalonToCardsWonSecondTeam();

            Collections.sort(CardsPlayers().get(PlayerRequest()));
            computeTeams();

            if (showTeamsAfterTalonSelection)
                sendTeams();

            String message = DataManipulationService.getListOfInteger(CardsPlayers().get(PlayerRequest()), "cards");
            sendPlayer(PlayerRequest(), message);
        }

        sendAllPlayersWithoutPlayerRequest("closeTalonWindow show;");
        sendPlayer(PlayerRequest(), "closeTalonWindow select;");
    }

    public void requestPope() throws IOException, ClassNotFoundException {
        List<String> listOfObjects;
        sendAllPlayers("requestedDeclaration " + PlayerRequest() + ";");
        listOfObjects = getListOfObjectsFromPlayer(PlayerRequest(), "requestPope;");

        if (listOfObjects.get(0).equals("pope")) {
            int pope = Integer.parseInt(listOfObjects.get(1));
            Declaration declaration = new Declaration(false, false, false, false, 0);
            declaration.setPope(pope);
            declaration.setThe1of2(The1of2());
            Declarations().set(PlayerRequest(), declaration);
        }

        sendAllPlayers(DataManipulationService.getRespondedDeclaration(PlayerRequest(), Declarations()));
        sendAllPlayers("clearDeclarationTurn;");
    }

    public void requestDeclaration(int player) throws IOException, ClassNotFoundException {
        List<String> listOfObjects;
        sendAllPlayers("requestedDeclaration " + player + ";");
        listOfObjects = getListOfObjectsFromPlayer(player, "requestDeclaration;");

        if (listOfObjects.get(0).equals("declaration")) {
            Declaration declaration;
            boolean popeAtFinish = DataManipulationService.stringToBool(listOfObjects.get(1));
            if (popeAtFinish) {
                sendTeams();
            }
            boolean pagatAtFinish = DataManipulationService.stringToBool(listOfObjects.get(2));
            boolean allPopes = DataManipulationService.stringToBool(listOfObjects.get(3));
            boolean trull = DataManipulationService.stringToBool(listOfObjects.get(4));
            int numberOfTarocks = Integer.parseInt(listOfObjects.get(5));
            declaration = new Declaration(popeAtFinish, pagatAtFinish, allPopes, trull, numberOfTarocks);
            if (player == PlayerRequest()) {
                int pope = Declarations().get(PlayerRequest()).getPope();
                declaration.setThe1of2(The1of2());
                declaration.setPope(pope);
            }
            Declarations().set(player, declaration);
        }

        sendAllPlayers(DataManipulationService.getRespondedDeclaration(player, Declarations()));
    }

    public void playerModeCancelOrRadler() throws IOException, ClassNotFoundException {
        playersStatus.remove(gameNumber);
        cards.remove(gameNumber);
        cardsPlayers.remove(gameNumber);
        declarations.remove(gameNumber);
        teams.remove(gameNumber);
        rounds.remove(gameNumber);
        cardsWon.remove(gameNumber);
        pope.remove(gameNumber);
        talonPart.remove(gameNumber);
        playerRequest.remove(gameNumber);
        the1of2.remove(gameNumber);
        gameNumber--;

        runHelper();
    }

    public void requestCard(int player, Round round) throws IOException, ClassNotFoundException {
        List<String> listOfObjects;
        sendAllPlayers("requestedCard " + player + ";");
        listOfObjects = getListOfObjectsFromPlayer(player, "requestCard;");

        int cardGiven = -1;
        if (listOfObjects.get(0).equals("card")) {
            cardGiven = Integer.parseInt(listOfObjects.get(1));
            round.addCard(player, cardGiven);
        }

        sendAllPlayers("respondedCard " + player + " " + cardGiven + ";");

        if (cardGiven == 30 + Pope() * 8)
            sendTeams();
    }

    public void requestPlayerMode(int player) throws IOException, ClassNotFoundException {
        List<String> listOfObjects;
        sendAllPlayers("requestedPlayerMode " + player + ";");
        listOfObjects = getListOfObjectsFromPlayer(player, "requestPlayerMode;");

        if (listOfObjects.get(1).equals("cancel")) {
            playerModeCancelOrRadler();
        }

        sendAllPlayers("respondedPlayerMode " + player + " " + listOfObjects.get(1) + ";");
        if (listOfObjects.get(0).equals("playerMode")) {
            PlayerStatus().set(player, listOfObjects.get(1));
            if (listOfObjects.get(1).equals("1/2")) {
                the1of2.set(gameNumber, The1of2() + 1);
                sendAllPlayers("increased1of2;");
            }
        }
    }

    public void doRound(int currentRound) throws IOException, ClassNotFoundException {
        int firstRequest;
        if (currentRound == 0)
            firstRequest = (gameNumber + offsetGameNumber) % 4;
        else
            firstRequest = Rounds().get(currentRound - 1).getPlayerThatWon();

        sendAllPlayers("beginRound;");

        Round round = new Round(firstRequest);
        for (int i = firstRequest; i < firstRequest + 4; i++) {
            requestCard(i % 4, round);
        }
        Rounds().add(round);

        int playerThatWon = round.getPlayerThatWon();
        int teamThatWon = 1;

        for (Integer player : Teams().get(0)) {
            if (playerThatWon == player) {
                teamThatWon = 0;
                break;
            }
        }

        for (int i = 0; i < 4; i++) {
            CardsWon().get(teamThatWon).add(round.getCard(i));
        }

        sendAllPlayers("roundFinished;");
    }

    public void doRounds() throws IOException, ClassNotFoundException {
        for (int i = 0; i < 12; i++) {
            doRound(i);
        }

        sendAllPlayers("showScoreWindow;");

        Collections.sort(CardsWon().get(0));
        Collections.sort(CardsWon().get(1));

        String message1 = DataManipulationService.getListOfInteger(CardsWon().get(0), "cardsTeam1");
        String message2 = DataManipulationService.getListOfInteger(CardsWon().get(1), "cardsTeam2");
        sendAllPlayers(message1 + message2);
    }

    public void computeScore() throws IOException, ClassNotFoundException {
        boolean isRadler = false;
        if (radlerTimes > 0) {
            isRadler = true;
            radlerTimes--;
        }

        ScoreComputationArguments scoreComputationArguments = new ScoreComputationArguments(PlayerRequest(), players,
                Teams(), Rounds(), Declarations(),
                CardsWon(), popeInTalon, isRadler);
        Pair<Game, Score> pair = ScoreService.getScore(scoreComputationArguments);

        Game game = pair.getKey();
        game.setRadlerTimes(radlerTimes);
        Score score = pair.getValue();

        if (games.size() > 0) {
            for (int i = 0; i < 4; i++) {
                int previousScore = games.get(games.size() - 1).getScorePlayer(i);
                int totalScore = previousScore + game.getScorePlayer(i);
                game.setScorePlayer(i, totalScore);
            }
        }

        game.setSessionID(session.getSessionID());
        games.add(game);

        String creator = session.getCreator();
        PHPConnection.addGame(creator, game);
        scores.add(score);

        sendAllPlayers(DataManipulationService.getScore(game));
        sendAllPlayers(DataManipulationService.getScoreDetailed(score));
        runHelper();
    }

    public void initialization() throws IOException, ClassNotFoundException {
        gameNumber++;
        showTeamsAfterTalonSelection = false;
        popeInTalon = false;
        playersStatus.add(new ArrayList<>());
        cardsPlayers.add(new ArrayList<>());
        talon.add(new ArrayList<>());
        declarations.add(new ArrayList<>());
        teams.add(new ArrayList<>());
        rounds.add(new ArrayList<>());
        cardsWon.add(new ArrayList<>());
        pope.add(-1);
        talonPart.add(-1);
        playerRequest.add(-1);
        the1of2.add(1);

        for (int i = 0; i < 2; i++) {
            Teams().add(new ArrayList<>());
            CardsWon().add(new ArrayList<>());
        }

        for (int i = 0; i < 4; i++) {
            PlayerStatus().add("");
            Declarations().add(null);
            CardsPlayers().add(new ArrayList<>());
        }

        cards.add(new ArrayList<>());
        for(int i=1;i<=54;i++){
            cards.get(cards.size() - 1).add(i);
        }
        Collections.shuffle(Cards());

        int currentPlayer = 0;
        for (List<Integer> cardsPlayer : CardsPlayers()) {
            for (int i = 0; i < 12; i++)
                cardsPlayer.add(Cards().get(currentPlayer * 12 + i));
            currentPlayer++;
        }

        for (int j = 0; j < 6; j++) {
            Talon().add(Cards().get(48 + j));
        }
        for (List<Integer> cardsPlayer : CardsPlayers())
            Collections.sort(cardsPlayer);

        if (neverInitializedGames) {
            getAllPreviousGames();
            neverInitializedGames = false;
        }
    }

    public void runHelper() throws IOException, ClassNotFoundException {
        initialization();

        int firstRequest = (gameNumber + offsetGameNumber) % 4;

        sendAllPlayers("beginGame;");

        for (int i = 0; i < 4; i++) {
            sendPlayer(i, DataManipulationService.getListOfInteger(CardsPlayers().get(i), "cards"));
        }

        for (int i = firstRequest; i < firstRequest + 4; i++)
            requestPlayerMode(i % 4);
        if (getNumberOf1of2Sent() == 0) {
            radlerTimes = 4;
            playerModeCancelOrRadler();
        }

        while (getNumberOf1of2Sent() > 1) {
            for (int i = firstRequest; i < firstRequest + 4; i++) {
                if (PlayerStatus().get(i % 4).equals("1/2"))
                    requestPlayerMode(i % 4);
                if (getNumberOf1of2Sent() == 1) i = firstRequest + 4;
            }
        }

        for (int i = 0; i < 4; i++)
            if (PlayerStatus().get(i).equals("1/2")) playerRequest.set(gameNumber, i);

        the1of2.set(gameNumber, The1of2() - 1);
        talonPart.set(gameNumber, (The1of2() - 1) % 3);

        sendAllPlayers("playerRequest " + PlayerRequest() + " " + The1of2() + ";");

        requestPope();
        pope.set(gameNumber, Declarations().get(PlayerRequest()).getPope());
        talonSelection();

        firstRequest = PlayerRequest() % 4;
        for (int i = firstRequest; i < firstRequest + 4; i++) {
            requestDeclaration(i % 4);
        }

        sendAllPlayers("clearDeclarationTurn;");
        Declarations().get(PlayerRequest()).setThe1of2(The1of2());
        sendAllPlayers(DataManipulationService.getRespondedDeclaration(PlayerRequest(), Declarations()));

        doRounds();
        computeScore();
    }

    public void run() {
        try {
            runHelper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
