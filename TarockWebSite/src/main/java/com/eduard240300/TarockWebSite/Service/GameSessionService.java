package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.Domain.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSessionService extends Thread{
    public List<ClientService> listOfClients;
    public Session session;
    public int round = 3;
    public List<Integer> cards;
    public List<List<Integer>> cardsSeparated;
    public List<String> playersStatus;
    public int the1of2 = 1;
    public GameSessionService(List<ClientService> listOfClients, Session session){
        this.listOfClients = listOfClients;
        this.session = session;
        playersStatus = new ArrayList<String>();
        cards = new ArrayList<Integer>();
        cardsSeparated = new ArrayList<List<Integer>>();
        for(int i=0;i<4;i++)
            playersStatus.add("");
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
        int player = 0;

        for(int i=0;i<4;i++)
        {
            message = "chairNumber " + i + ";";
            listOfClients.get(i).write(message);
            log(message, i);
        }
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
        {
            message = "wipeNextStatus;";
            listOfClients.get(i).write(message);
            log(message, i);
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
