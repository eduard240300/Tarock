package com.Tarock.Common.Domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ScoreAttribute {
    private final int ID;
    private final String attributeName;
    private String declaredOrDone;
    private int points;
}
