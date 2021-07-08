package com.TarockServer.Service;

import com.TarockServer.ConnectionManager.PHPConnection;
import com.TarockServer.Domain.User;
import com.TarockServer.Exception.LoginException;
import com.TarockServer.Exception.PHPException;

public class LoginService {
    public static User login(User inputUser)
    {
        User dbUser;
        try{
            dbUser = PHPConnection.getUser(inputUser.getUsername());
            if (PHPConnection.verifyPassword(inputUser.getUsername(), inputUser.getPassword())){
                return dbUser;
            }
            else
            {
                throw new LoginException("Wrong password !");
            }
        }
        catch (PHPException dbException) {
            throw new LoginException(dbException.getMessage());
        }
    }
}
