package com.adhamamr.passwordy.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordGenerationRequest {
    private int length = 12;
    private boolean includeUppercase = true;  // NEW
    private boolean includeLowercase = true;  // NEW
    private boolean includeNumbers = true;
    private boolean includeSymbols = true;
}