package com.gl.userservice.security;

import com.gl.userservice.exception.UserNotFoundException;
import com.gl.userservice.model.User;
import com.gl.userservice.repository.UserRepository;
import com.gl.userservice.service.PasswordService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UserPortalAuthProvider implements AuthenticationProvider {

    private final PasswordService passwordService;

    private final UserRepository userRepository;

    public UserPortalAuthProvider(PasswordService passwordService, UserRepository userRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UserNotFoundException("User not found with email - " + username);
        }

        if(passwordService.validatePassword(user.getPassword(), authentication.getCredentials().toString())){
            return new UsernamePasswordAuthenticationToken(username, authentication.getCredentials(), null);
        }
        throw new BadCredentialsException("Login failed..");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
