package Repository;

import Domain.User;
import Exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    public static boolean loggedIn;
    public static User loggedUser;
    public static int sessionID;
    public static List<String> players;
    public static List<Integer> cards;
    public static int chair;

    public static void initRepository()
    {
        loggedIn = false;
        loggedUser = null;
        players = new ArrayList<String>();
        cards = new ArrayList<Integer>();
        for(int i=0;i<12;i++)
            cards.add(0);
        players.add("Player 1");
        players.add("Player 2");
        players.add("Player 3");
        players.add("Player 4");
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
