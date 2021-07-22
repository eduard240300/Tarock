package com.Tarock.Client.GUI.TalonShowingForm;

import com.Tarock.Client.GUI.Template.JImage;
import com.Tarock.Client.GUI.Template.MyWindowListener;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Common.Service.DataManipulationService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TalonShowingForm extends JFrame{
    public static Font arialBig = new Font("Arial", Font.BOLD, 19);
    public static int sizeX = 800;
    public static int sizeY = 300;
    public static JLabel talonLabel;
    public static List<List<JImage>> talonCards = new ArrayList<>();

    public void updateCards() {
        for(int i=0;i<3;i++)
        {
            Repository.talonPart = i;
            revealTalonPart();
        }
        Repository.talonPart = -1;
    }
    public void revealTalonPart() {
        int talonPart = Repository.talonPart;
        for(int i=0;i<2;i++)
        {
            talonCards.get(talonPart).get(i).setCardID(Repository.talon.get(talonPart*2+i));
        }
    }

    public void initFirstTalonCards() {
        for(int i=0;i<3;i++) {
            talonCards.add(new ArrayList<>());
        }
    }
    public void initTalonLabel() {
        talonLabel = new JLabel();
        talonLabel.setText("Talon : ");
        talonLabel.setFont(arialBig);
        talonLabel.setBounds(40, 20, 131, 31);
        add(talonLabel);
    }
    public void initTalonCards() {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<2;j++)
            {
                talonCards.get(i).add(new JImage(0));
                talonCards.get(i).get(j).setBounds(40 + 110 * j + 260 * i, 60, 100, 180);
                add(talonCards.get(i).get(j));
            }
        }
    }

    public TalonShowingForm() {
        initFirstTalonCards();

        setLayout(null);

        initTalonLabel();
        initTalonCards();

        if (System.getProperty("os.name").equals("Windows 10"))
            sizeX += 18;
        setSize(sizeX, sizeY);
        setTitle("Tarock Client : Talon Selection Showing");

        getContentPane().setBackground(DataManipulationService.backgroundColor);
        setLocationRelativeTo(null);
        addWindowListener(new MyWindowListener());
        setResizable(false);
        //setVisible(true);
    }
}
