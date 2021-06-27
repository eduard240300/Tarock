package GUI;

import GUI.ConnectionForm.ConnectionForm;
import GUI.GameForm.GameForm;
import GUI.ScoreForm.ScoreForm;
import GUI.TalonSelectionForm.TalonSelectionForm;
import GUI.TalonShowingForm.TalonShowingForm;

public class Main {
    public static ConnectionForm connectionForm;
    public static GameForm gameForm;
    public static ScoreForm scoreForm;
    public static TalonSelectionForm talonSelectionForm;
    public static TalonShowingForm talonShowingForm;

    public static void main(String[] args) throws Exception {
        connectionForm = new ConnectionForm();
        gameForm = new GameForm();
        scoreForm = new ScoreForm();
        talonSelectionForm = new TalonSelectionForm();
        talonShowingForm = new TalonShowingForm();
    }
}