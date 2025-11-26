package com.adhamamr.passwordy.controller;

import com.adhamamr.passwordy.dto.PasswordGenerationRequest;
import com.adhamamr.passwordy.dto.PasswordResponse;
import com.adhamamr.passwordy.dto.PasswordSaveRequest;
import com.adhamamr.passwordy.service.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Generate password endpoint
    @PostMapping("/password/generate")
    public Map<String, String> generatePassword(@RequestBody PasswordGenerationRequest request) {
        System.out.println("Received request: " + request.getLength()
                + ", uppercase=" + request.isIncludeUppercase()
                + ", lowercase=" + request.isIncludeLowercase()
                + ", numbers=" + request.isIncludeNumbers()
                + ", symbols=" + request.isIncludeSymbols());

        String password = passwordService.generatePassword(
                request.getLength(),
                request.isIncludeUppercase(),
                request.isIncludeLowercase(),
                request.isIncludeNumbers(),
                request.isIncludeSymbols()
        );

        return Map.of("password", password);
    }

    // Save password
    @PostMapping("/passwords")
    public ResponseEntity<PasswordResponse> savePassword(@RequestBody PasswordSaveRequest request) {
        PasswordResponse response = passwordService.savePassword(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all passwords
    @GetMapping("/passwords")
    public ResponseEntity<List<PasswordResponse>> getAllPasswords() {
        List<PasswordResponse> passwords = passwordService.getAllPasswords();
        return ResponseEntity.ok(passwords);
    }

    // Get password by ID
    @GetMapping("/passwords/{id}")
    public ResponseEntity<PasswordResponse> getPasswordById(@PathVariable Long id) {
        PasswordResponse password = passwordService.getPasswordById(id);
        return ResponseEntity.ok(password);
    }

    // Update password
    @PutMapping("/passwords/{id}")
    public ResponseEntity<PasswordResponse> updatePassword(
            @PathVariable Long id,
            @RequestBody PasswordSaveRequest request) {
        PasswordResponse updated = passwordService.updatePassword(id, request);
        return ResponseEntity.ok(updated);
    }

    // Delete password
    @DeleteMapping("/passwords/{id}")
    public ResponseEntity<Void> deletePassword(@PathVariable Long id) {
        passwordService.deletePassword(id);
        return ResponseEntity.noContent().build();
    }
}