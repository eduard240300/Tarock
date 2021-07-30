package com.Tarock.Server.Service;

import com.Tarock.Common.Domain.*;

import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    public static void getPointsMethodWithThreeChecks(String type, int teamDeclared, int teamDone, Score score) {
        int points;
        String declaredOrDone;
        if (teamDeclared == 0) {
            if (teamDone == 0) {
                declaredOrDone = "Declared(T" + (teamDeclared + 1) +
                        ") and done(T" + (teamDone + 1) + ")";
                points = 2;
            } else if (teamDone == 1) {
                declaredOrDone = "Declared(T" + (teamDeclared + 1) +
                        ") and done(T" + (teamDone + 1) + ")";
                points = -3;
            } else {
                declaredOrDone = "Declared(T" + (teamDeclared + 1) + ") and not done";
                points = -2;
            }
        } else if (teamDeclared == 1) {
            if (teamDone == 0) {
                declaredOrDone = "Declared(T" + (teamDeclared + 1) +
                        ") and done(T" + (teamDone + 1) + ")";
                points = 3;
            } else if (teamDone == 1) {
                declaredOrDone = "Declared(T" + (teamDeclared + 1) +
                        ") and done(T" + (teamDone + 1) + ")";
                points = -2;
            } else {
                declaredOrDone = "Declared(T" + (teamDeclared + 1) + ") and not done";
                points = 2;
            }
        } else {
            if (teamDone == 0) {
                declaredOrDone = "Done(T" + (teamDone + 1) + ")";
                points = 1;
            } else if (teamDone == 1) {
                declaredOrDone = "Done(T" + (teamDone + 1) + ")";
                points = -1;
            } else {
                declaredOrDone = "No";
                points = 0;
            }
        }

        score.getAttribute(type).setPoints(points);
        score.getAttribute(type).setDeclaredOrDone(declaredOrDone);
    }

    public static List<Declaration> getDeclarationsTeams(ScoreComputationArguments computation) {
        List<List<Integer>> teams = computation.getTeams();
        List<Declaration> declarations = computation.getDeclarations();
        int playerRequest = computation.getPlayerRequest();

        List<Declaration> declarationsTeams = new ArrayList<>();
        for (List<Integer> team : teams) {
            Declaration declaration = new Declaration();
            for (int player : team) {
                if (declarations.get(player).getPopeAtFinish())
                    declaration.setPopeAtFinish(true);
                if (declarations.get(player).getPagatAtFinish())
                    declaration.setPagatAtFinish(true);
                if (declarations.get(player).getAllPopes())
                    declaration.setAllPopes(true);
                if (declarations.get(player).getTrull())
                    declaration.setTrull(true);
                if (player == playerRequest) {
                    declaration.setThe1of2(declarations.get(player).getThe1of2());
                    declaration.setPope(declarations.get(player).getPope());
                }
            }
            declarationsTeams.add(declaration);
        }
        return declarationsTeams;
    }

    public static String getDeclarationMessage(List<Declaration> declarationsTeams) {
        String declarationMessage = "";
        declarationMessage += "T1(";
        declarationMessage += declarationsTeams.get(0).toString();
        declarationMessage += ") T2(";
        declarationMessage += declarationsTeams.get(1).toString();
        declarationMessage += ")";
        return declarationMessage;
    }

    public static void swapPope(List<List<Integer>> cardsWon, int pope) {
        int positionPope = -1;
        int positionPlainCard = -1;
        for (int i = 0; i < cardsWon.get(1).size(); i++) {
            if (cardsWon.get(1).get(i) == 30 + pope * 8)
                positionPope = i;
        }
        int i = 0;
        while ((i < cardsWon.get(0).size()) && (positionPlainCard == -1)) {
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

    public static int getScoreCard(int cardID) {
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

    public static int getTeamThatWon(List<List<Integer>> cardsWon) {
        int teamThatWon = 0;
        List<Double> pointsTeams = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            double pointsTeam = 0;

            for (int j = 0; j < cardsWon.get(i).size(); j++) {
                if (getScoreCard(cardsWon.get(i).get(j)) == 0) {
                    pointsTeam++;
                } else {
                    pointsTeam += (getScoreCard(cardsWon.get(i).get(j)) * 3 - 2);
                }
            }

            pointsTeam = pointsTeam / 3;
            pointsTeams.add(pointsTeam);
        }

        if (pointsTeams.get(1) > pointsTeams.get(0))
            teamThatWon = 1;

        return teamThatWon;
    }

    public static void computeGamePoints(Score score, int teamThatWon, ScoreComputationArguments computation) {
        List<List<Integer>> teams = computation.getTeams();
        List<String> players = computation.getPlayers();
        List<List<Integer>> cardsWon = computation.getCardsWon();
        int playerRequest = computation.getPlayerRequest();
        int the1of2 = computation.getDeclarations().get(playerRequest).getThe1of2();

        StringBuilder tagTeamThatWon = new StringBuilder("Team 0" + (teamThatWon + 1));
        tagTeamThatWon.append("(");
        for (int i = 0; i < teams.get(teamThatWon).size(); i++) {
            if (i == 0) {
                tagTeamThatWon.append(players.get(teams.get(teamThatWon).get(i)));
            } else {
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
        } else if (cardsWon.get(1).size() == 52) {
            pointsGame = 8 * the1of2;
            declaredOrDoneGame = "Valat (T2)";
        } else {

            if (teamThatWon == 1) {
                pointsGame = pointsGame * the1of2;
                declaredOrDoneGame = "Team 2 won";
            } else {
                declaredOrDoneGame = "Team 1 won";
            }
        }

        score.getAttribute("Game").setPoints(pointsGame);
        score.getAttribute("Game").setDeclaredOrDone(declaredOrDoneGame);
    }

    public static boolean computeTeam1WonLastRound(ScoreComputationArguments computation) {
        List<Round> rounds = computation.getRounds();
        List<List<Integer>> teams = computation.getTeams();

        int playerThatWonLastRound = rounds.get(11).getPlayerThatWon();
        boolean team1WonLastRound = false;
        for (int i = 0; i < teams.get(0).size(); i++) {
            if (playerThatWonLastRound == teams.get(0).get(i)) {
                team1WonLastRound = true;
                break;
            }
        }

        return team1WonLastRound;
    }

    public static boolean computeDonePopeAtFinish(boolean team1WonLastRound, ScoreComputationArguments computation) {
        List<List<Integer>> teams = computation.getTeams();
        List<Round> rounds = computation.getRounds();
        List<Declaration> declarations = computation.getDeclarations();
        int playerRequest = computation.getPlayerRequest();
        int pope = declarations.get(playerRequest).getPope();

        boolean donePopeAtFinish = false;

        for (int i = 0; i < teams.get(0).size(); i++) {
            if ((team1WonLastRound) && (rounds.get(11).getCard(teams.get(0).get(i)) == 30 + pope * 8))
                donePopeAtFinish = true;
        }

        return donePopeAtFinish;
    }

    public static int computeDonePagatAtFinish(ScoreComputationArguments computation) {
        List<List<Integer>> teams = computation.getTeams();
        List<Round> rounds = computation.getRounds();

        int teamDonePagatAtFinish = -1;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < teams.get(i).size(); j++) {
                if ((rounds.get(11).getCard(teams.get(i).get(j)) == 1) &&
                        (rounds.get(11).getPlayerThatWon() == teams.get(i).get(j))) {
                    teamDonePagatAtFinish = i;
                }
            }
        }

        return teamDonePagatAtFinish;
    }

    public static Pair<Integer, Integer> computeTeamDoneAllPopesAndTeamDoneAllTrullCards(ScoreComputationArguments computation) {
        int teamDoneAllPopes = -1;
        int teamDoneAllTrullCards = -1;
        List<List<Integer>> cardsWon = computation.getCardsWon();

        for (int i = 0; i < 2; i++) {
            int numberOfTrullCards = 0;
            int numberOfPopes = 0;
            for (int j = 0; j < cardsWon.get(i).size(); j++) {
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

        return new Pair<>(teamDoneAllPopes, teamDoneAllTrullCards);
    }

    public static boolean computePopeCaught(ScoreComputationArguments computation) {
        List<List<Integer>> cardsWon = computation.getCardsWon();
        List<Declaration> declarations = computation.getDeclarations();
        int playerRequest = computation.getPlayerRequest();
        int pope = declarations.get(playerRequest).getPope();
        boolean popeCaught = false;

        for (int i = 0; i < cardsWon.get(1).size(); i++) {
            if (cardsWon.get(1).get(i) == 30 + pope * 8) {
                popeCaught = true;
                break;
            }
        }

        return popeCaught;
    }

    public static int computeLunaCaught(ScoreComputationArguments computation) {
        List<List<Integer>> teams = computation.getTeams();
        List<Round> rounds = computation.getRounds();
        int lunaCaughtFromTeam = -1;

        int playerThatGaveLuna = -1;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                if ((rounds.get(i).getCard(j) == 21) && (rounds.get(i).getPlayerThatWon() != j)) {
                    playerThatGaveLuna = j;
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < teams.get(i).size(); j++) {
                if (playerThatGaveLuna == teams.get(i).get(j)) {
                    lunaCaughtFromTeam = i;
                    break;
                }
            }
        }

        return lunaCaughtFromTeam;
    }

    public static void computePointsPagatAtFinish(Score score, int teamDeclaredPagatAtFinish, int teamDonePagatAtFinish) {
        int pointsPagatAtFinish = -1;
        String declaredOrDonePagatAtFinish;
        if (teamDeclaredPagatAtFinish == 0) {
            if (teamDonePagatAtFinish != -1) {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish + 1) + ") and done";
                pointsPagatAtFinish = 7;
            } else {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish + 1) + ") and not done";
                pointsPagatAtFinish = -7;
            }
        } else if (teamDeclaredPagatAtFinish == 1) {
            if (teamDonePagatAtFinish != -1) {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish + 1) + ") and done";
                pointsPagatAtFinish = -7;
            } else {
                declaredOrDonePagatAtFinish = "Declared(T" + (teamDeclaredPagatAtFinish + 1) + ") and not done";
                pointsPagatAtFinish = 7;
            }
        } else {
            if (teamDonePagatAtFinish == 0) {
                declaredOrDonePagatAtFinish = "Done(T" + (teamDonePagatAtFinish + 1) + ")";
                pointsPagatAtFinish = 1;
            } else if (teamDonePagatAtFinish == 1) {
                declaredOrDonePagatAtFinish = "Done(T" + (teamDonePagatAtFinish + 1) + ")";
            } else {
                declaredOrDonePagatAtFinish = "No";
                pointsPagatAtFinish = 0;
            }
        }

        score.getAttribute("Pagat at finish").setPoints(pointsPagatAtFinish);
        score.getAttribute("Pagat at finish").setDeclaredOrDone(declaredOrDonePagatAtFinish);
    }

    public static void computePointsPopeAtFinish(Score score, boolean declaredPopeAtFinish, boolean donePopeAtFinish) {
        int pointsPopeAtFinish;
        String declaredOrDonePopeAtFinish;
        if (declaredPopeAtFinish) {
            if (donePopeAtFinish) {
                declaredOrDonePopeAtFinish = "Declared and done";
                pointsPopeAtFinish = 2;
            } else {
                declaredOrDonePopeAtFinish = "Declared and not done";
                pointsPopeAtFinish = -2;
            }
        } else {
            if (donePopeAtFinish) {
                declaredOrDonePopeAtFinish = "Done";
                pointsPopeAtFinish = 1;
            } else {
                declaredOrDonePopeAtFinish = "No";
                pointsPopeAtFinish = 0;
            }
        }

        score.getAttribute("Pope at finish").setPoints(pointsPopeAtFinish);
        score.getAttribute("Pope at finish").setDeclaredOrDone(declaredOrDonePopeAtFinish);
    }

    public static void computePointsPopeCaught(Score score, boolean popeCaught) {
        int pointsPopeCaught = -1;
        String declaredOrDonePopeCaught;
        if (popeCaught) {
            declaredOrDonePopeCaught = "Yes";
        } else {
            declaredOrDonePopeCaught = "No";
            pointsPopeCaught = 0;
        }

        score.getAttribute("Pope caught").setPoints(pointsPopeCaught);
        score.getAttribute("Pope caught").setDeclaredOrDone(declaredOrDonePopeCaught);
    }

    public static void computePointsLunaCaught(Score score, int lunaCaughtFromTeam) {
        int pointsLunaCaught = -1;
        String declaredOrDoneLunaCaught;
        if (lunaCaughtFromTeam == 0) {
            declaredOrDoneLunaCaught = "Caught by T2";
        } else if (lunaCaughtFromTeam == 1) {
            declaredOrDoneLunaCaught = "Caught by T1";
            pointsLunaCaught = 1;
        } else {
            declaredOrDoneLunaCaught = "No";
            pointsLunaCaught = 0;
        }

        score.getAttribute("Luna caught").setPoints(pointsLunaCaught);
        score.getAttribute("Luna caught").setDeclaredOrDone(declaredOrDoneLunaCaught);
    }

    public static void computeScoreTarocks(Score score, int teamThatWon, int player, ScoreComputationArguments computation) {
        List<List<Integer>> teams = computation.getTeams();
        List<Declaration> declarations = computation.getDeclarations();

        int pointsTarocksPlayer;
        String declaredOrDoneTarocksPlayer;
        int teamPlayer = -1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < teams.get(i).size(); j++) {
                if (teams.get(i).get(j) == player) {
                    teamPlayer = i;
                    break;
                }
            }
        }
        if (declarations.get(player).getNumberOfTarocks() == 8) {
            declaredOrDoneTarocksPlayer = "8T";
            pointsTarocksPlayer = 1;
        } else if (declarations.get(player).getNumberOfTarocks() == 9) {
            declaredOrDoneTarocksPlayer = "9T";
            pointsTarocksPlayer = 2;
        } else if (declarations.get(player).getNumberOfTarocks() == 10) {
            declaredOrDoneTarocksPlayer = "10T";
            pointsTarocksPlayer = 3;
        } else {
            declaredOrDoneTarocksPlayer = "No";
            pointsTarocksPlayer = 0;
        }

        score.getAttribute("Tarocks Player 0" + (player + 1)).setPoints(pointsTarocksPlayer);
        score.getAttribute("Tarocks Player 0" + (player + 1)).setDeclaredOrDone(declaredOrDoneTarocksPlayer);
        if (teamPlayer != teamThatWon) {
            score.getAttribute("Tarocks Player 0" + (player + 1)).setPoints(-pointsTarocksPlayer);
        }
    }

    public static void computeTotal(int teamThatWon, List<Declaration> declarationsTeams, Score score, Game game, ScoreComputationArguments computation) {
        String declarationMessage = getDeclarationMessage(declarationsTeams);

        List<List<Integer>> teams = computation.getTeams();
        int playerRequest = computation.getPlayerRequest();

        if (teamThatWon == 1) {
            for (int i = 1; i < 7; i++) {
                int scorePoints = score.getAttributeByID(i).getPoints();
                score.getAttributeByID(i).setPoints(-scorePoints);
            }
        }

        score.computeTotal();
        game.setRadler(computation.isRadler());
        game.setDeclaration(declarationMessage);
        int total = score.getAttribute("Total").getPoints();
        if (teams.get(0).size() == 1) {
            if (teamThatWon == 0) {
                game.setScorePlayer(playerRequest, total * 3);
                for (int i = 0; i < 4; i++) {
                    if (i != playerRequest) {
                        game.setScorePlayer(i, -total);
                    }
                }
            } else {
                game.setScorePlayer(playerRequest, -total * 3);
                for (int i = 0; i < 4; i++) {
                    if (i != playerRequest) {
                        game.setScorePlayer(i, total);
                    }
                }
            }
        } else {
            if (teamThatWon == 0) {
                for (int i = 0; i < teams.get(0).size(); i++) {
                    game.setScorePlayer(teams.get(0).get(i), total);
                }
                for (int i = 0; i < teams.get(1).size(); i++) {
                    game.setScorePlayer(teams.get(1).get(i), -total);
                }
            } else {
                for (int i = 0; i < teams.get(0).size(); i++) {
                    game.setScorePlayer(teams.get(0).get(i), -total);
                }
                for (int i = 0; i < teams.get(1).size(); i++) {
                    game.setScorePlayer(teams.get(1).get(i), total);
                }
            }
        }
    }

    public static Pair<Game, Score> getScore(ScoreComputationArguments computation) {
        Game game = new Game();
        Score score = new Score();
        score.setRadler(computation.isRadler());

        List<Declaration> declarations = computation.getDeclarations();
        int playerRequest = computation.getPlayerRequest();
        boolean popeInTalon = computation.isPopeInTalon();
        List<List<Integer>> cardsWon = computation.getCardsWon();

        List<Declaration> declarationsTeams = getDeclarationsTeams(computation);
        int pope = declarations.get(playerRequest).getPope();
        if (popeInTalon) {
            swapPope(cardsWon, pope);
        }

        int teamThatWon = getTeamThatWon(cardsWon);
        computeGamePoints(score, teamThatWon, computation);

        //variables declarations
        boolean declaredPopeAtFinish = false;
        int teamDeclaredPagatAtFinish = -1;
        int teamDeclaredAllPopes = -1;
        int teamDeclaredAllTrullCards = -1;

        //get values of variables declaration
        for (int i = 0; i < 2; i++) {
            if (declarationsTeams.get(i).getPopeAtFinish())
                declaredPopeAtFinish = true;
            if (declarationsTeams.get(i).getPagatAtFinish())
                teamDeclaredPagatAtFinish = i;
            if (declarationsTeams.get(i).getAllPopes())
                teamDeclaredAllPopes = i;
            if (declarationsTeams.get(i).getTrull())
                teamDeclaredAllTrullCards = i;
        }

        boolean team1WonLastRound = computeTeam1WonLastRound(computation);
        boolean donePopeAtFinish = computeDonePopeAtFinish(team1WonLastRound, computation);
        int teamDonePagatAtFinish = computeDonePagatAtFinish(computation);

        Pair<Integer, Integer> popesAndTrull = computeTeamDoneAllPopesAndTeamDoneAllTrullCards(computation);
        int teamDoneAllPopes = popesAndTrull.getKey();
        int teamDoneAllTrullCards = popesAndTrull.getValue();
        boolean popeCaught = computePopeCaught(computation);
        int lunaCaughtFromTeam = computeLunaCaught(computation);

        computePointsPagatAtFinish(score, teamDeclaredPagatAtFinish, teamDonePagatAtFinish);
        computePointsPopeAtFinish(score, declaredPopeAtFinish, donePopeAtFinish);
        getPointsMethodWithThreeChecks("All popes", teamDeclaredAllPopes, teamDoneAllPopes, score);
        getPointsMethodWithThreeChecks("All trull cards", teamDeclaredAllTrullCards, teamDoneAllTrullCards, score);
        computePointsPopeCaught(score, popeCaught);
        computePointsLunaCaught(score, lunaCaughtFromTeam);

        for (int i = 0; i < 4; i++) {
            computeScoreTarocks(score, teamThatWon, i, computation);
        }

        computeTotal(teamThatWon, declarationsTeams, score, game, computation);

        return new Pair<>(game, score);
    }
}
