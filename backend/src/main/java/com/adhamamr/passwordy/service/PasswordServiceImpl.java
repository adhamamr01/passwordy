package com.adhamamr.passwordy.service;

import com.adhamamr.passwordy.dto.PasswordSaveRequest;
import com.adhamamr.passwordy.dto.PasswordResponse;
import com.adhamamr.passwordy.model.Password;
import com.adhamamr.passwordy.model.User;
import com.adhamamr.passwordy.repository.PasswordRepository;
import com.adhamamr.passwordy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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
    private final EncryptionService encryptionService;  // NEW

    public PasswordServiceImpl(PasswordRepository passwordRepository,
                               UserRepository userRepository,
                               EncryptionService encryptionService) {  // NEW
        this.passwordRepository = passwordRepository;
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;  // NEW
    }

    @Override
    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase,
                                   boolean includeNumbers, boolean includeSymbols) {
        StringBuilder chars = new StringBuilder();
        StringBuilder password = new StringBuilder();

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

        if (chars.isEmpty()) {
            chars.append(LOWERCASE);
        }

        for (int i = password.length(); i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return shuffleString(password.toString());
    }

    private String shuffleString(String input) {
        char[] chars = input.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }

    @Override
    public PasswordResponse savePassword(PasswordSaveRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Password password = new Password();
        password.setLabel(request.getLabel());

        // ENCRYPT password before saving
        try {
            String encryptedPassword = encryptionService.encrypt(request.getPassword());
            password.setValue(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt password", e);
        }

        password.setUsername(request.getUsername());
        password.setUrl(request.getUrl());
        password.setNotes(request.getNotes());
        password.setUser(user);

        Password saved = passwordRepository.save(password);
        return toResponse(saved);
    }

    @Override
    public List<PasswordResponse> getAllPasswords(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PasswordResponse getPasswordById(Long id, String username) {
        Password password = passwordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Password not found with id: " + id));

        if (!password.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to password");
        }

        return toResponse(password);
    }

    @Override
    public PasswordResponse updatePassword(Long id, PasswordSaveRequest request, String username) {
        Password password = passwordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Password not found with id: " + id));

        if (!password.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to password");
        }

        password.setLabel(request.getLabel());

        // ENCRYPT password before updating
        try {
            String encryptedPassword = encryptionService.encrypt(request.getPassword());
            password.setValue(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt password", e);
        }

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

        if (!password.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to password");
        }

        passwordRepository.deleteById(id);
    }

    private PasswordResponse toResponse(Password password) {
        // Return encrypted password (don't decrypt in list view for security)
        return new PasswordResponse(
                password.getId(),
                password.getLabel(),
                password.getValue(),  // Still encrypted
                password.getUsername(),
                password.getUrl(),
                password.getNotes(),
                password.getCreatedAt(),
                password.getUpdatedAt()
        );
    }
}