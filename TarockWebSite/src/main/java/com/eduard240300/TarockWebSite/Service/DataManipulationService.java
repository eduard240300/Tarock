package com.eduard240300.TarockWebSite.Service;

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
}
