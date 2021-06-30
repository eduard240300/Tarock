package Service;

import DBManager.DBManager;
import Domain.User;
import Exception.DBException;
import Exception.LoginException;

public class LoginService {
    public LoginService() {}

    public void login(User inputUser)
    {
        User dbUser;
        try{
            dbUser = DBManager.getUser(inputUser.getUsername());
            if (BCrypt.checkpw(inputUser.getPassword(), dbUser.getPassword())){
                return;
            }
            else
            {
                throw new LoginException("Wrong password !");
            }
        }
        catch (DBException dbException) {
            throw new LoginException(dbException.getMessage());
        }
    }
}
