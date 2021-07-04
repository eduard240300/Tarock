package com.eduard240300.TarockWebSite.Domain;

import java.util.ArrayList;
import java.util.List;

public class Score {
    private List<ScoreAttribute> attributes;
    private String teamThatWon;

    public Score(){
        attributes = new ArrayList<ScoreAttribute>();
        attributes.add(new ScoreAttribute(0, "Game", "", 2));
        attributes.add(new ScoreAttribute(1, "Pope at finish", "No", 0));
        attributes.add(new ScoreAttribute(2, "Pagat at finish", "No", 0));
        attributes.add(new ScoreAttribute(3, "All popes", "No", 0));
        attributes.add(new ScoreAttribute(4, "Trull", "No", 0));
        attributes.add(new ScoreAttribute(5, "Pope caught", "No", 0));
        attributes.add(new ScoreAttribute(6, "Luna caught", "No", 0));
        attributes.add(new ScoreAttribute(7, "Tarocks Player 01", "", 0));
        attributes.add(new ScoreAttribute(8, "Tarocks Player 02", "", 0));
        attributes.add(new ScoreAttribute(9, "Tarocks Player 03", "", 0));
        attributes.add(new ScoreAttribute(10, "Tarocks Player 04", "", 0));
        attributes.add(new ScoreAttribute(11, "Total", "", 0));
    }

    public ScoreAttribute getAttribute(String name)
    {
        for(int i=0;i<attributes.size();i++)
        {
            if (attributes.get(i).getAttributeName().equals(name))
            {
                return attributes.get(i);
            }
        }
        return null;
    }

    public void setAttribute(String name, Object declaredOrDone, int points)
    {
        for(int i=0;i<attributes.size();i++)
        {
            if (attributes.get(i).getAttributeName().equals(name))
            {
                attributes.get(i).setDeclaredOrDone(declaredOrDone);
                attributes.get(i).setPoints(points);
            }
        }
    }

    public String getTeamThatWon()
    {
        return teamThatWon;
    }

    public void setTeamThatWon(String teamThatWon)
    {
        this.teamThatWon = teamThatWon;
    }
}
