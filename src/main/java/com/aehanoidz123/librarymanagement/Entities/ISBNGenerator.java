package com.aehanoidz123.librarymanagement.Entities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ISBNGenerator {
    public static String generateISBN(String title, String author) {
        String input = title + author;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder isbn = new StringBuilder();
            for (byte b : hash) {
                int num = (b & 0xff) % 10;
                isbn.append(num);
                if (isbn.length() >=13) break;
            }
            return isbn.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
