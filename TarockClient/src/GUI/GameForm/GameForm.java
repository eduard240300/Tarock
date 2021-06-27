package GUI.GameForm;

import GUI.ConnectionForm.ControllerConnectionForm;
import GUI.Template.ClickableImage;
import GUI.Template.CustomJButton;
import GUI.Template.JImage;
import Repository.Repository;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class GameForm extends JFrame{
    public GameForm() throws IOException {
        ControllerConnectionForm controllerConnectionForm = new ControllerConnectionForm();

        Font arialDefault = new Font("Arial", Font.BOLD, 16);
        setLayout(null);

        CustomJButton cancelGameButton = new CustomJButton("Cancel Game", arialDefault);
        cancelGameButton.setBounds(670, 120, 111, 27);

        ClickableImage image = new ClickableImage(0, 200, 200);
        image.setBounds(0, 0, 200, 200);

        JLabel player1_Label = new JLabel();
        player1_Label.setText("Player 1");
        player1_Label.setFont(arialDefault);
        player1_Label.setBounds(200, 10, 111, 20);

        JImage cardPlayer1 = new JImage(0, 100, 175);
        cardPlayer1.setBounds(190, 40, 100, 180);

        JLabel player2_Label = new JLabel();
        player2_Label.setText("Player 2");
        player2_Label.setFont(arialDefault);
        player2_Label.setBounds(40, 110, 111, 20);

        JImage cardPlayer2 = new JImage(0, 100, 175);
        cardPlayer2.setBounds(30, 140, 100, 180);

        JLabel player3_Label = new JLabel();
        player3_Label.setText("Player 3");
        player3_Label.setFont(arialDefault);
        player3_Label.setBounds(200, 230, 111, 20);

        JImage cardPlayer3 = new JImage(0, 100, 175);
        cardPlayer3.setBounds(190, 260, 100, 180);

        JLabel player4_Label = new JLabel();
        player4_Label.setText("Player 4");
        player4_Label.setFont(arialDefault);
        player4_Label.setBounds(360, 110, 111, 20);

        JImage cardPlayer4 = new JImage(0, 100, 175);
        cardPlayer4.setBounds(350, 140, 100, 180);

        Repository.currentCards.add(cardPlayer1);
        Repository.currentCards.add(cardPlayer2);
        Repository.currentCards.add(cardPlayer3);
        Repository.currentCards.add(cardPlayer4);
        Repository.playerNames.add(player1_Label);
        Repository.playerNames.add(player2_Label);
        Repository.playerNames.add(player3_Label);
        Repository.playerNames.add(player4_Label);

        Repository.currentCards.get(0).setCardID(1);
        Repository.playerNames.get(1).setText("2. Teodora");

        add(player1_Label);
        add(cardPlayer1);
        add(player2_Label);
        add(cardPlayer2);
        add(player3_Label);
        add(cardPlayer3);
        add(player4_Label);
        add(cardPlayer4);
        //add(image);
        add(cancelGameButton);
        setSize(1330, 720);
        setTitle("Tarock Client : Game");
        try
        {
            // The read(), static method of ImageIO class
            // takes InputStream object pointing to the image file
            setIconImage(ImageIO.read(new FileInputStream("src/Resources/icon.png")));
        }catch(Exception e){}
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
