package GUI.TalonSelectionForm;

import GUI.Template.ClickableImage;
import GUI.Template.CustomJButton;
import GUI.Template.JImage;
import Repository.Repository;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TalonSelectionForm extends JFrame{
    public static JLabel talonLabel;
    public static CustomJButton takeButton;
    public static List<JImage> talonCards;
    public static CustomJButton nextButton;
    public static CustomJButton giveButton;
    public static List<JImage> givenCards;
    public static List<ClickableImage> playerCards;

    public void updateCards() throws IOException {
        for(int i=0;i<Repository.cards.size();i++)
            playerCards.get(i).setCardID(Repository.cards.get(i));
        for(int i=Repository.cards.size();i<14;i++)
            playerCards.get(i).setCardID(0);
    }

    public void updateTalonCards() throws IOException {
        int talonPart = Repository.talonPart;
        for(int i=0;i<2;i++)
            talonCards.get(i).setCardID(Repository.talon.get(talonPart*2+i));
    }

    public void updateGivenCards() throws IOException {
        List<Integer> givenCardsRepo = Repository.givenCards;
        int size = givenCardsRepo.size();
        for(int i=0;i<size;i++)
        {
            givenCards.get(i).setCardID(givenCardsRepo.get(i));
        }
    }

    public void updateTalonLabel() throws IOException {
        talonLabel.setText(Repository.the1of2 + "/2");
    }

    public TalonSelectionForm() throws IOException {
        talonCards = new ArrayList<JImage>();
        givenCards = new ArrayList<JImage>();
        playerCards = new ArrayList<ClickableImage>();

        Font arialDefault = new Font("Arial", Font.BOLD, 16);
        Font arialBig = new Font("Arial", Font.BOLD, 19);
        setLayout(null);

        talonLabel = new JLabel();
        talonLabel.setText("Talon : 1/2");
        talonLabel.setFont(arialBig);
        talonLabel.setBounds(240, 20, 131, 31);
        add(talonLabel);

        JLabel givenCardsLabel = new JLabel();
        givenCardsLabel.setText("Given cards :");
        givenCardsLabel.setFont(arialBig);
        givenCardsLabel.setBounds(790, 20, 140, 31);
        add(givenCardsLabel);

        JLabel myCardsLabel = new JLabel();
        myCardsLabel.setText("My cards :");
        myCardsLabel.setFont(arialBig);
        myCardsLabel.setBounds(550, 240, 131, 31);
        add(myCardsLabel);

        for(int i=0;i<2;i++) {
            talonCards.add(new JImage(0, 100, 175));
            talonCards.get(i).setBounds(230 + 110 * i, 50, 100, 180);
            add(talonCards.get(i));

            givenCards.add(new JImage(0, 100, 175));
            givenCards.get(i).setBounds(780 + 110 * i, 50, 100, 180);
            add(givenCards.get(i));
        }

        for(int i=0;i<14;i++) {
            playerCards.add(new ClickableImage(0, "TalonSelectionForm",i, 100, 175));
            playerCards.get(i).setBounds(10 + 110 * i, 280, 100, 180);
            add(playerCards.get(i));
        }

        takeButton = new CustomJButton("Take", arialBig);
        takeButton.setBounds(160, 110, 60, 60);
        add(takeButton);

        nextButton = new CustomJButton(">", arialBig);
        nextButton.setBounds(450, 110, 60, 60);
        add(nextButton);

        giveButton = new CustomJButton("Give", arialBig);
        giveButton.setBounds(710, 110, 60, 60);
        giveButton.setEnabled(false);
        add(giveButton);

        setSize(1600, 510);
        setTitle("Tarock Client : Talon Selection");
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        //setVisible(true);

        ControllerTalonSelectionForm controllerTalonSelectionForm = new ControllerTalonSelectionForm();
    }
}
