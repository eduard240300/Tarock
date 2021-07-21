package com.Tarock.Client.GUI.TalonShowingForm;

import com.Tarock.Client.GUI.Template.JImage;
import com.Tarock.Client.GUI.Template.MyWindowListener;
import com.Tarock.Client.Repository.Repository;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TalonShowingForm extends JFrame{
    public static JLabel talonLabel;
    public static List<List<JImage>> talonCards;

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

    public TalonShowingForm() {
        talonCards = new ArrayList<>();
        for(int i=0;i<3;i++)
            talonCards.add(new ArrayList<>());

        Font arialBig = new Font("Arial", Font.BOLD, 19);
        setLayout(null);

        talonLabel = new JLabel();
        talonLabel.setText("Talon : ");
        talonLabel.setFont(arialBig);
        talonLabel.setBounds(40, 20, 131, 31);
        add(talonLabel);

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<2;j++)
            {
                talonCards.get(i).add(new JImage(0));
                talonCards.get(i).get(j).setBounds(40 + 110 * j + 260 * i, 60, 100, 180);
                add(talonCards.get(i).get(j));
            }
        }

        if (System.getProperty("os.name").equals("Linux"))
            setSize(800, 300);
        else if (System.getProperty("os.name").equals("Windows 10"))
            setSize(818, 300);
        setTitle("Tarock Client : Talon Selection Showing");
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        addWindowListener(new MyWindowListener());
        setResizable(false);
        //setVisible(true);
    }
}
