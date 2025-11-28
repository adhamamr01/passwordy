package com.adhamamr.passwordy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AESEncryptionService implements EncryptionService {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int AES_KEY_BIT = 256;

    // In production, store this securely (environment variable, vault, etc.)
    // For now, we'll use a fixed key (32 bytes for AES-256)
    private static final String SECRET_KEY = "MySecretKey12345MySecretKey12345"; // 32 chars = 256 bits

    private final SecretKey secretKey;

    public AESEncryptionService() {
        // Convert the secret key string to SecretKey object
        byte[] keyBytes = SECRET_KEY.getBytes();
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    @Override
    public String encrypt(String plainText) throws Exception {
        // Generate random IV (Initialization Vector)
        byte[] iv = new byte[IV_LENGTH_BYTE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // Create cipher instance
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        // Encrypt the plaintext
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // Combine IV and encrypted data
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

        // Encode to Base64 for storage
        return Base64.getEncoder().encodeToString(combined);
    }

    @Override
    public String decrypt(String encryptedText) throws Exception {
        // Decode from Base64
        byte[] combined = Base64.getDecoder().decode(encryptedText);

        // Extract IV and encrypted data
        byte[] iv = new byte[IV_LENGTH_BYTE];
        byte[] encryptedBytes = new byte[combined.length - IV_LENGTH_BYTE];
        System.arraycopy(combined, 0, iv, 0, iv.length);
        System.arraycopy(combined, IV_LENGTH_BYTE, encryptedBytes, 0, encryptedBytes.length);

        // Create cipher instance
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        // Decrypt the data
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }
}