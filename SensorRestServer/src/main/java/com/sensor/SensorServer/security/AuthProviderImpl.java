package com.sensor.SensorServer.security;

import com.sensor.SensorServer.services.SensorUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final SensorUserDetailsService sensorUserDetailsService;

    @Autowired
    public AuthProviderImpl(SensorUserDetailsService sensorUserDetailsService) {
        this.sensorUserDetailsService = sensorUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails sensorUserDetails = sensorUserDetailsService.loadUserByUsername(username);

        if(!password.equals(sensorUserDetails.getPassword()))
            throw new BadCredentialsException("Wrong password");

        return new UsernamePasswordAuthenticationToken(sensorUserDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
