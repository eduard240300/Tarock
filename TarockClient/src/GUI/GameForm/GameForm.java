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

        //Player cards area
        JLabel player1_Label = new JLabel();
        player1_Label.setText("Player 1");
        player1_Label.setFont(arialDefault);
        player1_Label.setBounds(200, 10, 111, 20);
        Repository.labelPlayerNames.add(player1_Label);
        add(player1_Label);

        JImage cardPlayer1 = new JImage(0, 100, 175);
        cardPlayer1.setBounds(190, 40, 100, 180);
        Repository.currentCards.add(cardPlayer1);
        add(cardPlayer1);

        JLabel player2_Label = new JLabel();
        player2_Label.setText("Player 2");
        player2_Label.setFont(arialDefault);
        player2_Label.setBounds(40, 110, 111, 20);
        Repository.labelPlayerNames.add(player2_Label);
        add(player2_Label);

        JImage cardPlayer2 = new JImage(0, 100, 175);
        cardPlayer2.setBounds(30, 140, 100, 180);
        Repository.currentCards.add(cardPlayer2);
        add(cardPlayer2);

        JLabel player3_Label = new JLabel();
        player3_Label.setText("Player 3");
        player3_Label.setFont(arialDefault);
        player3_Label.setBounds(200, 230, 111, 20);
        Repository.labelPlayerNames.add(player3_Label);
        add(player3_Label);

        JImage cardPlayer3 = new JImage(0, 100, 175);
        cardPlayer3.setBounds(190, 260, 100, 180);
        Repository.currentCards.add(cardPlayer3);
        add(cardPlayer3);

        JLabel player4_Label = new JLabel();
        player4_Label.setText("Player 4");
        player4_Label.setFont(arialDefault);
        player4_Label.setBounds(360, 110, 111, 20);
        Repository.labelPlayerNames.add(player4_Label);
        add(player4_Label);

        JImage cardPlayer4 = new JImage(0, 100, 175);
        cardPlayer4.setBounds(350, 140, 100, 180);
        Repository.currentCards.add(cardPlayer4);
        add(cardPlayer4);

        for(int i=0;i<12;i++){
            ClickableImage newClickableImage = new ClickableImage(0, i+1,100,175);
            newClickableImage.setBounds(10+110*i, 460, 100, 180);
            Repository.playerCards.add(newClickableImage);
            add(newClickableImage);
        }

        JLabel statusLabel = new JLabel();
        statusLabel.setText("Status : ");
        statusLabel.setFont(arialDefault);
        statusLabel.setBounds(470, 10, 80, 19);
        add(statusLabel);

        for(int i=0;i<4;i++)
        {
            JLabel newLabel = new JLabel();
            newLabel.setText("Player " + String.valueOf(i+1) + " : ");
            newLabel.setFont(arialDefault);
            newLabel.setBounds(470, 30+i*20, 230, 19);
            Repository.statusPlayers.add(newLabel);
            add(newLabel);
        }

        CustomJButton the1of2Button = new CustomJButton("1/2", arialDefault);
        the1of2Button.setBounds(470, 120, 94, 27);
        the1of2Button.setEnabled(false);
        Repository.the1of2Button = the1of2Button;
        add(the1of2Button);

        CustomJButton passButton = new CustomJButton("Pass", arialDefault);
        passButton.setBounds(570, 120, 94, 27);
        passButton.setEnabled(false);
        Repository.passButton = passButton;
        add(passButton);

        CustomJButton cancelGameButton = new CustomJButton("Cancel Game", arialDefault);
        cancelGameButton.setBounds(670, 120, 111, 27);
        cancelGameButton.setEnabled(false);
        Repository.cancelGameButton = cancelGameButton;
        add(cancelGameButton);

        JPanel declarationsPanel = new JPanel();
        declarationsPanel.setLayout(null);
        declarationsPanel.setBorder(BorderFactory.createTitledBorder("Declarations"));
        declarationsPanel.setBackground(new Color(78, 154, 6));
        declarationsPanel.setName("Declarations");
        declarationsPanel.setFont(arialDefault);
        declarationsPanel.setBounds(470, 160, 341, 241);
        add(declarationsPanel);

        JCheckBox popeAtFinishCheckBox = new JCheckBox();
        popeAtFinishCheckBox.setBackground(new Color(78, 154, 6));
        popeAtFinishCheckBox.setFocusPainted(false);
        popeAtFinishCheckBox.setText("Pope at finish");
        popeAtFinishCheckBox.setFont(arialDefault);
        popeAtFinishCheckBox.setBounds(10, 30, 150, 25);
        declarationsPanel.add(popeAtFinishCheckBox);

        JCheckBox pagatAtFinishCheckBox = new JCheckBox();
        pagatAtFinishCheckBox.setBackground(new Color(78, 154, 6));
        pagatAtFinishCheckBox.setFocusPainted(false);
        pagatAtFinishCheckBox.setText("Pagat at finish");
        pagatAtFinishCheckBox.setFont(arialDefault);
        pagatAtFinishCheckBox.setBounds(10, 60, 150, 25);
        declarationsPanel.add(pagatAtFinishCheckBox);

        JCheckBox allPopesCheckBox = new JCheckBox();
        allPopesCheckBox.setBackground(new Color(78, 154, 6));
        allPopesCheckBox.setFocusPainted(false);
        allPopesCheckBox.setText("All popes");
        allPopesCheckBox.setFont(arialDefault);
        allPopesCheckBox.setBounds(10, 90, 150, 25);
        declarationsPanel.add(allPopesCheckBox);

        JCheckBox trulaCheckBox = new JCheckBox();
        trulaCheckBox.setBackground(new Color(78, 154, 6));
        trulaCheckBox.setFocusPainted(false);
        trulaCheckBox.setText("Trula");
        trulaCheckBox.setFont(arialDefault);
        trulaCheckBox.setBounds(10, 120, 150, 25);
        declarationsPanel.add(trulaCheckBox);

        JLabel numberOfTarocksLabel = new JLabel();
        numberOfTarocksLabel.setText("No. Tarocks : ");
        numberOfTarocksLabel.setFont(arialDefault);
        numberOfTarocksLabel.setBounds(10, 155, 110, 31);
        declarationsPanel.add(numberOfTarocksLabel);

        JComboBox<String> numberOfTarocksComboBox = new JComboBox<String>();
        numberOfTarocksComboBox.addItem("No");
        numberOfTarocksComboBox.addItem("8");
        numberOfTarocksComboBox.addItem("9");
        numberOfTarocksComboBox.addItem("10");
        numberOfTarocksComboBox.setFont(arialDefault);
        numberOfTarocksComboBox.setBounds(120, 155, 51, 31);
        declarationsPanel.add(numberOfTarocksComboBox);

        //Repository.currentCards.get(0).setCardID(1);
        //Repository.setPlayerName(1, "Teodora");
        //Repository.setPlayerName(2, "Carnat");


        setSize(1330, 690);
        setTitle("Tarock Client : Game");
        try
        {
            // The read(), static method of ImageIO class
            // takes InputStream object pointing to the image file
            setIconImage(ImageIO.read(new FileInputStream("./src/Resources/icon.png")));
        }catch(Exception e){}
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
