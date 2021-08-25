package com.Tarock.Common.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PHPResponse {
    private String result;
    private String exception;
    private String error;
}
