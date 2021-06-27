package Repository;

import Domain.User;
import Exception.RepositoryException;

public class Repository {
    public static boolean loggedIn;
    public static User loggedUser;

    public Repository()
    {
        loggedIn = false;
        loggedUser = null;
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
