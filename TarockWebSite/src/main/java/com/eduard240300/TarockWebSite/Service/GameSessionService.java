package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.Domain.Declaration;
import com.eduard240300.TarockWebSite.Domain.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSessionService extends Thread{
    public List<ClientService> listOfClients;
    public Session session;
    public int round = 1;
    public List<Integer> cards;
    public List<List<Integer>> cardsSeparated;
    public List<String> playersStatus;
    public List<String> declarations;
    public List<Declaration> declarationsObjects;
    public int playerRequest = -1; //player that requested the 1of2
    public int the1of2 = 1;
    public GameSessionService(List<ClientService> listOfClients, Session session){
        this.listOfClients = listOfClients;
        this.session = session;
        playersStatus = new ArrayList<String>();
        cards = new ArrayList<Integer>();
        cardsSeparated = new ArrayList<List<Integer>>();
        declarations = new ArrayList<String>();
        declarationsObjects = new ArrayList<Declaration>();
        for(int i=0;i<4;i++) {
            playersStatus.add("");
            declarations.add("");
            declarationsObjects.add(null);
        }
        for(int i=1;i<=54;i++)
            cards.add(i);
        Collections.shuffle(cards);
        for(int i=0;i<5;i++)
            cardsSeparated.add(new ArrayList<Integer>());
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<12;j++)
            cardsSeparated.get(i).add(cards.get(i*12+j));
        }
        for(int j=0;j<6;j++)
        {
            cardsSeparated.get(4).add(cards.get(48+j));
        }
        for(int i=0;i<4;i++)
            Collections.sort(cardsSeparated.get(i));
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
            if (playersStatus.get(i).equals("1/2"))
                result++;
        }
        return result;
    }

    public void requestDeclaration(int player) throws IOException, ClassNotFoundException {
        List<String> listOfCommands;
        List<String> listOfObjects;
        String processedDeclaration = null;
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
            boolean pagatAtFinish = DataManipulationService.stringToBool(listOfObjects.get(2));
            boolean allPopes = DataManipulationService.stringToBool(listOfObjects.get(3));
            boolean trull = DataManipulationService.stringToBool(listOfObjects.get(4));
            int numberOfTarocks = Integer.valueOf(listOfObjects.get(5));
            declaration = new Declaration(popeAtFinish, pagatAtFinish, allPopes, trull, numberOfTarocks);
            if (player == playerRequest)
            {
                int pope = Integer.parseInt(listOfObjects.get(6));
                declaration.setThe1of2(the1of2);
                declaration.setPope(pope);
            }
            declarationsObjects.set(player, declaration);
            declarations.set(player, declaration.toString());
        }
        for(int j=0;j<4;j++)
        {
            message = "respondedDeclaration " + player + " " + DataManipulationService.processName(declarations.get(player)) + " " + declarationsObjects.get(player).toSendableObject() + ";";
            listOfClients.get(j).write(message);
            log(message, j);
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
        for(int j=0;j<4;j++)
        {
            message = "respondedPlayerMode " + player + " " + listOfObjects.get(1) + ";";
            listOfClients.get(j).write(message);
            log(message, j);
        }
        if (listOfObjects.get(0).equals("playerMode"))
        {
            playersStatus.set(player, listOfObjects.get(1));
            System.out.println("ListOfObjects.get(1) = " + listOfObjects.get(1));
            if (listOfObjects.get(1).equals("1/2"))
            {
                message = "increased1of2;";
                the1of2++;
                for(int j=0;j<4;j++)
                {
                    listOfClients.get(j).write(message);
                    log(message, j);
                }
            }
        }
        System.out.println(playersStatus);
    }

    public void runHelper() throws IOException, ClassNotFoundException {
        String message;
        int firstRequest = (round-1)%4;
        int lastRequest = firstRequest + 4;

        for(int i=0;i<4;i++)
        {
            message = "cards";
            for(int j=0;j<12;j++)
                message = message + " " + String.valueOf(cardsSeparated.get(i).get(j));
            message = message + ";";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        for(int i=firstRequest;i<lastRequest;i++)
            requestPlayerMode(i%4);
        while(numberOf1of2() > 1)
        {
            for(int i=firstRequest;i<lastRequest;i++)
            {
                if (playersStatus.get(i%4).equals("1/2"))
                    requestPlayerMode(i%4);
                if (numberOf1of2() == 1) i = lastRequest;
            }
        }
        for(int i=0;i<4;i++)
            if (playersStatus.get(i).equals("1/2")) playerRequest = i;
        the1of2--;
        for(int i=0;i<4;i++)
        {
            message = "playerRequest " + playerRequest + " " + the1of2 + ";";
            listOfClients.get(i).write(message);
            log(message, i);
        }
        firstRequest = playerRequest%4;
        lastRequest = firstRequest + 4;
        for(int i=firstRequest;i<lastRequest;i++)
        {
            requestDeclaration(i%4);
        }
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
