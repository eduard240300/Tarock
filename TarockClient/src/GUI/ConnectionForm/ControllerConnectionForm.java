package GUI.ConnectionForm;

import Domain.User;
import Service.BCrypt;
import Service.LoginService;
import Service.MainService;
import Validator.UserValidator;

import java.awt.event.ActionEvent;

public class ControllerConnectionForm {
    private LoginService loginService;
    private MainService mainService;
    private UserValidator userValidator;

    public ControllerConnectionForm() {
        mainService = new MainService();
        userValidator = new UserValidator();
    }

    public void clickedLoginButton(ActionEvent actionEvent, String username, String password) {
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
    }
}
