package com.TarockServer.Service;

import com.TarockServer.Domain.Pair;

import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

public class DataManipulationService {
    public static String truncateClassName(String className){
        int i;
        String result = "";
        i = className.length()-1;
        while(className.charAt(i) != '.')
            i--;
        int j;
        for(j=i+1;j<className.length();j++)
            result += className.charAt(j);
        return result;
    }

    public static String processName(String name){
        String newName = "";
        int i;
        for(i=0;i<name.length();i++)
        {
            if (name.charAt(i) == ' ')
                newName += '_';
            else
                newName += name.charAt(i);
        }
        return newName;
    }

    public static boolean containsChar(String input, char charInput)
    {
        int i;
        for(i=0;i<input.length();i++) {
            if (input.charAt(i) == charInput) {
                return true;
            }
        }
        return false;
    }

    public static List<String> processMessage(String message)
    {
        List<String> listOfCommands = new ArrayList<String>();
        int i;
        String currentCommand = "";
        for(i=0;i<message.length();i++)
        {
            if (message.charAt(i) == ';')
            {
                listOfCommands.add(currentCommand);
                currentCommand = "";
            }
            else currentCommand = currentCommand + message.charAt(i);
        }
        return listOfCommands;
    }

    public static List<String> processCommand(String command) {
        List<String> processedCommand = new ArrayList<String>();
        String object = "";
        int i;
        for (i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                processedCommand.add(object);
                object = "";
            } else {
                object = object + command.charAt(i);
            }
        }
        if (object != "")
            processedCommand.add(object);
        return processedCommand;
    }

    public static String getName(String name){
        String newName = "";
        int i;
        for(i=0;i<name.length();i++)
        {
            if (name.charAt(i) == '_')
                newName += ' ';
            else if (name.charAt(i) == '.')
                newName += ':';
            else
                newName += name.charAt(i);
        }
        return newName;
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
        if (theBoolean == false)
            string = "0";
        return string;
    }

    public static List<Pair<String, String>> JSONtoList(String inputJSON)
    {
        boolean isKey = true;
        boolean isInQuote = false;

        StringBuilder message = new StringBuilder("");
        String key = "";
        String value = "";

        List<Pair<String, String>> output = new ArrayList<Pair<String, String>>();
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
                    message = new StringBuilder("");
                }
            }
            else if (inputJSON.charAt(i) == ':')
            {
                isKey = false;
            }
            else if (inputJSON.charAt(i) == ',')
            {
                isKey = true;
                Pair<String, String> pair = new Pair<String, String>(key, value);
                output.add(pair);
            }
            else if (isInQuote)
            {
                if (inputJSON.charAt(i) != '\\')
                    message.append(inputJSON.charAt(i));
            }
        }
        Pair<String, String> pair = new Pair<String, String>(key, value);
        output.add(pair);
        return output;
    }
}
