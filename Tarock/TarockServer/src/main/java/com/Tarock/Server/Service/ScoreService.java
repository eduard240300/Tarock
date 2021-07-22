package com.Tarock.Server.Service;

import com.Tarock.Common.Domain.Score;
import com.Tarock.Common.Domain.Declaration;
import com.Tarock.Common.Domain.Game;
import com.Tarock.Common.Domain.Round;
import com.Tarock.Common.Domain.Pair;
import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    public static void getPointsMethod(String type, int teamDeclared, int teamDone, Score score)
    {
        int points;
        String declaredOrDone;
        if (teamDeclared == 0)
        {
            if (teamDone == 0)
            {
                declaredOrDone = "Declared(T" + (teamDeclared+1) +
                        ") and done(T" + (teamDone+1) + ")";
                points = 2;
            }
            else if (teamDone == 1)
            {
                declaredOrDone = "Declared(T" + (teamDeclared+1) +
                        ") and done(T" + (teamDone+1) + ")";
                points = -3;
            }
            else
            {
                declaredOrDone = "Declared(T" + (teamDeclared+1) + ") and not done";
                points = -2;
            }
        }
        else if (teamDeclared == 1)
        {
            if (teamDone == 0)
            {
                declaredOrDone = "Declared(T" + (teamDeclared+1) +
                        ") and done(T" + (teamDone+1) + ")";
                points = 3;
            }
            else if (teamDone == 1)
            {
                declaredOrDone = "Declared(T" + (teamDeclared+1) +
                        ") and done(T" + (teamDone+1) + ")";
                points = -2;
            }
            else
            {
                declaredOrDone = "Declared(T" + (teamDeclared+1) + ") and not done";
                points = 2;
            }
        }
        else
        {
            if (teamDone == 0)
            {
                declaredOrDone = "Done(T" + (teamDone+1) + ")";
                points = 1;
            }
            else if (teamDone == 1)
            {
                declaredOrDone = "Done(T" + (teamDone+1) + ")";
                points = -1;
            }
            else
            {
                declaredOrDone = "No";
                points = 0;
            }
        }

        score.getAttribute(type).setPoints(points);
        score.getAttribute(type).setDeclaredOrDone(declaredOrDone);
    }

    public static List<Declaration> getDeclarationsTeams(List<Declaration> declarations, List<List<Integer>> teams, int playerRequest)
    {
        List<Declaration> declarationsTeams = new ArrayList<>();
        for(int i=0;i<2;i++)
        {
            Declaration declaration = new Declaration(false, false, false, false, 0);
            for(int j=0;j<teams.get(i).size();j++)
            {
                if (declarations.get(teams.get(i).get(j)).getPopeAtFinish())
                    declaration.setPopeAtFinish(true);
                if (declarations.get(teams.get(i).get(j)).getPagatAtFinish())
                    declaration.setPagatAtFinish(true);
                if (declarations.get(teams.get(i).get(j)).getAllPopes())
                    declaration.setAllPopes(true);
                if (declarations.get(teams.get(i).get(j)).getTrull())
                    declaration.setTrull(true);
                if (teams.get(i).get(j) == playerRequest)
                {
                    declaration.setThe1of2(declarations.get(teams.get(i).get(j)).getThe1of2());
                    declaration.setPope(declarations.get(teams.get(i).get(j)).getPope());
                }
            }
            declarationsTeams.add(declaration);
        }
        return declarationsTeams;
    }

    public static String getDeclarationMessage(List<Declaration> declarationsTeams)
    {
        String declarationMessage = "";
        declarationMessage += "T1(";
        declarationMessage += declarationsTeams.get(0).toString();
        declarationMessage += ") T2(";
        declarationMessage += declarationsTeams.get(1).toString();
        declarationMessage += ")";
        return declarationMessage;
    }

    public static void swapPope(List<List<Integer>> cardsWon, int pope)
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

    public static Pair<Game, Score> getScore(int playerRequest, List<String> players, List<List<Integer>> teams, List<Round> rounds, List<Declaration> declarations, List<List<Integer>> cardsWon, boolean popeInTalon, boolean isRadler)
    {
        Game game = new Game();
        Score score = new Score();
        score.setRadler(isRadler);
        List<Declaration> declarationsTeams = getDeclarationsTeams(declarations, teams, playerRequest);
        String declarationMessage = getDeclarationMessage(declarationsTeams);
        int pope = declarations.get(playerRequest).getPope();
        int the1of2 = declarations.get(playerRequest).getThe1of2();
        if (popeInTalon) {
            swapPope(cardsWon, pope);
        }

        int teamThatWon = 0;
        List<Double> pointsTeams = new ArrayList<>();

        //calculate points won by each team
        for(int i=0;i<2;i++)
        {
            double pointsTeam = 0;

            for(int j = 0; j< cardsWon.get(i).size(); j++)
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

        //calculate game points
        StringBuilder tagTeamThatWon = new StringBuilder("Team 0" + (teamThatWon + 1));
        tagTeamThatWon.append("(");
        for(int i=0;i<teams.get(teamThatWon).size();i++)
        {
            if (i==0)
            {
                tagTeamThatWon.append(players.get(teams.get(teamThatWon).get(i)));
            }
            else
            {
                tagTeamThatWon.append(" + ").append(players.get(teams.get(teamThatWon).get(i)));
            }
        }
        tagTeamThatWon.append(")");
        score.setTeamThatWon(tagTeamThatWon.toString());
        int pointsGame = 2;
        String declaredOrDoneGame;
        if (cardsWon.get(0).size() == 50) //valat
        {
            pointsGame = 8;
            declaredOrDoneGame = "Valat (T1)";
        }
        else if (cardsWon.get(1).size() == 52)
        {
            pointsGame = 8 * the1of2;
            declaredOrDoneGame = "Valat (T2)";
        }
        else {

            if (teamThatWon == 1) {
                pointsGame = pointsGame * the1of2;
                declaredOrDoneGame = "Team 2 won";
            } else {
                declaredOrDoneGame = "Team 1 won";
            }
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
        int lunaCaughtFromTeam = -1;

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
            if (playerThatWonLastRound == teams.get(0).get(i)) {
                team1WonLastRound = true;
                break;
            }
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
            for(int j=0;j<teams.get(i).size();j++)
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
            if (cardsWon.get(1).get(i) == 30 + pope * 8) {
                popeCaught = true;
                break;
            }
        }

        //calculate if lunaCaught
        int playerThatGaveLuna = -1;
        for(int i=0;i<12;i++)
        {
            for(int j=0;j<4;j++)
            {
                if ((rounds.get(i).getCard(j) == 21) && (rounds.get(i).getPlayerThatWon() != j)) {
                    playerThatGaveLuna = j;
                }
            }
        }

        for(int i=0;i<2;i++)
        {
            for(int j=0;j<teams.get(i).size();j++)
            {
                if (playerThatGaveLuna == teams.get(i).get(j)) {
                    lunaCaughtFromTeam = i;
                    break;
                }
            }
        }

        //calculate popeAtFinish points
        int pointsPopeAtFinish;
        String declaredOrDonePopeAtFinish;
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
        String declaredOrDonePagatAtFinish;
        if (teamDeclaredPagatAtFinish == 0)
        {
            if (teamDonePagatAtFinish != -1)
            {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish+1) + ") and done";
                pointsPagatAtFinish = 7;
            }
            else
            {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish+1) + ") and not done";
                pointsPagatAtFinish = -7;
            }
        }
        else if (teamDeclaredPagatAtFinish == 1)
        {
            if (teamDonePagatAtFinish != -1)
            {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish+1) + ") and done";
                pointsPagatAtFinish = -7;
            }
            else
            {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish+1) + ") and not done";
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
        getPointsMethod("All popes", teamDeclaredAllPopes, teamDoneAllPopes, score);

        //calculate allTrullCards
        getPointsMethod("All trull cards", teamDeclaredAllTrullCards, teamDoneAllTrullCards, score);

        //calculate popeCaught
        int pointsPopeCaught = -1;
        String declaredOrDonePopeCaught;
        if (popeCaught)
        {
            declaredOrDonePopeCaught = "Yes";
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
        String declaredOrDoneLunaCaught;
        if (lunaCaughtFromTeam == 0)
        {
            declaredOrDoneLunaCaught = "Caught by T2";
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

        score.computeTotal();
        game.setRadler(isRadler);
        game.setDeclaration(declarationMessage);
        int total = score.getAttribute("Total").getPoints();
        if (teams.get(0).size() == 1)
        {
            if (teamThatWon == 0) {
                game.setScorePlayer(playerRequest, total * 3);
                for(int i=0;i<4;i++)
                {
                    if (i != playerRequest)
                    {
                        game.setScorePlayer(i, -total);
                    }
                }
            }
            else {
                game.setScorePlayer(playerRequest, -total * 3);
                for(int i=0;i<4;i++)
                {
                    if (i != playerRequest)
                    {
                        game.setScorePlayer(i, total);
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
                    game.setScorePlayer(teams.get(1).get(i), -total);
                }
            }
            else {
                for(int i=0;i<teams.get(0).size();i++)
                {
                    game.setScorePlayer(teams.get(0).get(i), -total);
                }
                for(int i=0;i<teams.get(1).size();i++)
                {
                    game.setScorePlayer(teams.get(1).get(i), total);
                }
            }
        }

        return new Pair<>(game, score);
    }

    public static void calculateScoreTarocks(int teamThatWon, int player, List<List<Integer>> teams, List<Declaration> declarations, Score score)
    {
        //calculate TarocksPlayer
        int pointsTarocksPlayer;
        String declaredOrDoneTarocksPlayer;
        int teamPlayer = -1;
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<teams.get(i).size();j++)
            {
                if (teams.get(i).get(j) == player) {
                    teamPlayer = i;
                    break;
                }
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
