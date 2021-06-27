package Repository;

import Domain.User;
import Exception.RepositoryException;

public class Repository {
    private static boolean loggedIn;
    private static User loggedUser;

    public Repository()
    {
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
