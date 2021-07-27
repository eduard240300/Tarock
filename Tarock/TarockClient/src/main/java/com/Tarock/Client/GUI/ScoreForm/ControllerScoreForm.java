package com.Tarock.Client.GUI.ScoreForm;

import com.Tarock.Client.Main;
import com.Tarock.Client.Repository.Repository;

import java.io.IOException;

public class ControllerScoreForm {
    public ControllerScoreForm() {
        ScoreForm.previousTeam1Button.addActionListener(e -> {
            if (ScoreForm.offset1 > 0) {
                ScoreForm.offset1--;
                ScoreForm.nextTeam1Button.setEnabled(true);
            }
            ScoreForm.previousTeam1Button.setEnabled(ScoreForm.offset1 != 0);
            try {
                ScoreForm.updateCards();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        ScoreForm.previousTeam2Button.addActionListener(e -> {
            if (ScoreForm.offset2 > 0) {
                ScoreForm.offset2--;
                ScoreForm.nextTeam2Button.setEnabled(true);
            }
            ScoreForm.previousTeam2Button.setEnabled(ScoreForm.offset2 != 0);
            try {
                ScoreForm.updateCards();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        ScoreForm.nextTeam1Button.addActionListener(e -> {
            if (ScoreForm.offset1 < (Repository.cardsWon.get(0).size() - 6)) {
                ScoreForm.offset1++;
                ScoreForm.previousTeam1Button.setEnabled(true);
            }
            ScoreForm.nextTeam1Button.setEnabled(ScoreForm.offset1 != (Repository.cardsWon.get(0).size() - 6));
            try {
                ScoreForm.updateCards();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        ScoreForm.nextTeam2Button.addActionListener(e -> {
            if (ScoreForm.offset2 < (Repository.cardsWon.get(1).size() - 6)) {
                ScoreForm.offset2++;
                ScoreForm.previousTeam2Button.setEnabled(true);
            }
            ScoreForm.nextTeam2Button.setEnabled(ScoreForm.offset2 != (Repository.cardsWon.get(1).size() - 6));
            try {
                ScoreForm.updateCards();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        ScoreForm.OKButton.addActionListener(e -> Main.scoreForm.setVisible(false));
    }
}
