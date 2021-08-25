package com.Tarock.Common.Domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String name;
    private String username;
    private String password;
}
