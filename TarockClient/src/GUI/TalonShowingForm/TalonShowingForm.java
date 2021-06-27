package GUI.TalonShowingForm;

import GUI.Template.JImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TalonShowingForm extends JFrame{
    public static JLabel talonLabel;
    public static List<List<JImage>> talonCards;

    public TalonShowingForm() throws IOException {
        talonCards = new ArrayList<>();
        for(int i=0;i<3;i++)
            talonCards.add(new ArrayList<JImage>());

        Font arialDefault = new Font("Arial", Font.BOLD, 16);
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
                talonCards.get(i).add(new JImage(0, 100, 175));
                talonCards.get(i).get(j).setBounds(40 + 110 * j + 260 * i, 60, 100, 180);
                add(talonCards.get(i).get(j));
            }
        }
        setSize(800, 300);
        setTitle("Tarock Client : Talon Selection Showing");
        getContentPane().setBackground(new Color(78, 154, 6));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
