package com.adhamamr.passwordy.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PasswordResponse {
    private Long id;
    private String label;
    private String value;  // encrypted/masked in real implementation remember adham please!!!!
    private String username;
    private String url;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PasswordResponse(Long id, String label, String value, String username,
                            String url, String notes, LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.username = username;
        this.url = url;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}