package com.Tarock.Client.GUI.ConnectionForm;

import com.Tarock.Common.Domain.User;
import com.Tarock.Client.Main;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Client.Service.CommunicationService;
import com.Tarock.Common.Service.DataManipulationService;
import com.Tarock.Common.Validator.IntegerValidator;
import com.Tarock.Common.Validator.UserValidator;

@SuppressWarnings("deprecation")
public class ControllerConnectionForm {
    private static CommunicationService communicationService = null;
    private static final UserValidator userValidator = new UserValidator();
    private static User user;
    private static String sessionID;
    private static String exceptionMessage;

    public void initCommunicationService() {
        communicationService = new CommunicationService();
    }

    public void initSessionID() {
        sessionID = ConnectionForm.sessionIDField.getText();
    }

    public void initUser() {
        String username = ConnectionForm.usernameField.getText();
        String password = ConnectionForm.passwordField.getText();
        user = new User(username, password);
    }

    public ControllerConnectionForm() {
        ConnectionForm.loginButton.addActionListener(e -> {
            CommunicationService.ipAddress = ConnectionForm.ipAddressField.getText();

            if (communicationService == null) {
                initCommunicationService();
                communicationService.start();
            }

            String username = ConnectionForm.usernameField.getText();
            String password = ConnectionForm.passwordField.getText();
            initSessionID();
            initUser();
            User inputUser = new User(username, password);

            try {
                userValidator.validateUser(inputUser);
                IntegerValidator.validateInteger(sessionID);
                CommunicationService.sessionID = Integer.parseInt(sessionID);
                CommunicationService.user = user;
            } catch (RuntimeException exception) {
                String className = DataManipulationService.truncateClassName(exception.getClass().getName());
                exceptionMessage = exception.getMessage();
                ConnectionForm.popUpMessage(className + " : " + exceptionMessage);
            }
        });
    }

    public static void finishLogin() {
        Repository.login(user);
        Repository.sessionID = Integer.parseInt(sessionID);
        Main.gameForm.setVisible(true);
        Main.connectionForm.setVisible(false);
        System.out.println("Logged in !");
    }
}
