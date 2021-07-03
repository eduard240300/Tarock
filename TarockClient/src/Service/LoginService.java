package Service;

import Domain.User;

public class LoginService {

    public LoginService() { }

    public void login(User user, int sessionID)
    {
        CommunicationService.sessionID = sessionID;
        CommunicationService.user = user;
    }
}
