package Service;

import Domain.User;
import Exception.DBException;
import Exception.LoginException;

public class LoginService {

    public LoginService() { }

    public void login(User user, int sessionID)
    {
        CommunicationService.sessionID = sessionID;
        CommunicationService.user = user;
    }
}
