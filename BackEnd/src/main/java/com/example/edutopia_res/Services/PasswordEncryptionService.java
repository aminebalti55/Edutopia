package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IPasswordEncryptionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service

public class PasswordEncryptionService implements IPasswordEncryptionService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encrypt(String password) {
        return encoder.encode(password);
    }


    public String decrypt(String encryptedPassword, String password) {
        if (encoder.matches(password, encryptedPassword)) {
            return password;
        } else {
            return null;
        }
    }
}
