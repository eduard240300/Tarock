package GUI.Template;

import GUI.ConnectionForm.ConnectionForm;
import GUI.Main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MyWindowListener implements WindowListener {

    public void windowClosing(WindowEvent arg0) {
        boolean canClose = true;
        String name = arg0.getWindow().getClass().toString();
        if (!name.equals("class GUI.ConnectionForm.ConnectionForm"))
            canClose = canClose & !Main.connectionForm.isVisible();
        if (!name.equals("class GUI.GameForm.GameForm"))
            canClose = canClose & !Main.gameForm.isVisible();
        if (!name.equals("class GUI.ScoreForm.ScoreForm"))
            canClose = canClose & !Main.scoreForm.isVisible();
        if (!name.equals("class GUI.TalonSelectionForm.TalonSelectionForm"))
            canClose = canClose & !Main.talonSelectionForm.isVisible();
        if (!name.equals("class GUI.TalonShowingForm.TalonShowingForm"))
            canClose = canClose & !Main.talonShowingForm.isVisible();
        if (canClose)
            System.exit(0);
    }
    public void windowOpened(WindowEvent arg0) {}
    public void windowClosed(WindowEvent arg0) {}
    public void windowIconified(WindowEvent arg0) {}
    public void windowDeiconified(WindowEvent arg0) {}
    public void windowActivated(WindowEvent arg0) {}
    public void windowDeactivated(WindowEvent arg0) {}

}