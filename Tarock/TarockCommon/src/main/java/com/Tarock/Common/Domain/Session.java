package com.Tarock.Common.Domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Session {
    private int sessionID;
    private String creator;
    private String dateClosed;
    private String player1;
    private String player2;
    private String player3;
    private String player4;
}
