package com.adhamamr.passwordy.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordServiceImpl implements PasswordService {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+";

    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generatePassword(int length, boolean includeNumbers, boolean includeSymbols) {
        StringBuilder chars = new StringBuilder(LETTERS);
        if (includeNumbers) chars.append(NUMBERS);
        if (includeSymbols) chars.append(SYMBOLS);

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
