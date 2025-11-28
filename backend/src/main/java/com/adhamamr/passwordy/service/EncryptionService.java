package com.adhamamr.passwordy.service;

public interface EncryptionService {
    String encrypt(String plainText) throws Exception;
    String decrypt(String encryptedText) throws Exception;
}