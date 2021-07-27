package com.Tarock.Client.GUI.TalonSelectionForm;

import com.Tarock.Client.GUI.Template.ClickableImage;
import com.Tarock.Client.GUI.Template.CustomJButton;
import com.Tarock.Client.GUI.Template.JImage;
import com.Tarock.Client.GUI.Template.MyWindowListener;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Common.Service.DataManipulationService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TalonSelectionForm extends JFrame {
    public static Font arialBig = new Font("Arial", Font.BOLD, 19);
    public static int sizeX = 1550;
    public static int sizeY = 510;
    public static JLabel talonLabel;
    public static JLabel givenCardsLabel;
    public static JLabel myCardsLabel;
    public static CustomJButton takeButton;
    public static List<JImage> talonCards = new ArrayList<>();
    public static CustomJButton nextButton;
    public static CustomJButton giveButton;
    public static List<JImage> givenCards = new ArrayList<>();
    public static List<ClickableImage> playerCards = new ArrayList<>();

    public void updateCards() throws IOException {
        for (int i = 0; i < Repository.cards.size(); i++)
            playerCards.get(i).setCardID(Repository.cards.get(i));
        for (int i = Repository.cards.size(); i < 14; i++)
            playerCards.get(i).setCardID(0);
    }

    public void updateTalonCards() {
        int talonPart = Repository.talonPart;
        for (int i = 0; i < 2; i++)
            talonCards.get(i).setCardID(Repository.talon.get(talonPart * 2 + i));
    }

    public void updateGivenCards() {
        List<Integer> givenCardsRepo = Repository.givenCards;
        int size = givenCardsRepo.size();
        for (int i = 0; i < size; i++) {
            givenCards.get(i).setCardID(givenCardsRepo.get(i));
        }
        for (int i = size; i < 2; i++) {
            givenCards.get(i).setCardID(0);
        }
    }

    public void updateTalonLabel() {
        talonLabel.setText("Talon : " + Repository.the1of2 + "/2");
    }

    public void initTalonLabel() {
        talonLabel = new JLabel();
        talonLabel.setText("Talon : 1/2");
        talonLabel.setFont(arialBig);
        talonLabel.setBounds(350, 20, 131, 31);
        add(talonLabel);
    }

    public void initGivenCardsLabel() {
        givenCardsLabel = new JLabel();
        givenCardsLabel.setText("Given cards :");
        givenCardsLabel.setFont(arialBig);
        givenCardsLabel.setBounds(1010, 20, 140, 31);
        add(givenCardsLabel);
    }

    public void initMyCardsLabel() {
        myCardsLabel = new JLabel();
        myCardsLabel.setText("My cards :");
        myCardsLabel.setFont(arialBig);
        myCardsLabel.setBounds(710, 240, 130, 31);
        add(myCardsLabel);
    }

    public void initTalonCards() {
        for (int i = 0; i < 2; i++) {
            talonCards.add(new JImage(0));
            talonCards.get(i).setBounds(340 + 110 * i, 50, 100, 180);
            add(talonCards.get(i));
        }
    }

    public void initGivenCards() {
        for (int i = 0; i < 2; i++) {
            givenCards.add(new JImage(0));
            givenCards.get(i).setBounds(1000 + 110 * i, 50, 100, 180);
            add(givenCards.get(i));
        }
    }

    public void initPlayerCards() {
        for (int i = 0; i < 14; i++) {
            playerCards.add(new ClickableImage(0, "TalonSelectionForm", i));
            playerCards.get(i).setBounds(10 + 110 * i, 280, 100, 180);
            add(playerCards.get(i));
        }
    }

    public void initTakeButton() {
        takeButton = new CustomJButton("Take", arialBig);
        takeButton.setBounds(270, 110, 60, 60);
        add(takeButton);
    }

    public void initNextButton() {
        nextButton = new CustomJButton(">", arialBig);
        nextButton.setBounds(560, 110, 60, 60);
        add(nextButton);
    }

    public void initGiveButton() {
        giveButton = new CustomJButton("Give", arialBig);
        giveButton.setBounds(930, 110, 60, 60);
        giveButton.setEnabled(false);
        add(giveButton);
    }

    public TalonSelectionForm(boolean initializeController) {
        InputStream icon = DataManipulationService.getInputStream("icon.png");
        try {
            setIconImage(ImageIO.read(icon));
        } catch (Exception ignored) {
        }

        setLayout(null);

        initTalonLabel();
        initGivenCardsLabel();
        initMyCardsLabel();

        initTalonCards();
        initGivenCards();
        initPlayerCards();

        initTakeButton();
        initNextButton();
        initGiveButton();

        if (System.getProperty("os.name").equals("Windows 10"))
            sizeX += 18;
        setSize(sizeX, sizeY);
        setTitle("Tarock Client : Talon Selection");

        getContentPane().setBackground(DataManipulationService.backgroundColor);
        setLocationRelativeTo(null);
        addWindowListener(new MyWindowListener());
        setResizable(false);
        //setVisible(true);

        if (initializeController)
            new ControllerTalonSelectionForm();
    }
}
