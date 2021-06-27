package Repository;

import Domain.User;
import Exception.RepositoryException;
import GUI.Template.JImage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    public static boolean loggedIn;
    public static User loggedUser;
    public static List<JImage> currentCards;
    public static List<JLabel> playerNames;

    public Repository()
    {
        currentCards = new ArrayList<JImage>();
        playerNames = new ArrayList<JLabel>();
        loggedIn = false;
        loggedUser = null;
    }

    public void login(User newUser)
    {
        if (loggedIn == true)
            throw new RepositoryException("Already logged in !");
        else
        {
            loggedIn = true;
            loggedUser = newUser;
        }
    }

    public void logout()
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
