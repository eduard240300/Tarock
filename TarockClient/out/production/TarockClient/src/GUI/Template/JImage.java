package GUI.Template;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JImage extends JLabel {
    public int CardID;
    private int width;
    private int height;

    public void setCardID(int CardID) throws IOException {
        this.CardID = CardID;
        String SID;
        if (this.CardID < 10)
            SID = "0" + String.valueOf(this.CardID);
        else
            SID = String.valueOf(this.CardID);
        File inputFile = new File("./Resources/TarockCards/r" + SID + ".png");
        Icon icon = new ImageIcon(String.valueOf(inputFile));
        setIcon(icon);
    }

    public JImage(int CardID, int width, int height) throws IOException {
        this.width = width;
        this.height = height;
        setCardID(CardID);
    }

    public JImage() {}
}
