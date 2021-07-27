package com.Tarock.Client;

import com.Tarock.Client.GUI.ConnectionForm.ConnectionForm;
import com.Tarock.Client.GUI.GameForm.GameForm;
import com.Tarock.Client.GUI.ScoreForm.ScoreForm;
import com.Tarock.Client.GUI.TalonSelectionForm.TalonSelectionForm;
import com.Tarock.Client.GUI.TalonShowingForm.TalonShowingForm;
import com.Tarock.Client.Repository.Repository;

public class Main {
    public static ConnectionForm connectionForm;
    public static GameForm gameForm;
    public static ScoreForm scoreForm;
    public static TalonSelectionForm talonSelectionForm;
    public static TalonShowingForm talonShowingForm;

    public static void main(String[] args) {
        Repository.initRepository();

        connectionForm = new ConnectionForm(true, true);

        if (args.length == 3) {
            ConnectionForm.usernameField.setText(args[0]);
            ConnectionForm.passwordField.setText(args[1]);
            ConnectionForm.sessionIDField.setText(args[2]);
        } else if (args.length == 4) {
            ConnectionForm.usernameField.setText(args[0]);
            ConnectionForm.passwordField.setText(args[1]);
            ConnectionForm.sessionIDField.setText(args[2]);
            ConnectionForm.ipAddressField.setText(args[3]);
        }

        gameForm = new GameForm(true);
        scoreForm = new ScoreForm(true);
        talonSelectionForm = new TalonSelectionForm(true);
        talonShowingForm = new TalonShowingForm();
    }
}