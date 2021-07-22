package com.Tarock.Server.Service;

import com.Tarock.Server.ConnectionManager.PHPConnection;
import com.Tarock.Common.Domain.Round;
import com.Tarock.Common.Domain.Session;
import com.Tarock.Common.Domain.Declaration;
import com.Tarock.Common.Domain.Game;
import com.Tarock.Common.Domain.Score;
import com.Tarock.Common.Domain.Pair;
import com.Tarock.Common.Service.DataManipulationService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSessionService extends Thread{
    public List<ClientService> listOfClients;
    public Session session;
    public List<String> players;
    public int offsetGameNumber = -1;
    public int gameNumber = -1;
    public List<Integer> pope;
    public List<List<Integer>> cards;
    public List<List<List<Integer>>> teams;
    public List<List<List<Integer>>> cardsSeparated;
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

    public GameSessionService(List<ClientService> listOfClients, Session session, List<String> players){
        this.listOfClients = listOfClients;
        this.session = session;
        this.players = players;
        playersStatus = new ArrayList<>();
        cards = new ArrayList<>();
        cardsSeparated = new ArrayList<>();
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

    public void getAllPreviousGames() throws IOException, ClassNotFoundException {
        StringBuilder message;
        String creator = session.getCreator();
        games = PHPConnection.getGames(creator, session.getSessionID());
        offsetGameNumber = games.size();
        for (Game game : games) {
            for (int j = 0; j < 4; j++) {
                message = new StringBuilder("score");
                for (int k = 0; k < 4; k++) {
                    message.append(" ").append(game.getScorePlayer(k));
                }
                message.append(" ").append(DataManipulationService.processName(game.getDeclaration())).append(" ");
                message.append(DataManipulationService.boolToString(game.getRadler())).append(";");
                listOfClients.get(j).write(message.toString());
            }
        }
        if (games.size() > 0)
            radlerTimes = games.get(games.size()-1).getRadlerTimes();
    }

    public int numberOf1of2()
    {
        int result = 0;
        for(int i=0;i<4;i++)
        {
            if (playersStatus.get(gameNumber).get(i).equals("1/2"))
                result++;
        }
        return result;
    }

    public void computeTeams()
    {
        teams.get(gameNumber).get(0).add(playerRequest.get(gameNumber));
        int playerThatHasPope = -1;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<12;j++)
            {
                if (cardsSeparated.get(gameNumber).get(i).get(j) == 30 + pope.get(gameNumber) * 8) {
                    playerThatHasPope = i;
                    break;
                }
            }
        }
        if ((playerThatHasPope == -1) || (playerThatHasPope == playerRequest.get(gameNumber)))
        {
            for(int i=0;i<4;i++)
            {
                if (i != playerRequest.get(gameNumber))
                    teams.get(gameNumber).get(1).add(i);
            }
        }
        else{
            teams.get(gameNumber).get(0).add(playerThatHasPope);
            for(int i=0;i<4;i++)
            {
                if ((i != playerRequest.get(gameNumber)) && (i != playerThatHasPope))
                    teams.get(gameNumber).get(1).add(i);
            }
        }
    }

    public void sendTeams() throws IOException, ClassNotFoundException {
        StringBuilder message = new StringBuilder("team1");
        for(int j=0;j<teams.get(gameNumber).get(0).size();j++)
        {
            message.append(" ").append(teams.get(gameNumber).get(0).get(j));
        }
        message.append(";team2");
        for(int j=0;j<teams.get(gameNumber).get(1).size();j++)
        {
            message.append(" ").append(teams.get(gameNumber).get(1).get(j));
        }
        message.append(";");

        for(int i=0;i<4;i++)
        {
            listOfClients.get(i).write(message.toString());
        }
    }

    public void talonSelection() throws IOException, ClassNotFoundException {
        boolean talonSelected = false;
        List<String> listOfCommands;
        List<String> listOfObjects;
        StringBuilder message;
        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("talon");
            for(int j=0;j<6;j++)
            {
                message.append(" ").append(cardsSeparated.get(gameNumber).get(4).get(j));
            }
            message.append(";");
            listOfClients.get(i).write(message.toString());
        }
        for(int i=0;i<4;i++)
        {
            if (i != playerRequest.get(gameNumber)) {
                message = new StringBuilder("requestedTalonSelection " + playerRequest.get(gameNumber) + ";");
                listOfClients.get(i).write(message.toString());
            }
        }
        while(!talonSelected)
        {
            message = new StringBuilder("requestTalonSelection;");
            listOfCommands = listOfClients.get(playerRequest.get(gameNumber)).write(message.toString());
            listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
            if (listOfObjects.get(0).equals("talonSelection"))
            {
                if (listOfObjects.get(1).equals("next")) {
                    for(int k=0;k<2;k++)
                    {
                        if (cardsSeparated.get(gameNumber).get(4).get(talonPart.get(gameNumber) * 2 + k) == 30 + pope.get(gameNumber) * 8) {
                            showTeamsAfterTalonSelection = true;
                            break;
                        }
                    }
                    talonPart.set(gameNumber, talonPart.get(gameNumber) + 1);
                    the1of2.set(gameNumber, the1of2.get(gameNumber) + 1);
                    talonPart.set(gameNumber, talonPart.get(gameNumber) % 3);
                }
                else if (listOfObjects.get(1).equals("take"))
                {
                    talonSelected = true;
                }
                for(int i=0;i<4;i++)
                {
                    if (i != playerRequest.get(gameNumber))
                    {
                        message = new StringBuilder("respondedTalonSelection " + listOfObjects.get(1) + ";");
                        listOfClients.get(i).write(message.toString());
                    }
                }
            }
        }
        message = new StringBuilder("requestTalonReplacement;");
        listOfCommands = listOfClients.get(playerRequest.get(gameNumber)).write(message.toString());
        listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
        if (listOfObjects.get(0).equals("talonReplacement"))
        {
            int card1 = Integer.parseInt(listOfObjects.get(1));
            int card2 = Integer.parseInt(listOfObjects.get(2));
            cardsWon.get(gameNumber).get(0).add(card1);
            cardsWon.get(gameNumber).get(0).add(card2);
            for(int i=0;i<6;i++)
            {
                if ((i != talonPart.get(gameNumber)*2) && (i != talonPart.get(gameNumber)*2 + 1)) {
                    if (cardsSeparated.get(gameNumber).get(4).get(i) == 30+pope.get(gameNumber)*8)
                    {
                        popeInTalon = true;
                    }
                    cardsWon.get(gameNumber).get(1).add(cardsSeparated.get(gameNumber).get(4).get(i));
                }
            }
            for(int i=0;i<2;i++)
            {
                cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)).add(cardsSeparated.get(gameNumber).get(4).get(talonPart.get(gameNumber)*2+i));
            }
            cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)).remove((Object) card1);
            cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)).remove((Object) card2);

            cardsSeparated.get(gameNumber).remove(4);
            Collections.sort(cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)));
            computeTeams();
            if (showTeamsAfterTalonSelection)
                sendTeams();
            message = new StringBuilder("cards");
            for(int j=0;j<12;j++)
                message.append(" ").append(cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)).get(j));
            message.append(";");
            listOfClients.get(playerRequest.get(gameNumber)).write(message.toString());
        }
        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("closeTalonWindow");
            if (i == playerRequest.get(gameNumber))
                message.append(" select;");
            else
                message.append(" show;");
            listOfClients.get(i).write(message.toString());
        }
    }

    public void requestPope() throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        List<String> listOfObjects;
        String message;
        for(int j=0;j<4;j++)
        {
            message = "requestedDeclaration " + playerRequest.get(gameNumber) + ";";
            listOfClients.get(j).write(message);
        }
        message = "requestPope;";
        listOfCommands = listOfClients.get(playerRequest.get(gameNumber)).write(message);
        listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
        if (listOfObjects.get(0).equals("pope"))
        {
            int pope = Integer.parseInt(listOfObjects.get(1));
            Declaration declaration = new Declaration(false, false, false, false, 0);
            declaration.setPope(pope);
            declaration.setThe1of2(the1of2.get(gameNumber));
            declarations.get(gameNumber).set(playerRequest.get(gameNumber), declaration);
        }
        for(int j=0;j<4;j++)
        {
            message = "respondedDeclaration " +
                    playerRequest.get(gameNumber) + " " +
                    DataManipulationService.processName(declarations.get(gameNumber)
                            .get(playerRequest.get(gameNumber)).toString()) +
                    " " + declarations.get(gameNumber).get(playerRequest.get(gameNumber))
                    .toSendableObject() + ";";
            listOfClients.get(j).write(message);
        }
        for(int i=0;i<4;i++)
        {
            message = "clearDeclarationTurn;";
            listOfClients.get(i).write(message);
        }
    }

    public void requestDeclaration(int player) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        List<String> listOfObjects;
        String message;
        for(int j=0;j<4;j++)
        {
            message = "requestedDeclaration " + player + ";";
            listOfClients.get(j).write(message);
        }
        message = "requestDeclaration;";
        listOfCommands = listOfClients.get(player).write(message);
        listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
        if (listOfObjects.get(0).equals("declaration"))
        {
            Declaration declaration;
            boolean popeAtFinish = DataManipulationService.stringToBool(listOfObjects.get(1));
            if (popeAtFinish)
            {
                sendTeams();
            }
            boolean pagatAtFinish = DataManipulationService.stringToBool(listOfObjects.get(2));
            boolean allPopes = DataManipulationService.stringToBool(listOfObjects.get(3));
            boolean trull = DataManipulationService.stringToBool(listOfObjects.get(4));
            int numberOfTarocks = Integer.parseInt(listOfObjects.get(5));
            declaration = new Declaration(popeAtFinish, pagatAtFinish, allPopes, trull, numberOfTarocks);
            if (player == playerRequest.get(gameNumber))
            {
                int pope = declarations.get(gameNumber).get(playerRequest.get(gameNumber)).getPope();
                declaration.setThe1of2(the1of2.get(gameNumber));
                declaration.setPope(pope);
            }
            declarations.get(gameNumber).set(player, declaration);
        }
        for(int j=0;j<4;j++)
        {
            message = "respondedDeclaration " + player + " " +
                    DataManipulationService.processName(declarations.get(gameNumber)
                            .get(player).toString()) + " " +
                    declarations.get(gameNumber).get(player).toSendableObject() + ";";
            listOfClients.get(j).write(message);
        }
    }

    public void requestPlayerMode(int player) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        List<String> listOfObjects;
        String message;
        for(int j=0;j<4;j++)
        {
            message = "requestedPlayerMode " + player + ";";
            listOfClients.get(j).write(message);
        }
        message = "requestPlayerMode;";
        listOfCommands = listOfClients.get(player).write(message);
        listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
        if (listOfObjects.get(1).equals("cancel"))
        {
            playersStatus.remove(gameNumber);
            cards.remove(gameNumber);
            cardsSeparated.remove(gameNumber);
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
        for(int j=0;j<4;j++)
        {
            message = "respondedPlayerMode " + player + " " + listOfObjects.get(1) + ";";
            listOfClients.get(j).write(message);
        }
        if (listOfObjects.get(0).equals("playerMode"))
        {
            playersStatus.get(gameNumber).set(player, listOfObjects.get(1));
            if (listOfObjects.get(1).equals("1/2"))
            {
                message = "increased1of2;";
                the1of2.set(gameNumber, the1of2.get(gameNumber) + 1);
                for(int j=0;j<4;j++)
                {
                    listOfClients.get(j).write(message);
                }
            }
        }
    }

    public void doHand(int hand) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        List<String> listOfObjects;
        int firstRequest;
        if (hand == 0)
            firstRequest = (gameNumber+offsetGameNumber)%4;
        else
            firstRequest = rounds.get(gameNumber).get(hand - 1).getPlayerThatWon();
        Round round = new Round(firstRequest);

        int lastRequest = firstRequest + 4;
        String message;

        for(int i=0;i<4;i++)
        {
            message = "beginRound;";
            listOfClients.get(i).write(message);
        }
        for(int i=firstRequest;i<lastRequest;i++)
        {
            for(int j=0;j<4;j++)
            {
                message = "requestedCard " + i%4 + ";";
                listOfClients.get(j).write(message);
            }
            message = "requestCard;";
            listOfCommands = listOfClients.get(i%4).write(message);
            listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
            int cardGiven = -1;
            if (listOfObjects.get(0).equals("card"))
            {
                cardGiven = Integer.parseInt(listOfObjects.get(1));
                round.addCard(i%4, cardGiven);
            }
            for(int j=0;j<4;j++)
            {
                message = "respondedCard ";
                message += (i%4) + " " + cardGiven + ";";
                listOfClients.get(j).write(message);
            }
            if (cardGiven == 30+pope.get(gameNumber)*8)
                sendTeams();
        }
        rounds.get(gameNumber).add(round);

        int playerThatWon = round.getPlayerThatWon();
        int team = 1;
        for (int i=0;i<teams.get(gameNumber).get(0).size();i++)
        {
            if (playerThatWon == teams.get(gameNumber).get(0).get(i)) {
                team = 0;
                break;
            }
        }
        for(int i=0;i<4;i++)
        {
            cardsWon.get(gameNumber).get(team).add(round.getCard(i));
        }

        for(int i=0;i<4;i++)
        {
            message = "roundFinished;";
            listOfClients.get(i).write(message);
        }
    }

    public void doRounds() throws IOException, ClassNotFoundException {
        StringBuilder message;
        for(int i=0;i<12;i++)
        {
            doHand(i);
        }
        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("showScoreWindow;");
            listOfClients.get(i).write(message.toString());
        }

        Collections.sort(cardsWon.get(gameNumber).get(0));
        Collections.sort(cardsWon.get(gameNumber).get(1));

        message = new StringBuilder("cardsTeam1");
        for(int j=0;j<cardsWon.get(gameNumber).get(0).size();j++)
            message.append(" ").append(cardsWon.get(gameNumber).get(0).get(j));
        message.append(";cardsTeam2");
        for(int j=0;j<cardsWon.get(gameNumber).get(1).size();j++)
            message.append(" ").append(cardsWon.get(gameNumber).get(1).get(j));
        message.append(";");
        for(int i=0;i<4;i++)
        {
            listOfClients.get(i).write(message.toString());
        }
        boolean isRadler = false;
        if (radlerTimes > 0) {
            isRadler = true;
            radlerTimes--;
        }
        Pair<Game, Score> pair = ScoreService.getScore(playerRequest.get(gameNumber), players,
                teams.get(gameNumber), rounds.get(gameNumber), declarations.get(gameNumber),
                cardsWon.get(gameNumber), popeInTalon, isRadler);
        Game game = pair.getKey();
        game.setRadlerTimes(radlerTimes);
        Score score = pair.getValue();
        if (games.size() > 0)
        {
            for(int k=0;k<4;k++)
            {
                int previousScore = games.get(games.size()-1).getScorePlayer(k);
                int totalScore = previousScore + game.getScorePlayer(k);
                game.setScorePlayer(k, totalScore);
            }
        }
        game.setSessionID(session.getSessionID());
        games.add(game);
        String creator = session.getCreator();
        PHPConnection.addGame(creator, game);
        scores.add(score);
        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("score");
            for(int j=0;j<4;j++)
            {
                message.append(" ").append(game.getScorePlayer(j));
            }
            message.append(" ").append(DataManipulationService.processName(game.getDeclaration())).append(" ");
            message.append(DataManipulationService.boolToString(isRadler)).append(";");
            listOfClients.get(i).write(message.toString());
        }
        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("scoreDetailed ");
            message.append(score.toString());
            message.append(" ").append(DataManipulationService.processName(score.getTeamThatWon()));
            message.append(";");
            listOfClients.get(i).write(message.toString());
        }
        runHelper();
    }

    public void runHelper() throws IOException, ClassNotFoundException {
        // initialization

        gameNumber++;
        showTeamsAfterTalonSelection = false;
        popeInTalon = false;
        playersStatus.add(new ArrayList<>());
        cards.add(new ArrayList<>());
        cardsSeparated.add(new ArrayList<>());
        declarations.add(new ArrayList<>());
        teams.add(new ArrayList<>());
        rounds.add(new ArrayList<>());
        cardsWon.add(new ArrayList<>());
        pope.add(-1);
        talonPart.add(-1);
        playerRequest.add(-1);
        the1of2.add(1);
        for(int i=0;i<2;i++) {
            teams.get(gameNumber).add(new ArrayList<>());
            cardsWon.get(gameNumber).add(new ArrayList<>());
        }
        for(int i=0;i<4;i++) {
            playersStatus.get(gameNumber).add("");
            declarations.get(gameNumber).add(null);
        }
        for(int i=1;i<=54;i++)
            cards.get(gameNumber).add(i);
        Collections.shuffle(cards.get(gameNumber));

        for(int i=0;i<5;i++)
            cardsSeparated.get(gameNumber).add(new ArrayList<>());
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<12;j++)
                cardsSeparated.get(gameNumber).get(i).add(cards.get(gameNumber).get(i*12+j));
        }
        for(int j=0;j<6;j++)
        {
            cardsSeparated.get(gameNumber).get(4).add(cards.get(gameNumber).get(48+j));
        }
        for(int i=0;i<4;i++)
            Collections.sort(cardsSeparated.get(gameNumber).get(i));

        if (neverInitializedGames) {
            getAllPreviousGames();
            neverInitializedGames = false;
        }

        // finished initialization
        StringBuilder message;
        int firstRequest = (gameNumber+offsetGameNumber)%4;
        int lastRequest = firstRequest + 4;

        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("beginGame;");
            listOfClients.get(i).write(message.toString());
        }
        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("cards");
            for(int j=0;j<12;j++)
                message.append(" ").append(cardsSeparated.get(gameNumber).get(i).get(j));
            message.append(";");
            listOfClients.get(i).write(message.toString());
        }
        for(int i=firstRequest;i<lastRequest;i++)
            requestPlayerMode(i%4);
        if (numberOf1of2() == 0)
        {
            radlerTimes = 4;
            playersStatus.remove(gameNumber);
            cards.remove(gameNumber);
            cardsSeparated.remove(gameNumber);
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
        while(numberOf1of2() > 1)
        {
            for(int i=firstRequest;i<lastRequest;i++)
            {
                if (playersStatus.get(gameNumber).get(i%4).equals("1/2"))
                    requestPlayerMode(i%4);
                if (numberOf1of2() == 1) i = lastRequest;
            }
        }
        for(int i=0;i<4;i++)
            if (playersStatus.get(gameNumber).get(i).equals("1/2")) playerRequest.set(gameNumber, i);
        the1of2.set(gameNumber, the1of2.get(gameNumber) - 1);
        talonPart.set(gameNumber, (the1of2.get(gameNumber) - 1) % 3);
        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("playerRequest " + playerRequest.get(gameNumber) + " " + the1of2.get(gameNumber) + ";");
            listOfClients.get(i).write(message.toString());
        }

        requestPope();

        int playerRequest1 = playerRequest.get(gameNumber);
        pope.set(gameNumber, declarations.get(gameNumber).get(playerRequest1).getPope());
        talonSelection();

        firstRequest = playerRequest.get(gameNumber)%4;
        lastRequest = firstRequest + 4;
        for(int i=firstRequest;i<lastRequest;i++)
        {
            requestDeclaration(i%4);
        }
        for(int i=0;i<4;i++)
        {
            message = new StringBuilder("clearDeclarationTurn;");
            listOfClients.get(i).write(message.toString());
        }

        declarations.get(gameNumber).get(playerRequest1).setThe1of2(the1of2.get(gameNumber));
        for(int j=0;j<4;j++)
        {
            message = new StringBuilder("respondedDeclaration " + playerRequest.get(gameNumber) +
                    " " + DataManipulationService.processName(declarations
                    .get(gameNumber).get(playerRequest.get(gameNumber)).toString()) +
                    " " + declarations.get(gameNumber).get(playerRequest.get(gameNumber))
                    .toSendableObject() + ";");
            listOfClients.get(j).write(message.toString());
        }
        doRounds();
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
