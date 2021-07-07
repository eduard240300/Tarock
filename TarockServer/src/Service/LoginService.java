package Service;

import ConnectionManager.PHPConnection;
import Domain.User;
import Exception.DBException;
import Exception.LoginException;

public class LoginService {
    public static User login(User inputUser)
    {
        User dbUser;
        try{
            dbUser = PHPConnection.getUser(inputUser.getUsername());
            if (BCrypt.checkpw(inputUser.getPassword(), dbUser.getPassword())){
                return dbUser;
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
