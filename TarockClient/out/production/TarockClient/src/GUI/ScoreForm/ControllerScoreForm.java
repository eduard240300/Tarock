package GUI.ScoreForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerScoreForm {
    public ControllerScoreForm(){
        ScoreForm.previousTeam1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed previousTeam1Button Button");
            }
        });
        ScoreForm.previousTeam2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed previousTeam2Button Button");
            }
        });
        ScoreForm.nextTeam1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed nextTeam1Button Button");
            }
        });
        ScoreForm.nextTeam2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed nextTeam2Button Button");
            }
        });
        ScoreForm.OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed OKButton Button");
            }
        });
    }
}
