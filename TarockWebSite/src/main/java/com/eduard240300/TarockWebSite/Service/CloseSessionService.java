package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.DBManager.DBManager;
import com.eduard240300.TarockWebSite.Exception.CloseSessionException;
import com.eduard240300.TarockWebSite.Exception.DBException;

public class CloseSessionService {
    public static void closeSession(int sessionID, String closeTime, String username) {
        try{
            DBManager.existsSession(username, sessionID);
            DBManager.closeSession(sessionID, closeTime);
        }
        catch (DBException dbException) {
            throw new CloseSessionException(dbException.getMessage());
        }
    }
}
