package com.adhamamr.passwordy.service;

import com.adhamamr.passwordy.dto.PasswordSaveRequest;
import com.adhamamr.passwordy.dto.PasswordResponse;
import java.util.List;

public interface PasswordService {
    String generatePassword(int length, boolean includeUppercase, boolean includeLowercase,
                            boolean includeNumbers, boolean includeSymbols);

    PasswordResponse savePassword(PasswordSaveRequest request, String username);  // Added username
    List<PasswordResponse> getAllPasswords(String username);  // Added username
    PasswordResponse getPasswordById(Long id, String username);  // Added username
    PasswordResponse updatePassword(Long id, PasswordSaveRequest request, String username);  // Added username
    void deletePassword(Long id, String username);  // Added username
}