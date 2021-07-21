package com.Tarock.Client.Service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataManipulationService {
    public static boolean containsSpaces(String message)
    {
        for(int i=0;i<message.length();i++)
        {
            if (message.charAt(i) == ' ')
                return true;
        }
        return false;
    }

    public static String truncateClassName(String className){
        int i;
        StringBuilder result = new StringBuilder();
        i = className.length()-1;
        while(className.charAt(i) != '.')
            i--;
        int j;
        for(j=i+1;j<className.length();j++)
            result.append(className.charAt(j));
        return result.toString();
    }

    public static String getName(String name){
        StringBuilder newName = new StringBuilder();
        int i;
        for(i=0;i<name.length();i++)
        {
            if (name.charAt(i) == '_')
                newName.append(' ');
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

    public static File getFile(String path){
        try{
            InputStream inputStream = DataManipulationService.class.getClassLoader().getResourceAsStream(path);
            File tempFile = File.createTempFile("new", "file");
            FileUtils.copyInputStreamToFile(inputStream, tempFile);
            return tempFile;
        }
        catch (Exception e) {}
        return null;
    }

    public static InputStream getInputStream(String path){
        return DataManipulationService.class.getClassLoader().getResourceAsStream(path);
    }
}
