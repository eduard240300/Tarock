package com.TarockServer.Service;

import com.TarockServer.Domain.Pair;

import java.util.ArrayList;
import java.util.List;

public class DataManipulationService {
    public static String processName(String name){
        StringBuilder newName = new StringBuilder();
        int i;
        for(i=0;i<name.length();i++)
        {
            if (name.charAt(i) == ' ')
                newName.append('_');
            else
                newName.append(name.charAt(i));
        }
        return newName.toString();
    }

    public static List<String> processMessage(String message)
    {
        List<String> listOfCommands = new ArrayList<>();
        int i;
        StringBuilder currentCommand = new StringBuilder();
        for(i=0;i<message.length();i++)
        {
            if (message.charAt(i) == ';')
            {
                listOfCommands.add(currentCommand.toString());
                currentCommand = new StringBuilder();
            }
            else currentCommand.append(message.charAt(i));
        }
        return listOfCommands;
    }

    public static List<String> processCommand(String command) {
        List<String> processedCommand = new ArrayList<>();
        StringBuilder object = new StringBuilder();
        int i;
        for (i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                processedCommand.add(object.toString());
                object = new StringBuilder();
            } else {
                object.append(command.charAt(i));
            }
        }
        if (!object.toString().equals(""))
            processedCommand.add(object.toString());
        return processedCommand;
    }

    public static String getName(String name){
        StringBuilder newName = new StringBuilder();
        int i;
        for(i=0;i<name.length();i++)
        {
            if (name.charAt(i) == '_')
                newName.append(' ');
            else if (name.charAt(i) == '.')
                newName.append(':');
            else
                newName.append(name.charAt(i));
        }
        return newName.toString();
    }

    public static boolean stringToBool(String string)
    {
        boolean theBoolean = true;
        if (string.equals("0"))
            theBoolean = false;
        return theBoolean;
    }

    public static String boolToString(boolean theBoolean)
    {
        String string = "1";
        if (!theBoolean)
            string = "0";
        return string;
    }

    public static List<Pair<String, String>> JSONtoList(String inputJSON)
    {
        boolean isKey = true;
        boolean isInQuote = false;

        StringBuilder message = new StringBuilder();
        String key = "";
        String value = "";

        List<Pair<String, String>> output = new ArrayList<>();
        for(int i=0;i<inputJSON.length();i++)
        {
            if (inputJSON.charAt(i) == '"')
            {
                isInQuote = !isInQuote;
                if (!isInQuote)
                {
                    if (isKey)
                        key = message.toString();
                    else
                        value = message.toString();
                    message = new StringBuilder();
                }
            }
            else if (inputJSON.charAt(i) == ':')
            {
                isKey = false;
            }
            else if (inputJSON.charAt(i) == ',')
            {
                isKey = true;
                Pair<String, String> pair = new Pair<>(key, value);
                output.add(pair);
            }
            else if (isInQuote)
            {
                if (inputJSON.charAt(i) == '*')
                    message.append(',');
                else if (inputJSON.charAt(i) != '\\')
                    message.append(inputJSON.charAt(i));
            }
        }
        Pair<String, String> pair = new Pair<>(key, value);
        output.add(pair);
        return output;
    }

    public static String eliminateNewLines(String line){
        StringBuilder result = new StringBuilder();
        for(int i=0;i<line.length();i++)
        {
            if (line.charAt(i) != '\n')
                result.append(line.charAt(i));
        }
        return result.toString();
    }

    public static String processDeclaration(String declaration){
        StringBuilder newName = new StringBuilder();
        int i;
        for(i=0;i<declaration.length();i++)
        {
            if (declaration.charAt(i) == ' ')
                newName.append('_');
            else if (declaration.charAt(i) == ',')
                newName.append('*');
            else
                newName.append(declaration.charAt(i));
        }
        return newName.toString();
    }
}