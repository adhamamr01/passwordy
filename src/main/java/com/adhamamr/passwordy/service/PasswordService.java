package com.adhamamr.passwordy.service;

public interface PasswordService {
    String generatePassword(int length, boolean includeNumbers, boolean includeSymbols);
}
