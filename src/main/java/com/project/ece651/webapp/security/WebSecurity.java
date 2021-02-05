package com.project.ece651.webapp.security;

import com.project.ece651.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment environment;

    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment environment) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // have to add this, otherwise 403 Forbidden
        http.csrf().disable();  // CSRF: Cross Site Request Forgery

        // have to add this to connect to h2-console
        http.headers().frameOptions().disable();

        // TODO: temporary. need to edit antMatchers rules
        // https://stackoverflow.com/questions/24696717/spring-security-permitall-not-allowing-anonymous-access
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/h2-console").permitAll()
                    .antMatchers("/user/add_user").permitAll()
//                    .anyRequest().authenticated() // for other URL: if not login, redirect to login
                    .and()
//                    .addFilter(getAuthenticationFilter()) // for JWT token, need to edit AuthenticationFilter.java if use JWT
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .permitAll());
//                .logout()
//                .logoutUrl("/user/logout")
//                .logoutSuccessUrl("/user/login");
    }

    // not used now, for JWT
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, authenticationManager());
//        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));  // application.properties file
        return authenticationFilter;
    }

    // set the password encoder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
