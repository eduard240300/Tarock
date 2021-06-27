package GUI;

import GUI.ConnectionForm.ConnectionForm;
import GUI.GameForm.GameForm;

public class Main {
    public static ConnectionForm connectionForm;
    public static GameForm gameForm;

    public static void main(String[] args) throws Exception {
        connectionForm = new ConnectionForm();
        gameForm = new GameForm();
    }
}