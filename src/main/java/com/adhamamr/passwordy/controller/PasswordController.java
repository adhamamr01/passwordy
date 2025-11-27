package com.adhamamr.passwordy.controller;

import com.adhamamr.passwordy.dto.PasswordGenerationRequest;
import com.adhamamr.passwordy.dto.PasswordResponse;
import com.adhamamr.passwordy.dto.PasswordSaveRequest;
import com.adhamamr.passwordy.service.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /**
     * Get the username of the currently authenticated user
     */
    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    // Generate password endpoint (no auth required for generation)
    @PostMapping("/password/generate")
    public Map<String, String> generatePassword(@RequestBody PasswordGenerationRequest request) {
        String password = passwordService.generatePassword(
                request.getLength(),
                request.isIncludeUppercase(),
                request.isIncludeLowercase(),
                request.isIncludeNumbers(),
                request.isIncludeSymbols()
        );

        return Map.of("password", password);
    }

    // Save password (requires authentication)
    @PostMapping("/passwords")
    public ResponseEntity<PasswordResponse> savePassword(@RequestBody PasswordSaveRequest request) {
        String username = getAuthenticatedUsername();
        PasswordResponse response = passwordService.savePassword(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all passwords (requires authentication)
    @GetMapping("/passwords")
    public ResponseEntity<List<PasswordResponse>> getAllPasswords() {
        String username = getAuthenticatedUsername();
        List<PasswordResponse> passwords = passwordService.getAllPasswords(username);
        return ResponseEntity.ok(passwords);
    }

    // Get password by ID (requires authentication)
    @GetMapping("/passwords/{id}")
    public ResponseEntity<PasswordResponse> getPasswordById(@PathVariable Long id) {
        String username = getAuthenticatedUsername();
        PasswordResponse password = passwordService.getPasswordById(id, username);
        return ResponseEntity.ok(password);
    }

    // Update password (requires authentication)
    @PutMapping("/passwords/{id}")
    public ResponseEntity<PasswordResponse> updatePassword(
            @PathVariable Long id,
            @RequestBody PasswordSaveRequest request) {
        String username = getAuthenticatedUsername();
        PasswordResponse updated = passwordService.updatePassword(id, request, username);
        return ResponseEntity.ok(updated);
    }

    // Delete password (requires authentication)
    @DeleteMapping("/passwords/{id}")
    public ResponseEntity<Void> deletePassword(@PathVariable Long id) {
        String username = getAuthenticatedUsername();
        passwordService.deletePassword(id, username);
        return ResponseEntity.noContent().build();
    }
}