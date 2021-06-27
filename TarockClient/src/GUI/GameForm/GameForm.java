package GUI.GameForm;

import GUI.ConnectionForm.ControllerConnectionForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

public class GameForm extends JFrame{
    public GameForm()
    {
        ControllerConnectionForm controllerConnectionForm = new ControllerConnectionForm();

        Font arialDefault = new Font("Arial", Font.BOLD, 13);
        setLayout(null);

        JButton cancelGameButton = new JButton();
        cancelGameButton.setFont(arialDefault);
        cancelGameButton.setBounds(670, 120, 111, 27);
        cancelGameButton.setText("Cancel Game");

        add(cancelGameButton);
        setSize(1330, 720);
        setTitle("Tarock Client : Game");
        try
        {
            // The read(), static method of ImageIO class
            // takes InputStream object pointing to the image file
            setIconImage(ImageIO.read(new FileInputStream("icon.png")));
        }catch(Exception e){}
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
