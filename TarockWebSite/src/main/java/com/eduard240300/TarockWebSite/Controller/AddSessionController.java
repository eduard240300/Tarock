package com.eduard240300.TarockWebSite.Controller;

import com.eduard240300.TarockWebSite.Domain.Session;
import com.eduard240300.TarockWebSite.Service.AddSessionService;
import com.eduard240300.TarockWebSite.Service.CookieService;
import com.eduard240300.TarockWebSite.Service.DataManipulationService;
import com.eduard240300.TarockWebSite.Validator.SessionValidator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;

public class AddSessionController extends HttpServlet {

    public AddSessionController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = null;

        String player1 = request.getParameter("addSession_player1");
        String player2 = request.getParameter("addSession_player2");
        String player3 = request.getParameter("addSession_player3");
        String player4 = request.getParameter("addSession_player4");

        String now = new Timestamp(System.currentTimeMillis()).toString();
        Session newSession = new Session(CookieService.getCookie(request, "username").getValue(), now, player1, player2, player3, player4);

        HttpSession session = request.getSession();
        String finalMessage = "";

        try {
            SessionValidator.validateSession(newSession);
            AddSessionService.addSession(newSession);
            finalMessage = "<div class='alert alert-success'>Session added successfully !</div>";
        }
        catch (RuntimeException exception)
        {
            finalMessage = "<div class='alert alert-danger'>";
            finalMessage += DataManipulationService.truncateClassName(exception.getClass().getName()) + " : " + exception.getMessage();
            finalMessage += "</div>";
        }

        session.setAttribute("addSession_error_message", finalMessage);
        rd = request.getRequestDispatcher("/addSession.jsp");
        rd.forward(request, response);
    }
}