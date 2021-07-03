package Domain;

import Service.DataManipulationService;

public class Declaration {
    private int the1of2 = 0;
    private boolean popeAtFinish;
    private boolean pagatAtFinish;
    private boolean allPopes;
    private boolean trull;
    private int numberOfTarocks;
    private int pope = -1;

    public Declaration(boolean popeAtFinish, boolean pagatAtFinish, boolean allPopes, boolean trull, int numberOfTarocks)
    {
        this.popeAtFinish = popeAtFinish;
        this.pagatAtFinish = pagatAtFinish;
        this.allPopes = allPopes;
        this.trull = trull;
        this.numberOfTarocks = numberOfTarocks;
    }

    public String toSendableObject()
    {
        String result = "";
        result += DataManipulationService.boolToString(popeAtFinish) + " ";
        result += DataManipulationService.boolToString(pagatAtFinish) + " ";
        result += DataManipulationService.boolToString(allPopes) + " ";
        result += DataManipulationService.boolToString(trull) + " ";
        result += String.valueOf(numberOfTarocks);
        if (pope != -1)
            result += " " + String.valueOf(pope);
        return result;
    }

    public String toString()
    {
        String result = "";
        if (the1of2 != 0) {
            result = the1of2 + String.valueOf(pope);
            if (popeAtFinish)
                result += ", " + "PF";
            if (pagatAtFinish)
                result += ", " + "1F";
            if (allPopes)
                result += ", " + "AP";
            if (trull)
                result += ", " + "AT";
            if (numberOfTarocks > 0)
                result += ", " + String.valueOf(numberOfTarocks) + "T";
        }
        else
        {
            if (popeAtFinish) {
                if (result != "")
                    result += ", ";
                result += "PF";
            }
            if (pagatAtFinish) {
                if (result != "")
                    result += ", ";
                result += "1F";
            }
            if (allPopes) {
                if (result != "")
                    result += ", ";
                result += "AP";
            }
            if (trull) {
                if (result != "")
                    result += ", ";
                result += "AT";
            }
            if (numberOfTarocks > 0)
            {
                if (result != "")
                    result += ", ";
                result += String.valueOf(numberOfTarocks) + "T";
            }
        }
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

    public void setNumberOfTarocks(int numberOfTarocks) {
        this.numberOfTarocks = numberOfTarocks;
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
