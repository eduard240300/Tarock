package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.DBManager.DBManager;
import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Exception.DBException;
import com.eduard240300.TarockWebSite.Exception.RegisterException;

public class RegisterService {
    public static void register(User inputUser)
    {
        try{
            DBManager.registerUser(inputUser);
        }
        catch (DBException dbException) {
            throw new RegisterException(dbException.getMessage());
        }
    }
}
