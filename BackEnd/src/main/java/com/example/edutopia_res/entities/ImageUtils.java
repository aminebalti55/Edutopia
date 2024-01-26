package com.example.edutopia_res.entities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtils {

    public static String encodeImageToBase64(String imagePath) throws Exception {
        Path path = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        String base64String = Base64.getEncoder().encodeToString(imageBytes);
        return base64String;
    }
}
