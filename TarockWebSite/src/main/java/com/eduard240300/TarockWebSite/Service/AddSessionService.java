package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.DBManager.DBManager;
import com.eduard240300.TarockWebSite.Domain.Session;
import com.eduard240300.TarockWebSite.Exception.AddSessionException;
import com.eduard240300.TarockWebSite.Exception.DBException;

public class AddSessionService {
    public static void addSession(Session session)
    {
        try{
            DBManager.addSession(session);
        }
        catch (DBException dbException) {
            throw new AddSessionException(dbException.getMessage());
        }
    }
}
