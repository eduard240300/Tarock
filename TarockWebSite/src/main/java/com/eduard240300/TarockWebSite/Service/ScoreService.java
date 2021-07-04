package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.Domain.*;

import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    public static List<Declaration> getDeclarationsTeams(List<Declaration> declarations, List<List<Integer>> teams, int playerRequest)
    {
        List<Declaration> declarationsTeams = new ArrayList<Declaration>();
        for(int i=0;i<2;i++)
        {
            Declaration declaration = new Declaration(false, false, false, false, 0);
            for(int j=0;j<teams.get(0).size();j++)
            {
                if (declarations.get(teams.get(0).get(j)).getPopeAtFinish())
                    declaration.setPopeAtFinish(true);
                else if (declarations.get(teams.get(0).get(j)).getPagatAtFinish())
                    declaration.setPagatAtFinish(true);
                else if (declarations.get(teams.get(0).get(j)).getAllPopes())
                    declaration.setAllPopes(true);
                else if (declarations.get(teams.get(0).get(j)).getTrull())
                    declaration.setTrull(true);
                if (teams.get(0).get(j) == playerRequest)
                {
                    declaration.setThe1of2(declarations.get(teams.get(0).get(j)).getThe1of2());
                    declaration.setPope(declarations.get(teams.get(0).get(j)).getPope());
                }
            }
            declarationsTeams.add(declaration);
        }
        return declarationsTeams;
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

    public static Pair<String, Game, Score> getScore(int playerRequest, List<String> players, List<List<Integer>> teams, List<Round> rounds, List<Declaration> declarations, List<List<Integer>> cardsWon, boolean popeInTalon, boolean isRadler)
    {
        Game game = new Game();
        Score score = new Score();
        score.setRadler(isRadler);
        List<Declaration> declarationsTeams = getDeclarationsTeams(declarations, teams, playerRequest);
        int pope = declarations.get(playerRequest).getPope();
        int the1of2 = declarations.get(playerRequest).getThe1of2();
        List<List<Integer>> cardsWonNew = cardsWon;
        if (popeInTalon)
            cardsWonNew = swapPope(cardsWon, pope);

        int teamThatWon = 0;
        List<Double> pointsTeams = new ArrayList<Double>();

        //calculate points won by each team
        for(int i=0;i<2;i++)
        {
            double pointsTeam = 0;

            for(int j=0;j<cardsWonNew.get(i).size();j++)
            {
                if (getScoreCard(cardsWonNew.get(i).get(j)) == 0) {
                    pointsTeam++;
                }
                else
                {
                    pointsTeam += (getScoreCard(cardsWonNew.get(i).get(j)) * 3 - 2);
                }
            }

            pointsTeam = pointsTeam / 3;
            pointsTeams.add(pointsTeam);
        }

        if (pointsTeams.get(1) > pointsTeams.get(0))
            teamThatWon = 1;

        //calculate game points
        String tagTeamThatWon = "Team 0" + (teamThatWon+1);
        tagTeamThatWon += "(";
        for(int i=0;i<teams.get(teamThatWon).size();i++)
        {
            if (i==0)
            {
                tagTeamThatWon += players.get(teams.get(teamThatWon).get(i));
            }
            else
            {
                tagTeamThatWon += " + " + players.get(teams.get(teamThatWon).get(i));
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

        //variables declarations
        boolean declaredPopeAtFinish = false;
        int teamDeclaredPagatAtFinish = -1;
        int teamDeclaredAllPopes = -1;
        int teamDeclaredAllTrullCards = -1;
        boolean donePopeAtFinish = false;
        int teamDonePagatAtFinish = -1;
        int teamDoneAllPopes = -1;
        int teamDoneAllTrullCards = -1;
        boolean popeCaught = false;
        int lunaCaughtFromTeam = 0;

        //get values of variables declaration
        for(int i=0;i<2;i++)
        {
            if (declarationsTeams.get(i).getPopeAtFinish())
                declaredPopeAtFinish = true;
            if (declarationsTeams.get(i).getPagatAtFinish())
                teamDeclaredPagatAtFinish = i;
            if (declarationsTeams.get(i).getAllPopes())
                teamDeclaredAllPopes = i;
            if (declarationsTeams.get(i).getTrull())
                teamDeclaredAllTrullCards = i;
        }

        //calculate if team 1 won the last round
        int playerThatWonLastRound = rounds.get(11).getPlayerThatWon();
        boolean team1WonLastRound = false;
        for(int i=0;i<teams.get(0).size();i++)
        {
            if (playerThatWonLastRound == teams.get(0).get(i))
                team1WonLastRound = true;
        }

        //calculate if popeAtFinish done
        for(int i=0;i<teams.get(0).size();i++)
        {
            if (team1WonLastRound)
                if (rounds.get(11).getCard(teams.get(0).get(i)) == 30+pope*8)
                    donePopeAtFinish = true;
        }

        //calculate if pagatAtFinish done
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<teams.get(0).size();j++)
            {
                if ((rounds.get(11).getCard(teams.get(i).get(j)) == 1) &&
                        (rounds.get(11).getPlayerThatWon() == teams.get(i).get(j))) {
                    teamDonePagatAtFinish = i;
                }
            }
        }

        //calculate if allPopes and allTrullCards done
        for(int i=0;i<2;i++)
        {
            int numberOfTrullCards = 0;
            int numberOfPopes = 0;
            for(int j=0;j<cardsWon.get(i).size();j++)
            {
                if ((cardsWon.get(i).get(j) == 1) || (cardsWon.get(i).get(j) == 21) ||
                    (cardsWon.get(i).get(j) == 22))
                    numberOfTrullCards++;
                if ((cardsWon.get(i).get(j) == 30) || (cardsWon.get(i).get(j) == 38) ||
                        (cardsWon.get(i).get(j) == 46) || (cardsWon.get(i).get(j) == 54))
                    numberOfPopes++;
            }
            if (numberOfTrullCards == 3)
                teamDoneAllTrullCards = i;
            if (numberOfPopes == 4)
                teamDoneAllPopes = i;
        }

        //calculate if popeCaught
        for(int i=0;i<cardsWon.get(1).size();i++)
        {
            if (cardsWon.get(1).get(i) == 30+pope*8)
                popeCaught = true;
        }

        //calculate if lunaCaught
        boolean lunaCaught = false;
        int playerThatGaveLuna = -1;
        for(int i=0;i<12;i++)
        {
            for(int j=0;j<4;j++)
            {
                if ((rounds.get(i).getCard(j) == 21) && (rounds.get(i).getPlayerThatWon() != j)) {
                    lunaCaught = true;
                    playerThatGaveLuna = i;
                }
            }
        }

        for(int i=0;i<teams.get(1).size();i++)
        {
            if (playerThatGaveLuna == teams.get(1).get(i))
                lunaCaughtFromTeam = 1;
        }

        //calculate popeAtFinish points
        int pointsPopeAtFinish = -1;
        String declaredOrDonePopeAtFinish = "";
        if (declaredPopeAtFinish)
        {
            if (donePopeAtFinish)
            {
                declaredOrDonePopeAtFinish = "Declared and done";
                pointsPopeAtFinish = 2;
            }
            else {
                declaredOrDonePopeAtFinish = "Declared and not done";
                pointsPopeAtFinish = -2;
            }
        }
        else
        {
            if (donePopeAtFinish)
            {
                declaredOrDonePopeAtFinish = "Done";
                pointsPopeAtFinish = 1;
            }
            else {
                declaredOrDonePopeAtFinish = "No";
                pointsPopeAtFinish = 0;
            }
        }

        score.getAttribute("Pope at finish").setPoints(pointsPopeAtFinish);
        score.getAttribute("Pope at finish").setDeclaredOrDone(declaredOrDonePopeAtFinish);

        //calculate pagatAtFinish points
        int pointsPagatAtFinish = -1;
        String declaredOrDonePagatAtFinish = "";
        if (teamDeclaredPagatAtFinish == 0)
        {
            if (teamDonePagatAtFinish != -1)
            {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish+1) + "and done";
                pointsPagatAtFinish = 7;
            }
            else
            {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish+1) + "and not done";
                pointsPagatAtFinish = -7;
            }
        }
        else if (teamDeclaredPagatAtFinish == 1)
        {
            if (teamDonePagatAtFinish != -1)
            {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish+1) + "and done";
                pointsPagatAtFinish = -7;
            }
            else
            {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish+1) + "and not done";
                pointsPagatAtFinish = 7;
            }
        }
        else
        {
            if (teamDonePagatAtFinish == 0)
            {
                declaredOrDonePagatAtFinish = "Done(T" + (teamDonePagatAtFinish+1) + ")";
                pointsPagatAtFinish = 1;
            }
            else if (teamDonePagatAtFinish == 1)
            {
                declaredOrDonePagatAtFinish = "Done(T" + (teamDonePagatAtFinish+1) + ")";
                pointsPagatAtFinish = -1;
            }
            else
            {
                declaredOrDonePagatAtFinish = "No";
                pointsPagatAtFinish = 0;
            }
        }

        score.getAttribute("Pagat at finish").setPoints(pointsPagatAtFinish);
        score.getAttribute("Pagat at finish").setDeclaredOrDone(declaredOrDonePagatAtFinish);

        //calculate allPopes
        int pointsAllPopes = -1;
        String declaredOrDoneAllPopes = "";
        if (teamDeclaredAllPopes == 0)
        {
            if (teamDoneAllPopes == 0)
            {
                declaredOrDoneAllPopes = "Declared(T" + (teamDeclaredAllPopes+1) +
                        "and done(T" + (teamDoneAllPopes+1) + ")";
                pointsAllPopes = 2;
            }
            else if (teamDoneAllPopes == 1)
            {
                declaredOrDoneAllPopes = "Declared(T" + (teamDeclaredAllPopes+1) +
                        "and done(T" + (teamDoneAllPopes+1) + ")";
                pointsAllPopes = -3;
            }
            else
            {
                declaredOrDoneAllPopes = "Declared(T" + (teamDeclaredAllPopes+1) + "and not done";
                pointsAllPopes = -2;
            }
        }
        else if (teamDeclaredAllPopes == 1)
        {
            if (teamDoneAllPopes == 0)
            {
                declaredOrDoneAllPopes = "Declared(T" + (teamDeclaredAllPopes+1) +
                        "and done(T" + (teamDoneAllPopes+1) + ")";
                pointsAllPopes = 3;
            }
            else if (teamDoneAllPopes == 1)
            {
                declaredOrDoneAllPopes = "Declared(T" + (teamDeclaredAllPopes+1) +
                        "and done(T" + (teamDoneAllPopes+1) + ")";
                pointsAllPopes = -2;
            }
            else
            {
                declaredOrDoneAllPopes = "Declared(T" + (teamDeclaredAllPopes+1) + "and not done";
                pointsAllPopes = 2;
            }
        }
        else
        {
            if (teamDoneAllPopes == 0)
            {
                declaredOrDoneAllPopes = "Done(T" + (teamDoneAllPopes+1) + ")";
                pointsAllPopes = 1;
            }
            else if (teamDoneAllPopes == 1)
            {
                declaredOrDoneAllPopes = "Done(T" + (teamDoneAllPopes+1) + ")";
                pointsAllPopes = -1;
            }
            else
            {
                declaredOrDoneAllPopes = "No";
                pointsAllPopes = 0;
            }
        }

        //calculate allTrullCards
        int pointsAllTrullCards = -1;
        String declaredOrDoneAllTrullCards = "";
        if (teamDeclaredAllTrullCards == 0)
        {
            if (teamDoneAllTrullCards == 0)
            {
                declaredOrDoneAllTrullCards = "Declared(T" + (teamDeclaredAllTrullCards+1) +
                        "and done(T" + (teamDoneAllTrullCards+1) + ")";
                pointsAllTrullCards = 2;
            }
            else if (teamDoneAllTrullCards == 1)
            {
                declaredOrDoneAllTrullCards = "Declared(T" + (teamDeclaredAllTrullCards+1) +
                        "and done(T" + (teamDoneAllTrullCards+1) + ")";
                pointsAllTrullCards = -3;
            }
            else
            {
                declaredOrDoneAllTrullCards = "Declared(T" + (teamDeclaredAllTrullCards+1) + "and not done";
                pointsAllTrullCards = -2;
            }
        }
        else if (teamDeclaredAllTrullCards == 1)
        {
            if (teamDoneAllTrullCards == 0)
            {
                declaredOrDoneAllTrullCards = "Declared(T" + (teamDeclaredAllTrullCards+1) +
                        "and done(T" + (teamDoneAllTrullCards+1) + ")";
                pointsAllTrullCards = 3;
            }
            else if (teamDoneAllTrullCards == 1)
            {
                declaredOrDoneAllTrullCards = "Declared(T" + (teamDeclaredAllTrullCards+1) +
                        "and done(T" + (teamDoneAllTrullCards+1) + ")";
                pointsAllTrullCards = -2;
            }
            else
            {
                declaredOrDoneAllTrullCards = "Declared(T" + (teamDeclaredAllTrullCards+1) + "and not done";
                pointsAllTrullCards = 2;
            }
        }
        else
        {
            if (teamDoneAllTrullCards == 0)
            {
                declaredOrDoneAllTrullCards = "Done(T" + (teamDoneAllTrullCards+1) + ")";
                pointsAllTrullCards = 1;
            }
            else if (teamDoneAllTrullCards == 1)
            {
                declaredOrDoneAllTrullCards = "Done(T" + (teamDoneAllTrullCards+1) + ")";
                pointsAllTrullCards = -1;
            }
            else
            {
                declaredOrDoneAllTrullCards = "No";
                pointsAllTrullCards = 0;
            }
        }

        score.getAttribute("All trull cards").setPoints(pointsAllTrullCards);
        score.getAttribute("All trull cards").setDeclaredOrDone(declaredOrDoneAllTrullCards);

        //calculate popeCaught
        int pointsPopeCaught = -1;
        String declaredOrDonePopeCaught = "";
        if (popeCaught)
        {
            declaredOrDonePopeCaught = "Yes";
            pointsPopeCaught = -1;
        }
        else
        {
            declaredOrDonePopeCaught = "No";
            pointsPopeCaught = 0;
        }

        score.getAttribute("Pope caught").setPoints(pointsPopeCaught);
        score.getAttribute("Pope caught").setDeclaredOrDone(declaredOrDonePopeCaught);

        //calculate lunaCaught
        int pointsLunaCaught = -1;
        String declaredOrDoneLunaCaught = "";
        if (lunaCaughtFromTeam == 0)
        {
            declaredOrDoneLunaCaught = "Caught by T2";
            pointsLunaCaught = -1;
        }
        else if (lunaCaughtFromTeam == 1)
        {
            declaredOrDoneLunaCaught = "Caught by T1";
            pointsLunaCaught = 1;
        }
        else
        {
            declaredOrDoneLunaCaught = "No";
            pointsLunaCaught = 0;
        }

        score.getAttribute("Luna caught").setPoints(pointsLunaCaught);
        score.getAttribute("Luna caught").setDeclaredOrDone(declaredOrDoneLunaCaught);

        for(int i=0;i<4;i++)
        {
            calculateScoreTarocks(teamThatWon, i, teams, declarations, score);
        }

        if (teamThatWon == 1)
        {
            for(int i=1;i<7;i++)
            {
                int scorePoints = score.getAttributeByID(i).getPoints();
                score.getAttributeByID(i).setPoints(-scorePoints);
            }
        }

        game.setRadler(isRadler);
        int total = score.getAttribute("Total").getPoints();
        if (teams.get(0).size() == 1)
        {
            if (teamThatWon == 0) {
                game.setScorePlayer(playerRequest, total * 3);
                for(int i=0;i<4;i++)
                {
                    if (i != playerRequest)
                    {
                        game.setScorePlayer(playerRequest, -total);
                    }
                }
            }
            else if (teamThatWon == 1) {
                game.setScorePlayer(playerRequest, -total * 3);
                for(int i=0;i<4;i++)
                {
                    if (i != playerRequest)
                    {
                        game.setScorePlayer(playerRequest, total);
                    }
                }
            }
        }
        else
        {
            if (teamThatWon == 0) {
                for(int i=0;i<teams.get(0).size();i++)
                {
                    game.setScorePlayer(teams.get(0).get(i), total);
                }
                for(int i=0;i<teams.get(1).size();i++)
                {
                    game.setScorePlayer(teams.get(0).get(i), -total);
                }
            }
            else if (teamThatWon == 1) {
                for(int i=0;i<teams.get(0).size();i++)
                {
                    game.setScorePlayer(teams.get(0).get(i), -total);
                }
                for(int i=0;i<teams.get(1).size();i++)
                {
                    game.setScorePlayer(teams.get(0).get(i), total);
                }
            }
        }

        score.computeTotal();
        Pair<String, Game, Score> pair = new Pair<String,Game,Score>(game);
        pair.setSpecialValue(score);
        return pair;
    }

    public static void calculateScoreTarocks(int teamThatWon, int player, List<List<Integer>> teams, List<Declaration> declarations, Score score)
    {
        //calculate TarocksPlayer
        int pointsTarocksPlayer = -1;
        String declaredOrDoneTarocksPlayer = "";
        int teamPlayer = -1;
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<teams.get(i).size();j++)
            {
                if (teams.get(i).get(j) == player)
                    teamPlayer = i;
            }
        }
        if (declarations.get(player).getNumberOfTarocks() == 8)
        {
            declaredOrDoneTarocksPlayer = "8T";
            pointsTarocksPlayer = 1;
        }
        else if (declarations.get(player).getNumberOfTarocks() == 9)
        {
            declaredOrDoneTarocksPlayer = "9T";
            pointsTarocksPlayer = 2;
        }
        else if (declarations.get(player).getNumberOfTarocks() == 10)
        {
            declaredOrDoneTarocksPlayer = "10T";
            pointsTarocksPlayer = 3;
        }
        else
        {
            declaredOrDoneTarocksPlayer = "No";
            pointsTarocksPlayer = 0;
        }

        score.getAttribute("Tarocks Player 0" + (player+1)).setPoints(pointsTarocksPlayer);
        score.getAttribute("Tarocks Player 0" + (player+1)).setDeclaredOrDone(declaredOrDoneTarocksPlayer);
        if (teamPlayer != teamThatWon)
        {
            score.getAttribute("Tarocks Player 0" + (player+1)).setPoints(-pointsTarocksPlayer);
        }
    }
}
