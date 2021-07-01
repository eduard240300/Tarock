package Service;

import Domain.User;
import Exception.DBException;
import Exception.LoginException;

public class LoginService {
    public CommunicationService communicationService = null;

    public LoginService()
    {
        communicationService = new CommunicationService();
        communicationService.start();
    }

    public void login(User user, int sessionID)
    {
        CommunicationService.sessionID = sessionID;
        CommunicationService.user = user;
    }
}
