package com.cloudpi.cloudpi;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptMain {

    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder(10);
        System.out.println(encoder.encode("Pajonk!234"));

        System.out.println(encoder.matches("Pajonk!23", "$2a$10$KOodFTwdSFXhMC1fRO9k1.IC3BoBBdcUzslLsVvSTBSiEtIwTXuka"));
    }

}
