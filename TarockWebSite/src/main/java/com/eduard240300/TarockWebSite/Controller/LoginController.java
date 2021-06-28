package com.eduard240300.TarockWebSite.Controller;

/**
 * Created by forest.
 */


import com.eduard240300.TarockWebSite.DBManager.DBManager;
import com.eduard240300.TarockWebSite.Repository.Repository;
import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Service.BCrypt;
import com.eduard240300.TarockWebSite.Service.LoginService;
import com.eduard240300.TarockWebSite.Service.MainService;
import com.eduard240300.TarockWebSite.Validator.UserValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginController extends HttpServlet {

    public LoginController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = null;

        String username = request.getParameter("user_username");
        String password = request.getParameter("user_password");

        LoginService loginService = null;
        UserValidator userValidator = new UserValidator();
        MainService mainService = new MainService();


        String ipAddress = "localhost";
        try {
            loginService = new LoginService(ipAddress);
        }
        catch (RuntimeException exc)
        {
            System.out.println(exc.getClass().getName() + " : " + exc.getMessage());
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        User inputUser = new User(username, password);
        try {
            userValidator.validateUser(inputUser);
            loginService.login(inputUser);
            mainService.setLoggedIn(new User(username, hashedPassword));
            System.out.println("Logged in !");
        }
        catch (RuntimeException exception)
        {
            String message = exception.getMessage();
            System.out.println(exception.getClass().getName() + " : " + message);
        }

        if (Repository.loggedIn == true) {
            rd = request.getRequestDispatcher("/success.jsp");
            HttpSession session = request.getSession();
        } else {
            rd = request.getRequestDispatcher("/index.jsp");
        }
        rd.forward(request, response);
    }

}