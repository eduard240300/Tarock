package GUI.ConnectionForm;

import Domain.User;
import GUI.Main;
import Service.BCrypt;
import Service.LoginService;
import Service.MainService;
import Validator.UserValidator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerConnectionForm {
    private LoginService loginService;
    private MainService mainService;
    private UserValidator userValidator;

    public ControllerConnectionForm() {
        ConnectionForm.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainService = new MainService();
                userValidator = new UserValidator();

                String username = ConnectionForm.usernameField.getText();
                String password = ConnectionForm.passwordField.getText();
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
                    Main.gameForm.setVisible(true);
                    System.out.println("Logged in !");
                }
                catch (RuntimeException exception)
                {
                    String message = exception.getMessage();
                    System.out.println(exception.getClass().getName() + " : " + message);
                }
            }
        });
    }
}
