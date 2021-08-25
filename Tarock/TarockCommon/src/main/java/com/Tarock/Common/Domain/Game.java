package com.Tarock.Common.Domain;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class Game {
    private int sessionID;
    private int scorePlayer1;
    private int scorePlayer2;
    private int scorePlayer3;
    private int scorePlayer4;
    private String declaration;
    private int radler;
    private int radlerTimes;

    public void setScorePlayer(int player, int score) {
        if (player == 0)
            setScorePlayer1(score);
        else if (player == 1)
            setScorePlayer2(score);
        else if (player == 2)
            setScorePlayer3(score);
        else if (player == 3)
            setScorePlayer4(score);
    }

    public int getScorePlayer(int player) {
        if (player == 0)
            return getScorePlayer1();
        else if (player == 1)
            return getScorePlayer2();
        else if (player == 2)
            return getScorePlayer3();
        else if (player == 3)
            return getScorePlayer4();
        return 0;
    }

    public Map<String, String> getMap(){
        Map<String, String> map = new HashMap<>();
        map.put("sessionID", String.valueOf(sessionID));
        map.put("scorePlayer1", String.valueOf(scorePlayer1));
        map.put("scorePlayer2", String.valueOf(scorePlayer2));
        map.put("scorePlayer3", String.valueOf(scorePlayer3));
        map.put("scorePlayer4", String.valueOf(scorePlayer4));
        map.put("declaration", declaration);
        map.put("radler", String.valueOf(radler));
        map.put("radlerTimes", String.valueOf(radlerTimes));
        return map;
    }
}
