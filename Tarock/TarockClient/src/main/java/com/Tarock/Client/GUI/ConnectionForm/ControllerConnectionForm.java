package com.Tarock.Client.GUI.ConnectionForm;

import com.Tarock.Common.Domain.User;
import com.Tarock.Client.GUI.Main;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Client.Service.BCrypt;
import com.Tarock.Client.Service.CommunicationService;
import com.Tarock.Common.Service.DataManipulationService;
import com.Tarock.Client.Service.LoginService;
import com.Tarock.Common.Validator.IntegerValidator;
import com.Tarock.Common.Validator.UserValidator;

@SuppressWarnings("deprecation")
public class ControllerConnectionForm {
    private static LoginService loginService = null;
    private static CommunicationService communicationService = null;
    private static final UserValidator userValidator = new UserValidator();
    private static User user;
    private static String sessionID;

    public void resetServices()
    {
        loginService = null;
    }
    public void initLoginService() { loginService = new LoginService(); }
    public void initSessionID() { sessionID = ConnectionForm.sessionIDField.getText(); }
    public void initUser() {
        String username = ConnectionForm.usernameField.getText();
        String password = ConnectionForm.passwordField.getText();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        user = new User(username, hashedPassword); };

    public ControllerConnectionForm() {
        ConnectionForm.loginButton.addActionListener(e -> {
            CommunicationService.ipAddress = ConnectionForm.ipAddressField.getText();

            if (communicationService == null) {
                communicationService = new CommunicationService();
                communicationService.start();
            }
            if (loginService == null)
                initLoginService();

            String username = ConnectionForm.usernameField.getText();
            String password = ConnectionForm.passwordField.getText();
            initSessionID();
            initUser();
            User inputUser = new User(username, password);

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
        });
    }

    public static void finishLogin()
    {
        Repository.login(user);
        Repository.sessionID = Integer.parseInt(sessionID);
        Main.gameForm.setVisible(true);
        Main.connectionForm.setVisible(false);
        System.out.println("Logged in !");
    }
}
