package com.Tarock.Common.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class ScoreComputationArguments {
    private int playerRequest;
    private List<String> players;
    private List<List<Integer>> teams;
    private List<Round> rounds;
    private List<Declaration> declarations;
    private List<List<Integer>> cardsWon;
    private boolean popeInTalon;
    private boolean isRadler;
}
