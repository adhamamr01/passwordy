package com.adhamamr.passwordy.controller;

import com.adhamamr.passwordy.dto.PasswordGenerationRequest;
import com.adhamamr.passwordy.model.Password;
import com.adhamamr.passwordy.repository.PasswordRepository;
import com.adhamamr.passwordy.service.PasswordService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordService passwordService;
    private final PasswordRepository passwordRepository;

    public PasswordController(PasswordService passwordService, PasswordRepository passwordRepository) {
        this.passwordService = passwordService;
        this.passwordRepository = passwordRepository;
    }

    @PostMapping("/generate")
    public Map<String, String> generate(@RequestBody PasswordGenerationRequest request) {
        System.out.println("Received request: " + request.getLength()
                + ", numbers=" + request.isIncludeNumbers()
                + ", symbols=" + request.isIncludeSymbols());

        String password = passwordService.generatePassword(
                request.getLength(),
                request.isIncludeNumbers(),
                request.isIncludeSymbols()
        );

        passwordRepository.save(new Password(password));
        return Map.of("password", password);
    }

}
