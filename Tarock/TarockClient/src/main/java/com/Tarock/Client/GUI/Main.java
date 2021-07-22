package com.Tarock.Client.GUI;

import com.Tarock.Client.GUI.ConnectionForm.ConnectionForm;
import com.Tarock.Client.GUI.GameForm.GameForm;
import com.Tarock.Client.GUI.ScoreForm.ScoreForm;
import com.Tarock.Client.GUI.TalonSelectionForm.TalonSelectionForm;
import com.Tarock.Client.GUI.TalonShowingForm.TalonShowingForm;
import com.Tarock.Client.Repository.Repository;
import com.Tarock.Common.Service.DataManipulationService;

import javax.imageio.ImageIO;
import java.io.InputStream;

public class Main {
    public static ConnectionForm connectionForm;
    public static GameForm gameForm;
    public static ScoreForm scoreForm;
    public static TalonSelectionForm talonSelectionForm;
    public static TalonShowingForm talonShowingForm;

    public static void main(String[] args){
        InputStream icon = DataManipulationService.getInputStream("icon.png");

        Repository.initRepository();

        connectionForm = new ConnectionForm();

        if (args.length == 3)
        {
            ConnectionForm.usernameField.setText(args[0]);
            ConnectionForm.passwordField.setText(args[1]);
            ConnectionForm.sessionIDField.setText(args[2]);
        }
        else if (args.length == 4)
        {
            ConnectionForm.usernameField.setText(args[0]);
            ConnectionForm.passwordField.setText(args[1]);
            ConnectionForm.sessionIDField.setText(args[2]);
            ConnectionForm.ipAddressField.setText(args[3]);
        }

        gameForm = new GameForm();
        scoreForm = new ScoreForm();
        talonSelectionForm = new TalonSelectionForm();
        talonShowingForm = new TalonShowingForm();
        try
        {
            connectionForm.setIconImage(ImageIO.read(icon));
            gameForm.setIconImage(ImageIO.read(icon));
            scoreForm.setIconImage(ImageIO.read(icon));
            talonSelectionForm.setIconImage(ImageIO.read(icon));
            talonShowingForm.setIconImage(ImageIO.read(icon));
        } catch(Exception ignored){}
    }
}