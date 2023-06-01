package com.sensor.SensorServer.security;

import com.sensor.SensorServer.models.SensorUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SensorUserDetails implements UserDetails {
    private SensorUser sensorUser;

    public SensorUserDetails(SensorUser sensorUser) {
        this.sensorUser = sensorUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return sensorUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sensorUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
