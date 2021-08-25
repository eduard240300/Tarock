package com.Tarock.Common.Service;

import com.Tarock.Common.Domain.Declaration;
import com.Tarock.Common.Domain.Game;
import com.Tarock.Common.Domain.Pair;
import com.Tarock.Common.Domain.Score;

import java.util.ArrayList;
import java.util.List;

public class DataManipulationService {
    public static String processName(String name) {
        return name.replace(' ', '_');
    }

    public static List<String> processMessage(String message) {
        List<String> listOfCommands = new ArrayList<>();
        StringBuilder currentCommand = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (c == ';') {
                listOfCommands.add(currentCommand.toString());
                currentCommand = new StringBuilder();
            } else currentCommand.append(c);
        }
        return listOfCommands;
    }

    public static List<String> processCommand(String command) {
        List<String> processedCommand = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : command.toCharArray()) {
            if (c == ' ') {
                processedCommand.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            } else {
                stringBuilder.append(c);
            }
        }
        if (!stringBuilder.toString().equals(""))
            processedCommand.add(stringBuilder.toString());
        return processedCommand;
    }

    public static String getName(String name) {
        return name.replace('_', ' ').replace('.', ':');
    }

    public static boolean stringToBool(String string) {
        return !string.equals("0");
    }

    public static String boolToString(boolean bool) {
        return bool ? "1" : "0";
    }

    public static List<Pair<String, String>> JSONtoList(String inputJSON) {
        boolean isKey = true;
        boolean isInQuote = false;

        StringBuilder message = new StringBuilder();
        String key = "";
        String value = "";

        List<Pair<String, String>> output = new ArrayList<>();
        for (char c : inputJSON.toCharArray()) {
            if (c == '"') {
                isInQuote = !isInQuote;
                if (!isInQuote) {
                    if (isKey)
                        key = message.toString();
                    else
                        value = message.toString();
                    message = new StringBuilder();
                }
            } else if (c == ':') {
                isKey = false;
            } else if (c == ',') {
                isKey = true;
                Pair<String, String> pair = new Pair<>(key, value);
                output.add(pair);
            } else if (isInQuote) {
                if (c == '*')
                    message.append(',');
                else if (c != '\\')
                    message.append(c);
            }
        }
        Pair<String, String> pair = new Pair<>(key, value);
        output.add(pair);
        return output;
    }

    public static String processDeclaration(String declaration) {
        return declaration.replace(' ', '_').replace(',', '*');
    }

    public static String getListOfInteger(List<Integer> cards, String startMessage)
    {
        StringBuilder message = new StringBuilder(startMessage);
        for(Integer card : cards)
        {
            message.append(" ").append(card);
        }
        message.append(";");
        return message.toString();
    }

    public static String getRespondedDeclaration(int player, List<Declaration> declarations){
        return "respondedDeclaration " + player + " " +
                processName(declarations.get(player).toString()) + " " +
                declarations.get(player).toSendableObject() + ";";
    }

    public static String getScore(Game game)
    {
        StringBuilder message = new StringBuilder("score");
        for (int i = 0; i < 4; i++) {
            message.append(" ").append(game.getScorePlayer(i));
        }
        String declaration = DataManipulationService.processName(game.getDeclaration());
        message.append(" ").append(declaration).append(" ");
        String radler = boolToString(game.getRadler());
        message.append(radler).append(";");
        return message.toString();
    }

    public static String getScoreDetailed(Score score){
        return "scoreDetailed " + score.toString() + " " + processName(score.getTeamThatWon()) + ";";
    }
}