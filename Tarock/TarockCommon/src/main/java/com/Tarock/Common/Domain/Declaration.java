package com.Tarock.Common.Domain;

import com.Tarock.Common.Service.DataManipulationService;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Declaration {
    private int the1of2;
    private boolean popeAtFinish;
    private boolean pagatAtFinish;
    private boolean allPopes;
    private boolean trull;
    private int numberOfTarocks;
    private int pope;

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
}
