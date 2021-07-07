package GUI.ConnectionForm;

import Domain.User;
import GUI.Main;
import Service.*;
import Validator.IntegerValidator;
import Validator.UserValidator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerConnectionForm {
    private static LoginService loginService = null;
    private static MainService mainService = null;
    private static UserValidator userValidator = new UserValidator();
    private static User user;
    private static String sessionID;

    public void resetServices()
    {
        loginService = null;
        mainService = null;
    }

    public ControllerConnectionForm() {
        ConnectionForm.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommunicationService.ipAddress = ConnectionForm.ipAddressField.getText();

                if (mainService == null)
                    mainService = new MainService();
                if (loginService == null)
                    loginService = new LoginService();

                String username = ConnectionForm.usernameField.getText();
                String password = ConnectionForm.passwordField.getText();
                sessionID = ConnectionForm.sessionIDField.getText();

                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                User inputUser = new User(username, password);
                user = new User(username, hashedPassword);
                try {
                    userValidator.validateUser(inputUser);
                    IntegerValidator.validateInteger(sessionID);
                    loginService.login(inputUser, Integer.parseInt(sessionID));
                }
                catch (RuntimeException exception)
                {
                    String className = DataManipulationService.truncateClassName(exception.getClass().getName());
                    String message = exception.getMessage();
                    ConnectionForm.popUpMessage(className + " : " + message);
                }
            }
        });
    }

    public static void finishLogin()
    {
        mainService.setLoggedIn(user);
        mainService.setSessionID(Integer.parseInt(sessionID));
        Main.gameForm.setVisible(true);
        Main.connectionForm.setVisible(false);
        System.out.println("Logged in !");
    }
}
