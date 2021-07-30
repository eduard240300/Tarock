package com.Tarock.Common.Domain;

import java.util.List;

public class ScoreComputationArguments {
    private final int playerRequest;
    private final List<String> players;
    private final List<List<Integer>> teams;
    private final List<Round> rounds;
    private final List<Declaration> declarations;
    private final List<List<Integer>> cardsWon;
    private final boolean popeInTalon;
    private final boolean isRadler;

    public ScoreComputationArguments(int playerRequest, List<String> players, List<List<Integer>> teams, List<Round> rounds, List<Declaration> declarations, List<List<Integer>> cardsWon, boolean popeInTalon, boolean isRadler) {
        this.playerRequest = playerRequest;
        this.players = players;
        this.teams = teams;
        this.rounds = rounds;
        this.declarations = declarations;
        this.cardsWon = cardsWon;
        this.popeInTalon = popeInTalon;
        this.isRadler = isRadler;
    }

    public int getPlayerRequest() {
        return playerRequest;
    }

    public List<String> getPlayers() {
        return players;
    }

    public List<List<Integer>> getTeams() {
        return teams;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public List<List<Integer>> getCardsWon() {
        return cardsWon;
    }

    public boolean isPopeInTalon() {
        return popeInTalon;
    }

    public boolean isRadler() {
        return isRadler;
    }
}
