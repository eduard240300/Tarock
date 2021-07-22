package com.Tarock.Client.GUI.TalonSelectionForm;

import com.Tarock.Client.GUI.Template.ClickableImage;
import com.Tarock.Client.GUI.Template.CustomJButton;
import com.Tarock.Client.GUI.Template.JImage;
import com.Tarock.Client.GUI.Template.MyWindowListener;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Client.Service.DataManipulationService;

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

    public void updateTalonCards() {
        int talonPart = Repository.talonPart;
        for(int i=0;i<2;i++)
            talonCards.get(i).setCardID(Repository.talon.get(talonPart*2+i));
    }

    public void updateGivenCards() {
        List<Integer> givenCardsRepo = Repository.givenCards;
        int size = givenCardsRepo.size();
        for(int i=0;i<size;i++)
        {
            givenCards.get(i).setCardID(givenCardsRepo.get(i));
        }
        for(int i=size;i<2;i++)
        {
            givenCards.get(i).setCardID(0);
        }
    }

    public void updateTalonLabel() {
        talonLabel.setText("Talon : " + Repository.the1of2 + "/2");
    }

    public TalonSelectionForm(){
        talonCards = new ArrayList<>();
        givenCards = new ArrayList<>();
        playerCards = new ArrayList<>();

        Font arialBig = new Font("Arial", Font.BOLD, 19);
        setLayout(null);

        talonLabel = new JLabel();
        talonLabel.setText("Talon : 1/2");
        talonLabel.setFont(arialBig);
        talonLabel.setBounds(350, 20, 131, 31);
        add(talonLabel);

        JLabel givenCardsLabel = new JLabel();
        givenCardsLabel.setText("Given cards :");
        givenCardsLabel.setFont(arialBig);
        givenCardsLabel.setBounds(1010, 20, 140, 31);
        add(givenCardsLabel);

        JLabel myCardsLabel = new JLabel();
        myCardsLabel.setText("My cards :");
        myCardsLabel.setFont(arialBig);
        myCardsLabel.setBounds(710, 240, 130, 31);
        add(myCardsLabel);

        for(int i=0;i<2;i++) {
            talonCards.add(new JImage(0));
            talonCards.get(i).setBounds(340 + 110 * i, 50, 100, 180);
            add(talonCards.get(i));

            givenCards.add(new JImage(0));
            givenCards.get(i).setBounds(1000 + 110 * i, 50, 100, 180);
            add(givenCards.get(i));
        }

        for(int i=0;i<14;i++) {
            playerCards.add(new ClickableImage(0, "TalonSelectionForm",i));
            playerCards.get(i).setBounds(10 + 110 * i, 280, 100, 180);
            add(playerCards.get(i));
        }

        takeButton = new CustomJButton("Take", arialBig);
        takeButton.setBounds(270, 110, 60, 60);
        add(takeButton);

        nextButton = new CustomJButton(">", arialBig);
        nextButton.setBounds(560, 110, 60, 60);
        add(nextButton);

        giveButton = new CustomJButton("Give", arialBig);
        giveButton.setBounds(930, 110, 60, 60);
        giveButton.setEnabled(false);
        add(giveButton);

        if (System.getProperty("os.name").equals("Linux"))
            setSize(1550, 510);
        else if (System.getProperty("os.name").equals("Windows 10"))
            setSize(1568, 510);
        setTitle("Tarock Client : Talon Selection");
        getContentPane().setBackground(Repository.backgroundColor);
        setLocationRelativeTo(null);
        addWindowListener(new MyWindowListener());
        setResizable(false);
        //setVisible(true);

        new ControllerTalonSelectionForm();
    }
}
