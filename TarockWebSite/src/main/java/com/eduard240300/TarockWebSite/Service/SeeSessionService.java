package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.DBManager.DBManager;
import com.eduard240300.TarockWebSite.Exception.DBException;
import com.eduard240300.TarockWebSite.Exception.SeeSessionException;

public class SeeSessionService {
    public static void existsSession(String username, int sessionID) {
        try{
            DBManager.existsSession(username, sessionID);
        }
        catch (DBException dbException)
        {
            throw new SeeSessionException(dbException.getMessage());
        }
    }
}
