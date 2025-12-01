package com.adhamamr.passwordy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PasswordResponse {
    private Long id;
    private String label;
    private String value;  // encrypted/masked in real implementation remember adham please!!!!
    private String username;
    private String url;
    private String notes;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}