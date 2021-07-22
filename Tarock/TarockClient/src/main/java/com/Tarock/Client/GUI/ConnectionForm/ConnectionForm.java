package com.Tarock.Client.GUI.ConnectionForm;

import com.Tarock.Client.GUI.Template.CustomJButton;
import com.Tarock.Client.GUI.Template.MyWindowListener;
import com.Tarock.Client.Service.DataManipulationService;
import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class ConnectionForm extends JFrame{
    public static int sizeX = 540;
    public static int sizeY = 320;
    public static ControllerConnectionForm controllerConnectionForm = null;
    public static Font arialDefault = new Font("Arial", Font.PLAIN, 30);
    public static JLabel usernameLabel;
    public static JTextField usernameField;
    public static JLabel passwordLabel;
    public static JPasswordField passwordField;
    public static JLabel sessionIDLabel;
    public static JTextField sessionIDField;
    public static JLabel ipAddressLabel;
    public static JTextField ipAddressField;
    public static CustomJButton loginButton;

    public static void popUpMessage(String message)
    {
        showMessageDialog(null, message);
    }
    public void initUsernameLabel() {
        usernameLabel = new JLabel();
        usernameLabel.setFont(arialDefault);
        usernameLabel.setText("Username : ");
        usernameLabel.setBounds(20, 20, 300,40);
        add(usernameLabel);
    }
    public void initUsernameField() {
        usernameField = new JTextField();
        usernameField.setFont(arialDefault);
        usernameField.setBounds(190, 20, 330, 40);
        add(usernameField);
    }
    public void initPasswordLabel() {
        passwordLabel = new JLabel();
        passwordLabel.setFont(arialDefault);
        passwordLabel.setText("Password : ");
        passwordLabel.setBounds(20, 70, 300,40);
        add(passwordLabel);
    }
    public void initPasswordField() {
        passwordField = new JPasswordField();
        passwordField.setFont(arialDefault);
        passwordField.setBounds(190, 70, 330, 40);
        add(passwordField);
    }
    public void initSessionIDLabel() {
        sessionIDLabel = new JLabel();
        sessionIDLabel.setFont(arialDefault);
        sessionIDLabel.setText("SessionID : ");
        sessionIDLabel.setBounds(20, 120, 300,40);
        add(sessionIDLabel);
    }
    public void initSessionIDField() {
        sessionIDField = new JTextField();
        sessionIDField.setFont(arialDefault);
        sessionIDField.setBounds(190, 120, 330, 40);
        add(sessionIDField);
    }
    public void initIPAddressLabel() {
        ipAddressLabel = new JLabel();
        ipAddressLabel.setFont(arialDefault);
        ipAddressLabel.setText("Server IP : ");
        ipAddressLabel.setBounds(20, 170, 300,40);
        add(ipAddressLabel);
    }
    public void initIPAddressField() {
        ipAddressField = new JTextField();
        ipAddressField.setFont(arialDefault);
        ipAddressField.setText("");
        ipAddressField.setBounds(190, 170, 330, 40);
        add(ipAddressField);
    }
    public void initLoginButton() {
        loginButton = new CustomJButton("Start/Resume Session", arialDefault);
        loginButton.setBounds(20, 220, 500, 40);
        add(loginButton);
    }

    public ConnectionForm(){
        setLayout(null);

        initUsernameLabel();
        initUsernameField();
        initPasswordLabel();
        initPasswordField();
        initSessionIDLabel();
        initSessionIDField();
        initIPAddressLabel();
        initIPAddressField();
        initLoginButton();

        if (System.getProperty("os.name").equals("Windows 10"))
            sizeX += 18;
        setSize(sizeX, sizeY);
        setTitle("Tarock Client : Login");

        getContentPane().setBackground(DataManipulationService.backgroundColor);
        setLocationRelativeTo(null);
        addWindowListener(new MyWindowListener());
        setVisible(true);
        setResizable(false);

        controllerConnectionForm = new ControllerConnectionForm();
    }
}
