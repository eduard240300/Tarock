package com.eduard240300.TarockWebSite.Controller;

import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Repository.Repository;
import com.eduard240300.TarockWebSite.Service.*;
import com.eduard240300.TarockWebSite.Validator.UserValidator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterController extends HttpServlet {

    public RegisterController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = null;

        String name = request.getParameter("register_name");
        String username = request.getParameter("register_username");
        String password = request.getParameter("register_password");
        String repeatPassword = request.getParameter("register_password_repeat");
        String email = request.getParameter("register_email");

        HttpSession session = request.getSession();
        String finalMessage;

        if (!password.equals(repeatPassword))
            finalMessage = "<div class='alert alert-danger'>ValidationException : The passwords don't match !</div>";
        else
        {
            RegisterService registerService = new RegisterService();
            UserValidator userValidator = new UserValidator();

            User inputUser = new User(name, username, password, email);
            try {
                userValidator.validateUser(inputUser);
                registerService.register(inputUser);
                finalMessage = "<div class='alert alert-success'>User registered successfully !</div>";
            }
            catch (RuntimeException exception)
            {
                finalMessage = "<div class='alert alert-danger'>";
                finalMessage += DataManipulationService.truncateClassName(exception.getClass().getName()) + " : " + exception.getMessage();
                finalMessage += "</div>";
            }
        }
        session.setAttribute("register_error_message", finalMessage);
        rd = request.getRequestDispatcher("/register.jsp");
        rd.forward(request, response);
    }
}