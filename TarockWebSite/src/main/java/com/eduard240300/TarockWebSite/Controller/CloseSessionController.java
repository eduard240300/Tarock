package com.eduard240300.TarockWebSite.Controller;

import com.eduard240300.TarockWebSite.Domain.Session;
import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Repository.Repository;
import com.eduard240300.TarockWebSite.Service.*;
import com.eduard240300.TarockWebSite.Validator.IntegerValidator;
import com.eduard240300.TarockWebSite.Validator.SessionValidator;
import com.eduard240300.TarockWebSite.Validator.UserValidator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;

public class CloseSessionController extends HttpServlet {

    public CloseSessionController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = null;

        String sessionID = request.getParameter("closeSession_sessionID");
        String now = new Timestamp(System.currentTimeMillis()).toString();

        HttpSession session = request.getSession();
        String finalMessage = "";

        try {
            IntegerValidator.validateInteger(sessionID);
            String username = CookieService.getCookie(request, "username").getValue();
            CloseSessionService.closeSession(Integer.parseInt(sessionID), now, username);
            finalMessage = "<div class='alert alert-success'>Session closed successfully !</div>";
        }
        catch (RuntimeException exception)
        {
            finalMessage = "<div class='alert alert-danger'>";
            finalMessage += DataManipulationService.truncateClassName(exception.getClass().getName()) + " : " + exception.getMessage();
            finalMessage += "</div>";
        }

        session.setAttribute("closeSession_error_message", finalMessage);
        rd = request.getRequestDispatcher("/closeSession.jsp");
        rd.forward(request, response);
    }
}