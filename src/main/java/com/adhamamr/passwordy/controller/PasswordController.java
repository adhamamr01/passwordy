package com.adhamamr.passwordy.controller;

import com.adhamamr.passwordy.model.Password;
import com.adhamamr.passwordy.repository.PasswordRepository;
import com.adhamamr.passwordy.service.PasswordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordService passwordService;
    private final PasswordRepository passwordRepository;

    public PasswordController(PasswordService passwordService, PasswordRepository passwordRepository) {
        this.passwordService = passwordService;
        this.passwordRepository = passwordRepository;
    }

    @GetMapping("/generate")
    public String generate(
            @RequestParam(defaultValue = "12") int length,
            @RequestParam(defaultValue = "true") boolean includeNumbers,
            @RequestParam(defaultValue = "true") boolean includeSymbols) {

        String password = passwordService.generatePassword(length, includeNumbers, includeSymbols);
        passwordRepository.save(new Password(password));
        return password;
    }

}
