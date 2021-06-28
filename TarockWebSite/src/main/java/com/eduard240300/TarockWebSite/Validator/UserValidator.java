package com.eduard240300.TarockWebSite.Validator;

import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Exception.ValidationException;

public class UserValidator {
    public void validateUser(User user)
    {
        String message = "";
        if (user.getUsername().equals(""))
            message = message + "Username can't be empty !\n";
        if (user.getPassword().equals(""))
            message = message + "Password can't be empty !\n";
        if (!message.equals(""))
            throw new ValidationException(message);
    }
}
