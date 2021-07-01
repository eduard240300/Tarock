package com.eduard240300.TarockWebSite.Controller;

import com.eduard240300.TarockWebSite.Service.*;
import com.eduard240300.TarockWebSite.Validator.IntegerValidator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class SeeSessionController extends HttpServlet {

    public SeeSessionController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = null;

        String potentialID = request.getParameter("seeSession_id");

        HttpSession session = request.getSession();
        String finalMessage = "";

        try {
            IntegerValidator.validateInteger(potentialID);
            SeeSessionService.existsSession(CookieService.getCookie(request, "username").getValue(), Integer.parseInt(potentialID));
            session.setAttribute("sessionID", potentialID);
        }
        catch (RuntimeException exception) {
            if (session.getAttribute("sessionID") != null)
                session.removeAttribute("sessionID");
        }

        rd = request.getRequestDispatcher("/seeGames.jsp");
        rd.forward(request, response);
    }
}