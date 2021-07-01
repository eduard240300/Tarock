package Service;

import java.util.ArrayList;
import java.util.List;

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
}
