package com.Tarock.Server;

import com.Tarock.Server.ConnectionManager.PHPConnection;
import com.Tarock.Server.GUI.StatusForm;
import com.Tarock.Server.Service.CommunicationService;
import com.Tarock.Common.Service.DataManipulationService;

import javax.imageio.ImageIO;
import java.io.InputStream;

public class Main {
    public static StatusForm statusForm;

    public static void main(String[] args) throws Exception {
        InputStream icon = DataManipulationService.getInputStream("icon.png");
        PHPConnection.initPHPConnection();

        statusForm = new StatusForm();
        statusForm.setIconImage(ImageIO.read(icon));

        CommunicationService communicationService = new CommunicationService();
        communicationService.start();
    }
}
