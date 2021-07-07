package com.TarockServer;

import com.TarockServer.ConnectionManager.PHPConnection;
import com.TarockServer.Domain.Pair;
import com.TarockServer.Domain.Session;
import com.TarockServer.Domain.User;
import com.TarockServer.Service.CommunicationService;
import com.TarockServer.Service.DataManipulationService;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String args[]) throws Exception {
        CommunicationService communicationService = new CommunicationService();
        communicationService.start();

        /*int[] cardsWonTeam1 = {1, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 28, 29, 30, 33, 37, 39, 40, 41, 42, 44, 46, 47, 48, 51, 53, 54};
        int[] cardsWonTeam2 = {2, 11, 13, 14, 27, 31, 32, 34, 35, 36, 38, 43, 45, 49, 50, 52};
        List<List<Integer>> cardsWon = new ArrayList<List<Integer>>();
        cardsWon.add(new ArrayList<Integer>());
        cardsWon.add(new ArrayList<Integer>());
        for(int i=0;i<cardsWonTeam1.length;i++)
            cardsWon.get(0).add(cardsWonTeam1[i]);
        for(int i=0;i<cardsWonTeam2.length;i++)
            cardsWon.get(1).add(cardsWonTeam2[i]);
        List<List<Integer>> teams = new ArrayList<List<Integer>>();
        ScoreService.getScore(1, teams, null, cardsWon, false);
        */
    }
}
