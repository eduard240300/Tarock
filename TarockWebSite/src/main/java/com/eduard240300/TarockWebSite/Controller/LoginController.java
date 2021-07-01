package com.eduard240300.TarockWebSite.Controller;

import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Service.DataManipulationService;
import com.eduard240300.TarockWebSite.Service.LoginService;
import com.eduard240300.TarockWebSite.Validator.UserValidator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;

public class LoginController extends HttpServlet {

    public LoginController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = null;

        String username = request.getParameter("login_username");
        String password = request.getParameter("login_password");

        User inputUser = new User(username, password);
        HttpSession session = request.getSession();
        String finalMessage = "";

        Cookie loggedIn = new Cookie("loggedIn", "false");

        try {
            UserValidator.validateUser(inputUser);
            User user = LoginService.login(inputUser);
            loggedIn = new Cookie("loggedIn", "true");
            Cookie cookieUsername = new Cookie("username", user.getUsername());
            Cookie cookieName = new Cookie("name", DataManipulationService.processName(user.getName()));
            Cookie cookieEmail = new Cookie("email", user.getEmail());
            loggedIn.setMaxAge(24*60*60);
            cookieUsername.setMaxAge(24*60*60);
            cookieName.setMaxAge(24*60*60);
            cookieEmail.setMaxAge(24*60*60);
            response.addCookie(loggedIn);
            response.addCookie(cookieUsername);
            response.addCookie(cookieName);
            response.addCookie(cookieEmail);
        }
        catch (RuntimeException exception)
        {
            finalMessage = "<div class='alert alert-danger'>";
            finalMessage += DataManipulationService.truncateClassName(exception.getClass().getName()) + " : " + exception.getMessage();
            finalMessage += "</div>";
        }
        if (loggedIn.getComment() == "true") {
            finalMessage = "<div class='alert alert-success'>Logged in successfully !</div>";
        }

        session.setAttribute("login_error_message", finalMessage);
        rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
}