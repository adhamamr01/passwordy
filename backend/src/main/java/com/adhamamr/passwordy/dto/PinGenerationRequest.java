package com.adhamamr.passwordy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinGenerationRequest {
    private int length = 6;  // Default PIN length
}