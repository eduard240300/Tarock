package GUI.ConnectionForm;

import GUI.Template.CustomJButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class ConnectionForm extends JFrame{
    public static JTextField usernameField;
    public static JPasswordField passwordField;
    public static CustomJButton loginButton;

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

        loginButton = new CustomJButton("Login", arialDefault);
        loginButton.setBounds(120, 120, 300, 40);
        add(loginButton);

        setSize(540, 220);
        setTitle("Tarock Client : Login");
        try
        {
            setIconImage(ImageIO.read(new FileInputStream("./src/Resources/icon.png")));
        }catch(Exception e){}
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        setVisible(true);

        ControllerConnectionForm controllerConnectionForm = new ControllerConnectionForm();
    }
}
