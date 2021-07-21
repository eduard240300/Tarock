package com.Tarock.Server.Domain;

public class ScoreAttribute {
    private final int ID;
    private final String attributeName;
    private String declaredOrDone;
    private int points;

    public ScoreAttribute(int ID, String attributeName, String declaredOrDone, int points)
    {
        this.ID = ID;
        this.attributeName = attributeName;
        this.declaredOrDone = declaredOrDone;
        this.points = points;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getDeclaredOrDone() {
        return declaredOrDone;
    }

    public void setDeclaredOrDone(String declaredOrDone) {
        this.declaredOrDone = declaredOrDone;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getID() {
        return ID;
    }
}
