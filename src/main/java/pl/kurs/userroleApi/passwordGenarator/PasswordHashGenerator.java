package pl.kurs.userroleApi.passwordGenarator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("hashed_password4");
        System.out.println(hashedPassword);
    }
}
