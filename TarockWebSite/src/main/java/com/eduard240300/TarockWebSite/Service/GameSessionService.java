package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.Domain.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSessionService extends Thread{
    public List<ClientService> listOfClients;
    public Session session;
    public List<String> players;
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
    public GameSessionService(List<ClientService> listOfClients, Session session, List<String> players){
        this.listOfClients = listOfClients;
        this.session = session;
        this.players = players;
        playersStatus = new ArrayList<List<String>>();
        cards = new ArrayList<List<Integer>>();
        cardsSeparated = new ArrayList<List<List<Integer>>>();
        declarations = new ArrayList<List<Declaration>>();
        teams = new ArrayList<List<List<Integer>>>();
        rounds = new ArrayList<List<Round>>();
        cardsWon = new ArrayList<List<List<Integer>>>();
        pope = new ArrayList<Integer>();
        talonPart = new ArrayList<Integer>();
        playerRequest = new ArrayList<Integer>();
        the1of2 = new ArrayList<Integer>();
    }

    public void log(String message, int player)
    {
        System.out.println("Sent (" + listOfClients.get(player).username + ") : " + message);
    }

    public void logReceived(Object message, int player)
    {
        System.out.println("Received (" + listOfClients.get(player).username + ") : " + message);
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
                if (cardsSeparated.get(gameNumber).get(i).get(j) == 30+pope.get(gameNumber)*8)
                    playerThatHasPope = i;
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
        String message = "team1";
        for(int j=0;j<teams.get(gameNumber).get(0).size();j++)
        {
            message += " " + String.valueOf(teams.get(gameNumber).get(0).get(j));
        }
        message += ";team2";
        for(int j=0;j<teams.get(gameNumber).get(1).size();j++)
        {
            message += " " + String.valueOf(teams.get(gameNumber).get(1).get(j));
        }
        message += ";";

        for(int i=0;i<4;i++)
        {
            listOfClients.get(i).write(message);
            log(message, i);
        }
    }

    public void talonSelection() throws IOException, ClassNotFoundException {
        boolean talonSelected = false;
        List<String> listOfCommands;
        List<String> listOfObjects;
        String message;
        for(int i=0;i<4;i++)
        {
            message = "talon";
            for(int j=0;j<6;j++)
            {
                message += " " + cardsSeparated.get(gameNumber).get(4).get(j);
            }
            message += ";";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        for(int i=0;i<4;i++)
        {
            if (i != playerRequest.get(gameNumber)) {
                message = "requestedTalonSelection " + playerRequest.get(gameNumber) + ";";
                listOfClients.get(i).write(message);
                log(message, i);
            }
        }
        while(!talonSelected)
        {
            message = "requestTalonSelection;";
            listOfCommands = listOfClients.get(playerRequest.get(gameNumber)).write(message);
            log(message, playerRequest.get(gameNumber));
            logReceived(listOfCommands, playerRequest.get(gameNumber));
            listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
            if (listOfObjects.get(0).equals("talonSelection"))
            {
                if (listOfObjects.get(1).equals("next")) {
                    for(int k=0;k<2;k++)
                    {
                        if (cardsSeparated.get(gameNumber).get(4).get(talonPart.get(gameNumber)*2+k) == 30+pope.get(gameNumber)*8)
                            showTeamsAfterTalonSelection = true;
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
                        message = "respondedTalonSelection " + listOfObjects.get(1) + ";";
                        listOfClients.get(i).write(message);
                        log(message, i);
                    }
                }
            }
        }
        message = "requestTalonReplacement;";
        listOfCommands = listOfClients.get(playerRequest.get(gameNumber)).write(message);
        log(message, playerRequest.get(gameNumber));
        logReceived(listOfCommands, playerRequest.get(gameNumber));
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
            cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)).remove((Object) card1);
            cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)).remove((Object) card2);
            for(int i=0;i<2;i++)
            {
                cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)).add(cardsSeparated.get(gameNumber).get(4).get(talonPart.get(gameNumber)*2+i));
            }
            cardsSeparated.get(gameNumber).remove(4);
            Collections.sort(cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)));
            computeTeams();
            if (showTeamsAfterTalonSelection)
                sendTeams();
            message = "cards";
            for(int j=0;j<12;j++)
                message = message + " " + String.valueOf(cardsSeparated.get(gameNumber).get(playerRequest.get(gameNumber)).get(j));
            message = message + ";";
            listOfClients.get(playerRequest.get(gameNumber)).write(message);
            log(message, playerRequest.get(gameNumber));
        }
        for(int i=0;i<4;i++)
        {
            message = "closeTalonWindow";
            if (i == playerRequest.get(gameNumber))
                message += " select;";
            else
                message += " show;";
            listOfClients.get(i).write(message);
            log(message, i);
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
            log(message, j);
        }
        message = "requestPope;";
        listOfCommands = listOfClients.get(playerRequest.get(gameNumber)).write(message);
        log(message, playerRequest.get(gameNumber));
        logReceived(listOfCommands, playerRequest.get(gameNumber));
        listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
        if (listOfObjects.get(0).equals("pope"))
        {
            int pope = Integer.valueOf(listOfObjects.get(1));
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
            log(message, j);
        }
        for(int i=0;i<4;i++)
        {
            message = "clearDeclarationTurn;";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        System.out.println(declarations);
    }

    public void requestDeclaration(int player) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        List<String> listOfObjects;
        String message;
        for(int j=0;j<4;j++)
        {
            message = "requestedDeclaration " + player + ";";
            listOfClients.get(j).write(message);
            log(message, j);
        }
        message = "requestDeclaration;";
        listOfCommands = listOfClients.get(player).write(message);
        log(message, player);
        logReceived(listOfCommands, player);
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
            int numberOfTarocks = Integer.valueOf(listOfObjects.get(5));
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
            log(message, j);
        }
        for(int i=0;i<4;i++)
        {
            message = "clearDeclarationTurn;";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        System.out.println(declarations);
    }

    public void requestPlayerMode(int player) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        List<String> listOfObjects;
        String message;
        for(int j=0;j<4;j++)
        {
            message = "requestedPlayerMode " + player + ";";
            listOfClients.get(j).write(message);
            log(message, j);
        }
        message = "requestPlayerMode;";
        listOfCommands = listOfClients.get(player).write(message);
        log(message, player);
        logReceived(listOfCommands, player);
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
            log(message, j);
        }
        if (listOfObjects.get(0).equals("playerMode"))
        {
            playersStatus.get(gameNumber).set(player, listOfObjects.get(1));
            System.out.println("ListOfObjects.get(1) = " + listOfObjects.get(1));
            if (listOfObjects.get(1).equals("1/2"))
            {
                message = "increased1of2;";
                the1of2.set(gameNumber, the1of2.get(gameNumber) + 1);
                for(int j=0;j<4;j++)
                {
                    listOfClients.get(j).write(message);
                    log(message, j);
                }
            }
        }
        System.out.println(playersStatus);
    }

    public void doHand(int hand) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        List<String> listOfObjects;
        int firstRequest = -1;
        if (hand == 0)
            firstRequest = (gameNumber) % 4;
        else
            firstRequest = rounds.get(gameNumber).get(hand - 1).getPlayerThatWon();
        Round round = new Round(hand, firstRequest);

        int lastRequest = firstRequest + 4;
        String message = "";

        for(int i=0;i<4;i++)
        {
            message = "beginRound;";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        for(int i=firstRequest;i<lastRequest;i++)
        {
            for(int j=0;j<4;j++)
            {
                message = "requestedCard " + i%4 + ";";
                listOfClients.get(j).write(message);
                log(message, j);
            }
            message = "requestCard;";
            listOfCommands = listOfClients.get(i%4).write(message);
            log(message, i%4);
            logReceived(listOfCommands, i%4);
            listOfObjects = DataManipulationService.processCommand(listOfCommands.get(0));
            int cardGiven = -1;
            if (listOfObjects.get(0).equals("card"))
            {
                cardGiven = Integer.valueOf(listOfObjects.get(1));
                round.addCard(i%4, cardGiven);
            }
            for(int j=0;j<4;j++)
            {
                message = "respondedCard ";
                message += (i%4) + " " + cardGiven + ";";
                if (cardGiven == 30+pope.get(gameNumber)*8)
                    sendTeams();
                listOfClients.get(j).write(message);
                log(message, j);
            }
        }
        rounds.get(gameNumber).add(round);

        int playerThatWon = round.getPlayerThatWon();
        int team = 1;
        for (int i=0;i<teams.get(gameNumber).get(0).size();i++)
        {
            if (playerThatWon == teams.get(gameNumber).get(0).get(i))
                team = 0;
        }
        for(int i=0;i<4;i++)
        {
            cardsWon.get(gameNumber).get(team).add(round.getCard(i));
        }

        for(int i=0;i<4;i++)
        {
            message = "roundFinished;";
            listOfClients.get(i).write(message);
            log(message, i);
        }
    }

    public void doRounds() throws IOException, ClassNotFoundException {
        String message;
        for(int i=0;i<12;i++)
        {
            doHand(i);
        }
        for(int i=0;i<4;i++)
        {
            message = "showScoreWindow;";
            listOfClients.get(i).write(message);
            log(message, i);
        }

        Collections.sort(cardsWon.get(gameNumber).get(0));
        Collections.sort(cardsWon.get(gameNumber).get(1));

        message = "cardsTeam1";
        for(int j=0;j<cardsWon.get(gameNumber).get(0).size();j++)
            message += " " + cardsWon.get(gameNumber).get(0).get(j);
        message += ";cardsTeam2";
        for(int j=0;j<cardsWon.get(gameNumber).get(1).size();j++)
            message += " " + cardsWon.get(gameNumber).get(1).get(j);
        message += ";";
        for(int i=0;i<4;i++)
        {
            listOfClients.get(i).write(message);
            log(message, i);
        }
        boolean isRadler = false;
        if (radlerTimes > 0)
            isRadler = true;
        radlerTimes--;
        Pair<String, Game, Score> pair = ScoreService.getScore(playerRequest.get(gameNumber), players,
                teams.get(gameNumber), rounds.get(gameNumber), declarations.get(gameNumber),
                cardsWon.get(gameNumber), popeInTalon, isRadler);
        String declarationMessage = pair.getKey();
        Game game = pair.getValue();
        Score score = pair.getSpecialValue();
        for(int i=0;i<4;i++)
        {
            message = "score";
            for(int j=0;j<4;j++)
            {
                message += " " + game.getScorePlayer(j);
            }
            message += " " + DataManipulationService.processName(declarationMessage) + " ";
            if (isRadler)
                message += "1;";
            else
                message += "0;";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        for(int i=0;i<4;i++)
        {
            message = "scoreDetailed ";
            message += score.toString();
            message += " " + DataManipulationService.processName(score.getTeamThatWon());
            message += ";";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        runHelper();
    }

    public void runHelper() throws IOException, ClassNotFoundException {
        // initialization

        gameNumber++;
        showTeamsAfterTalonSelection = false;
        popeInTalon = false;
        playersStatus.add(new ArrayList<String>());
        cards.add(new ArrayList<Integer>());
        cardsSeparated.add(new ArrayList<List<Integer>>());
        declarations.add(new ArrayList<Declaration>());
        teams.add(new ArrayList<List<Integer>>());
        rounds.add(new ArrayList<Round>());
        cardsWon.add(new ArrayList<List<Integer>>());
        pope.add(-1);
        talonPart.add(-1);
        playerRequest.add(-1);
        the1of2.add(1);
        for(int i=0;i<2;i++) {
            teams.get(gameNumber).add(new ArrayList<Integer>());
            cardsWon.get(gameNumber).add(new ArrayList<Integer>());
        }
        for(int i=0;i<4;i++) {
            playersStatus.get(gameNumber).add("");
            declarations.get(gameNumber).add(null);
        }
        for(int i=1;i<=54;i++)
            cards.get(gameNumber).add(i);
        Collections.shuffle(cards.get(gameNumber));

        for(int i=0;i<5;i++)
            cardsSeparated.get(gameNumber).add(new ArrayList<Integer>());
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

        //begin
        /*
        for(int i=0;i<cardsSeparated.get(gameNumber).get(0).size();i++)
        {
            int aux = cardsSeparated.get(gameNumber).get(0).get(i);
            if (Round.isTarock(cardsSeparated.get(gameNumber).get(0).get(i)))
            {
                cardsSeparated.get(gameNumber).get(0).set(i, 23);
            }
            if ((aux == 30) || (aux == 38) || (aux == 46) || (aux == 54)){
                cardsSeparated.get(gameNumber).get(0).set(i, 23);
            }
        }
        */
        //end

        // finished initialization
        String message;
        int firstRequest = (gameNumber)%4;
        int lastRequest = firstRequest + 4;

        for(int i=0;i<4;i++)
        {
            message = "beginGame;";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        for(int i=0;i<4;i++)
        {
            message = "cards";
            for(int j=0;j<12;j++)
                message = message + " " + String.valueOf(cardsSeparated.get(gameNumber).get(i).get(j));
            message = message + ";";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        for(int i=firstRequest;i<lastRequest;i++)
            requestPlayerMode(i%4);
        if (numberOf1of2() == 0)
        {
            radlerTimes = 4;
            for(int i=0;i<4;i++)
            {
                message = "score 0 0 0 0 Radler 1;";
                listOfClients.get(i).write(message);
                log(message, i);
            }
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
            message = "playerRequest " + playerRequest.get(gameNumber) + " " + the1of2.get(gameNumber) + ";";
            listOfClients.get(i).write(message);
            log(message, i);
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
            message = "clearDeclarationTurn;";
            listOfClients.get(i).write(message);
            log(message, i);
        }

        declarations.get(gameNumber).get(playerRequest1).setThe1of2(the1of2.get(gameNumber));
        for(int j=0;j<4;j++)
        {
            message = "respondedDeclaration " + playerRequest.get(gameNumber) +
                    " " + DataManipulationService.processName(declarations
                    .get(gameNumber).get(playerRequest.get(gameNumber)).toString()) +
                    " " + declarations.get(gameNumber).get(playerRequest.get(gameNumber))
                    .toSendableObject() + ";";
            listOfClients.get(j).write(message);
            log(message, j);
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
