package com.Tarock.Common.Validator;

import com.Tarock.Common.Exception.ValidationException;

public class IntegerValidator {
    public static void validateInteger(String potentialInteger){
        try{
            Integer.parseInt(potentialInteger);
        }
        catch (Exception e){
            throw new ValidationException("This isn't an integer !");
        }
    }
}
