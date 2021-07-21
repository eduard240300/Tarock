package com.Tarock.Client.Domain;

import com.Tarock.Client.Service.DataManipulationService;

public class Declaration {
    private final boolean popeAtFinish;
    private final boolean pagatAtFinish;
    private final boolean allPopes;
    private final boolean trull;
    private final int numberOfTarocks;

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
        return result;
    }

    public String toString()
    {
        String result = "";
        if (popeAtFinish) {
            result += "PF";
        }
        if (pagatAtFinish) {
            if (!result.equals(""))
                result += ", ";
            result += "1F";
        }
        if (allPopes) {
            if (!result.equals(""))
                result += ", ";
            result += "AP";
        }
        if (trull) {
            if (!result.equals(""))
                result += ", ";
            result += "AT";
        }
        if (numberOfTarocks > 0)
        {
            if (!result.equals(""))
                result += ", ";
            result += numberOfTarocks + "T";
        }
        if (result.equals(""))
            return "Nothing";
        return result;
    }
}
