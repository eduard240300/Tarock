package com.Tarock.Client.GUI.Template;

import com.Tarock.Client.GUI.Main;
import com.Tarock.Client.Service.DataManipulationService;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class JImage extends JLabel {
    public int CardID;

    public void setCardID(int CardID) {
        this.CardID = CardID;
        String SID;
        if (this.CardID < 10)
            SID = "0" + this.CardID;
        else
            SID = String.valueOf(this.CardID);
        File inputFile = DataManipulationService.getFile("TarockCards/r" + SID + ".png");
        Icon icon = new ImageIcon(String.valueOf(inputFile));
        setIcon(icon);
    }

    public JImage(int CardID) {
        setCardID(CardID);
    }
}
