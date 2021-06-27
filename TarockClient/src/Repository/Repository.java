package Repository;

import Domain.User;
import Exception.RepositoryException;
import GUI.Template.ClickableImage;
import GUI.Template.CustomJButton;
import GUI.Template.JImage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    public static boolean loggedIn;
    public static User loggedUser;
    public static List<JImage> currentCards;
    public static List<JLabel> labelPlayerNames;
    public static List<String> playerNames;
    public static List<ClickableImage> playerCards;
    public static List<JLabel> statusPlayers;
    public static CustomJButton the1of2Button;
    public static CustomJButton passButton;
    public static CustomJButton cancelGameButton;

    public Repository()
    {
        currentCards = new ArrayList<JImage>();
        labelPlayerNames = new ArrayList<JLabel>();
        playerNames = new ArrayList<String>();
        playerNames.add(""); //Player1
        playerNames.add(""); //Player2
        playerNames.add(""); //Player3
        playerNames.add(""); //Player4
        playerCards = new ArrayList<ClickableImage>();
        statusPlayers = new ArrayList<JLabel>();
        loggedIn = false;
        loggedUser = null;
    }

    public static void setPlayerName(int ID, String playerName)
    {
        playerNames.set(ID-1, playerName);
        labelPlayerNames.get(ID-1).setText(String.valueOf(ID) + ". " + playerName);
    }

    public static void login(User newUser)
    {
        if (loggedIn == true)
            throw new RepositoryException("Already logged in !");
        else
        {
            loggedIn = true;
            loggedUser = newUser;
        }
    }

    public static void logout()
    {
        if (loggedIn == false)
            throw new RepositoryException("Not logged in !");
        else
        {
            loggedIn = false;
            loggedUser = null;
        }
    }
}
