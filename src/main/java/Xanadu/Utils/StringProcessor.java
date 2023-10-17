package Xanadu.Utils;

import java.util.Random;

public class StringProcessor {
    public static String generateRandomCharacters( Integer length, String excludedChars) {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~";
        allowedChars = allowedChars.replaceAll(excludedChars, "");
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
    public static String generateRandomCharacters( String fromStrings, Integer length) {

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(fromStrings.length());
            char randomChar = fromStrings.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
