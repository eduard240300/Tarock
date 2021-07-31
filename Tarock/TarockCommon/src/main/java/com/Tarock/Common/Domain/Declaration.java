package com.Tarock.Common.Domain;

import com.Tarock.Common.Service.DataManipulationService;

public class Declaration {
    private int the1of2 = 0;
    private boolean popeAtFinish;
    private boolean pagatAtFinish;
    private boolean allPopes;
    private boolean trull;
    private final int numberOfTarocks;
    private int pope = -1;

    public Declaration() {
        this.popeAtFinish = false;
        this.pagatAtFinish = false;
        this.allPopes = false;
        this.trull = false;
        this.numberOfTarocks = 0;
    }

    public Declaration(boolean popeAtFinish, boolean pagatAtFinish, boolean allPopes, boolean trull, int numberOfTarocks) {
        this.popeAtFinish = popeAtFinish;
        this.pagatAtFinish = pagatAtFinish;
        this.allPopes = allPopes;
        this.trull = trull;
        this.numberOfTarocks = numberOfTarocks;
    }


    public String toSendableObject() {
        String result = "";
        result += DataManipulationService.boolToString(popeAtFinish) + " ";
        result += DataManipulationService.boolToString(pagatAtFinish) + " ";
        result += DataManipulationService.boolToString(allPopes) + " ";
        result += DataManipulationService.boolToString(trull) + " ";
        result += String.valueOf(numberOfTarocks);
        if (pope != -1)
            result += " " + pope;
        return result;
    }

    private String addToResult(String result, String toAdd) {
        String finalResult = result;
        if (!result.equals(""))
            finalResult += ", ";
        finalResult += toAdd;
        return finalResult;
    }

    public String toString() {
        String result = "";
        if (the1of2 != 0) result = the1of2 + "p" + pope;
        if (popeAtFinish) result = addToResult(result, "PF");
        if (pagatAtFinish) result = addToResult(result, "1F");
        if (allPopes) result = addToResult(result, "AP");
        if (trull) result = addToResult(result, "AT");
        if (numberOfTarocks > 0) result = addToResult(result, numberOfTarocks + "T");
        if (result.equals(""))
            return "Nothing";
        return result;
    }

    public boolean getPopeAtFinish() {
        return popeAtFinish;
    }

    public void setPopeAtFinish(boolean popeAtFinish) {
        this.popeAtFinish = popeAtFinish;
    }

    public boolean getPagatAtFinish() {
        return pagatAtFinish;
    }

    public void setPagatAtFinish(boolean pagatAtFinish) {
        this.pagatAtFinish = pagatAtFinish;
    }

    public boolean getAllPopes() {
        return allPopes;
    }

    public void setAllPopes(boolean allPopes) {
        this.allPopes = allPopes;
    }

    public boolean getTrull() {
        return trull;
    }

    public void setTrull(boolean trull) {
        this.trull = trull;
    }

    public int getNumberOfTarocks() {
        return numberOfTarocks;
    }

    public int getPope() {
        return pope;
    }

    public void setPope(int pope) {
        this.pope = pope;
    }

    public int getThe1of2() {
        return the1of2;
    }

    public void setThe1of2(int the1of2) {
        this.the1of2 = the1of2;
    }
}
