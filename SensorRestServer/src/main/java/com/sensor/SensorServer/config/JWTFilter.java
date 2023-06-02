package com.sensor.SensorServer.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensor.SensorServer.models.SensorUser;
import com.sensor.SensorServer.security.JWTUtil;
import com.sensor.SensorServer.security.SensorUserDetails;
import com.sensor.SensorServer.services.SensorUserDetailsService;
import com.sensor.SensorServer.utils.SensorUserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    private final SensorUserDetailsService sensorUserDetailsService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, SensorUserDetailsService sensorUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.sensorUserDetailsService = sensorUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token in Bearer Header");
            } else {
                try {
                    System.out.println("Before validate");
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    System.out.println("After validate");

                    SensorUserDetails sensorUserDetails = null;
                    try {
                        sensorUserDetails = (SensorUserDetails) sensorUserDetailsService.loadUserByUsername(username);
                    } catch (SensorUserException ex) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
                    }

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            sensorUserDetails,
                            sensorUserDetails.getPassword(),
                            sensorUserDetails.getAuthorities()
                    );

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException ex) {
                    System.out.println("Invalid JWT Token");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
