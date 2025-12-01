package com.adhamamr.passwordy.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordGenerationRequest {
    private int length = 16;
    private boolean includeSymbols = true;
}