package com.yuldashov.weathermvc.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordCheck {
    public boolean passwordVerification(String storerPassword, String enteredPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = passwordEncoder.matches(enteredPassword,storerPassword);
        return isPasswordMatch;
    }
}
