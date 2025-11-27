package com.adhamamr.passwordy.service;

import com.adhamamr.passwordy.dto.PasswordSaveRequest;
import com.adhamamr.passwordy.dto.PasswordResponse;
import com.adhamamr.passwordy.model.Password;
import com.adhamamr.passwordy.model.User;
import com.adhamamr.passwordy.repository.PasswordRepository;
import com.adhamamr.passwordy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasswordServiceImpl implements PasswordService {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+";

    private static final SecureRandom random = new SecureRandom();

    private final PasswordRepository passwordRepository;
    private final UserRepository userRepository;

    public PasswordServiceImpl(PasswordRepository passwordRepository, UserRepository userRepository) {
        this.passwordRepository = passwordRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase,
                                   boolean includeNumbers, boolean includeSymbols) {
        StringBuilder chars = new StringBuilder();
        StringBuilder password = new StringBuilder();

        // Ensure at least one character from each selected type
        if (includeUppercase) {
            chars.append(UPPERCASE);
            password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        }
        if (includeLowercase) {
            chars.append(LOWERCASE);
            password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        }
        if (includeNumbers) {
            chars.append(NUMBERS);
            password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
        if (includeSymbols) {
            chars.append(SYMBOLS);
            password.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        }

        // Default to lowercase if nothing selected
        if (chars.isEmpty()) {
            chars.append(LOWERCASE);
        }

        // Fill remaining length with random characters
        for (int i = password.length(); i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        // Shuffle using Fisher-Yates algorithm
        return shuffleString(password.toString());
    }

    /**
     * Fisher-Yates shuffle algorithm
     */
    private String shuffleString(String input) {
        char[] chars = input.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Swap chars[i] and chars[j]
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }

    @Override
    public PasswordResponse savePassword(PasswordSaveRequest request, String username) {
        // Find the user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Password password = new Password();
        password.setLabel(request.getLabel());
        password.setValue(request.getPassword());
        password.setUsername(request.getUsername());
        password.setUrl(request.getUrl());
        password.setNotes(request.getNotes());
        password.setUser(user);  // Link to user

        Password saved = passwordRepository.save(password);
        return toResponse(saved);
    }

    @Override
    public List<PasswordResponse> getAllPasswords(String username) {
        // Find the user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find all passwords for this user
        return passwordRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PasswordResponse getPasswordById(Long id, String username) {
        Password password = passwordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Password not found with id: " + id));

        // Verify the password belongs to the authenticated user
        if (!password.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to password");
        }

        return toResponse(password);
    }

    @Override
    public PasswordResponse updatePassword(Long id, PasswordSaveRequest request, String username) {
        Password password = passwordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Password not found with id: " + id));

        // Verify the password belongs to the authenticated user
        if (!password.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to password");
        }

        password.setLabel(request.getLabel());
        password.setValue(request.getPassword());
        password.setUsername(request.getUsername());
        password.setUrl(request.getUrl());
        password.setNotes(request.getNotes());

        Password updated = passwordRepository.save(password);
        return toResponse(updated);
    }

    @Override
    public void deletePassword(Long id, String username) {
        Password password = passwordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Password not found with id: " + id));

        // Verify the password belongs to the authenticated user
        if (!password.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to password");
        }

        passwordRepository.deleteById(id);
    }

    private PasswordResponse toResponse(Password password) {
        return new PasswordResponse(
                password.getId(),
                password.getLabel(),
                password.getValue(),
                password.getUsername(),
                password.getUrl(),
                password.getNotes(),
                password.getCreatedAt(),
                password.getUpdatedAt()
        );
    }
}