package com.TarockServer.Validator;

import com.TarockServer.ConnectionManager.PHPConnection;
import com.TarockServer.Domain.Session;
import com.TarockServer.Exception.PHPException;
import com.TarockServer.Exception.ValidationException;
import com.TarockServer.Service.DataManipulationService;

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
                PHPConnection.getUser(session.getPlayer1());
            }
            catch (PHPException dbException)
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
                PHPConnection.getUser(session.getPlayer2());
            }
            catch (PHPException dbException)
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
                PHPConnection.getUser(session.getPlayer3());
            }
            catch (PHPException dbException)
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
                PHPConnection.getUser(session.getPlayer4());
            }
            catch (PHPException dbException)
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
