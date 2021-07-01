package Service;

import Domain.User;
import Repository.Repository;

public class MainService {

    public MainService() {}

    public void setLoggedIn(User user)
    {
        Repository.login(user);
    }

    public void setLoggedOut()
    {
        Repository.logout();
    }

    public void setSessionID(int sessionID) { Repository.sessionID = sessionID;}
}
