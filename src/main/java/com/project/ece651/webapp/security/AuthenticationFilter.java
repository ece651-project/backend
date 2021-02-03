package com.project.ece651.webapp.security;

import com.project.ece651.webapp.services.UserService;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// not used now, for JWT
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService usersService;
    private final Environment environment;

    public AuthenticationFilter(UserService usersService, Environment environment,
                                AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    /* TODO:
        1. JWT authentication
        2. add AuthorizationFilter
     */
}
