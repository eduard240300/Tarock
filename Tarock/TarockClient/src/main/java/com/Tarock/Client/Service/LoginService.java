package com.Tarock.Client.Service;

import com.Tarock.Common.Domain.User;

public class LoginService {
    public void login(User user, int sessionID) {
        CommunicationService.sessionID = sessionID;
        CommunicationService.user = user;
    }
}
