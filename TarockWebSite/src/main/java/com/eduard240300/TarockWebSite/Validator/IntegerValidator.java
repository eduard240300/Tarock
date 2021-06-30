package com.eduard240300.TarockWebSite.Validator;

import com.eduard240300.TarockWebSite.Exception.ValidationException;

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
