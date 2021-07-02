package GUI;

import GUI.ConnectionForm.ConnectionForm;
import GUI.GameForm.GameForm;
import GUI.ScoreForm.ScoreForm;
import GUI.TalonSelectionForm.TalonSelectionForm;
import GUI.TalonShowingForm.TalonShowingForm;
import Repository.Repository;
import Service.CommunicationService;

import javax.imageio.ImageIO;
import java.io.FileInputStream;

public class Main {
    public static ConnectionForm connectionForm;
    public static GameForm gameForm;
    public static ScoreForm scoreForm;
    public static TalonSelectionForm talonSelectionForm;
    public static TalonShowingForm talonShowingForm;

    public static void main(String[] args) throws Exception {

        String iconPath = "./Resources/icon.png";

        Repository.initRepository();

        connectionForm = new ConnectionForm();
        gameForm = new GameForm();
        //scoreForm = new ScoreForm();
        //talonSelectionForm = new TalonSelectionForm();
        //talonShowingForm = new TalonShowingForm();
        try
        {
            connectionForm.setIconImage(ImageIO.read(new FileInputStream(iconPath)));
            gameForm.setIconImage(ImageIO.read(new FileInputStream(iconPath)));
            scoreForm.setIconImage(ImageIO.read(new FileInputStream(iconPath)));
            talonSelectionForm.setIconImage(ImageIO.read(new FileInputStream(iconPath)));
            talonShowingForm.setIconImage(ImageIO.read(new FileInputStream(iconPath)));
        }catch(Exception e){}
    }
}