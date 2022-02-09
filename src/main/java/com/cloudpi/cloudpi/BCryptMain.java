package com.cloudpi.cloudpi;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptMain {

    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder(4);
        System.out.println(encoder.encode("P@ssword123"));
    }

}
