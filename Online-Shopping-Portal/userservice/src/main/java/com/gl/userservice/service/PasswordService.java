package com.gl.userservice.service;

import com.gl.userservice.config.PasswordConfig;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordConfig passwordConfig;
    private final Argon2 argon2;

    public PasswordService(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
        argon2 = getArgon2Instance();
    }

    public String securePassword(String password) {

        return argon2.hash(passwordConfig.getIterations(), passwordConfig.getMemory(), passwordConfig.getParrallelism(), password.toCharArray());

    }

    public boolean validatePassword(String hash, String userEnterPassword){
        return argon2.verify(hash, userEnterPassword.toCharArray());
    }
    private Argon2 getArgon2Instance() {
        Argon2Factory.Argon2Types type = Argon2Factory.Argon2Types.ARGON2d;
        if(passwordConfig.getType() == 1){
            type = Argon2Factory.Argon2Types.ARGON2i;
        }else if(passwordConfig.getType() == 2){
            type = Argon2Factory.Argon2Types.ARGON2id;
        }
        return Argon2Factory.create(type, passwordConfig.getSaltLength(), passwordConfig.getHashLength());
    }
}
