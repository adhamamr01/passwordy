package com.adhamamr.passwordy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordSaveRequest {
    private String label;
    private String password;
    private String username;
    private String url;
    private String notes;
}