package com.TarockServer;

import com.TarockServer.GUI.StatusForm;
import com.TarockServer.Service.CommunicationService;

import javax.imageio.ImageIO;
import java.io.FileInputStream;

public class Main {
    public static StatusForm statusForm;

    public static void main(String[] args) throws Exception {
        statusForm = new StatusForm();
        statusForm.setIconImage(ImageIO.read(new FileInputStream("./Resources/icon.png")));

        CommunicationService communicationService = new CommunicationService();
        communicationService.start();
    }
}
