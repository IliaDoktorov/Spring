package com.sensor.SensorServer.services;

import com.sensor.SensorServer.models.SensorUser;
import com.sensor.SensorServer.repositories.AuthRepository;
import com.sensor.SensorServer.security.SensorUserDetails;
import com.sensor.SensorServer.utils.SensorUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SensorUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    @Autowired
    public SensorUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public UserDetails loadUserByUsername(String username){
        Optional<SensorUser> sensorUser = authRepository.findByUsername(username);
        if(sensorUser.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new SensorUserDetails(sensorUser.get());
    }
}
