package GUI.ScoreForm;

import GUI.GameForm.GameForm;
import GUI.Main;
import Repository.Repository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControllerScoreForm {
    public ControllerScoreForm(){
        ScoreForm.previousTeam1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ScoreForm.offset1 > 0) {
                    ScoreForm.offset1--;
                    ScoreForm.nextTeam1Button.setEnabled(true);
                }
                if (ScoreForm.offset1 == 0)
                    ScoreForm.previousTeam1Button.setEnabled(false);
                else
                    ScoreForm.previousTeam1Button.setEnabled(true);
                try {
                    Main.scoreForm.updateCards();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        ScoreForm.previousTeam2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ScoreForm.offset2 > 0) {
                    ScoreForm.offset2--;
                    ScoreForm.nextTeam2Button.setEnabled(true);
                }
                if (ScoreForm.offset2 == 0)
                    ScoreForm.previousTeam2Button.setEnabled(false);
                else
                    ScoreForm.previousTeam2Button.setEnabled(true);
                try {
                    Main.scoreForm.updateCards();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        ScoreForm.nextTeam1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ScoreForm.offset1 < (Repository.cardsWon.get(0).size() - 6)) {
                    ScoreForm.offset1++;
                    ScoreForm.previousTeam1Button.setEnabled(true);
                }
                if (ScoreForm.offset1 == (Repository.cardsWon.get(0).size() - 6))
                    ScoreForm.nextTeam1Button.setEnabled(false);
                else
                    ScoreForm.nextTeam1Button.setEnabled(true);
                try {
                    Main.scoreForm.updateCards();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        ScoreForm.nextTeam2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ScoreForm.offset2 < (Repository.cardsWon.get(1).size() - 6)) {
                    ScoreForm.offset2++;
                    ScoreForm.previousTeam2Button.setEnabled(true);
                }
                if (ScoreForm.offset2 == (Repository.cardsWon.get(1).size() - 6))
                    ScoreForm.nextTeam2Button.setEnabled(false);
                else
                    ScoreForm.nextTeam2Button.setEnabled(true);
                try {
                    Main.scoreForm.updateCards();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        ScoreForm.OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
    }
}
