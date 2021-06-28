package com.eduard240300.TarockWebSite.Repository;

import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Exception.RepositoryException;

public class Repository {
    public static boolean loggedIn;
    public static User loggedUser;

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
