package com.example.edutopia_res.Iservices;

import javax.crypto.spec.SecretKeySpec;

public interface IPasswordEncryptionService {

    String encrypt(String password);
    String decrypt(String encryptedPassword, String password) ;
}
