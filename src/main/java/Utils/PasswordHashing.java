package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {

    // Hash the password using SHA-256
    public static String hashPassword(String password) {
        try {
            // Get the MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Convert the password to byte array
            byte[] hashBytes = digest.digest(password.getBytes());
            
            // Convert byte array to hexadecimal format
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b)); // Convert each byte to hex
            }
            return hexString.toString();  // Return the hashed password in hex format
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;  // Return null if SHA-256 algorithm is not found
        }
    }

    // Verify the raw password against the hashed password
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        // Hash the raw password and compare it with the stored hash
        return hashPassword(rawPassword).equals(hashedPassword);
    }
}
