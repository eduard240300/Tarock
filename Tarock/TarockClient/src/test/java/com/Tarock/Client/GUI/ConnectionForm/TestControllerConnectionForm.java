package com.Tarock.Client.GUI.ConnectionForm;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.Tarock.Client.Refactoring;
import com.Tarock.Client.Service.CommunicationService;
import com.Tarock.Client.Service.LoginService;
import com.Tarock.Common.Domain.User;
import com.Tarock.Common.Validator.UserValidator;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class TestControllerConnectionForm {
    public ControllerConnectionForm resetValuesControllerConnectionForm() throws NoSuchFieldException {
        ControllerConnectionForm controllerConnectionForm = new ControllerConnectionForm(true);

        Field privateField = ControllerConnectionForm.class.getDeclaredField("loginService");
        privateField.setAccessible(true);
        try {
            privateField.set(null, null);
        } catch (Exception ignored) {
        }

        privateField = ControllerConnectionForm.class.getDeclaredField("communicationService");
        privateField.setAccessible(true);
        try {
            privateField.set(null, null);
        } catch (Exception ignored) {
        }

        privateField = ControllerConnectionForm.class.getDeclaredField("userValidator");
        privateField.setAccessible(true);
        try {
            privateField.set(null, new UserValidator());
        } catch (Exception ignored) {
        }

        return controllerConnectionForm;
    }

    @Test
    public void testResetServices() throws NoSuchFieldException {
        new ConnectionForm(false, false);
        ControllerConnectionForm controllerConnectionForm = resetValuesControllerConnectionForm();

        controllerConnectionForm.resetServices();
        Refactoring<ControllerConnectionForm, LoginService> refactoring = new Refactoring<>();
        Field privateField = ControllerConnectionForm.class.getDeclaredField("loginService");
        LoginService loginService = (LoginService) refactoring.getVariable(controllerConnectionForm, privateField);

        assertNull(loginService);
    }

    @Test
    public void testInitCommunicationService() throws NoSuchFieldException {
        new ConnectionForm(false, false);
        ControllerConnectionForm controllerConnectionForm = resetValuesControllerConnectionForm();

        controllerConnectionForm.initCommunicationService();
        Refactoring<ControllerConnectionForm, CommunicationService> refactoring = new Refactoring<>();
        Field privateField = ControllerConnectionForm.class.getDeclaredField("communicationService");
        CommunicationService communicationService = (CommunicationService) refactoring.getVariable(controllerConnectionForm, privateField);

        assertNotNull(communicationService);
    }

    @Test
    public void testInitLoginService() throws NoSuchFieldException {
        new ConnectionForm(false, false);
        ControllerConnectionForm controllerConnectionForm = resetValuesControllerConnectionForm();

        controllerConnectionForm.initLoginService();
        Refactoring<ControllerConnectionForm, LoginService> refactoring = new Refactoring<>();
        Field privateField = ControllerConnectionForm.class.getDeclaredField("loginService");
        LoginService loginService = (LoginService) refactoring.getVariable(controllerConnectionForm, privateField);

        assertNotNull(loginService);
    }

    @Test
    public void testInitSessionID() throws NoSuchFieldException {
        new ConnectionForm(false, false);
        ControllerConnectionForm controllerConnectionForm = resetValuesControllerConnectionForm();

        ConnectionForm.sessionIDField.setText("5");
        controllerConnectionForm.initSessionID();
        Refactoring<ControllerConnectionForm, Integer> refactoring = new Refactoring<>();
        Field privateField = ControllerConnectionForm.class.getDeclaredField("sessionID");
        Integer sessionID = Integer.valueOf(((String) refactoring.getVariable(controllerConnectionForm, privateField)));

        assertEquals(5, sessionID.intValue());
    }

    @Test
    public void testInitUser() throws NoSuchFieldException {
        new ConnectionForm(false, false);
        ControllerConnectionForm controllerConnectionForm = resetValuesControllerConnectionForm();

        ConnectionForm.usernameField.setText("username");
        ConnectionForm.passwordField.setText("password");
        controllerConnectionForm.initUser();
        Refactoring<ControllerConnectionForm, User> refactoring = new Refactoring<>();
        Field privateField = ControllerConnectionForm.class.getDeclaredField("user");
        User user = (User) refactoring.getVariable(controllerConnectionForm, privateField);

        assertEquals("username", user.getUsername());
        assertTrue((at.favre.lib.crypto.bcrypt.BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verify(Bytes.from("password").array(), Bytes.from(user.getPassword()).array()).verified));
    }

    @Test
    public void testControllerConnectionForm() throws NoSuchFieldException {
        new ConnectionForm(false, false);
        ConnectionForm.ipAddressField.setText("192.168.0.1");
        ControllerConnectionForm controllerConnectionForm = resetValuesControllerConnectionForm();
        ConnectionForm.loginButton.doClick();
        CommunicationService communicationService = new CommunicationService();

        Refactoring<CommunicationService, String> refactoring = new Refactoring<>();
        Field privateField = CommunicationService.class.getDeclaredField("ipAddress");
        String ipAddress = (String) refactoring.getVariable(communicationService, privateField);
        assertEquals("192.168.0.1", ipAddress);

        Refactoring<ControllerConnectionForm, CommunicationService> refactoring1 = new Refactoring<>();
        privateField = ControllerConnectionForm.class.getDeclaredField("communicationService");
        communicationService = (CommunicationService) refactoring1.getVariable(controllerConnectionForm, privateField);
        assertNotNull(communicationService);

        Refactoring<ControllerConnectionForm, LoginService> refactoring2 = new Refactoring<>();
        privateField = ControllerConnectionForm.class.getDeclaredField("loginService");
        LoginService loginService = (LoginService) refactoring2.getVariable(controllerConnectionForm, privateField);
        assertNotNull(loginService);
    }
}
