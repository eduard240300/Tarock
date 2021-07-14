package GUI.Template;

import javax.swing.*;
import java.io.File;

public class JImage extends JLabel {
    public int CardID;

    public void setCardID(int CardID) {
        this.CardID = CardID;
        String SID;
        if (this.CardID < 10)
            SID = "0" + this.CardID;
        else
            SID = String.valueOf(this.CardID);
        File inputFile = new File("./Resources/TarockCards/r" + SID + ".png");
        Icon icon = new ImageIcon(String.valueOf(inputFile));
        setIcon(icon);
    }

    public JImage(int CardID) {
        setCardID(CardID);
    }
}
