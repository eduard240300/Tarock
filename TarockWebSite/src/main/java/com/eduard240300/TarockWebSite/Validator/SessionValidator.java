package com.eduard240300.TarockWebSite.Validator;

import com.eduard240300.TarockWebSite.DBManager.DBManager;
import com.eduard240300.TarockWebSite.Domain.Session;
import com.eduard240300.TarockWebSite.Exception.DBException;
import com.eduard240300.TarockWebSite.Exception.ValidationException;
import com.eduard240300.TarockWebSite.Service.DataManipulationService;

public class SessionValidator {
    public static void validateSession(Session session)
    {
        String message = "";
        if (DataManipulationService.containsChar(session.getPlayer1(), ' '))
            message = message + "Player1 can't contain spaces ! ";
        else if (session.getPlayer1().equals(""))
            message = message + "Player1 can't be empty ! ";
        else {
            try {
                DBManager.getUser(session.getPlayer1());
            }
            catch (DBException dbException)
            {
                message = message + "Player1 isn't a valid username ! ";
            }
        }

        if (DataManipulationService.containsChar(session.getPlayer2(), ' '))
            message = message + "Player2 can't contain spaces ! ";
        else if (session.getPlayer2().equals(""))
            message = message + "Player2 can't be empty ! ";
        else {
            try {
                DBManager.getUser(session.getPlayer2());
            }
            catch (DBException dbException)
            {
                message = message + "Player2 isn't a valid username ! ";
            }
        }

        if (DataManipulationService.containsChar(session.getPlayer3(), ' '))
            message = message + "Player3 can't contain spaces ! ";
        else if (session.getPlayer3().equals(""))
            message = message + "Player3 can't be empty ! ";
        else {
            try {
                DBManager.getUser(session.getPlayer3());
            }
            catch (DBException dbException)
            {
                message = message + "Player3 isn't a valid username ! ";
            }
        }

        if (DataManipulationService.containsChar(session.getPlayer4(), ' '))
            message = message + "Player4 can't contain spaces ! ";
        else if (session.getPlayer4().equals(""))
            message = message + "Player4 can't be empty ! ";
        else {
            try {
                DBManager.getUser(session.getPlayer4());
            }
            catch (DBException dbException)
            {
                message = message + "Player4 isn't a valid username ! ";
            }
        }

        if ((session.getPlayer1().equals(session.getPlayer2())) ||
                (session.getPlayer1().equals(session.getPlayer3())) ||
                (session.getPlayer1().equals(session.getPlayer4())) ||
                (session.getPlayer2().equals(session.getPlayer3())) ||
                (session.getPlayer2().equals(session.getPlayer4())) ||
                (session.getPlayer3().equals(session.getPlayer4())))
            message = "Players can't repeat !";

        if (message != "")
            throw new ValidationException(message);
    }
}
