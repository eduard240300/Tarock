package com.Tarock.Client.Validator;

import com.Tarock.Client.Domain.User;
import com.Tarock.Client.Exception.ValidationException;
import com.Tarock.Client.Service.DataManipulationService;

public class UserValidator {
    public void validateUser(User user)
    {
        String message = "";
        if (DataManipulationService.containsSpaces(user.getUsername()))
            message = message + "Username can't contain spaces !\n";
        if (user.getUsername().equals(""))
            message = message + "Username can't be empty !\n";
        if (DataManipulationService.containsSpaces(user.getPassword()))
            message = message + "Password can't contain spaces !\n";
        if (user.getPassword().equals(""))
            message = message + "Password can't be empty !\n";
        if (!message.equals(""))
            throw new ValidationException(message);
    }
}
