package GUI.ConnectionForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ConnectionForm extends JFrame{
    public ConnectionForm(){
        ControllerConnectionForm controllerConnectionForm = new ControllerConnectionForm();

        Font arialDefault = new Font("Arial", Font.PLAIN, 30);
        setLayout(null);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(arialDefault);
        usernameLabel.setText("Username : ");
        usernameLabel.setBounds(20, 20, 300,40);

        JTextField usernameField = new JTextField();
        usernameField.setFont(arialDefault);
        usernameField.setBounds(190, 20, 330, 40);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setFont(arialDefault);
        passwordLabel.setText("Password : ");
        passwordLabel.setBounds(20, 70, 300,40);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(arialDefault);
        passwordField.setBounds(190, 70, 330, 40);

        JButton loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.setBounds(120, 120, 300, 40);
        loginButton.setFont(arialDefault);
        loginButton.setBackground(Color.lightGray);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                controllerConnectionForm.clickedLoginButton(e, username, password);
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        setSize(540, 220);
        setTitle("Tarock Client : Login");
        try
        {
            // The read(), static method of ImageIO class
            // takes InputStream object pointing to the image file
            setIconImage(ImageIO.read(new FileInputStream("icon.png")));
        }catch(Exception e){}
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
