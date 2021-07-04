package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.Domain.Declaration;
import com.eduard240300.TarockWebSite.Domain.Game;
import com.eduard240300.TarockWebSite.Domain.Score;
import com.eduard240300.TarockWebSite.Domain.Session;

import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    public static List<Declaration> getDeclarationsTeams(List<Declaration> declarations)
    {
        //List<Declaration> declarationsTeams =
        return null;
    }

    public static List<List<Integer>> swapPope(List<List<Integer>> cardsWon, int pope)
    {
        int positionPope = -1;
        int positionPlainCard = -1;
        for(int i=0;i<cardsWon.get(1).size();i++)
        {
            if (cardsWon.get(1).get(i) == 30+pope*8)
                positionPope = i;
        }
        int i = 0;
        while ((i < cardsWon.get(0).size()) && (positionPlainCard == -1))
        {
            if (getScoreCard(cardsWon.get(0).get(i)) == 0)
                positionPlainCard = i;
            i++;
        }
        if (positionPlainCard == -1)
            positionPlainCard = 0;
        int swapCard = cardsWon.get(0).get(positionPlainCard);
        cardsWon.get(0).set(positionPlainCard, cardsWon.get(1).get(positionPope));
        cardsWon.get(1).set(positionPope, swapCard);
        return cardsWon;
    }

    public static int getScoreCard(int cardID)
    {
        if ((cardID == 1) || (cardID == 21) || (cardID == 22) ||
                (cardID == 30) || (cardID == 38) || (cardID == 46) || (cardID == 54))
            return 5;
        else if ((cardID == 29) || (cardID == 37) || (cardID == 45) || (cardID == 53))
            return 4;
        else if ((cardID == 28) || (cardID == 36) || (cardID == 44) || (cardID == 52))
            return 3;
        else if ((cardID == 27) || (cardID == 35) || (cardID == 43) || (cardID == 51))
            return 2;
        else if ((cardID == 26) || (cardID == 34) || (cardID == 42) || (cardID == 50))
            return 1;
        else
            return 0;
    }

    public static void getScore(int playerRequest, Session session, List<List<Integer>> teams, List<Declaration> declarations, List<List<Integer>> cardsWon, boolean popeInTalon, boolean isRadler)
    {
        Game game;
        Score score = new Score();
        List<Declaration> declarationsTeams = getDeclarationsTeams(declarations);
        int pope = declarations.get(playerRequest).getPope();
        int the1of2 = declarations.get(playerRequest).getThe1of2();
        if (popeInTalon)
            cardsWon = swapPope(cardsWon, pope);

        int teamThatWon = 0;
        List<Double> pointsTeams = new ArrayList<Double>();

        for(int i=0;i<2;i++)
        {
            double pointsTeam = 0;

            for(int j=0;j<cardsWon.get(i).size();j++)
            {
                if (getScoreCard(cardsWon.get(i).get(j)) == 0) {
                    pointsTeam++;
                }
                else
                {
                    pointsTeam += (getScoreCard(cardsWon.get(i).get(j)) * 3 - 2);
                }
            }

            pointsTeam = pointsTeam / 3;
            pointsTeams.add(pointsTeam);
        }

        if (pointsTeams.get(1) > pointsTeams.get(0))
            teamThatWon = 1;

        String tagTeamThatWon = "Team 0" + (teamThatWon+1);
        tagTeamThatWon += "(";
        for(int i=0;i<teams.get(teamThatWon).size();i++)
        {
            if (i==0)
            {
                tagTeamThatWon += session.getPlayer(teams.get(teamThatWon).get(i));
            }
            else
            {
                tagTeamThatWon += " + " + session.getPlayer(teams.get(teamThatWon).get(i));
            }
        }
        tagTeamThatWon += ")";
        score.setTeamThatWon(tagTeamThatWon);
        int pointsGame = 2;
        String declaredOrDoneGame = "";
        if (teamThatWon == 1) {
            pointsGame = pointsGame * the1of2;
            declaredOrDoneGame = "Team 1 lost";
        }
        score.getAttribute("Game").setPoints(pointsGame);
        score.getAttribute("Game").setDeclaredOrDone(declaredOrDoneGame);



        System.out.println(pointsTeams);
    }
}
