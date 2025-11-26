package com.adhamamr.passwordy.service;

import com.adhamamr.passwordy.dto.PasswordSaveRequest;
import com.adhamamr.passwordy.dto.PasswordResponse;
import java.util.List;

public interface PasswordService {
    String generatePassword(int length, boolean includeUppercase, boolean includeLowercase,
                            boolean includeNumbers, boolean includeSymbols);

    PasswordResponse savePassword(PasswordSaveRequest request);
    List<PasswordResponse> getAllPasswords();
    PasswordResponse getPasswordById(Long id);
    PasswordResponse updatePassword(Long id, PasswordSaveRequest request);
    void deletePassword(Long id);
}