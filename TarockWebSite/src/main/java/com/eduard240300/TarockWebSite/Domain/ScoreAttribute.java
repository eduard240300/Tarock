package com.eduard240300.TarockWebSite.Domain;

public class ScoreAttribute {
    private int ID;
    private String attributeName;
    private Object declaredOrDone;
    private int points;

    public ScoreAttribute(int ID, String attributeName, Object declaredOrDone, int points)
    {
        this.ID = ID;
        this.attributeName = attributeName;
        this.declaredOrDone = declaredOrDone;
        this.points = points;
    }

    public ScoreAttribute() {}

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Object getDeclaredOrDone() {
        return declaredOrDone;
    }

    public void setDeclaredOrDone(Object declaredOrDone) {
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

    public void setID(int ID) {
        this.ID = ID;
    }
}
