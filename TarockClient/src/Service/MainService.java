package Service;

import Domain.User;
import Repository.Repository;

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
