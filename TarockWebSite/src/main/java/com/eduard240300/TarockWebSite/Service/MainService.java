package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Repository.Repository;

public class MainService {
    private Repository repo;

    public MainService() {
        repo = new Repository();
    }

    public void setLoggedIn(User user)
    {
        repo.login(user);
    }

    public void setLoggedOut()
    {
        repo.logout();
    }
}
