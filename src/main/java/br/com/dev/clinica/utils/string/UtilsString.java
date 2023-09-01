package br.com.dev.clinica.utils.string;

public class UtilsString {
    public static String generateRandomPassword(int length){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int i = 0;
        String password = "";

        while (i < length) {
            int randomIndex = (int) (Math.random() * characters.length());
            char randomChar = characters.charAt(randomIndex);
            password += randomChar;
            i++;
        }
        
        return password;
    }
}
