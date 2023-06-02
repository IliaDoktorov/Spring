package com.sensor.SensorServer.config;

import com.sensor.SensorServer.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    private final JWTFilter jwtFilter;

    private final AuthProviderImpl authProvider;

    @Autowired
    public WebSecurityConfiguration(JWTFilter jwtFilter, AuthProviderImpl authProvider) {
        this.jwtFilter = jwtFilter;
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http
                .getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(sensorUserDetailsService)
                        .authenticationProvider(authProvider)
                                .build();

//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authProvider);
////        authenticationManagerBuilder.userDetailsService(sensorUserDetailsService);
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/authentication/login").permitAll()
                        .anyRequest().authenticated())
                .authenticationManager(authenticationManager)
//                .userDetailsService(sensorUserDetailsService)
//                .authenticationProvider(authProvider)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//        http
//                .authenticationManager(authenticationManager);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
