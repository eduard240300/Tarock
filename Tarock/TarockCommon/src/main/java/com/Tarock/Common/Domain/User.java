package com.Tarock.Common.Domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String name;
    private String username;
    private String password;

    @Override
    public String toString(){
        return "User(name=" + name + ", username=" + username + ")";
    }
}
