package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.DBManager.DBManager;
import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Exception.DBException;
import com.eduard240300.TarockWebSite.Exception.LoginException;

public class LoginService {
    public static User login(User inputUser)
    {
        User dbUser;
        try{
            dbUser = DBManager.getUser(inputUser.getUsername());
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
