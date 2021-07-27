package com.Tarock.Common.Validator;

import com.Tarock.Common.Domain.User;
import com.Tarock.Common.Exception.ValidationException;
import com.Tarock.Common.Service.DataManipulationService;

public class UserValidator {
    public void validateUser(User user) {
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
