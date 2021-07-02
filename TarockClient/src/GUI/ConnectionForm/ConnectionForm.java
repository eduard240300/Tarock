package GUI.ConnectionForm;

import GUI.Template.CustomJButton;

import javax.swing.*;
import java.awt.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class ConnectionForm extends JFrame{
    public static JTextField usernameField;
    public static JPasswordField passwordField;
    public static JTextField sessionIDField;
    public static CustomJButton loginButton;

    public static void popUpMessage(String message)
    {
        showMessageDialog(null, message);
    }

    public ConnectionForm(){
        Font arialDefault = new Font("Arial", Font.PLAIN, 30);
        setLayout(null);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(arialDefault);
        usernameLabel.setText("Username : ");
        usernameLabel.setBounds(20, 20, 300,40);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(arialDefault);
        usernameField.setBounds(190, 20, 330, 40);
        add(usernameField);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setFont(arialDefault);
        passwordLabel.setText("Password : ");
        passwordLabel.setBounds(20, 70, 300,40);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(arialDefault);
        passwordField.setBounds(190, 70, 330, 40);
        add(passwordField);

        JLabel sessionIDLabel = new JLabel();
        sessionIDLabel.setFont(arialDefault);
        sessionIDLabel.setText("SessionID : ");
        sessionIDLabel.setBounds(20, 120, 300,40);
        add(sessionIDLabel);

        sessionIDField = new JTextField();
        sessionIDField.setFont(arialDefault);
        sessionIDField.setBounds(190, 120, 330, 40);
        add(sessionIDField);

        loginButton = new CustomJButton("Start/Resume Session", arialDefault);
        loginButton.setBounds(20, 170, 500, 40);
        add(loginButton);

        setSize(540, 270);
        setTitle("Tarock Client : Login");
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        setVisible(true);

        ControllerConnectionForm controllerConnectionForm = new ControllerConnectionForm();
    }
}
