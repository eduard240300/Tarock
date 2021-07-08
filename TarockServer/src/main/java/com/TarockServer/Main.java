package com.TarockServer;

import com.TarockServer.ConnectionManager.PHPConnection;
import com.TarockServer.Domain.Game;
import com.TarockServer.Domain.Pair;
import com.TarockServer.Domain.Session;
import com.TarockServer.Domain.User;
import com.TarockServer.GUI.StatusForm;
import com.TarockServer.Service.CommunicationService;
import com.TarockServer.Service.DataManipulationService;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static StatusForm statusForm;

    public static void main(String args[]) throws Exception {
        //statusForm = new StatusForm();
        //statusForm.setIconImage(ImageIO.read(new FileInputStream("./Resources/icon.png")));

        //CommunicationService communicationService = new CommunicationService();
        //communicationService.start();

        PHPConnection.getGames("eduard", 5);
    }
}
