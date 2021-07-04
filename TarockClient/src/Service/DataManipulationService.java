package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static String getName(String name){
        String newName = "";
        int i;
        for(i=0;i<name.length();i++)
        {
            if (name.charAt(i) == '_')
                newName += ' ';
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

    public static String[][] transformStringArray(List<List<String>> input)
    {
        String[][] nestedArray = input
                // using the stream API
                .stream()
                // mapping each `List`...
                .map(
                        // ... to a resulting array
                        (l) -> l.toArray(new String[l.size()])
                )
                // collecting as a List<String[]>
                .collect(Collectors.toList())
                // converting the resulting List<String[]> to a String[][]
                .toArray(new String[input.size()][]);
        return nestedArray;
    }
}
