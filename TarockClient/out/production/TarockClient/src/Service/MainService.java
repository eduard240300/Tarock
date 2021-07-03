package Service;

import Domain.User;
import Repository.Repository;

public class MainService {
    public static CommunicationService communicationService = null;

    public MainService() {
        communicationService = new CommunicationService();
        communicationService.start();
    }

    public static void setLoggedIn(User user)
    {
        Repository.login(user);
    }

    public static void setPlayerMode(String playerMode)
    {
        CommunicationService.playerMode = playerMode;
    }

    public static void setLoggedOut()
    {
        Repository.logout();
    }

    public void setSessionID(int sessionID) { Repository.sessionID = sessionID;}
}
