package com.Tarock.Common.Domain;

import androidx.annotation.NonNull;

import com.Tarock.Common.Service.DataManipulationService;

import java.util.ArrayList;
import java.util.List;

public class Score {
    private final List<ScoreAttribute> attributes;
    private boolean isRadler = false;
    private String teamThatWon;

    public Score() {
        attributes = new ArrayList<>();
        attributes.add(new ScoreAttribute(0, "Game", "", 2));
        attributes.add(new ScoreAttribute(1, "Pope at finish", "No", 0));
        attributes.add(new ScoreAttribute(2, "Pagat at finish", "No", 0));
        attributes.add(new ScoreAttribute(3, "All popes", "No", 0));
        attributes.add(new ScoreAttribute(4, "All trull cards", "No", 0));
        attributes.add(new ScoreAttribute(5, "Pope caught", "No", 0));
        attributes.add(new ScoreAttribute(6, "Luna caught", "No", 0));
        attributes.add(new ScoreAttribute(7, "Tarocks Player 01", "", 0));
        attributes.add(new ScoreAttribute(8, "Tarocks Player 02", "", 0));
        attributes.add(new ScoreAttribute(9, "Tarocks Player 03", "", 0));
        attributes.add(new ScoreAttribute(10, "Tarocks Player 04", "", 0));
        attributes.add(new ScoreAttribute(11, "Total", "", 0));
    }

    @NonNull
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            if (!string.toString().equals(""))
                string.append(" ");
            string.append(DataManipulationService.processName(getAttributeByID(i).getDeclaredOrDone()));
        }
        for (int i = 0; i < 12; i++) {
            string.append(" ").append(getAttributeByID(i).getPoints());
        }
        return string.toString();
    }

    public void setRadler(boolean isRadler) {
        this.isRadler = isRadler;
    }

    public void computeTotal() {
        int total = 0;
        for (int i = 0; i < 11; i++) {
            total += getAttributeByID(i).getPoints();
        }
        if (isRadler) {
            total = total * 2;
            getAttribute("Total").setDeclaredOrDone("Radler");
        } else {
            getAttribute("Total").setDeclaredOrDone("Simple");
        }
        getAttribute("Total").setPoints(total);
    }

    public ScoreAttribute getAttributeByID(int ID) {
        for (ScoreAttribute attribute : attributes) {
            if (attribute.getID() == ID) {
                return attribute;
            }
        }
        return null;
    }

    public ScoreAttribute getAttribute(String name) {
        for (ScoreAttribute attribute : attributes) {
            if (attribute.getAttributeName().equals(name)) {
                return attribute;
            }
        }
        return null;
    }

    public String getTeamThatWon() {
        return teamThatWon;
    }

    public void setTeamThatWon(String teamThatWon) {
        this.teamThatWon = teamThatWon;
    }
}
