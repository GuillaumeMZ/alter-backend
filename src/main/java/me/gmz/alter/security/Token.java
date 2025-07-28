package me.gmz.alter.security;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Random;

public class Token {
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generate() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < 128; i++) {
            stringBuilder.append(ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));
        }

        return stringBuilder.toString();
    }

    public static String extractFromHeader(String authorizationHeader) throws MissingTokenException {
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new MissingTokenException();
        }

        return authorizationHeader.substring(7);
    }
}
