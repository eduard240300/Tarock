package com.eduard240300.TarockWebSite.Validator;

import com.eduard240300.TarockWebSite.Domain.User;
import com.eduard240300.TarockWebSite.Exception.ValidationException;
import com.eduard240300.TarockWebSite.Service.DataManipulationService;

public class UserValidator {
    public void validateUser(User user)
    {
        String message = "";
        if (user.getName() != null)
            if (user.getName().equals(""))
                message = message + "Name can't be empty ! ";
        if (user.getUsername().equals(""))
            message = message + "Username can't be empty ! ";
        if (DataManipulationService.containsChar(user.getUsername(), ' '))
            message = message + "Username can't contain spaces ! ";
        if (user.getPassword().equals(""))
            message = message + "Password  can't be empty ! ";
        if (DataManipulationService.containsChar(user.getPassword(), ' '))
            message = message + "Password can't contain spaces ! ";
        if (user.getEmail() != null) {
            if (user.getEmail().equals(""))
                message = message + "Email field can't be empty ! ";
            if (DataManipulationService.containsChar(user.getEmail(), ' '))
                message = message + "Email can't contain spaces ! ";
        }
        if (!message.equals(""))
            throw new ValidationException(message);
    }
}
